package br.com.verity.pause.bean;

import java.sql.Time;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * @author guilherme.oliveira
 *
 */
@Component
public class ApontamentosBean {

	private Integer id;
	private String pis;
	private Date data;
	private Time hora;
	private Boolean tipoApontamento = true;
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

	public Boolean getTipoApontamento() {
		return tipoApontamento;
	}

	public void setTipoApontamento(Boolean tipoApontamento) {
		this.tipoApontamento = tipoApontamento;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

}