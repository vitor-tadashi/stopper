package br.com.verity.pause.bean;

import java.sql.Date;
import java.sql.Time;

import org.springframework.stereotype.Component;

@Component
public class ApontamentoPivotBean {
	
	public ApontamentoPivotBean(){
		
	}
	
	public ApontamentoPivotBean(int idFuncionario, Date dataApontamento, Time entrada1, Time entrada2, Time entrada3,
			Time entrada4, Time saida1, Time saida2, Time saida3, Time saida4, int idEntrada1, int idSaida1,
			int idEntrada2, int idSaida2, int idEntrada3, int idSaida3, int idEntrada4, int idSaida4, int tipoEntrada1, int tipoSaida1, int tipoEntrada2, int tipoSaida2, int tipoEntrada3, int tipoSaida3, int tipoEntrada4, int tipoSaida4) {
		super();
		this.idFuncionario = idFuncionario;
		this.dataApontamento = dataApontamento;
		this.entrada1 = entrada1;
		this.entrada2 = entrada2;
		this.entrada3 = entrada3;
		this.entrada4 = entrada4;
		this.saida1 = saida1;
		this.saida2 = saida2;
		this.saida3 = saida3;
		this.saida4 = saida4;
		this.idEntrada1 = idEntrada1;
		this.idSaida1 = idSaida1;
		this.idEntrada2 = idEntrada2;
		this.idSaida2 = idSaida2;
		this.idEntrada3 = idEntrada3;
		this.idSaida3 = idSaida3;
		this.idEntrada4 = idEntrada4;
		this.idSaida4 = idSaida4;
		this.tipoEntrada1 = tipoEntrada1;
		this.tipoSaida1 = tipoSaida1;
		this.tipoEntrada2 = tipoEntrada2;
		this.tipoSaida2 = tipoSaida2;
		this.tipoEntrada3 = tipoEntrada3;
		this.tipoSaida3 = tipoSaida3;
		this.tipoEntrada4 = tipoEntrada4;
		this.tipoSaida4 = tipoSaida4;
	}

	public ApontamentoPivotBean(int idFuncionario, Date dataApontamento, Time entrada1, Time entrada2, Time entrada3,
			Time entrada4, Time saida1, Time saida2, Time saida3, Time saida4) {
		super();
		this.idFuncionario = idFuncionario;
		this.dataApontamento = dataApontamento;
		this.entrada1 = entrada1;
		this.entrada2 = entrada2;
		this.entrada3 = entrada3;
		this.entrada4 = entrada4;
		this.saida1 = saida1;
		this.saida2 = saida2;
		this.saida3 = saida3;
		this.saida4 = saida4;			
	}

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

	private int idEntrada1;
	private int idSaida1;
	private int idEntrada2;
	private int idSaida2;
	private int idEntrada3;
	private int idSaida3;
	private int idEntrada4;
	private int idSaida4;
	private int tipoEntrada1;
	private int tipoSaida1;
	private int tipoEntrada2;
	private int tipoSaida2;
	private int tipoEntrada3;
	private int tipoSaida3;
	private int tipoEntrada4;
	private int tipoSaida4;

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
	
	public int getIdEntrada1() {
		return idEntrada1;
	}

	public void setIdEntrada1(int idEntrada1) {
		this.idEntrada1 = idEntrada1;
	}

	public int getIdSaida1() {
		return idSaida1;
	}

	public void setIdSaida1(int idSaida1) {
		this.idSaida1 = idSaida1;
	}

	public int getIdEntrada2() {
		return idEntrada2;
	}

	public void setIdEntrada2(int idEntrada2) {
		this.idEntrada2 = idEntrada2;
	}

	public int getIdSaida2() {
		return idSaida2;
	}

	public void setIdSaida2(int idSaida2) {
		this.idSaida2 = idSaida2;
	}

	public int getIdEntrada3() {
		return idEntrada3;
	}

	public void setIdEntrada3(int idEntrada3) {
		this.idEntrada3 = idEntrada3;
	}

	public int getIdSaida3() {
		return idSaida3;
	}

	public void setIdSaida3(int idSaida3) {
		this.idSaida3 = idSaida3;
	}

	public int getIdEntrada4() {
		return idEntrada4;
	}

	public void setIdEntrada4(int idEntrada4) {
		this.idEntrada4 = idEntrada4;
	}

	public int getIdSaida4() {
		return idSaida4;
	}

	public void setIdSaida4(int idSaida4) {
		this.idSaida4 = idSaida4;
	}
	
	public int getTipoEntrada1() {
		return tipoEntrada1;
	}

	public void setTipoEntrada1(int tipoEntrada1) {
		this.tipoEntrada1 = tipoEntrada1;
	}

	public int getTipoSaida1() {
		return tipoSaida1;
	}

	public void setTipoSaida1(int tipoSaida1) {
		this.tipoSaida1 = tipoSaida1;
	}

	public int getTipoEntrada2() {
		return tipoEntrada2;
	}

	public void setTipoEntrada2(int tipoEntrada2) {
		this.tipoEntrada2 = tipoEntrada2;
	}

	public int getTipoSaida2() {
		return tipoSaida2;
	}

	public void setTipoSaida2(int tipoSaida2) {
		this.tipoSaida2 = tipoSaida2;
	}

	public int getTipoEntrada3() {
		return tipoEntrada3;
	}

	public void setTipoEntrada3(int tipoEntrada3) {
		this.tipoEntrada3 = tipoEntrada3;
	}

	public int getTipoSaida3() {
		return tipoSaida3;
	}

	public void setTipoSaida3(int tipoSaida3) {
		this.tipoSaida3 = tipoSaida3;
	}

	public int getTipoEntrada4() {
		return tipoEntrada4;
	}

	public void setTipoEntrada4(int tipoEntrada4) {
		this.tipoEntrada4 = tipoEntrada4;
	}

	public int getTipoSaida4() {
		return tipoSaida4;
	}

	public void setTipoSaida4(int tipoSaida4) {
		this.tipoSaida4 = tipoSaida4;
	}
}
