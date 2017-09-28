package br.com.verity.pause.bean;

import org.springframework.stereotype.Component;

@Component
public class ConsultaApontamentosBean {
	private Integer idFuncionario;
	private String nmFuncionario;
	private ControleDiarioBean controleDiario;

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getNmFuncionario() {
		return nmFuncionario;
	}

	public void setNmFuncionario(String nmFuncionario) {
		this.nmFuncionario = nmFuncionario;
	}

	public ControleDiarioBean getControleDiario() {
		return controleDiario;
	}

	public void setControleDiario(ControleDiarioBean controleDiario) {
		this.controleDiario = controleDiario;
	}

}