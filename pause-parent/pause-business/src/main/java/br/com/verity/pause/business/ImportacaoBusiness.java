package br.com.verity.pause.business;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.bean.ArquivoApontamentoBean;
import br.com.verity.pause.bean.EmpresaBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.converter.ApontamentoConverter;
import br.com.verity.pause.converter.ArquivoApontamentoConverter;
import br.com.verity.pause.converter.ControleDiarioConverter;
import br.com.verity.pause.dao.ApontamentoDAO;
import br.com.verity.pause.dao.ArquivoApontamentoDAO;
import br.com.verity.pause.entity.ApontamentoEntity;
import br.com.verity.pause.entity.ArquivoApontamentoEntity;
import br.com.verity.pause.entity.ControleDiarioEntity;
import br.com.verity.pause.exception.BusinessException;
import br.com.verity.pause.integration.SavIntegration;
import br.com.verity.pause.util.ImportarTxt;

@Service
public class ImportacaoBusiness {

	@Autowired
	private ImportarTxt importar;

	@Autowired
	private SavIntegration integration;

	@Autowired
	private ApontamentoDAO apontamentoDao;
	
	@Autowired
	private ArquivoApontamentoDAO arquivoApontamentoDao;

	@Autowired
	private ApontamentoConverter apontamentoConverter;
	
	@Autowired
	private ArquivoApontamentoConverter arquivoApontamentoConverter;
	
	@Autowired
	private CalculoBusiness calculoBusiness;
	
	@Autowired
	private ControleDiarioBusiness controleDiarioBusiness;
	
	@Autowired
	private ControleDiarioConverter controleDiarioConverter;

	public List<FuncionarioBean> importarTxt(String caminho, int idEmpresa) throws BusinessException, IOException, ParseException {
		List<FuncionarioBean> funcionarios = new ArrayList<FuncionarioBean>();
		List<FuncionarioBean> funcionariosComApontamentos = new ArrayList<FuncionarioBean>();
		FuncionarioBean funcMensagem = new FuncionarioBean();
		List<ApontamentoBean> apontamento = new ArrayList<ApontamentoBean>();
		Boolean verificarImportacao;
		EmpresaBean empresaBean = new EmpresaBean();

		try {
			empresaBean = integration.getEmpresa(idEmpresa);
			apontamento = importar.importar(caminho, empresaBean);
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
		}
		
		try {
			verificarImportacao = apontamentoDao.findByData(apontamentoConverter.convertBeanToEntity(apontamento.get(0)), idEmpresa);
			
			if(verificarImportacao){
				funcMensagem.setMensagem("Este arquivo já foi importado. Deseja substituí-lo?");
				funcionariosComApontamentos.add(funcMensagem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		funcionarios = integration.getFuncionarios(idEmpresa);

		for (FuncionarioBean bean : funcionarios) {

			bean.setApontamentos(apontamento.stream().filter(hr -> hr.getPis().equals(bean.getPis())).collect(Collectors.toList()));

			if (bean.getApontamentos() != null && bean.getApontamentos().size() > 0) {
				funcionariosComApontamentos.add(bean);
			}
		}

		return funcionariosComApontamentos;
	}

	public void salvarApontamentos(List<ApontamentoBean> apontamentos, ArquivoApontamentoBean arquivoApontamento) {
		ArquivoApontamentoEntity arquivoApontamentoEntity = arquivoApontamentoConverter.convertBeanToEntity(arquivoApontamento);
		List<ApontamentoEntity> apontamentoEntity = this.setControleDiarioBeanToEntity(apontamentos);
		Integer idArquivo = null;
		try {
			apontamentoDao.excludeAllDate(new java.sql.Date(arquivoApontamentoEntity.getData().getTime()));
			arquivoApontamentoDao.excludeDate(arquivoApontamentoEntity);
			arquivoApontamentoDao.save(arquivoApontamentoEntity);
			idArquivo = arquivoApontamentoDao.findByDateAndEmpresa(arquivoApontamentoEntity);
			apontamentoDao.saveAll(apontamentoEntity, idArquivo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private List<ApontamentoEntity> setControleDiarioBeanToEntity(List<ApontamentoBean> apontamentosBean) {
		List<ApontamentoEntity> apontamentos = new ArrayList<ApontamentoEntity>();
		ApontamentoEntity apontamento = null;
		ControleDiarioEntity controleDiario = null;
		
		for (ApontamentoBean bean : apontamentosBean) {
			apontamento = new ApontamentoEntity();
			apontamento = apontamentoConverter.convertBeanToEntity(bean);
			if(bean.getCntrDiario() != null && bean.getCntrDiario().getId() != null){
				controleDiario = new ControleDiarioEntity();
				controleDiario = controleDiarioConverter.convertBeanToEntity(bean.getCntrDiario());
			}
			apontamento.setControleDiario(controleDiario);
			apontamentos.add(apontamento);
		}
		
		return apontamentos;
	}

	public void acionarCalculos(List<FuncionarioBean> funcionariosImportacao) {
		Date data = null;
		for (FuncionarioBean funcionarioBean : funcionariosImportacao) {
			data = funcionarioBean.getApontamentos().get(0).getData();
			controleDiarioBusiness.obterPorDataIdFuncionario(data, funcionarioBean.getId());
			calculoBusiness.calcularApontamento(funcionarioBean.getId(), data);
		}
	}
}