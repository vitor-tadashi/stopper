package br.com.verity.pause.entity;

import java.sql.Date;

import org.springframework.stereotype.Component;

import br.com.verity.pause.entity.enumerator.TipoAfastamento;

@Component
public class AfastamentoEntity {

	private int idAfastamento;

	private Date dataInicio;

	private Date dataFim;

	private Date dataInclusao;

	private int idFuncionario;

	private TipoAfastamento tipoAfastamento;

	private int idUsuarioInclusao;

	public int getIdAfastamento() {
		return idAfastamento;
	}

	public void setIdAfastamento(int idAfastamento) {
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

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public TipoAfastamento getTipoAfastamento() {
		return tipoAfastamento;
	}

	public void setTipoAfastamento(TipoAfastamento tipoAfastamento) {
		this.tipoAfastamento = tipoAfastamento;
	}

	public int getIdUsuarioInclusao() {
		return idUsuarioInclusao;
	}

	public void setIdUsuarioInclusao(int idUsuarioInclusao) {
		this.idUsuarioInclusao = idUsuarioInclusao;
	}
}
