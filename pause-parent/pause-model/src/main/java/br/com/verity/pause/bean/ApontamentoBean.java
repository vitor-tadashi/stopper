package br.com.verity.pause.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.stereotype.Component;
/**
 * @author guilherme.oliveira
 *
 */
@Component
public class ApontamentoBean {

	private Integer id;
	private String pis;
	private Date data;
	private LocalTime horario;
	private Boolean tipoImportacao = true;
	private Date dataInclusao;
	private String observacao;
	private TipoJustificativaBean tpJustificativa;
	private ControleDiarioBean cntrDiario;
	private Integer idEmpresa;
	private Integer idUsuarioInclusao;
	private ArquivoApontamentoBean arqApontamento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}
	public Date getData() {
		return data;
	}
	public void setDataJson(String dataJson) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			this.data = formatter.parse(dataJson);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public void setData(Date data) {
		this.data = data;
	}
	public LocalTime getHorario() {
		return horario;
	}
	public void setHorarioJson(String horarioJson) {
		this.horario = LocalTime.parse(horarioJson);
	}
	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}
	public Boolean getTipoImportacao() {
		return tipoImportacao;
	}

	public void setTipoImportacao(Boolean tipoImportacao) {
		this.tipoImportacao = tipoImportacao;
	}

	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public TipoJustificativaBean getTpJustificativa() {
		return tpJustificativa;
	}
	public void setTpJustificativa(TipoJustificativaBean tpJustificativa) {
		this.tpJustificativa = tpJustificativa;
	}
	public void setTpJustificativaJson(Integer id) {
		this.tpJustificativa.setId(id);
	}
	public ControleDiarioBean getCntrDiario() {
		return cntrDiario;
	}

	public void setCntrDiario(ControleDiarioBean cntrDiario) {
		this.cntrDiario = cntrDiario;
	}

	public Integer getIdUsuarioInclusao() {
		return idUsuarioInclusao;
	}

	public void setIdUsuarioInclusao(Integer idUsuarioInclusao) {
		this.idUsuarioInclusao = idUsuarioInclusao;
	}

	public ArquivoApontamentoBean getArqApontamento() {
		return arqApontamento;
	}

	public void setArqApontamento(ArquivoApontamentoBean arqApontamento) {
		this.arqApontamento = arqApontamento;
	}

}