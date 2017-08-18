package br.com.verity.regponto.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import br.com.verity.regponto.bean.EmpresaBean;
import br.com.verity.regponto.bean.FuncionarioBean;
import br.com.verity.regponto.business.EmpresaBusiness;
import br.com.verity.regponto.business.ImportacaoBusiness;

@Controller
@RequestMapping(value = "/importacao")
public class ImportacaoController {

	@Autowired
	private EmpresaBusiness empresaBusiness;
	
	@Autowired
	private ImportacaoBusiness importacaoBusiness;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String acessar(Model model) {
		List<EmpresaBean> empresas = empresaBusiness.obterTodos();
		model.addAttribute("empresas", empresas);
		return "importacao/importacao";
	}

	@ResponseBody
	@RequestMapping(value = "importar-arquivo", method = RequestMethod.POST)
	public List<FuncionarioBean> importarArquivo(MultipartHttpServletRequest request, Model model) {
		List<MultipartFile> arquivo = request.getFiles("file");
		List<FuncionarioBean> funcionario = new ArrayList<FuncionarioBean>();
		try {
			String caminho = this.salvarTxt(arquivo);
			funcionario = importacaoBusiness.importarTxt(caminho);
		} catch (Exception e) {
			return null;
		}
		return funcionario;
	}

	public String salvarTxt(List<MultipartFile> multipartFiles) throws IOException {
		String arquivo = null;
		String directory = "C:/";
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