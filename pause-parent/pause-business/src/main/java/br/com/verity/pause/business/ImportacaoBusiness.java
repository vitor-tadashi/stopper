package br.com.verity.pause.business;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.converter.ApontamentoConverter;
import br.com.verity.pause.dao.ApontamentosDAO;
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
	private ApontamentosDAO apontamentoDao;

	@Autowired
	private ApontamentoConverter apontamentoConverter;

	public List<FuncionarioBean> importarTxt(String caminho, int idEmpresa) throws BusinessException, IOException, ParseException {
		List<FuncionarioBean> funcionarios = new ArrayList<FuncionarioBean>();
		List<FuncionarioBean> funcionariosComApontamentos = new ArrayList<FuncionarioBean>();
		FuncionarioBean funcMensagem = new FuncionarioBean();
		List<ApontamentoBean> apontamento = new ArrayList<ApontamentoBean>();
		Boolean verificarImportacao;

		try {
			apontamento = importar.importar(caminho, idEmpresa);
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
		}
		
		try {
			verificarImportacao = apontamentoDao.findByData(apontamentoConverter.convertBeanToEntity(apontamento.get(0)), 2);
			
			if(verificarImportacao){
				funcMensagem.setMensagem("Já foi importado arquivo com está data.");
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

	public void salvarApontamentos(List<ApontamentoBean> apontamentos) {
		try {
			apontamentoDao.excludeAllDate(apontamentos.get(0).getData());
			apontamentoDao.saveAll(apontamentoConverter.convertBeanToEntity(apontamentos));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}