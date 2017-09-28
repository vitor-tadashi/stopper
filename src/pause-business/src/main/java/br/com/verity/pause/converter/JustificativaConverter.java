package br.com.verity.pause.converter;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.TipoJustificativaBean;
import br.com.verity.pause.entity.TipoJustificativaEntity;

@Component
public class JustificativaConverter implements Converter<TipoJustificativaEntity, TipoJustificativaBean> {

	@Override
	public TipoJustificativaEntity convertBeanToEntity(TipoJustificativaBean bean) {
		if (bean == null) {
			return null;
		}
		TipoJustificativaEntity entity = new TipoJustificativaEntity();
		
		entity.setDescricao(bean.getDescricao());
		entity.setId(bean.getId());
		
		return entity;
	}

	@Override
	public TipoJustificativaBean convertEntityToBean(TipoJustificativaEntity entity) {
		if (entity == null) {
			return null;
		}
		TipoJustificativaBean bean = new TipoJustificativaBean();
		
		bean.setDescricao(entity.getDescricao());
		bean.setId(entity.getId());
		
		return bean;
	}

}
