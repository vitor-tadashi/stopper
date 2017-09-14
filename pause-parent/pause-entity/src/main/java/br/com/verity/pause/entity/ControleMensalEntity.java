package br.com.verity.pause.entity;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ControleMensalEntity {
	private Integer id;
	private Double hrTotal;
	private Double bancoHora;
	private Double adcNoturno;
	private Double sobreAviso;
	private Double sobreAvisoTrabalhado;
	private Integer mes;
	private Integer ano;
	private Integer idFuncionario;
	private List<ControleDiarioEntity> controleDiario;

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

	public Double getHrTotal() {
		return hrTotal;
	}

	public void setHrTotal(Double hrTotal) {
		this.hrTotal = hrTotal;
	}

	public Double getBancoHora() {
		return bancoHora;
	}

	public void setBancoHora(Double bancoHora) {
		this.bancoHora = bancoHora;
	}

	public Double getAdcNoturno() {
		return adcNoturno;
	}

	public void setAdcNoturno(Double adcNoturno) {
		this.adcNoturno = adcNoturno;
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

	public List<ControleDiarioEntity> getControleDiario() {
		return controleDiario;
	}

	public void setControleDiario(List<ControleDiarioEntity> controleDiario) {
		this.controleDiario = controleDiario;
	}

}
