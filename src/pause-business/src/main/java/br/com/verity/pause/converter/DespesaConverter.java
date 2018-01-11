package br.com.verity.pause.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.DespesaBean;
import br.com.verity.pause.entity.DespesaEntity;

@Component
public class DespesaConverter  implements Converter<DespesaEntity, DespesaBean> {

	@Override
	public DespesaEntity convertBeanToEntity(DespesaBean bean) {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		if (bean == null) {
			return null;
		}
		
		DespesaEntity entity = new DespesaEntity();
		
		entity.setId(bean.getId());
		entity.setStatus(bean.getStatus());
		entity.setTipoDespesa(bean.getTipoDespesa());
		entity.setJustificativa(bean.getJustificativa());
		entity.setValor(bean.getValor());
		entity.setIdProjeto(bean.getIdProjeto());
		entity.setIdSolicitante(bean.getIdFuncionario());
		entity.setIdGpAprovador(bean.getIdGpAprovador());
		entity.setIdFinanceiroAprovador(bean.getIdFinanceiroAprovador());
		
		try {
			if(bean.getData() != null)
				entity.setDataSolicitacao(format.parse(bean.getData()));
			
			if(bean.getDataAcaoGp() != null)
				entity.setDataAcaoGp(format.parse(bean.getDataAcaoGp()));
			
			if(bean.getDataAcaoFinanceiro() != null)
				entity.setDataAcaoFinanceiro(format.parse(bean.getDataAcaoFinanceiro()));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return entity;
	}

	@Override
	public DespesaBean convertEntityToBean(DespesaEntity entity) {
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		if (entity == null) {
			return null;
		}
		
		DespesaBean bean = new DespesaBean();
		
		bean.setId(entity.getId());		
		bean.setStatus(entity.getStatus());
		bean.setTipoDespesa(entity.getTipoDespesa());
		bean.setJustificativa(entity.getJustificativa());
		bean.setValor(entity.getValor());
		
		if(entity.getDataSolicitacao() != null)
			bean.setData(df.format(entity.getDataSolicitacao()));
		
		bean.setIdProjeto(entity.getIdProjeto());
		bean.setIdFuncionario(entity.getIdSolicitante());
		
		if(entity.getDataAcaoGp() != null)
			bean.setDataAcaoGp(df.format(entity.getDataSolicitacao()));
		
		if(entity.getDataAcaoFinanceiro() != null)
			bean.setDataAcaoFinanceiro(df.format(entity.getDataSolicitacao()));
		
		bean.setIdGpAprovador(entity.getIdGpAprovador());
		bean.setIdFinanceiroAprovador(entity.getIdFinanceiroAprovador());
		
		return bean;
	}

}
