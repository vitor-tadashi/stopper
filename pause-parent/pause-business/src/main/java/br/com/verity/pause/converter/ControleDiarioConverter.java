package br.com.verity.pause.converter;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.ControleDiarioBean;
import br.com.verity.pause.entity.ControleDiarioEntity;

@Component
public class ControleDiarioConverter implements Converter<ControleDiarioEntity, ControleDiarioBean> {

	@Override
	public ControleDiarioEntity convertBeanToEntity(ControleDiarioBean bean) {
		if (bean == null) {
			return null;
		}
		ControleDiarioEntity entity = new ControleDiarioEntity();
		
		entity.setId(bean.getId());
		entity.setData(new java.sql.Date(bean.getData().getTime()));
		entity.setIdFuncionario(bean.getIdFuncionario());
		
		return entity;
	}

	@Override
	public ControleDiarioBean convertEntityToBean(ControleDiarioEntity entity) {
		if (entity == null) {
			return null;
		}
		
		ControleDiarioBean bean = new ControleDiarioBean();
		
		bean.setId(entity.getId());
		bean.setData(entity.getData());
		bean.setIdFuncionario(entity.getIdFuncionario());
		
		return bean;
	}

}