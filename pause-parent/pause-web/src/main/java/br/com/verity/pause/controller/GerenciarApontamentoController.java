package br.com.verity.pause.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.business.ApontamentoBusiness;

@Controller
@RequestMapping(value = "/gerenciar-apontamento")
public class GerenciarApontamentoController {
	
	@Autowired
	private ApontamentoBusiness apontamentoBusiness;
	
	@RequestMapping(method = RequestMethod.GET)
	public String consultar(Model model) {
		
		return "apontamento/gerenciar";
	}
	@RequestMapping(value = "/apontar", method = RequestMethod.POST)
	public void apontar(@RequestBody ApontamentoBean apontamento){
		apontamentoBusiness.apontar(apontamento);
	}
}
