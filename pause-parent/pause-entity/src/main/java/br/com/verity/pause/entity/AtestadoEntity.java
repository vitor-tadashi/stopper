package br.com.verity.pause.entity;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class AtestadoEntity {
	private Integer idAtestado;
	private Double quantidadeHora;
	private Date dataInclusao;
	private Integer idUsuarioInclusao;
	private ControleDiarioEntity controleDiario;
	private TipoAtestadoEntity tipoAtestado;

	public Integer getIdAtestado() {
		return idAtestado;
	}

	public void setIdAtestado(Integer idAtestado) {
		this.idAtestado = idAtestado;
	}

	public Double getQuantidadeHora() {
		return quantidadeHora;
	}

	public void setQuantidadeHora(Double quantidadeHora) {
		this.quantidadeHora = quantidadeHora;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Integer getIdUsuarioInclusao() {
		return idUsuarioInclusao;
	}

	public void setIdUsuarioInclusao(Integer idUsuarioInclusao) {
		this.idUsuarioInclusao = idUsuarioInclusao;
	}

	public ControleDiarioEntity getControleDiario() {
		return controleDiario;
	}

	public void setControleDiario(ControleDiarioEntity controleDiario) {
		this.controleDiario = controleDiario;
	}

	public TipoAtestadoEntity getTipoAtestado() {
		return tipoAtestado;
	}

	public void setTipoAtestado(TipoAtestadoEntity tipoAtestado) {
		this.tipoAtestado = tipoAtestado;
	}
	
	
}
