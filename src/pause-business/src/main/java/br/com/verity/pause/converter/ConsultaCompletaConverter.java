package br.com.verity.pause.converter;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.ConsultaCompletaBean;
import br.com.verity.pause.entity.ConsultaCompletaEntity;

@Component
public class ConsultaCompletaConverter implements Converter<ConsultaCompletaEntity, ConsultaCompletaBean> {

	@Override
	public ConsultaCompletaEntity convertBeanToEntity(ConsultaCompletaBean bean) {
		if(bean == null){
			return null;
		}
		
		ConsultaCompletaEntity entity = new ConsultaCompletaEntity();
		
		entity.setData(bean.getData());
		entity.setIdFuncionario(bean.getIdFuncionario());
		entity.setCmAno(bean.getControleMensalAno());
		entity.setCmMes(bean.getControleMensalMes());
		entity.setCmHoraTotal(bean.getControleMensalHoraTotal());
		entity.setCmBancoHora(bean.getControleMensalBancoHora());
		entity.setCmAdcNoturno(bean.getControleMensalAdcNoturno());
		entity.setCmSA(bean.getControleMensalSA());
		entity.setCmST(bean.getControleMensalST());
		entity.setCdData(bean.getControleDiarioData());
		entity.setCdHoraTotal(bean.getControleDiarioHoraTotal());
		entity.setCdBancoHora(bean.getControleDiarioBancoHora());
		entity.setCdAdcNoturno(bean.getControleDiarioAdcNoturno());
		entity.setCdSA(bean.getControleDiarioSA());
		entity.setCdST(bean.getControleDiarioST());
		entity.setApData(bean.getApontamentoData());
		entity.setApIdApontamento(bean.getApontamentoId());
		entity.setApHorario(bean.getApontamentoHorario());
		entity.setApTpImportacao(bean.getApontamentoTpImportacao());
		entity.setApObs(bean.getApontamentoObs());
		entity.setApIdTpJustificativa(bean.getApontamentoIdTipoJustificativa());
		entity.setAtQtdHora(bean.getAtestadoQuantidadeHora());
		entity.setSbId(bean.getSobreAvisoId());
		
		return entity;
	}

	@Override
	public ConsultaCompletaBean convertEntityToBean(ConsultaCompletaEntity entity) {
		if(entity == null){
			return null;
		}
		
		ConsultaCompletaBean bean = new ConsultaCompletaBean();
		
		bean.setData(entity.getData());
		bean.setIdFuncionario(entity.getIdFuncionario());
		bean.setControleMensalAno(entity.getCmAno());
		bean.setControleMensalMes(entity.getCmMes());
		bean.setControleMensalHoraTotal(entity.getCmHoraTotal());
		bean.setControleMensalBancoHora(entity.getCmBancoHora());
		bean.setControleMensalAdcNoturno(entity.getCmAdcNoturno());
		bean.setControleMensalSA(entity.getCmSA());
		bean.setControleMensalST(entity.getCmST());
		bean.setControleDiarioData(entity.getCdData());
		bean.setControleDiarioHoraTotal(entity.getCdHoraTotal());
		bean.setControleDiarioBancoHora(entity.getCdBancoHora());
		bean.setControleDiarioAdcNoturno(entity.getCdAdcNoturno());
		bean.setControleDiarioSA(entity.getCdSA());
		bean.setControleDiarioST(entity.getCdST());
		bean.setApontamentoData(entity.getApData());
		bean.setApontamentoId(entity.getApIdApontamento());
		bean.setApontamentoHorario(entity.getApHorario());
		bean.setApontamentoTpImportacao(entity.getApTpImportacao());
		bean.setApontamentoObs(entity.getApObs());
		bean.setApontamentoIdTipoJustificativa(entity.getApIdTpJustificativa());
		bean.setAtestadoQuantidadeHora(entity.getAtQtdHora());
		bean.setSobreAvisoId(entity.getSbId());
		
		return bean;
	}

}
