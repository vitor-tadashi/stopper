package br.com.verity.pause.business;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.verity.pause.bean.DespesaBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.ProjetoBean;
import br.com.verity.pause.converter.DespesaConverter;
import br.com.verity.pause.dao.DespesaDAO;
import br.com.verity.pause.dao.StatusDAO;
import br.com.verity.pause.entity.DespesaEntity;
import br.com.verity.pause.entity.StatusEntity;
import br.com.verity.pause.entity.enumerator.StatusEnum;
import br.com.verity.pause.integration.SavIntegration;

@Service
public class DespesaBusiness {

	@Autowired
	DespesaDAO dao;
	
	@Autowired
	StatusDAO statusDao;

	@Autowired
	DespesaConverter converter;
	
	@Autowired
	private Environment ambiente;
	
	@Autowired
	SavIntegration integration;

	public DespesaBean salvaDespesa(DespesaBean despesa, MultipartFile multipartFile)
			throws Exception {

		DespesaEntity entity = converter.convertBeanToEntity(despesa);
		
		
		// Seta despesa como em análise, que é o estado inicial
		entity.setStatus(statusDao.findByName(StatusEnum.EM_ANALISE));
		entity.setDataSolicitacao(new Date());
		
		if (multipartFile != null) {
			String fileName = entity.getIdSolicitante() + "_" + System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
			entity.setCaminhoJustificativa(ambiente.getProperty("despesa.comprovante.path") + fileName);
			try{
				saveMultipartFile(multipartFile, entity, fileName);
	 			
			} catch (Exception e) {
				throw e;
			}
		}
		return converter.convertEntityToBean(dao.salvaDespesa(entity));
	}

	public void saveMultipartFile(MultipartFile multipart, DespesaEntity despesa, String fileName) throws IOException {
		if (!multipart.getOriginalFilename().isEmpty()) {
			BufferedOutputStream outputStream = null;
			try {
				outputStream = new BufferedOutputStream(
						new FileOutputStream(new File(ambiente.getProperty("despesa.comprovante.path"), fileName)));
				outputStream.write(multipart.getBytes());
				outputStream.flush();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (outputStream != null){
					outputStream.close();
				}
			}
		}
	}
	
	public List<DespesaBean> listarDespesasPorFuncionario(Integer idFuncionario) {
		List<DespesaEntity> despesas = dao.listarDespesaPorFuncionario(idFuncionario);
		List<DespesaBean> despesasBean = despesaEntityToDespesaBeanComProjetoEFuncionario(despesas);
		return despesasBean;
	}

	private List<DespesaBean> despesaEntityToDespesaBeanComProjetoEFuncionario(List<DespesaEntity> despesas) {
		List<DespesaBean> despesasBean = new ArrayList<>();
		for (DespesaEntity despesa : despesas) {
			DespesaBean despesaBean = converter.convertEntityToBean(despesa);
			preencherProjetoDespesa(despesa, despesaBean);
			preencherFuncionarioDespesa(despesa, despesaBean);
			despesasBean.add(despesaBean);
		}
		return despesasBean;
	}

	private void preencherFuncionarioDespesa(DespesaEntity despesa, DespesaBean despesaBean) {
		FuncionarioBean b = integration.getFuncionario(despesa.getIdSolicitante().intValue());
		despesaBean.setNomeFuncionario(b.getNome());
	}

	private void preencherProjetoDespesa(DespesaEntity despesa, DespesaBean despesaBean) {
		ProjetoBean projeto = integration.getProjetoById(despesa.getIdProjeto());
		despesaBean.setDescricaoProjeto(projeto.getNome());
	}

	public void salvarAnaliseDespesa(Long idDespesa, Long idAprovador, String fgAprovador, boolean despesaAprovada) throws Exception {
		StatusEntity status = null;
		if (despesaAprovada) {
			status = statusDao.findByName(StatusEnum.APROVADO);
		} else {
			status = statusDao.findByName(StatusEnum.REPROVADO);
		}
		if ("G".equalsIgnoreCase(fgAprovador)) {
			dao.salvarAnaliseDespesaGP(idDespesa, idAprovador, status.getId());
		} else if ("F".equalsIgnoreCase(fgAprovador)) {
			dao.salvarAnaliseDespesaFinanceiro(idDespesa, idAprovador, status.getId());
		} else {
			throw new IllegalArgumentException("Flag aprovador não identificada!");
		}
	}

	public List<DespesaBean> buscarDespesasParaAnalise(Integer idFuncionarioAnalisador, String fgFinanceiroGP) {
		List<DespesaBean> despesasBean = new ArrayList<>();
		StatusEntity statusEmAnalise = statusDao.findByName(StatusEnum.EM_ANALISE);
		if ("G".equalsIgnoreCase(fgFinanceiroGP)) {
			List<ProjetoBean> projetosGestor = integration.listProjetosPorGestor(idFuncionarioAnalisador);
			for (ProjetoBean projetoBean : projetosGestor) {
				List<DespesaEntity> despesas = dao.listarDespesaPorStatusPorProjeto(statusEmAnalise, projetoBean.getId());
				despesasBean.addAll(despesaEntityToDespesaBeanComProjetoEFuncionario(despesas));
			}
		} else if ("F".equalsIgnoreCase(fgFinanceiroGP)) {
			List<DespesaEntity> despesas = dao.listarDespesaPorStatusPendenteAnaliseFinaceiro(statusEmAnalise);
			despesasBean = despesaEntityToDespesaBeanComProjetoEFuncionario(despesas);
		} else {
			throw new IllegalArgumentException("Flag aprovador não identificada!");
		}
		return despesasBean;
	}
	
	public DespesaBean buscarDespesa(Long id) {
		DespesaEntity despesaEntity = dao.findById(id);
		DespesaBean despesaBean = converter.convertEntityToBean(despesaEntity);
		preencherProjetoDespesa(despesaEntity, despesaBean);
		preencherFuncionarioDespesa(despesaEntity, despesaBean);
		return despesaBean;
	}
}
