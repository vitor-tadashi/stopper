package br.com.verity.pause.bean;

import java.time.LocalTime;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ConsultaCompletaBean {
	private Date data;
	private Integer idFuncionario;
	private Integer controleMensalAno;
	private Integer controleMensalMes;
	private Double controleMensalHoraTotal;
	private Double controleMensalBancoHora;
	private Double controleMensalAdcNoturno;
	private Double controleMensalSA;
	private Double controleMensalST;
	private Date controleDiarioData;
	private Double controleDiarioHoraTotal;
	private Double controleDiarioBancoHora;
	private Double controleDiarioAdcNoturno;
	private Double controleDiarioSA;
	private Double controleDiarioST;
	private Date apontamentoData;
	private Integer apontamentoId;
	private LocalTime apontamentoHorario;
	private Boolean apontamentoTpImportacao;
	private String apontamentoObs;
	private Integer apontamentoIdTipoJustificativa;
	private Double atestadoQuantidadeHora;
	private Integer SobreAvisoId;

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Integer getControleMensalAno() {
		return controleMensalAno;
	}

	public void setControleMensalAno(Integer controleMensalAno) {
		this.controleMensalAno = controleMensalAno;
	}

	public Integer getControleMensalMes() {
		return controleMensalMes;
	}

	public void setControleMensalMes(Integer controleMensalMes) {
		this.controleMensalMes = controleMensalMes;
	}

	public Double getControleMensalHoraTotal() {
		return controleMensalHoraTotal;
	}

	public void setControleMensalHoraTotal(Double controleMensalHoraTotal) {
		this.controleMensalHoraTotal = controleMensalHoraTotal;
	}

	public Double getControleMensalBancoHora() {
		return controleMensalBancoHora;
	}

	public void setControleMensalBancoHora(Double controleMensalBancoHora) {
		this.controleMensalBancoHora = controleMensalBancoHora;
	}

	public Double getControleMensalAdcNoturno() {
		return controleMensalAdcNoturno;
	}

	public void setControleMensalAdcNoturno(Double controleMensalAdcNoturno) {
		this.controleMensalAdcNoturno = controleMensalAdcNoturno;
	}

	public Double getControleMensalSA() {
		return controleMensalSA;
	}

	public void setControleMensalSA(Double controleMensalSA) {
		this.controleMensalSA = controleMensalSA;
	}

	public Double getControleMensalST() {
		return controleMensalST;
	}

	public void setControleMensalST(Double controleMensalST) {
		this.controleMensalST = controleMensalST;
	}

	public Date getControleDiarioData() {
		return controleDiarioData;
	}

	public void setControleDiarioData(Date controleDiarioData) {
		this.controleDiarioData = controleDiarioData;
	}

	public Double getControleDiarioHoraTotal() {
		return controleDiarioHoraTotal;
	}

	public void setControleDiarioHoraTotal(Double controleDiarioHoraTotal) {
		this.controleDiarioHoraTotal = controleDiarioHoraTotal;
	}

	public Double getControleDiarioBancoHora() {
		return controleDiarioBancoHora;
	}

	public void setControleDiarioBancoHora(Double controleDiarioBancoHora) {
		this.controleDiarioBancoHora = controleDiarioBancoHora;
	}

	public Double getControleDiarioAdcNoturno() {
		return controleDiarioAdcNoturno;
	}

	public void setControleDiarioAdcNoturno(Double controleDiarioAdcNoturno) {
		this.controleDiarioAdcNoturno = controleDiarioAdcNoturno;
	}

	public Double getControleDiarioSA() {
		return controleDiarioSA;
	}

	public void setControleDiarioSA(Double controleDiarioSA) {
		this.controleDiarioSA = controleDiarioSA;
	}

	public Double getControleDiarioST() {
		return controleDiarioST;
	}

	public void setControleDiarioST(Double controleDiarioST) {
		this.controleDiarioST = controleDiarioST;
	}

	public Date getApontamentoData() {
		return apontamentoData;
	}

	public void setApontamentoData(Date apontamentoData) {
		this.apontamentoData = apontamentoData;
	}

	public Integer getApontamentoId() {
		return apontamentoId;
	}

	public void setApontamentoId(Integer apontamentoId) {
		this.apontamentoId = apontamentoId;
	}

	public LocalTime getApontamentoHorario() {
		return apontamentoHorario;
	}

	public void setApontamentoHorario(LocalTime apontamentoHorario) {
		this.apontamentoHorario = apontamentoHorario;
	}

	public Boolean getApontamentoTpImportacao() {
		return apontamentoTpImportacao;
	}

	public void setApontamentoTpImportacao(Boolean apontamentoTpImportacao) {
		this.apontamentoTpImportacao = apontamentoTpImportacao;
	}

	public String getApontamentoObs() {
		return apontamentoObs;
	}

	public void setApontamentoObs(String apontamentoObs) {
		this.apontamentoObs = apontamentoObs;
	}

	public Double getAtestadoQuantidadeHora() {
		return atestadoQuantidadeHora;
	}

	public void setAtestadoQuantidadeHora(Double atestadoQuantidadeHora) {
		this.atestadoQuantidadeHora = atestadoQuantidadeHora;
	}

	public Integer getApontamentoIdTipoJustificativa() {
		return apontamentoIdTipoJustificativa;
	}

	public void setApontamentoIdTipoJustificativa(Integer apontamentoIdTipoJustificativa) {
		this.apontamentoIdTipoJustificativa = apontamentoIdTipoJustificativa;
	}

	public Integer getSobreAvisoId() {
		return SobreAvisoId;
	}

	public void setSobreAvisoId(Integer sobreAvisoId) {
		SobreAvisoId = sobreAvisoId;
	}

}
