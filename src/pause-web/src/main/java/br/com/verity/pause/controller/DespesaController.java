package br.com.verity.pause.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.verity.pause.bean.DespesaBean;
import br.com.verity.pause.business.TipoDespesaBusiness;

@Controller
@RequestMapping(value = "/despesa")
public class DespesaController {
	
	@Autowired
	TipoDespesaBusiness business;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView acessar(Model model) {
		
		model.addAttribute("tipoDespesas", business.findAll());
		return new ModelAndView("despesa/despesa");
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> salvar(@RequestBody DespesaBean despesa) {
		
		return ResponseEntity.ok("Salvo com sucesso!");
	}
}
