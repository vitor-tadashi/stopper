package br.com.verity.pause.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class DespesaBean implements Serializable {
	
	private String data;
	private Double valor;
	private Long tipoDespesa;
	private Long centroCusto;
	private String justificativa;
	private Long idFuncionario;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Long getTipoDespesa() {
		return tipoDespesa;
	}
	public void setTipoDespesa(Long tipoDespesa) {
		this.tipoDespesa = tipoDespesa;
	}
	public Long getCentroCusto() {
		return centroCusto;
	}
	public void setCentroCusto(Long centroCusto) {
		this.centroCusto = centroCusto;
	}
	public String getJustificativa() {
		return justificativa;
	}
	public void setJustificativa(String justificativa) {
		this.justificativa = justificativa;
	}
	
	public Long getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	
}
