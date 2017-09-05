package br.com.verity.pause.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.business.FuncionarioBusiness;

@Controller
@RequestMapping(value = "/relatorio")
public class RelatorioController {
	
	@Autowired
	private FuncionarioBusiness funcionarioBusiness;

	@RequestMapping(method = RequestMethod.GET)
	public String loginPage(Model model) {
		List<FuncionarioBean> funcionarios = funcionarioBusiness.obterTodos();
		model.addAttribute("funcionarios", funcionarios);
		return "relatorio/relatorio";
	}
	
	@ResponseBody
	@RequestMapping(value = "gerar-relatorio", method = RequestMethod.POST)
	public void gerarRelatorio(Integer idFuncionario, String de, String ate) {
		funcionarioBusiness.gerarRelatorio(idFuncionario, de, ate);
	}
}
