package br.com.verity.pause.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.verity.pause.bean.SobreAvisoBean;
import br.com.verity.pause.business.SobreAvisoBusiness;
import br.com.verity.pause.exception.BusinessException;

@RestController
@RequestMapping(value = "/sobre-aviso")
public class GerenciarSobreAvisoController {
	
	@Autowired
	private SobreAvisoBusiness sobreAvisoBusiness;
	
	@PreAuthorize("hasRole('ROLE_INSERIR_SOBRE_AVISO')")
	@PostMapping(value = "/inserir-sa")
	public ResponseEntity<?> salvar(@RequestBody SobreAvisoBean sobreAviso){
		SobreAvisoBean sobreAvisoCriado = null;
		try {
			
			sobreAvisoCriado = sobreAvisoBusiness.salvar(sobreAviso);
			
			
		} catch (BusinessException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok(sobreAvisoCriado);
	}
	
	@PreAuthorize("hasRole('ROLE_REMOVER_SOBRE_AVISO')")
	@DeleteMapping(value = "/remover/{id}")
	public ResponseEntity<String> remover(@PathVariable Integer id){
		
		try {
			
			sobreAvisoBusiness.remover(id);
			
		} catch (BusinessException e) {
			
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
			
		}
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}
}
