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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.verity.pause.bean.AfastamentoBean;
import br.com.verity.pause.business.AfastamentoBusiness;
import br.com.verity.pause.exception.BusinessException;

@RestController
@RequestMapping(value = "/afastamento")
public class GerenciarAfastamentoController {
	@Autowired
	private AfastamentoBusiness afastamentoBusiness;
	
	@PreAuthorize("hasRole('ROLE_INSERIR_AFASTAMENTO')")
	@PostMapping(value = "/inserir")
	public ResponseEntity<?> salvar(@RequestBody AfastamentoBean afastamento){
		AfastamentoBean afastamentoCriado = null;
		try {
			afastamentoCriado = afastamentoBusiness.salvar(afastamento);
		} catch (BusinessException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(afastamentoCriado);
	}
	@PreAuthorize("hasRole('ROLE_REMOVER_AFASTAMENTO')")
	@DeleteMapping(value = "/remover/{id}")
	public ResponseEntity<String> remover(@PathVariable Integer id){
		try {
			afastamentoBusiness.remover(id);
		} catch (BusinessException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
