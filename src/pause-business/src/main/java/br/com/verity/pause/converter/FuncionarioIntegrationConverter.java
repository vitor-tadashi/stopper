package br.com.verity.pause.converter;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.FuncionarioIntegrationBean;

@Component
public class FuncionarioIntegrationConverter implements Converter<FuncionarioIntegrationBean, FuncionarioBean>{

	@Override
	public FuncionarioIntegrationBean convertBeanToEntity(FuncionarioBean bean) {
		if (bean == null) {
			return null;
		}

		FuncionarioIntegrationBean entity = new FuncionarioIntegrationBean();
		
		entity.setId(bean.getId());
		entity.setNome(bean.getNome());
		entity.setPis(bean.getPis());
		entity.setMatricula(bean.getMatricula());
		entity.setEmpresa(bean.getEmpresa());
		entity.setAtivo(bean.getAtivo());
		
		return entity;
	}

	@Override
	public FuncionarioBean convertEntityToBean(FuncionarioIntegrationBean entity) {
		if (entity == null) {
			return null;
		}

		FuncionarioBean bean = new FuncionarioBean();
		
		bean.setId(entity.getId());
		bean.setNome(entity.getNome());
		bean.setPis(entity.getPis());
		bean.setMatricula(entity.getMatricula());
		bean.setEmpresa(entity.getEmpresa());
		bean.setAtivo(entity.getAtivo());
		
		return bean;
	}
}