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
	private Integer id;
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date data;
	@DateTimeFormat(iso = ISO.TIME)
	private LocalTime entrada;
	@DateTimeFormat(iso = ISO.TIME)
	private LocalTime saida;
	private Date dataInclusao;
	private ControleDiarioBean controleDiario;
	private Integer idUsuarioInclusao;
	private Integer idFuncionario;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
		entrada = entrada.length()== 4? "0"+entrada : entrada;
		this.entrada = LocalTime.parse(entrada);
	}
	public void setEntrada(LocalTime entrada) {
		this.entrada = entrada;
	}
	public LocalTime getSaida() {
		return saida;
	}
	public void setSaida(String saida) {
		saida = saida.length()== 4? "0"+saida : saida;
		this.saida = LocalTime.parse(saida);
	}
	public void setSaida(LocalTime saida) {
		this.saida = saida;
	}
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	public ControleDiarioBean getControleDiario() {
		return controleDiario;
	}
	public void setControleDiario(ControleDiarioBean controleDiario) {
		this.controleDiario = controleDiario;
	}
	public Integer getIdUsuarioInclusao() {
		return idUsuarioInclusao;
	}
	public void setIdUsuarioInclusao(Integer idUsuarioInclusao) {
		this.idUsuarioInclusao = idUsuarioInclusao;
	}
	public Integer getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
}
