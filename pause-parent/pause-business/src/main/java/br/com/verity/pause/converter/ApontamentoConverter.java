package br.com.verity.pause.converter;

import java.sql.Time;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.entity.ApontamentoEntity;

@Component
public class ApontamentoConverter implements Converter<ApontamentoEntity, ApontamentoBean> {

	@Override
	public ApontamentoEntity convertBeanToEntity(ApontamentoBean bean) {
		if (bean == null) {
			return null;
		}

		ApontamentoEntity entity = new ApontamentoEntity();

		entity.setId(bean.getId());
		entity.setPis(bean.getPis());
		entity.setHorario(Time.valueOf(bean.getHorario()));
		entity.setData(new java.sql.Date(bean.getData().getTime()));
		entity.setTipoImportacao(bean.getTipoImportacao());
		entity.setIdEmpresa(bean.getIdEmpresa());
		entity.setDataInclusao(new java.sql.Date(bean.getDataInclusao().getTime()));
		entity.setIdUsuarioInclusao(bean.getIdUsuarioInclusao());
		entity.setObservacao(bean.getObservacao());

		return entity;
	}

	@Override
	public ApontamentoBean convertEntityToBean(ApontamentoEntity entity) {
		if (entity == null) {
			return null;
		}

		ApontamentoBean bean = new ApontamentoBean();

		bean.setId(entity.getId());
		bean.setData(entity.getData());
		bean.setHorario(entity.getHorario().toLocalTime());
		bean.setPis(entity.getPis());
		bean.setTipoImportacao(entity.getTipoImportacao());
		bean.setIdEmpresa(entity.getIdEmpresa());
		bean.setDataInclusao(entity.getDataInclusao());
		bean.setIdUsuarioInclusao(entity.getIdUsuarioInclusao());
		bean.setObservacao(entity.getObservacao());

		return bean;
	}

}
