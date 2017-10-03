package br.com.verity.pause.bean;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class ArquivoApontamentoBean {
	private Integer id;
	private Date data;
	private String caminho;
	private Date dtInclusao;
	private Integer idEmpresa;
	private Integer idUsuarioInclusao;
	
	public ArquivoApontamentoBean(){
		
	}
	
	public ArquivoApontamentoBean(String caminho, Date dtInclusao, Integer idUsuarioInclusao, Integer idEmpresa){
		this.caminho = caminho;
		this.dtInclusao = dtInclusao;
		this.idUsuarioInclusao = idUsuarioInclusao;
		this.idEmpresa = idEmpresa;
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

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public Date getDtInclusao() {
		return dtInclusao;
	}

	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Integer getIdUsuarioInclusao() {
		return idUsuarioInclusao;
	}

	public void setIdUsuarioInclusao(Integer idUsuarioInclusao) {
		this.idUsuarioInclusao = idUsuarioInclusao;
	}

}
