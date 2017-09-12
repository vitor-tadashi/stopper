package br.com.verity.pause.entity;

import org.springframework.stereotype.Component;

@Component
public class ControleMensalEntity {
	private Integer id;
	private Integer mes;
	private Integer ano;
	private Integer idFuncionario;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMes() {
		return mes;
	}
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public Integer getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
}
