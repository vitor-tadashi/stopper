package br.com.verity.pause.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.bean.ArquivoApontamentoBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.business.ControleDiarioBusiness;
import br.com.verity.pause.business.ControleMensalBusiness;
import br.com.verity.pause.business.CustomUserDetailsBusiness;
import br.com.verity.pause.business.ImportacaoBusiness;
import br.com.verity.pause.exception.BusinessException;

@Controller
@RequestMapping(value = "/importacao")
public class ImportacaoController {

	@Autowired
	private ImportacaoBusiness importacaoBusiness;

	@Autowired
	private List<FuncionarioBean> funcionariosImportacao;

	@Autowired
	private ArquivoApontamentoBean arquivoApontamento;

	@Autowired
	private CustomUserDetailsBusiness customUser;

	@Autowired
	private ControleDiarioBusiness controleDiarioBusiness;
	
	@Autowired
	private ControleMensalBusiness controleMensalBusiness;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(2048);
	}

	@PreAuthorize("hasRole('ROLE_IMPORTAR_APONTAMENTOS')")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String acessar() {
		return "importacao/importacao";
	}

	@PreAuthorize("hasRole('ROLE_IMPORTAR_APONTAMENTOS')")
	@ResponseBody
	@RequestMapping(value = "importar-arquivo", method = RequestMethod.POST)
	public List<FuncionarioBean> importarArquivo(MultipartHttpServletRequest request, Model model) {
		UsuarioBean usuarioLogado = customUser.usuarioLogado();
		List<MultipartFile> arquivo = request.getFiles("file");
		String caminho = "";
		funcionariosImportacao = new ArrayList<FuncionarioBean>();
		FuncionarioBean funcionario = new FuncionarioBean();
		arquivoApontamento = new ArquivoApontamentoBean();
		try {
			caminho = this.salvarTxt(arquivo, usuarioLogado.getIdEmpresaSessao());
			arquivoApontamento.setCaminho(caminho);
			arquivoApontamento.setDtInclusao(new Date());
			arquivoApontamento.setIdUsuarioInclusao(usuarioLogado.getId());
			arquivoApontamento.setIdEmpresa(usuarioLogado.getFuncionario().getEmpresa().getId());
			funcionariosImportacao = importacaoBusiness.importarTxt(caminho, usuarioLogado.getIdEmpresaSessao());
			arquivoApontamento.setData(
					funcionariosImportacao.get(funcionariosImportacao.size() - 1).getApontamentos().get(0).getData());
		} catch (BusinessException | ParseException | IOException e) {
			this.cancelar(caminho, model);
			funcionario.setMensagem(e.getMessage());
			funcionariosImportacao.add(funcionario);
			return funcionariosImportacao;
		}
		return funcionariosImportacao;
	}

	@PreAuthorize("hasRole('ROLE_IMPORTAR_APONTAMENTOS')")
	@ResponseBody
	@RequestMapping(value = "cancelar/{arquivo}/", method = RequestMethod.POST)
	public void cancelar(@PathVariable String arquivo, Model model) {
		UsuarioBean usuarioLogado = customUser.usuarioLogado();
		String directory = "C:" + File.separator + "Pause" + File.separator + "importacao" + File.separator
				+ usuarioLogado.getFuncionario().getEmpresa().getRazaoSocial() + File.separator + arquivo;
		File file = new File(directory);
		file.delete();
	}

	@PreAuthorize("hasRole('ROLE_IMPORTAR_APONTAMENTOS')")
	@RequestMapping(value = "salvar", method = RequestMethod.POST)
	public String salvar(RedirectAttributes redirect) {
		List<ApontamentoBean> apontamentos = new ArrayList<ApontamentoBean>();

		if (funcionariosImportacao.get(0).getId() == null) {
			funcionariosImportacao.remove(0);
		}

		for (FuncionarioBean funcionarioBean : funcionariosImportacao) {
			funcionarioBean.getApontamentos().get(0).setCntrDiario(controleDiarioBusiness.obterPorDataIdFuncionario(
					funcionarioBean.getApontamentos().get(funcionarioBean.getApontamentos().size() - 1).getData(),
					funcionarioBean.getId()));
			apontamentos.addAll(funcionarioBean.getApontamentos());
		}

		importacaoBusiness.salvarApontamentos(apontamentos, arquivoApontamento);

		importacaoBusiness.acionarCalculos(funcionariosImportacao);

		redirect.addFlashAttribute("log", "Apontamentos salvos com sucesso.");

		return "redirect:/importacao";
	}

	@PreAuthorize("hasRole('ROLE_IMPORTAR_APONTAMENTOS')")
	public String salvarTxt(List<MultipartFile> multipartFiles, Integer idEmpresa) throws IOException {
		String arquivo = null;
		String directory = "C:" + File.separator + "Pause" + File.separator + "importacao" + File.separator + idEmpresa
				+ File.separator;
		File file = new File(directory);
		file.mkdirs();
		for (MultipartFile multipartFile : multipartFiles) {
			file = new File(directory + multipartFile.getOriginalFilename());
			IOUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));
			arquivo = directory + multipartFile.getOriginalFilename();
		}
		return arquivo;
	}
}