package br.com.verity.pause.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
public class AfastamentoBean {
	private Integer id;
	private Date dataInicio;
	private Date dataFim;
	private Date dataInclusao;
	private Integer idFuncionario;
	private TipoAfastamentoBean tipoAfastamento;
	private Integer idUsuarioInclusao;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}
	public void setDataInicio(String dataInicio) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.dataInicio = formatter.parse(dataInicio);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public void setDataFim(String dataFim) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.dataFim = formatter.parse(dataFim);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	public Integer getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	public TipoAfastamentoBean getTipoAfastamento() {
		return tipoAfastamento;
	}
	public void setTipoAfastamento(TipoAfastamentoBean tipoAfastamento) {
		this.tipoAfastamento = tipoAfastamento;
	}
	public Integer getIdUsuarioInclusao() {
		return idUsuarioInclusao;
	}
	public void setIdUsuarioInclusao(Integer idUsuarioInclusao) {
		this.idUsuarioInclusao = idUsuarioInclusao;
	}
}
