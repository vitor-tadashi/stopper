package br.com.verity.pause.bean;

import org.springframework.stereotype.Component;

@Component
public class TipoAtestadoBean {
	private Integer id;
	private String descricao;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
