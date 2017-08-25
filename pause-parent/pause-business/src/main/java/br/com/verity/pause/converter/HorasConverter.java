package br.com.verity.pause.converter;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.HorasBean;
import br.com.verity.pause.entity.HorasEntity;

@Component
public class HorasConverter implements Converter<HorasEntity, HorasBean> {

	@Override
	public HorasEntity convertBeanToEntity(HorasBean bean) {
		if (bean == null) {
			return null;
		}

		HorasEntity entity = new HorasEntity();

		entity.setId(bean.getId());
		entity.setPis(bean.getPis());
		entity.setHora(bean.getHora());
		entity.setDtImportacao(bean.getDataImportacao());
		entity.setTpApontamento(bean.getTipoApontamento());

		return entity;
	}

	@Override
	public HorasBean convertEntityToBean(HorasEntity entity) {
		if (entity == null) {
			return null;
		}

		HorasBean bean = new HorasBean();

		bean.setId(entity.getId());
		bean.setDataImportacao(entity.getDtImportacao());
		bean.setHora(entity.getHora());
		bean.setPis(entity.getPis());
		bean.setTipoApontamento(entity.getTpApontamento());

		return bean;
	}

}
