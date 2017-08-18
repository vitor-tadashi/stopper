package br.com.verity.regponto.bean;

import org.springframework.stereotype.Component;

/**
 * @author guilherme.oliveira
 *
 */
/**
 * @author guilherme.oliveira
 *
 */
@Component
public class FuncionalidadeBean {
	private Integer id;
	private String nome;
	private SistemaBean sistema;
	private String observacao;
	private Boolean contem = false;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public SistemaBean getSistema() {
		return sistema;
	}

	public void setSistema(SistemaBean sistema) {
		this.sistema = sistema;
	}

	public Boolean getContem() {
		return contem;
	}

	public void setContem(Boolean contem) {
		this.contem = contem;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
}