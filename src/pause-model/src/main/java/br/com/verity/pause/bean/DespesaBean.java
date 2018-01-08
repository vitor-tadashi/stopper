package br.com.verity.pause.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class DespesaBean implements Serializable {
	
	private Date data;
	private Double valor;
	private Long tipoDespesa;
	private Long centroCusto;
	private String justificativa;
	private MultipartFile comprovante;
	private Long idFuncionario;
	
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
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
	public MultipartFile getComprovante() {
		return comprovante;
	}
	public void setComprovante(MultipartFile comprovante) {
		this.comprovante = comprovante;
	}
	public Long getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	
}
