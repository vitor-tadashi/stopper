package br.com.verity.pause.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/despesa")
public class DespesaController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String acessar() {
		return "despesa/despesa";
	}

}
