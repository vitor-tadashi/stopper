package br.com.verity.pause.entity;

import java.sql.Time;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * @author guilherme.oliveira
 *
 */
@Component
public class ApontamentosEntity {

	private Integer id;
	private String pis;
	private Date data;
	private Time hora;
	private Boolean tpApontamento;
	private String empresa;

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

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

}