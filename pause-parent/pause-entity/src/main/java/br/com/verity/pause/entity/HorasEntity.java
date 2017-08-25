package br.com.verity.pause.entity;

import java.sql.Time;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class HorasEntity {

	private Integer id;
	private String pis;
	private Date dtImportacao;
	private Time hora;
	private Boolean tpApontamento;

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

	public Date getDtImportacao() {
		return dtImportacao;
	}

	public void setDtImportacao(Date dtImportacao) {
		this.dtImportacao = dtImportacao;
	}

	public Time getHora() {
		return hora;
	}

	public void setHora(Time hora) {
		this.hora = hora;
	}

	public Boolean getTpApontamento() {
		return tpApontamento;
	}

	public void setTpApontamento(Boolean tpApontamento) {
		this.tpApontamento = tpApontamento;
	}

}