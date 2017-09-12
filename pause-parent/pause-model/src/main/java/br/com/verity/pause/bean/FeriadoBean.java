package br.com.verity.pause.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class FeriadoBean {

	private Integer id;
	private String nomeFeriado;
	private Date dataFeriado;
	private Boolean fixo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeFeriado() {
		return nomeFeriado;
	}

	public void setNomeFeriado(String nomeFeriado) {
		this.nomeFeriado = nomeFeriado;
	}

	public Date getDataFeriado() {
		return dataFeriado;
	}

	public void setDataFeriado(Date dataFeriado) {
		this.dataFeriado = dataFeriado;
	}

	public Boolean getFixo() {
		return fixo;
	}

	public void setFixo(Boolean fixo) {
		this.fixo = fixo;
	}

}