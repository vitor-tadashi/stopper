package br.com.verity.pause.bean;

import java.sql.Time;
import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * @author guilherme.oliveira
 *
 */
@Component
public class HorasBean {

	private Integer id;
	private String pis;
	private Date dataImportacao;
	private Time hora;
	private Boolean tipoApontamento = true;

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

	public Date getDataImportacao() {
		return dataImportacao;
	}

	public void setDataImportacao(Date dataImportacao) {
		this.dataImportacao = dataImportacao;
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
}