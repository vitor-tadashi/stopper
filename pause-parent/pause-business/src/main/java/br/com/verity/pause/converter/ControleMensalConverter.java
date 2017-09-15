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
		entity.setAdcNoturno(bean.getAdicionalNoturno());
		entity.setBancoHora(bean.getBancoHora());
		entity.setHrTotal(bean.getHoraTotal());
		entity.setSobreAviso(bean.getSobreAviso());
		entity.setSobreAvisoTrabalhado(bean.getSobreAvisoTrabalhado());
		entity.setMes(bean.getMes());
		entity.setAno(bean.getAno());
		entity.setIdFuncionario(bean.getIdFuncionario());
		
		return entity;
	}

	@Override
	public ControleMensalBean convertEntityToBean(ControleMensalEntity entity) {
		if (entity == null) {
			return null;
		}
		ControleMensalBean bean = new ControleMensalBean();
		
		bean.setId(entity.getId());
		bean.setAdicionalNoturno(entity.getAdcNoturno());
		bean.setBancoHora(entity.getBancoHora());
		bean.setHoraTotal(entity.getHrTotal());
		bean.setSobreAviso(entity.getSobreAviso());
		bean.setSobreAvisoTrabalhado(entity.getSobreAvisoTrabalhado());
		bean.setMes(entity.getMes());
		bean.setAno(entity.getAno());
		bean.setIdFuncionario(entity.getIdFuncionario());
		
		
		return bean;
	}

}
