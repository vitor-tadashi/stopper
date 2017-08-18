package br.com.verity.regponto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REGPONTOEmpresa")
public class EmpresaEntity {
	@Id
	@Column(name = "idEmpresa")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nmEmpresa")
	private String nmEmpresa;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNmEmpresa() {
		return nmEmpresa;
	}

	public void setNmEmpresa(String nmEmpresa) {
		this.nmEmpresa = nmEmpresa;
	}

}