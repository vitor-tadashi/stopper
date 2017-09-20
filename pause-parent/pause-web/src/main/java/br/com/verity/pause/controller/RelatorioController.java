package br.com.verity.pause.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.business.FuncionarioBusiness;
import br.com.verity.pause.business.RelatorioBusiness;

@Controller
@RequestMapping(value = "/relatorio")
public class RelatorioController {

	@Autowired
	private FuncionarioBusiness funcionarioBusiness;
	
	@Autowired
	private RelatorioBusiness relatorioBusiness;

	@RequestMapping(method = RequestMethod.GET)
	public String loginPage(Model model) {
		List<FuncionarioBean> funcionarios = funcionarioBusiness.obterTodos();
		model.addAttribute("funcionarios", funcionarios);
		return "relatorio/relatorio";
	}

	@RequestMapping(value = "gerar-relatorio", method = RequestMethod.POST)
	@ResponseBody
	public String gerarRelatorio(Integer idFuncionario, String de, String ate, HttpServletResponse response) {
		String caminho;
		caminho = relatorioBusiness.gerarRelatorio(idFuncionario, de, ate);
		return caminho;
	}

	@RequestMapping(value = "download", method = RequestMethod.GET)
	public void download(String caminho, HttpServletResponse response) {
		String[] nome = caminho.split("\\\\");
		int tamanho = nome.length - 1;
		try {
			File file = new File(caminho);
			response.addHeader("Content-Disposition", "attachment; filename=" + nome[tamanho]);
			InputStream is = new FileInputStream(file);
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

			is.close();
			file.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}