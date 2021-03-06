package br.com.verity.pause.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	private Double sobreAvisoTrabalhado;
	private Double qtdAtestadoHoras;
	private Integer idFuncionario;
	private Boolean mesFechado;
	private ControleMensalBean controleMensal;

	private String qtdAtestadoHorasString;
	private String horaTotalString;
	private String bancoHoraString;
	private String adicNoturnoString;
	private String sobreAvisoString;
	private String sobreAvisoTrabalhadoString;
	
	public String getQtdAtestadoHorasString() {
		return qtdAtestadoHorasString;
	}

	public void setQtdAtestadoHorasString(String qtdAtestadoHorasString) {
		this.qtdAtestadoHorasString = qtdAtestadoHorasString;
	}

	public String getHoraTotalString() {
		return horaTotalString;
	}

	public void setHoraTotalString(String horaTotalString) {
		this.horaTotalString = horaTotalString;
	}

	public String getBancoHoraString() {
		return bancoHoraString;
	}

	public void setBancoHoraString(String bancoHoraString) {
		this.bancoHoraString = bancoHoraString;
	}

	public String getAdicNoturnoString() {
		return adicNoturnoString;
	}

	public void setAdicNoturnoString(String adicNoturnoString) {
		this.adicNoturnoString = adicNoturnoString;
	}

	public String getSobreAvisoString() {
		return sobreAvisoString;
	}

	public void setSobreAvisoString(String sobreAvisoString) {
		this.sobreAvisoString = sobreAvisoString;
	}

	public String getSobreAvisoTrabalhadoString() {
		return sobreAvisoTrabalhadoString;
	}

	public void setSobreAvisoTrabalhadoString(String sobreAvisoTrabalhadoString) {
		this.sobreAvisoTrabalhadoString = sobreAvisoTrabalhadoString;
	}

	public ControleDiarioBean(){
	}
	
	public ControleDiarioBean(Double horaTotal, Double bancoHora, Double adicNoturno, Double sobreAviso, Double sobreAvisoTrabalhado){
		this.horaTotal = horaTotal;
		this.bancoHora = bancoHora;
		this.adicNoturno = adicNoturno;
		this.sobreAviso = sobreAviso;
		this.sobreAvisoTrabalhado = sobreAvisoTrabalhado;
	}

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

	public void setDataJson(String data) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.data = formatter.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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

	public Boolean getMesFechado() {
		return mesFechado;
	}

	public void setMesFechado(Boolean mesFechado) {
		this.mesFechado = mesFechado;
	}

	public ControleMensalBean getControleMensal() {
		return controleMensal;
	}

	public void setControleMensal(ControleMensalBean controleMensal) {
		this.controleMensal = controleMensal;
	}

	public Double getSobreAvisoTrabalhado() {
		return sobreAvisoTrabalhado;
	}

	public void setSobreAvisoTrabalhado(Double sobreAvisoTrabalhado) {
		this.sobreAvisoTrabalhado = sobreAvisoTrabalhado;
	}

}
