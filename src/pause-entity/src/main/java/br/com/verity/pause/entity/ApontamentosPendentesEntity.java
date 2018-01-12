package br.com.verity.pause.entity;

import java.sql.Date;
import org.springframework.stereotype.Component;

@Component
public class ApontamentosPendentesEntity {

	private Integer id;
	private Date data;
	private Integer idEmpresa;
	private Integer idFuncionario;
	private Integer diasSemApontar;
	
	public ApontamentosPendentesEntity() {
	}

	public ApontamentosPendentesEntity(Integer id, Date data, Integer idEmpresa, Integer idFuncionario,
			Integer diasSemApontar) {
		this.id = id;
		this.data = data;
		this.idEmpresa = idEmpresa;
		this.idFuncionario = idFuncionario;
		this.diasSemApontar = diasSemApontar;
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

}