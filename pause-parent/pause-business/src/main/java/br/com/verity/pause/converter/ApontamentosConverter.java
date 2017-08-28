package br.com.verity.pause.converter;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.ApontamentosBean;
import br.com.verity.pause.entity.ApontamentosEntity;

@Component
public class ApontamentosConverter implements Converter<ApontamentosEntity, ApontamentosBean> {

	@Override
	public ApontamentosEntity convertBeanToEntity(ApontamentosBean bean) {
		if (bean == null) {
			return null;
		}

		ApontamentosEntity entity = new ApontamentosEntity();

		entity.setId(bean.getId());
		entity.setPis(bean.getPis());
		entity.setHora(bean.getHora());
		entity.setData(bean.getData());
		entity.setTpApontamento(bean.getTipoApontamento());
		entity.setEmpresa(bean.getEmpresa());

		return entity;
	}

	@Override
	public ApontamentosBean convertEntityToBean(ApontamentosEntity entity) {
		if (entity == null) {
			return null;
		}

		ApontamentosBean bean = new ApontamentosBean();

		bean.setId(entity.getId());
		bean.setData(entity.getData());
		bean.setHora(entity.getHora());
		bean.setPis(entity.getPis());
		bean.setTipoApontamento(entity.getTpApontamento());
		bean.setEmpresa(entity.getEmpresa());

		return bean;
	}

}
