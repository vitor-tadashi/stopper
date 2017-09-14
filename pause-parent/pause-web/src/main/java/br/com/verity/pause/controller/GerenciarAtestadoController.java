package br.com.verity.pause.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.verity.pause.bean.AtestadoBean;
import br.com.verity.pause.business.AtestadoBusiness;
import br.com.verity.pause.exception.BusinessException;

@RestController
@RequestMapping(value = "/atestado")
public class GerenciarAtestadoController {
	
	@Autowired
	private AtestadoBusiness atestadoBusiness;
	
	@RequestMapping(value = "/inserir", method = RequestMethod.POST)
	public ResponseEntity<?> salvar(@RequestBody AtestadoBean atestado){
		AtestadoBean atestadoCriado = null;
		try {
			atestadoCriado = atestadoBusiness.salvar(atestado);
		} catch (BusinessException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(atestadoCriado);
	}
	@DeleteMapping(value = "/remover/{id}")
	public ResponseEntity<String> remover(@PathVariable Integer id){
		try {
			atestadoBusiness.remover(id);
		} catch (BusinessException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
