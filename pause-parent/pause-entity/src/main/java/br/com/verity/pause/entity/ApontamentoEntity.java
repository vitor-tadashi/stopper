package br.com.verity.pause.entity;

import java.sql.Date;
import java.sql.Time;

import org.springframework.stereotype.Component;

/**
 * @author guilherme.oliveira
 *
 */
@Component
public class ApontamentoEntity {

	private Integer id;
	private String pis;
	private Date data;
	private Time horario;
	private Boolean tipoImportacao;
	private Date dataInclusao;
	private String observacao;
	private TipoJustificativaEntity tipoJustificativa;
	private ControleDiarioEntity controleDiario;
	private Integer idEmpresa;
	private Integer idUsuarioInclusao;
	private ArquivoApontamentoEntity arquivoApontamento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Time getHorario() {
		return horario;
	}

	public void setHorario(Time horario) {
		this.horario = horario;
	}

	public Boolean getTipoImportacao() {
		return tipoImportacao;
	}

	public void setTipoImportacao(Boolean tipoImportacao) {
		this.tipoImportacao = tipoImportacao;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public TipoJustificativaEntity getTipoJustificativa() {
		return tipoJustificativa;
	}

	public void setTipoJustificativa(TipoJustificativaEntity tipoJustificativa) {
		this.tipoJustificativa = tipoJustificativa;
	}

	public ControleDiarioEntity getControleDiario() {
		return controleDiario;
	}

	public void setControleDiario(ControleDiarioEntity controleDiario) {
		this.controleDiario = controleDiario;
	}

	public Integer getIdUsuarioInclusao() {
		return idUsuarioInclusao;
	}

	public void setIdUsuarioInclusao(Integer idUsuarioInclusao) {
		this.idUsuarioInclusao = idUsuarioInclusao;
	}

	public ArquivoApontamentoEntity getArquivoApontamento() {
		return arquivoApontamento;
	}

	public void setArquivoApontamento(ArquivoApontamentoEntity arquivoApontamento) {
		this.arquivoApontamento = arquivoApontamento;
	}

}