package br.com.verity.pause.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.DespesaBean;
import br.com.verity.pause.entity.DespesaEntity;
import br.com.verity.pause.entity.StatusEntity;
import br.com.verity.pause.entity.TipoDespesaEntity;

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
		entity.setStatus(new StatusEntity());
		entity.getStatus().setId(bean.getIdStatus());
		entity.getStatus().setNome(bean.getNomeStatus());
		entity.setTipoDespesa(new TipoDespesaEntity());
		entity.getTipoDespesa().setId(bean.getIdTipoDespesa());
		entity.getTipoDespesa().setNome(bean.getNomeTipoDespesa());
		entity.setJustificativa(bean.getJustificativa());
		entity.setValor(bean.getValor());
		entity.setIdProjeto(bean.getIdProjeto());
		entity.setIdSolicitante(bean.getIdFuncionario());
		entity.setIdGpAprovador(bean.getIdGpAprovador());
		entity.setIdFinanceiroAprovador(bean.getIdFinanceiroAprovador());
		
		try {
			if(bean.getDataOcorrencia() != null)
				entity.setDataOcorrencia(format.parse(bean.getDataOcorrencia()));
			
			if(bean.getDataAcaoGp() != null)
				entity.setDataAcaoGp(format.parse(bean.getDataAcaoGp()));
			
			if(bean.getDataAcaoFinanceiro() != null)
				entity.setDataAcaoFinanceiro(format.parse(bean.getDataAcaoFinanceiro()));
			
			if(bean.getDataSolicitacao() != null)
				entity.setDataSolicitacao(format.parse(bean.getDataSolicitacao()));
			
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
		bean.setIdStatus(entity.getStatus().getId());
		bean.setNomeStatus(entity.getStatus().getNome());
		bean.setIdTipoDespesa(entity.getTipoDespesa().getId());
		bean.setNomeTipoDespesa(entity.getTipoDespesa().getNome());
		bean.setJustificativa(entity.getJustificativa());
		bean.setValor(entity.getValor());
		bean.setCaminhoComprovante(entity.getCaminhoComprovante());
		
		if(entity.getDataSolicitacao() != null)
			bean.setDataSolicitacao(df.format(entity.getDataSolicitacao()));
		
		if(entity.getDataOcorrencia() != null)
			bean.setDataOcorrencia(df.format(entity.getDataOcorrencia()));
		
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
