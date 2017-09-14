package br.com.verity.pause.bean;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ControleMensalBean {
	private Integer id;
	private Double horaTotal;
	private Double bancoHora;
	private Double adicionalNoturno;
	private Double sobreAviso;
	private Double sobreAvisoTrabalhado;
	private Integer mes;
	private Integer ano;
	private Integer idFuncionario;
	private List<ControleDiarioBean> controleDiario;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getHoraTotal() {
		return horaTotal;
	}

	public void setHoraTotal(Double horaTotal) {
		this.horaTotal = horaTotal;
	}

	public Double getBancoHora() {
		return bancoHora;
	}

	public void setBancoHora(Double bancoHora) {
		this.bancoHora = bancoHora;
	}

	public Double getAdicionalNoturno() {
		return adicionalNoturno;
	}

	public void setAdicionalNoturno(Double adicionalNoturno) {
		this.adicionalNoturno = adicionalNoturno;
	}

	public Double getSobreAviso() {
		return sobreAviso;
	}

	public void setSobreAviso(Double sobreAviso) {
		this.sobreAviso = sobreAviso;
	}

	public Double getSobreAvisoTrabalhado() {
		return sobreAvisoTrabalhado;
	}

	public void setSobreAvisoTrabalhado(Double sobreAvisoTrabalhado) {
		this.sobreAvisoTrabalhado = sobreAvisoTrabalhado;
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

	public List<ControleDiarioBean> getControleDiario() {
		return controleDiario;
	}

	public void setControleDiario(List<ControleDiarioBean> controleDiario) {
		this.controleDiario = controleDiario;
	}

}