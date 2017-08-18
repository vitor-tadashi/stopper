package br.com.verity.regponto.bean;

import org.springframework.stereotype.Component;

@Component
public class HorasBean {

	private String pis;
	private String dataImportacao;
	private String hora;

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public String getDataImportacao() {
		return dataImportacao;
	}

	public void setDataImportacao(String dataImportacao) {
		this.dataImportacao = dataImportacao;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

}