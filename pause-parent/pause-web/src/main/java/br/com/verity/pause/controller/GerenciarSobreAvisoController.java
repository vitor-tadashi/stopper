package br.com.verity.pause.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.verity.pause.bean.SobreAvisoBean;
import br.com.verity.pause.business.SobreAvisoBusiness;

@RestController
@RequestMapping(value = "/sobre-aviso")
public class GerenciarSobreAvisoController {
	
	@Autowired
	private SobreAvisoBusiness sobreAvisoBusiness;
	
	@RequestMapping(value = "/inserir-sa", method = RequestMethod.POST)
	public ResponseEntity<String> salvar(@RequestBody SobreAvisoBean sobreAviso){
		sobreAvisoBusiness.salvar(sobreAviso);
		
		return new ResponseEntity<String>("ok", HttpStatus.OK) ;
	}
	@RequestMapping(value = "/remover-sa", method = RequestMethod.DELETE)
	public ResponseEntity<String> remover(Integer id){
		sobreAvisoBusiness.remover(id);
		
		return new ResponseEntity<String>("ok", HttpStatus.OK) ;
	}
}
