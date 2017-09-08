package br.com.verity.pause.entity;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class ControleDiarioEntity {
	private Integer id;
	private Date data;
	private ControleMensalEntity controleMensal;
	private Integer idFuncionario;

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

	public ControleMensalEntity getControleMensal() {
		return controleMensal;
	}

	public void setControleMensal(ControleMensalEntity controleMensal) {
		this.controleMensal = controleMensal;
	}

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

}
