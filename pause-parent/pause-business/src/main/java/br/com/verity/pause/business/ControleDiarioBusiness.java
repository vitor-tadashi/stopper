package br.com.verity.pause.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ControleDiarioBean;
import br.com.verity.pause.converter.ControleDiarioConverter;
import br.com.verity.pause.dao.ControleDiarioDAO;
import br.com.verity.pause.entity.ControleDiarioEntity;

@Service
public class ControleDiarioBusiness {

	@Autowired
	private ControleDiarioDAO controleDiarioDAO;
	
	@Autowired
	private ControleDiarioConverter controleDiarioConverter;

	public ControleDiarioBean obterPorData(Date data) {
		ControleDiarioBean bean = new ControleDiarioBean();
		
		ControleDiarioEntity entity = controleDiarioDAO.findByData(new java.sql.Date(data.getTime()));
		if(entity != null) {
			bean = controleDiarioConverter.convertEntityToBean(entity);
			return bean;
		}else {
			bean.setData(data);
			inserir(bean);
		}
		return obterPorData(data);
	}

	public void inserir(ControleDiarioBean bean) {
		ControleDiarioEntity entity = controleDiarioConverter.convertBeanToEntity(bean);
		
		controleDiarioDAO.save(entity);
	}

}
