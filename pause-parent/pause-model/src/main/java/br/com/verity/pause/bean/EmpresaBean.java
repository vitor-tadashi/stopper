package br.com.verity.pause.bean;

import org.springframework.stereotype.Component;

@Component
public class EmpresaBean {
	private Integer id;
	private String cnpj;
	private String razaoSocial;
	private String nomeFantasia;
	private Boolean grupoVerity;
	private EnderecoBean endereco;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getNomeFantasia() {
		return nomeFantasia;
	}
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}
	public Boolean getGrupoVerity() {
		return grupoVerity;
	}
	public void setGrupoVerity(Boolean grupoVerity) {
		this.grupoVerity = grupoVerity;
	}
	public EnderecoBean getEndereco() {
		return endereco;
	}
	public void setEndereco(EnderecoBean endereco) {
		this.endereco = endereco;
	}
}
