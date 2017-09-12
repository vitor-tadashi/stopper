package br.com.verity.pause.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.business.FuncionarioBusiness;

@Controller
@RequestMapping(value = "/consultar-apontamento")
public class ConsultarApontamento {
	
	@Autowired
	private FuncionarioBusiness funcionarioBusiness;

	@RequestMapping(method = RequestMethod.GET)
	public String consultar(Model model) {
		List<FuncionarioBean> funcionarios = funcionarioBusiness.obterTodos();
		model.addAttribute("funcionarios", funcionarios);
		
		
		return "apontamento/consultar";
	}
}