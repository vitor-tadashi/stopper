package br.com.verity.pause.entity;

import java.sql.Date;
import java.sql.Time;

import org.springframework.stereotype.Component;

@Component
public class SobreAvisoEntity {
	private Integer id;
	private Date data;
	private Time entrada;
	private Time saida;
	private Date dataInclusao;
	private ControleDiarioEntity controleDiario;
	private Integer idUsuarioInclusao;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Time getEntrada() {
		return entrada;
	}
	public void setEntrada(Time entrada) {
		this.entrada = entrada;
	}
	public Time getSaida() {
		return saida;
	}
	public void setSaida(Time saida) {
		this.saida = saida;
	}
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
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
}
