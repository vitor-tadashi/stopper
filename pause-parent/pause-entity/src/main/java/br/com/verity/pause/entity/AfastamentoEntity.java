package br.com.verity.pause.entity;

import java.sql.Date;

import org.springframework.stereotype.Component;

import br.com.verity.pause.entity.enumerator.TipoAfastamento;

@Component
public class AfastamentoEntity {

	private Integer idAfastamento;

	private Date dataInicio;

	private Date dataFim;

	private Date dataInclusao;

	private Integer idFuncionario;

	private TipoAfastamento tipoAfastamento;

	private Integer idUsuarioInclusao;

	public Integer getIdAfastamento() {
		return idAfastamento;
	}

	public void setIdAfastamento(Integer idAfastamento) {
		this.idAfastamento = idAfastamento;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public TipoAfastamento getTipoAfastamento() {
		return tipoAfastamento;
	}

	public void setTipoAfastamento(TipoAfastamento tipoAfastamento) {
		this.tipoAfastamento = tipoAfastamento;
	}

	public Integer getIdUsuarioInclusao() {
		return idUsuarioInclusao;
	}

	public void setIdUsuarioInclusao(Integer idUsuarioInclusao) {
		this.idUsuarioInclusao = idUsuarioInclusao;
	}
}
