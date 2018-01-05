package br.com.verity.pause.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.verity.pause.dao.TipoDespesaDAO;

@Controller
@RequestMapping(value = "/despesa")
public class DespesaController {
	
	@Autowired
	TipoDespesaDAO dao;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String acessar() {
		
		return "despesa/despesa";
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> salvar() {
		
		return ResponseEntity.ok("Salvo com sucesso!");
	}
}
