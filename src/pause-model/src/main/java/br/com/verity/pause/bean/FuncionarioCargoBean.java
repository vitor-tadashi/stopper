package br.com.verity.pause.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioCargoBean {
	private Integer id;
	private CargoBean cargo;
	private NivelBean nivel;
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dtInicio;
	private Boolean ativo;
	private String observacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CargoBean getCargo() {
		return cargo;
	}

	public void setCargo(CargoBean cargo) {
		this.cargo = cargo;
	}

	public NivelBean getNivel() {
		return nivel;
	}

	public void setNivel(NivelBean nivel) {
		this.nivel = nivel;
	}

	public Date getDtInicio() {
		return dtInicio;
	}

	public void setDtInicio(Date dtInicio) {
		this.dtInicio = dtInicio;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

}
