package br.com.verity.pause.converter;

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
		entity.setHorario(bean.getHorario());
		entity.setData(bean.getData());
		entity.setTipoImportacao(bean.getTipoImportacao());
		entity.setIdEmpresa(bean.getIdEmpresa());
		entity.setDataInclusao(bean.getDataInclusao());
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
		bean.setHorario(entity.getHorario());
		bean.setPis(entity.getPis());
		bean.setTipoImportacao(entity.getTipoImportacao());
		bean.setIdEmpresa(entity.getIdEmpresa());
		bean.setDataInclusao(entity.getDataInclusao());
		bean.setIdUsuarioInclusao(entity.getIdUsuarioInclusao());
		bean.setObservacao(entity.getObservacao());

		return bean;
	}

}
