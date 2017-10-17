package br.com.verity.pause.business;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ControleMensalBean;
import br.com.verity.pause.bean.FuncionarioBean;

@Service
public class ControleApontamentoBusiness {

	@Autowired
	private FuncionarioBusiness funcionarioBusiness;
	
	@Autowired
	private ControleMensalBusiness controleMensalBusiness;
	
	@Autowired
	private ControleDiarioBusiness controleDiarioBusiness;
	
	@Autowired
	private CalculoBusiness calculoBusiness;

	/**
	 * Criação de controle mensal e/ou controle diário.
	 * @param dataDeCriacao representa a data para que serão criados os controles diário e mensal 
	 */
	public void Criar(Date dataDeCriacao){
		List<FuncionarioBean> funcionarios = funcionarioBusiness.obterTodosComPis();
		ControleMensalBean controleMensalBean;
		
		for (FuncionarioBean funcionarioBean : funcionarios) {
			
			controleMensalBean = controleMensalBusiness.obterPorIdFuncionarioMesAnoDataDia(dataDeCriacao, funcionarioBean.getId());
			
			if(controleMensalBean == null){
				
				controleMensalBean = controleMensalBusiness.obterPorMesAnoIdFuncionario(dataDeCriacao, funcionarioBean.getId());
				controleDiarioBusiness.obterPorDataIdFuncionario(dataDeCriacao, funcionarioBean.getId());
				
			} else if (controleMensalBean.getControleDiario() == null || controleMensalBean.getControleDiario().size() == 0){
				
				controleDiarioBusiness.obterPorDataIdFuncionario(dataDeCriacao, funcionarioBean.getId());
				
			}
			calculoBusiness.calcularApontamento(funcionarioBean.getId(), dataDeCriacao);
		}
	}

}
