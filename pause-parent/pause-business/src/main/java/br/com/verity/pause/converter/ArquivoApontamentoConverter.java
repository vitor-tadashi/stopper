package br.com.verity.pause.converter;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.ArquivoApontamentoBean;
import br.com.verity.pause.entity.ArquivoApontamentoEntity;

@Component
public class ArquivoApontamentoConverter implements Converter<ArquivoApontamentoEntity, ArquivoApontamentoBean> {

	@Override
	public ArquivoApontamentoEntity convertBeanToEntity(ArquivoApontamentoBean bean) {
		if (bean == null) {
			return null;
		}
		
		ArquivoApontamentoEntity entity = new ArquivoApontamentoEntity();
		
		entity.setId(bean.getId());
		entity.setCaminho(bean.getCaminho());
		entity.setData(bean.getData());
		entity.setDataInclusao(bean.getDtInclusao());
		entity.setIdEmpresa(bean.getIdEmpresa());
		entity.setIdUsuarioInclusao(bean.getIdUsuarioInclusao());
		
		return entity;
	}

	@Override
	public ArquivoApontamentoBean convertEntityToBean(ArquivoApontamentoEntity entity) {
		if (entity == null) {
			return null;
		}
		
		ArquivoApontamentoBean bean = new ArquivoApontamentoBean();

		bean.setId(entity.getId());
		bean.setCaminho(entity.getCaminho());
		bean.setData(entity.getData());
		bean.setDtInclusao(entity.getDataInclusao());
		bean.setIdEmpresa(entity.getIdEmpresa());
		bean.setIdUsuarioInclusao(entity.getIdUsuarioInclusao());
		
		return bean;
	}

}
