package br.com.verity.pause.converter;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.TipoAfastamentoBean;
import br.com.verity.pause.entity.enumerator.TipoAfastamento;

@Component
public class TipoAfastamentoConverter implements Converter<TipoAfastamento, TipoAfastamentoBean> {

	@Override
	public TipoAfastamento convertBeanToEntity(TipoAfastamentoBean bean) {
		if (bean == null) {
			return null;
		}
		return TipoAfastamento.getById(bean.getId());
	}

	@Override
	public TipoAfastamentoBean convertEntityToBean(TipoAfastamento entity) {
		if (entity == null) {
			return null;
		}
		TipoAfastamentoBean bean = new TipoAfastamentoBean();
		
		bean.setId(entity.getIdTipoAfastamento());
		bean.setDescricao(entity.getDescricao());
		
		return bean;
	}

}
