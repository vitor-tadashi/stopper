package br.com.verity.pause.bean;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UsuarioBean {
	private Integer id;
	private String usuario;
	private String senha;
	private Boolean ativo;
	private FuncionarioIntegrationBean funcionario;
	private List<PerfilBean> perfis;

	private Integer idEmpresaSessao;

	public UsuarioBean(UsuarioBean usuario) {
		super();
		this.id = usuario.getId();
		this.usuario = usuario.getUsuario();
		this.senha = usuario.getSenha();
		this.ativo = usuario.getAtivo();
		this.funcionario = usuario.getFuncionario();
		this.perfis = usuario.getPerfis();
	}

	public UsuarioBean() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public FuncionarioIntegrationBean getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioIntegrationBean funcionario) {
		this.funcionario = funcionario;
	}

	public List<PerfilBean> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<PerfilBean> perfis) {
		this.perfis = perfis;
	}

	public Integer getIdEmpresaSessao() {
		return idEmpresaSessao;
	}

	public void setIdEmpresaSessao(Integer idEmpresaSessao) {
		this.idEmpresaSessao = idEmpresaSessao;
	}
}