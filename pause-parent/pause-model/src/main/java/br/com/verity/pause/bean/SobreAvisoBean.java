package br.com.verity.pause.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Component;

@Component
public class SobreAvisoBean{
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date data;
	@DateTimeFormat(iso = ISO.TIME)
	private LocalTime entrada;
	@DateTimeFormat(iso = ISO.TIME)
	private LocalTime saida;
	
	public Date getData() {
		return data;
	}
	public void setData(String data) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.data = formatter.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public void setData(Date data) {
		this.data = data;

	}
	public LocalTime getEntrada() {
		return entrada;
	}
	public void setEntrada(String entrada) {
		this.entrada = LocalTime.parse(entrada);
	}
	public void setEntrada(LocalTime entrada) {
		this.entrada = entrada;
	}
	public LocalTime getSaida() {
		return saida;
	}
	public void setSaida(String saida) {
		this.saida = LocalTime.parse(saida);
	}
	public void setSaida(LocalTime saida) {
		this.saida = saida;
	}
}
