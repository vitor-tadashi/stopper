package br.com.verity.pause.converter;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.ControleMensalBean;
import br.com.verity.pause.entity.ControleMensalEntity;

@Component
public class ControleMensalConverter implements Converter<ControleMensalEntity, ControleMensalBean>{

	@Override
	public ControleMensalEntity convertBeanToEntity(ControleMensalBean bean) {
		if (bean == null) {
			return null;
		}
		ControleMensalEntity entity = new ControleMensalEntity();
		
		entity.setId(bean.getId());
		entity.setAno(bean.getAno());
		entity.setIdFuncionario(bean.getIdFuncionario());
		entity.setMes(bean.getMes());
		
		return entity;
	}

	@Override
	public ControleMensalBean convertEntityToBean(ControleMensalEntity entity) {
		if (entity == null) {
			return null;
		}
		ControleMensalBean bean = new ControleMensalBean();
		
		bean.setId(entity.getId());
		bean.setAno(entity.getAno());
		bean.setIdFuncionario(entity.getIdFuncionario());
		bean.setMes(entity.getMes());
		
		return bean;
	}

}
