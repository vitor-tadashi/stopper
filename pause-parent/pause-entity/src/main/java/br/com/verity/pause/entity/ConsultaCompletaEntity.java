package br.com.verity.pause.entity;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * @author guilherme.oliveira
 *
 */
/**
 * @author guilherme.oliveira
 *
 */
@Component
public class ConsultaCompletaEntity {

	private Date data;
	private Integer idFuncionario;
	private Integer cmAno;
	private Integer cmMes;
	private Double cmHoraTotal;
	private Double cmBancoHora;
	private Double cmAdcNoturno;
	private Double cmSA;
	private Double cmST;
	private Date cdData;
	private Double cdHoraTotal;
	private Double cdBancoHora;
	private Double cdAdcNoturno;
	private Double cdSA;
	private Double cdST;
	private Date apData;
	private Integer apIdApontamento;
	private LocalTime apHorario;
	private Boolean apTpImportacao;
	private String apObs;
	private Integer apIdTpJustificativa;
	private Double atQtdHora;
	private Integer sbId;

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

	public Integer getCmAno() {
		return cmAno;
	}

	public void setCmAno(Integer cmAno) {
		this.cmAno = cmAno;
	}

	public Integer getCmMes() {
		return cmMes;
	}

	public void setCmMes(Integer cmMes) {
		this.cmMes = cmMes;
	}

	public Double getCmHoraTotal() {
		return cmHoraTotal;
	}

	public void setCmHoraTotal(Double cmHoraTotal) {
		this.cmHoraTotal = cmHoraTotal;
	}

	public Double getCmBancoHora() {
		return cmBancoHora;
	}

	public void setCmBancoHora(Double cmBancoHora) {
		this.cmBancoHora = cmBancoHora;
	}

	public Double getCmAdcNoturno() {
		return cmAdcNoturno;
	}

	public void setCmAdcNoturno(Double cmAdcNoturno) {
		this.cmAdcNoturno = cmAdcNoturno;
	}

	public Double getCmSA() {
		return cmSA;
	}

	public void setCmSA(Double cmSA) {
		this.cmSA = cmSA;
	}

	public Double getCmST() {
		return cmST;
	}

	public void setCmST(Double cmST) {
		this.cmST = cmST;
	}

	public Date getCdData() {
		return cdData;
	}

	public void setCdData(Date cdData) {
		this.cdData = cdData;
	}

	public Double getCdHoraTotal() {
		return cdHoraTotal;
	}

	public void setCdHoraTotal(Double cdHoraTotal) {
		this.cdHoraTotal = cdHoraTotal;
	}

	public Double getCdBancoHora() {
		return cdBancoHora;
	}

	public void setCdBancoHora(Double cdBancoHora) {
		this.cdBancoHora = cdBancoHora;
	}

	public Double getCdAdcNoturno() {
		return cdAdcNoturno;
	}

	public void setCdAdcNoturno(Double cdAdcNoturno) {
		this.cdAdcNoturno = cdAdcNoturno;
	}

	public Double getCdSA() {
		return cdSA;
	}

	public void setCdSA(Double cdSA) {
		this.cdSA = cdSA;
	}

	public Double getCdST() {
		return cdST;
	}

	public void setCdST(Double cdST) {
		this.cdST = cdST;
	}

	public Date getApData() {
		return apData;
	}

	public void setApData(Date apData) {
		this.apData = apData;
	}

	public Integer getApIdApontamento() {
		return apIdApontamento;
	}

	public void setApIdApontamento(Integer apIdApontamento) {
		this.apIdApontamento = apIdApontamento;
	}

	public LocalTime getApHorario() {
		return apHorario;
	}

	public void setApHorario(LocalTime apHorario) {
		this.apHorario = apHorario;
	}

	public void setApHorario(Time apHorario) {
		this.apHorario = apHorario.toLocalTime();
	}

	public Boolean getApTpImportacao() {
		return apTpImportacao;
	}

	public void setApTpImportacao(Boolean apTpImportacao) {
		this.apTpImportacao = apTpImportacao;
	}

	public String getApObs() {
		return apObs;
	}

	public void setApObs(String apObs) {
		this.apObs = apObs;
	}

	public Double getAtQtdHora() {
		return atQtdHora;
	}

	public void setAtQtdHora(Double atQtdHora) {
		this.atQtdHora = atQtdHora;
	}

	public Integer getApIdTpJustificativa() {
		return apIdTpJustificativa;
	}

	public void setApIdTpJustificativa(Integer apIdTpJustificativa) {
		this.apIdTpJustificativa = apIdTpJustificativa;
	}

	public Integer getSbId() {
		return sbId;
	}

	public void setSbId(Integer sbId) {
		this.sbId = sbId;
	}

}