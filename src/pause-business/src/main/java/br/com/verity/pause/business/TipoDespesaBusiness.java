package br.com.verity.pause.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.dao.TipoDespesaDAO;
import br.com.verity.pause.entity.TipoDespesaEntity;

@Service
public class TipoDespesaBusiness {
	
	@Autowired
	TipoDespesaDAO dao;
	
	public List<TipoDespesaEntity> findAll() {
		
		List<TipoDespesaEntity> entities = dao.findAll();
		
		return entities;
	}
}
