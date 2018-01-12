package br.com.verity.pause.bean;

public class DespesaBean{
	
	private Long id;
	private Integer idStatus;
	private Long idTipoDespesa;
	private String nomeTipoDespesa;
	private String justificativa;
	private Double valor;
	private String dataOcorrencia;
	private Long idProjeto;
	private Long idFuncionario;
	private String dataAcaoGp;
	private String dataAcaoFinanceiro;
	private Long idGpAprovador;
	private Long idFinanceiroAprovador;
	private String descricaoProjeto;
	private String nomeStatus;
	private String dataSolicitacao;
	private String nomeFuncionario;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(Integer idStatus) {
		this.idStatus = idStatus;
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
	public String getDataOcorrencia() {
		return dataOcorrencia;
	}
	public void setDataOcorrencia(String dataOcorrencia) {
		this.dataOcorrencia = dataOcorrencia;
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
	public String getDescricaoProjeto() {
		return descricaoProjeto;
	}
	public void setDescricaoProjeto(String descricaoProjeto) {
		this.descricaoProjeto = descricaoProjeto;
	}
	public String getNomeStatus() {
		return nomeStatus;
	}
	public void setNomeStatus(String nomeStatus) {
		this.nomeStatus = nomeStatus;
	}
	public Long getIdTipoDespesa() {
		return idTipoDespesa;
	}
	public void setIdTipoDespesa(Long idTipoDespesa) {
		this.idTipoDespesa = idTipoDespesa;
	}
	public String getNomeTipoDespesa() {
		return nomeTipoDespesa;
	}
	public void setNomeTipoDespesa(String nomeTipoDespesa) {
		this.nomeTipoDespesa = nomeTipoDespesa;
	}
	public String getDataSolicitacao() {
		return dataSolicitacao;
	}
	public void setDataSolicitacao(String dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}
	public String getNomeFuncionario() {
		return nomeFuncionario;
	}
	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}
}
