package br.com.verity.regponto.bean;

import org.springframework.stereotype.Component;

@Component
public class LocalDeTrabalhoBean {

	private Integer id;
	private String local;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

}
