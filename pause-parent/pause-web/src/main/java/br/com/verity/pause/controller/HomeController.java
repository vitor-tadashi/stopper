package br.com.verity.pause.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.verity.pause.business.EmpresaBusiness;

@Controller
public class HomeController {
	
	@Autowired
	private EmpresaBusiness empresaBusiness;
	

	@RequestMapping(value= {"","/","/home"}, method = RequestMethod.GET)
    public String teste() {
		
		empresaBusiness.obterTodos();
		
        return "home/index";
    } 
}
