package br.com.verity.pause.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/consultar-apontamento")
public class ConsultarApontamento {

	@RequestMapping(method = RequestMethod.GET)
	public String consultar(Model model) {
		
		return "apontamento/consultar";
	}
}
