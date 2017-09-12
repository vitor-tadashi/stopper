package br.com.verity.pause.entity;

import org.springframework.stereotype.Component;

@Component
public class AtestadoEntity {
	private int idAtestado;
	
	private Double quantidadeHora;

	public int getIdAtestado() {
		return idAtestado;
	}

	public void setIdAtestado(int idAtestado) {
		this.idAtestado = idAtestado;
	}

	public Double getQuantidadeHora() {
		return quantidadeHora;
	}

	public void setQuantidadeHora(Double quantidadeHora) {
		this.quantidadeHora = quantidadeHora;
	}
	
	
}
