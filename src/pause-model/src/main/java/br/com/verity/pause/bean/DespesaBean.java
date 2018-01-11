package br.com.verity.pause.bean;

public class DespesaBean{
	
	private Long id;
	private Long status;
	private Long tipoDespesa;
	private String justificativa;
	private Double valor;
	private String data;
	private Long idProjeto;
	private Long idFuncionario;
	private String dataAcaoGp;
	private String dataAcaoFinanceiro;
	private Long idGpAprovador;
	private Long idFinanceiroAprovador;
	
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
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Long getIdProjeto() {
		return idProjeto;
	}
	public void setIdProjeto(Long idProjeto) {
		this.idProjeto = idProjeto;
	}
	public Long getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	public String getDataAcaoGp() {
		return dataAcaoGp;
	}
	public void setDataAcaoGp(String dataAcaoGp) {
		this.dataAcaoGp = dataAcaoGp;
	}
	public String getDataAcaoFinanceiro() {
		return dataAcaoFinanceiro;
	}
	public void setDataAcaoFinanceiro(String dataAcaoFinanceiro) {
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
}
