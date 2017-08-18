package br.com.verity.regponto.bean;

import org.springframework.stereotype.Component;

@Component
public class GeneroBean {
	private Integer id;
	private String genero;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

}
