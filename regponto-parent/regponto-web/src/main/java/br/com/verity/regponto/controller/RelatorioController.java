package br.com.verity.regponto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/relatorio")
public class RelatorioController {

	@RequestMapping(method = RequestMethod.GET)
	public String loginPage() {
		return "relatorio/relatorio";
	}
}
