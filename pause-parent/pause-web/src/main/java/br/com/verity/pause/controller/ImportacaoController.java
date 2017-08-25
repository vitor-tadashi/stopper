package br.com.verity.pause.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.HorasListWrapperBean;
import br.com.verity.pause.business.ImportacaoBusiness;
import br.com.verity.pause.exception.BusinessException;

@Controller
@RequestMapping(value = "/importacao")
public class ImportacaoController {

	@Autowired
	private ImportacaoBusiness importacaoBusiness;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.setAutoGrowCollectionLimit(1024);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String acessar(ModelMap model) {
		
		model.addAttribute("horas", new HorasListWrapperBean());
		
		return "importacao/teste";
	}

	@ResponseBody
	@RequestMapping(value = "importar-arquivo/{empresa}", method = RequestMethod.POST)
	public List<FuncionarioBean> importarArquivo(@PathVariable String empresa, MultipartHttpServletRequest request,
			Model model) {
		List<MultipartFile> arquivo = request.getFiles("file");
		List<FuncionarioBean> funcionario = new ArrayList<FuncionarioBean>();
		try {
			String caminho = this.salvarTxt(arquivo);
			funcionario = importacaoBusiness.importarTxt(caminho, empresa);
		} catch (BusinessException e) {
			return null;
		}catch (IOException e) {
			return funcionario;
		}
		return funcionario;
	}
	
	@RequestMapping(value = "salvar", method = RequestMethod.POST)
	@ResponseBody
	public String salvar(@ModelAttribute("horas") HorasListWrapperBean horas, BindingResult result) {
		importacaoBusiness.salvarHoras(horas.getHoras());
		return "/importacao/teste";
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