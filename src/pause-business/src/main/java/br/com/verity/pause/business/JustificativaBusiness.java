package br.com.verity.pause.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.TipoJustificativaBean;
import br.com.verity.pause.converter.JustificativaConverter;
import br.com.verity.pause.dao.JustificativaDAO;
import br.com.verity.pause.entity.TipoJustificativaEntity;

@Service
public class JustificativaBusiness {
	
	@Autowired
	private JustificativaDAO justificativaDAO;
	
	@Autowired
	private JustificativaConverter justificativaConverter;

	public List<TipoJustificativaBean> listar() {
		List<TipoJustificativaEntity> entities = justificativaDAO.findAll();
		List<TipoJustificativaBean> beans = justificativaConverter.convertEntityToBean(entities);
		
		return beans;
	}

}
