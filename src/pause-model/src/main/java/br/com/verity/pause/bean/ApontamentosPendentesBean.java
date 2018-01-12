package br.com.verity.pause.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ApontamentosPendentesBean {

	private Integer id;
	private Date data;
	private Integer idEmpresa;
	private Integer idFuncionario;
	private Integer diasSemApontar;
	private String nomeFuncionario;
	private String emailFuncionario;

	public ApontamentosPendentesBean() {

	}

	public ApontamentosPendentesBean(Integer id, Date data, Integer idEmpresa, Integer idFuncionario,
			Integer diasSemApontar, String nomeFuncionario, String emailFuncionario) {
		this.id = id;
		this.data = data;
		this.idEmpresa = idEmpresa;
		this.idFuncionario = idFuncionario;
		this.diasSemApontar = diasSemApontar;
		this.nomeFuncionario = nomeFuncionario;
		this.emailFuncionario = emailFuncionario;
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

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public Integer getDiasSemApontar() {
		return diasSemApontar;
	}

	public void setDiasSemApontar(Integer diasSemApontar) {
		this.diasSemApontar = diasSemApontar;
	}

	public void setDataJson(String dataJson) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.data = formatter.parse(dataJson);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public String getEmailFuncionario() {
		return emailFuncionario;
	}

	public void setEmailFuncionario(String emailFuncionario) {
		this.emailFuncionario = emailFuncionario;
	}
}