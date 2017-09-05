package br.com.verity.pause.entity;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class ControleDiarioEntity {
	private Integer id;
	private Date data;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
