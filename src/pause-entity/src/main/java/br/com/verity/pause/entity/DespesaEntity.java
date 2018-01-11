package br.com.verity.pause.entity;

import java.util.Date;

public class DespesaEntity{
	
	private Long id;
	private Long status;
	private Long tipoDespesa;
	private String justificativa;
	private Double valor;
	private Date dataSolicitacao;
	private Long idProjeto;
	private Long idSolicitante;
	private Date dataAcaoGp;
	private Date dataAcaoFinanceiro;
	private Long idGpAprovador;
	private Long idFinanceiroAprovador;
	private String caminhoJustificativa;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getStatus() {
		return status;
	}
	
	public void setStatus(Long status) {
		this.status = status;
	}
	
	public Long getTipoDespesa() {
		return tipoDespesa;
	}
	
	public void setTipoDespesa(Long tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}
	
	public String getJustificativa() {
		return justificativa;
	}
	
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	
	public Double getValor() {
		return valor;
	}
	
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}
	
	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	
	public Long getIdProjeto() {
		return idProjeto;
	}
	
	public void setIdProjeto(Long idProjeto) {
		this.idProjeto = idProjeto;
	}
	
	public Long getIdSolicitante() {
		return idSolicitante;
	}
	
	public void setIdSolicitante(Long idSolicitante) {
		this.idSolicitante = idSolicitante;
	}
	
	public Date getDataAcaoGp() {
		return dataAcaoGp;
	}
	
	public void setDataAcaoGp(Date dataAcaoGp) {
		this.dataAcaoGp = dataAcaoGp;
	}
	
	public Date getDataAcaoFinanceiro() {
		return dataAcaoFinanceiro;
	}
	
	public void setDataAcaoFinanceiro(Date dataAcaoFinanceiro) {
		this.dataAcaoFinanceiro = dataAcaoFinanceiro;
	}
	
	public Long getIdGpAprovador() {
		return idGpAprovador;
	}
	
	public void setIdGpAprovador(Long idGpAprovador) {
		this.idGpAprovador = idGpAprovador;
	}
	
	public Long getIdFinanceiroAprovador() {
		return idFinanceiroAprovador;
	}
	
	public void setIdFinanceiroAprovador(Long idFinanceiroAprovador) {
		this.idFinanceiroAprovador = idFinanceiroAprovador;
	}
	
	public String getCaminhoJustificativa() {
		return caminhoJustificativa;
	}
	
	public void setCaminhoJustificativa(String caminhoJustificativa) {
		this.caminhoJustificativa = caminhoJustificativa;
	}
}
