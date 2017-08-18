package br.com.verity.regponto.converter;

import org.springframework.stereotype.Component;

import br.com.verity.regponto.bean.EmpresaBean;
import br.com.verity.regponto.entity.EmpresaEntity;

@Component
public class EmpresaConverter implements Converter<EmpresaEntity, EmpresaBean>{

	@Override
	public EmpresaEntity convertBeanToEntity(EmpresaBean bean) {
		if (bean == null) {
			return null;
		}
		EmpresaEntity entity = new EmpresaEntity();
		
		entity.setId(bean.getId());
		entity.setNmEmpresa(bean.getNome());
		
		return entity;
	}

	@Override
	public EmpresaBean convertEntityToBean(EmpresaEntity entity) {
		if (entity == null) {
			return null;
		}
		EmpresaBean bean = new EmpresaBean();
		
		bean.setId(entity.getId());
		bean.setNome(entity.getNmEmpresa());
		
		return bean;
	}
}
