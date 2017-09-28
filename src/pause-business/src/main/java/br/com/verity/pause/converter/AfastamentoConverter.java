package br.com.verity.pause.converter;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.AfastamentoBean;
import br.com.verity.pause.entity.AfastamentoEntity;

@Component
public class AfastamentoConverter implements Converter<AfastamentoEntity, AfastamentoBean> {

	@Override
	public AfastamentoEntity convertBeanToEntity(AfastamentoBean bean) {
		if (bean == null) {
			return null;
		}
		AfastamentoEntity entity = new AfastamentoEntity();
		
		entity.setIdAfastamento(bean.getId());
		entity.setDataFim(new java.sql.Date(bean.getDataFim().getTime()));
		entity.setDataInicio(new java.sql.Date(bean.getDataInicio().getTime()));
		entity.setDataInclusao(new java.sql.Date(bean.getDataInclusao().getTime()));
		entity.setIdUsuarioInclusao(bean.getIdUsuarioInclusao());
		entity.setIdFuncionario(bean.getIdFuncionario());
		
		return entity;
	}

	@Override
	public AfastamentoBean convertEntityToBean(AfastamentoEntity entity) {
		if (entity == null) {
			return null;
		}
		AfastamentoBean bean = new AfastamentoBean();
		
		bean.setDataFim(entity.getDataFim());
		bean.setDataInclusao(entity.getDataInclusao());
		bean.setDataInicio(entity.getDataInicio());
		bean.setId(entity.getIdAfastamento());
		bean.setIdFuncionario(entity.getIdFuncionario());
		bean.setIdUsuarioInclusao(entity.getIdUsuarioInclusao());
		
		return bean;
	}

}
