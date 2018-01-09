package br.com.verity.pause.bean;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

@Component
public class ProjetoBean {
	private Integer id;

	private String codigo;

	private String nome;
	
	private EmpresaBean empresa;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataInicio;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataFim;

	private Integer tempoGarantia;
	private Boolean ativo;
	private List<FuncionarioBean> funcionarios;
	private Integer centroCustoProject;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public EmpresaBean getEmpresa() {
		return empresa;
	}

	public void setEmpresa(EmpresaBean empresa) {
		this.empresa = empresa;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Integer getTempoGarantia() {
		return tempoGarantia;
	}

	public void setTempoGarantia(Integer tempoGarantia) {
		this.tempoGarantia = tempoGarantia;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
	public List<FuncionarioBean> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<FuncionarioBean> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public Integer getCentroCustoProject() {
		return centroCustoProject;
	}

	public void setCentroCustoProject(Integer centroCustoProject) {
		this.centroCustoProject = centroCustoProject;
	}
}
