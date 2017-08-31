
package br.com.verity.pause.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String matricula;
	private String nome;
	private String cpf;
	private String rg;
	private Boolean ativo;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataInativo;
	private String telefone;
	private String pis;
	private String celular;
	private String emailPessoal;
	private String emailCorporativo;
	private EnderecoBean endereco;
	private GeneroBean genero;
	private LocalDeTrabalhoBean localDeTrabalho;
	private EmpresaBean empresa;
	private List<FuncionarioCargoBean> funcionarioCargo;
	private List<ApontamentoBean> apontamentos;
	private String mensagem;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Date getDataInativo() {
		return dataInativo;
	}

	public void setDataInativo(Date dataInativo) {
		this.dataInativo = dataInativo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmailPessoal() {
		return emailPessoal;
	}

	public void setEmailPessoal(String emailPessoal) {
		this.emailPessoal = emailPessoal;
	}

	public EnderecoBean getEndereco() {
		return endereco;
	}

	public void setEndereco(EnderecoBean endereco) {
		this.endereco = endereco;
	}

	public String getEmailCorporativo() {
		return emailCorporativo;
	}

	public void setEmailCorporativo(String emailCorporativo) {
		this.emailCorporativo = emailCorporativo;
	}

	public GeneroBean getGenero() {
		return genero;
	}

	public void setGenero(GeneroBean genero) {
		this.genero = genero;
	}

	public LocalDeTrabalhoBean getLocalDeTrabalho() {
		return localDeTrabalho;
	}

	public void setLocalDeTrabalho(LocalDeTrabalhoBean localDeTrabalho) {
		this.localDeTrabalho = localDeTrabalho;
	}

	public List<FuncionarioCargoBean> getFuncionarioCargo() {
		return funcionarioCargo;
	}

	public void setFuncionarioCargo(List<FuncionarioCargoBean> funcionarioCargo) {
		this.funcionarioCargo = funcionarioCargo;
	}

	public EmpresaBean getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaBean empresa) {
		this.empresa = empresa;
	}

	public List<ApontamentoBean> getApontamentos() {
		return apontamentos;
	}

	public void setApontamentos(List<ApontamentoBean> apontamentos) {
		this.apontamentos = apontamentos;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}