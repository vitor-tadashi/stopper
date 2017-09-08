package br.com.verity.pause.bean;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class ControleDiarioBean {
	private Integer id;
	private Date data;
	private String diaDaSemana;
	private List<ApontamentoBean> apontamentos;
	private Double horaTotal;
	private Double bancoHora;
	private Double adicNoturno;
	private Double sobreAviso;
	private Double qtdAtestadoHoras;
	private Integer idFuncionario;
	private ControleMensalBean controleMensal;

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

	public String getDiaDaSemana() {
		return diaDaSemana;
	}

	public void setDiaDaSemana(String diaDaSemana) {
		this.diaDaSemana = diaDaSemana;
	}

	public List<ApontamentoBean> getApontamentos() {
		return apontamentos;
	}

	public void setApontamentos(List<ApontamentoBean> apontamentos) {
		this.apontamentos = apontamentos;
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

	public Double getAdicNoturno() {
		return adicNoturno;
	}

	public void setAdicNoturno(Double adicNoturno) {
		this.adicNoturno = adicNoturno;
	}

	public Double getSobreAviso() {
		return sobreAviso;
	}

	public void setSobreAviso(Double sobreAviso) {
		this.sobreAviso = sobreAviso;
	}

	public Double getQtdAtestadoHoras() {
		return qtdAtestadoHoras;
	}

	public void setQtdAtestadoHoras(Double qtdAtestadoHoras) {
		this.qtdAtestadoHoras = qtdAtestadoHoras;
	}

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public ControleMensalBean getControleMensal() {
		return controleMensal;
	}

	public void setControleMensal(ControleMensalBean controleMensal) {
		this.controleMensal = controleMensal;
	}
}
