package br.com.verity.pause.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.business.ImportacaoBusiness;
import br.com.verity.pause.exception.BusinessException;

@Controller
@RequestMapping(value = "/importacao")
public class ImportacaoController {

	@Autowired
	private ImportacaoBusiness importacaoBusiness;

	@Autowired
	private List<FuncionarioBean> funcionariosImportacao;
	
	private Authentication auth;
	private UsuarioBean usuarioLogado;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(2048);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String acessar() {
		auth = SecurityContextHolder.getContext().getAuthentication();
		usuarioLogado = (UsuarioBean) auth.getPrincipal();
		return "importacao/importacao";
	}

	@ResponseBody
	@RequestMapping(value = "importar-arquivo", method = RequestMethod.POST)
	public List<FuncionarioBean> importarArquivo(MultipartHttpServletRequest request, Model model) {
		List<MultipartFile> arquivo = request.getFiles("file");
		String caminho = "";
		funcionariosImportacao = new ArrayList<FuncionarioBean>();
		FuncionarioBean funcionario = new FuncionarioBean();
		try {
			caminho = this.salvarTxt(arquivo, usuarioLogado.getFuncionario().getEmpresa().getRazaoSocial());
			funcionariosImportacao = importacaoBusiness.importarTxt(caminho,
					usuarioLogado.getFuncionario().getEmpresa().getId());
		} catch (BusinessException e) {
			this.cancelar(caminho, model);
			funcionario.setMensagem("Arquivo contém mais de uma data.");
			funcionariosImportacao.add(funcionario);
			return funcionariosImportacao;
		} catch (ParseException | IOException e) {
			this.cancelar(caminho, model);
			funcionario.setMensagem("Não foi possível abrir o arquivo.");
			funcionariosImportacao.add(funcionario);
			return funcionariosImportacao;
		}
		return funcionariosImportacao;
	}

	@ResponseBody
	@RequestMapping(value = "cancelar/{arquivo}/", method = RequestMethod.POST)
	public void cancelar(@PathVariable String arquivo, Model model) {
		String directory = "C:" + File.separator + "Pause" + File.separator + "importacao" + File.separator
				+ usuarioLogado.getFuncionario().getEmpresa().getRazaoSocial() + File.separator + arquivo;
		File file = new File(directory);
		file.delete();
	}

	@RequestMapping(value = "salvar", method = RequestMethod.POST)
	public String salvar(RedirectAttributes redirect) {
		List<ApontamentoBean> apontamentos = new ArrayList<ApontamentoBean>();

		if (funcionariosImportacao.get(0).getId() == null) {
			funcionariosImportacao.remove(0);
		}

		for (FuncionarioBean funcionarioBean : funcionariosImportacao) {
			apontamentos.addAll(funcionarioBean.getApontamentos());
		}

		importacaoBusiness.salvarApontamentos(apontamentos);

		redirect.addFlashAttribute("log", "Apontamentos salvos com sucesso.");

		return "redirect:/importacao";
	}

	public String salvarTxt(List<MultipartFile> multipartFiles, String nome) throws IOException {
		String arquivo = null;
		String directory = "C:" + File.separator + "Pause" + File.separator + "importacao" + File.separator + nome
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