package br.com.verity.pause.business;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.ApontamentosBean;
import br.com.verity.pause.converter.ApontamentosConverter;
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
	private ApontamentosDAO apontamentosDao;

	@Autowired
	private ApontamentosConverter apontamentosConverter;

	public List<FuncionarioBean> importarTxt(String caminho, String empresa) throws BusinessException {
		List<FuncionarioBean> funcionarios = new ArrayList<FuncionarioBean>();
		List<FuncionarioBean> funcionariosComApontamentos = new ArrayList<FuncionarioBean>();
		FuncionarioBean funcMensagem = new FuncionarioBean();
		List<ApontamentosBean> apontamentos = new ArrayList<ApontamentosBean>();
		Boolean verificarImportacao;

		try {
			apontamentos = importar.importar(caminho, empresa);
		} catch (BusinessException | ParseException e) {
			throw new BusinessException(e.getMessage());
		}
		
		try {
			verificarImportacao = apontamentosDao.findByData(apontamentosConverter.convertBeanToEntity(apontamentos.get(0)), empresa);
			
			if(verificarImportacao){
				funcMensagem.setMensagem("Já foi importado arquivo com está data.");
				funcionariosComApontamentos.add(funcMensagem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		funcionarios = integration.getFuncionarios(empresa);

		for (FuncionarioBean bean : funcionarios) {

			bean.setApontamentos(apontamentos.stream().filter(hr -> hr.getPis().equals(bean.getPis())).collect(Collectors.toList()));

			if (bean.getApontamentos() != null && bean.getApontamentos().size() > 0) {
				funcionariosComApontamentos.add(bean);
			}
		}

		return funcionariosComApontamentos;
	}

	public void salvarApontamentos(List<ApontamentosBean> apontamentos) {
		try {
			apontamentosDao.saveAll(apontamentosConverter.convertBeanToEntity(apontamentos));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}