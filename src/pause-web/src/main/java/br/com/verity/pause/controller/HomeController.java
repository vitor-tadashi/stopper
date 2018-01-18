package br.com.verity.pause.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@RequestMapping(value= {"/home"}, method = RequestMethod.GET)
    public String home() {
        return "home/index";
    }

	@RequestMapping(value= {"/403"}, method = RequestMethod.GET)
    public String erro403() {
        return "error/403";
    }
}
