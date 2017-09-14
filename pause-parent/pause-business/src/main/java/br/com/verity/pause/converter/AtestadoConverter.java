package br.com.verity.pause.converter;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.AtestadoBean;
import br.com.verity.pause.entity.AtestadoEntity;

@Component
public class AtestadoConverter implements Converter<AtestadoEntity, AtestadoBean>{

	@Override
	public AtestadoEntity convertBeanToEntity(AtestadoBean bean) {
		if (bean == null) {
			return null;
		}
		AtestadoEntity entity = new AtestadoEntity();
		
		entity.setIdAtestado(bean.getId());
		entity.setQuantidadeHora(bean.getQuantidadeHora());
		entity.setDataInclusao(new java.sql.Date(bean.getDataInclusao().getTime()));
		entity.setIdUsuarioInclusao(bean.getIdUsuarioInclusao());
		
		return entity;
	}

	@Override
	public AtestadoBean convertEntityToBean(AtestadoEntity entity) {
		if (entity == null) {
			return null;
		}
		AtestadoBean bean = new AtestadoBean();
		
		bean.setId(entity.getIdAtestado());
		bean.setDataInclusao(entity.getDataInclusao());
		bean.setIdUsuarioInclusao(entity.getIdUsuarioInclusao());
		bean.setQuantidadeHora(entity.getQuantidadeHora());
		
		return bean;
	}

}
