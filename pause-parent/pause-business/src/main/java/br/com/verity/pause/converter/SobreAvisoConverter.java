package br.com.verity.pause.converter;

import java.sql.Time;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.SobreAvisoBean;
import br.com.verity.pause.entity.SobreAvisoEntity;

@Component
public class SobreAvisoConverter implements Converter<SobreAvisoEntity, SobreAvisoBean>{

	@Override
	public SobreAvisoEntity convertBeanToEntity(SobreAvisoBean bean) {
		if (bean == null) {
			return null;
		}
		SobreAvisoEntity entity = new SobreAvisoEntity();
		
		entity.setId(bean.getId());
		entity.setData(new java.sql.Date(bean.getData().getTime()));
		entity.setDataInclusao(new java.sql.Date(bean.getDataInclusao().getTime()));
		entity.setHoraInicio(Time.valueOf(bean.getEntrada()));
		entity.setHoraFim(Time.valueOf(bean.getSaida()));
		entity.setIdUsuarioInclusao(bean.getIdUsuarioInclusao());
		
		return entity;
	}

	@Override
	public SobreAvisoBean convertEntityToBean(SobreAvisoEntity entity) {
		if (entity == null) {
			return null;
		}
		SobreAvisoBean bean = new SobreAvisoBean();
		
		bean.setId(entity.getId());
		bean.setData(entity.getData());
		bean.setDataInclusao(entity.getDataInclusao());
		bean.setEntrada(entity.getHoraInicio().toLocalTime());
		bean.setSaida(entity.getHoraFim().toLocalTime());
		bean.setIdUsuarioInclusao(entity.getIdUsuarioInclusao());
		
		return bean;
	}

}
