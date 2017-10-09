package br.com.verity.pause.entity;

import java.sql.Date;
import java.sql.Time;

import org.springframework.stereotype.Component;

@Component
public class ApontamentoPivotEntity {

	private int idFuncionario;

	private Date dataApontamento;

	private Time entrada1;

	private Time entrada2;

	private Time entrada3;

	private Time entrada4;

	private Time saida1;

	private Time saida2;

	private Time saida3;

	private Time saida4;

	private Double totalHoras;
	private Double totalHorasDiarias;
	private Double horasExtras;
	private Double totalSobreAvisoTrabalhado;
	private Double totalSobreAviso;
	private Double totalAdicionalNoturno;

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Date getDataApontamento() {
		return dataApontamento;
	}

	public void setDataApontamento(Date dataApontamento) {
		this.dataApontamento = dataApontamento;
	}

	public Time getEntrada1() {
		return entrada1;
	}

	public void setEntrada1(Time entrada1) {
		this.entrada1 = entrada1;
	}

	public Time getEntrada2() {
		return entrada2;
	}

	public void setEntrada2(Time entrada2) {
		this.entrada2 = entrada2;
	}

	public Time getEntrada3() {
		return entrada3;
	}

	public void setEntrada3(Time entrada3) {
		this.entrada3 = entrada3;
	}

	public Time getEntrada4() {
		return entrada4;
	}

	public void setEntrada4(Time entrada4) {
		this.entrada4 = entrada4;
	}

	public Time getSaida1() {
		return saida1;
	}

	public void setSaida1(Time saida1) {
		this.saida1 = saida1;
	}

	public Time getSaida2() {
		return saida2;
	}

	public void setSaida2(Time saida2) {
		this.saida2 = saida2;
	}

	public Time getSaida3() {
		return saida3;
	}

	public void setSaida3(Time saida3) {
		this.saida3 = saida3;
	}

	public Time getSaida4() {
		return saida4;
	}

	public void setSaida4(Time saida4) {
		this.saida4 = saida4;
	}

	public Double getTotalHoras() {
		return totalHoras;
	}

	public void setTotalHoras(Double totalHoras) {
		this.totalHoras = totalHoras;
	}

	public Double getTotalHorasDiarias() {
		return totalHorasDiarias;
	}

	public void setTotalHorasDiarias(Double totalHorasDiarias) {
		this.totalHorasDiarias = totalHorasDiarias;
	}

	public Double getHorasExtras() {
		return horasExtras;
	}

	public void setHorasExtras(Double horasExtras) {
		this.horasExtras = horasExtras;
	}

	public Double getTotalSobreAvisoTrabalhado() {
		return totalSobreAvisoTrabalhado;
	}

	public void setTotalSobreAvisoTrabalhado(Double totalSobreAvisoTrabalhado) {
		this.totalSobreAvisoTrabalhado = totalSobreAvisoTrabalhado;
	}

	public Double getTotalSobreAviso() {
		return totalSobreAviso;
	}

	public void setTotalSobreAviso(Double totalSobreAviso) {
		this.totalSobreAviso = totalSobreAviso;
	}

	public Double getTotalAdicionalNoturno() {
		return totalAdicionalNoturno;
	}

	public void setTotalAdicionalNoturno(Double totalAdicionalNoturno) {
		this.totalAdicionalNoturno = totalAdicionalNoturno;
	}

}
