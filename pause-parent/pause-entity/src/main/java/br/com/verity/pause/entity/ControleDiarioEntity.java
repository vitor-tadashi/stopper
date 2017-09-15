package br.com.verity.pause.entity;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class ControleDiarioEntity {
	private Integer id;
	private Double hrTotal;
	private Double bancoHora;
	private Double adcNoturno;
	private Double sobreAviso;
	private Double sobreAvisoTrabalhado;
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

}