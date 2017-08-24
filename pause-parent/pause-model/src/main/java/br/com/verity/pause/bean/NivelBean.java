package br.com.verity.pause.bean;

import org.springframework.stereotype.Component;

@Component
public class NivelBean {
	private Integer id;
	private String nivel;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

}
