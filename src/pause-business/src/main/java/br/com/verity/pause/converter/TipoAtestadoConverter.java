package br.com.verity.pause.converter;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.TipoAtestadoBean;
import br.com.verity.pause.entity.TipoAtestadoEntity;

@Component
public class TipoAtestadoConverter implements Converter<TipoAtestadoEntity, TipoAtestadoBean>{

	@Override
	public TipoAtestadoEntity convertBeanToEntity(TipoAtestadoBean bean) {
		if (bean == null) {
			return null;
		}
		TipoAtestadoEntity entity = new TipoAtestadoEntity();
		
		entity.setIdTipoAtestado(bean.getId());
		entity.setDescricao(bean.getDescricao());
		
		return entity;
	}

	@Override
	public TipoAtestadoBean convertEntityToBean(TipoAtestadoEntity entity) {
		if (entity == null) {
			return null;
		}
		TipoAtestadoBean bean = new TipoAtestadoBean();
		
		bean.setDescricao(entity.getDescricao());
		bean.setId(entity.getIdTipoAtestado());
		
		return bean;
	}

}
