package br.com.verity.pause.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class AtestadoBean {
	private Integer id;
	private Double quantidadeHora;
	private Date dataInclusao;
	private Integer idUsuarioInclusao;
	private Integer idFuncionario;
	private ControleDiarioBean controleDiario;
	private TipoAtestadoBean tipoAtestado;
	private Boolean mesFechado;
	
	public Boolean getMesFechado() {
		return mesFechado;
	}
	public void setMesFechado(Boolean mesFechado) {
		this.mesFechado = mesFechado;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Double getQuantidadeHora() {
		return quantidadeHora;
	}
	public void setQuantidadeHora(Double quantidadeHora) {
		this.quantidadeHora = quantidadeHora;
	}
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	public Integer getIdUsuarioInclusao() {
		return idUsuarioInclusao;
	}
	public void setIdUsuarioInclusao(Integer idUsuarioInclusao) {
		this.idUsuarioInclusao = idUsuarioInclusao;
	}
	public Integer getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	public ControleDiarioBean getControleDiario() {
		return controleDiario;
	}
	public void setControleDiario(ControleDiarioBean controleDiario) {
		this.controleDiario = controleDiario;
	}
	public TipoAtestadoBean getTipoAtestado() {
		return tipoAtestado;
	}
	public void setTipoAtestado(TipoAtestadoBean tipoAtestado) {
		this.tipoAtestado = tipoAtestado;
	}
}
