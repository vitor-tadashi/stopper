package br.com.verity.pause.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ConsultaCompletaBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.integration.SavIntegration;
import br.com.verity.pause.util.GerarRelatorioXlsx;

@Service
public class FuncionarioBusiness {
	
	@Autowired
	private SavIntegration integration;
	
	@Autowired
	private GerarRelatorioXlsx gerarRelatorio;
	
	@Autowired
	private ApontamentoBusiness apontamentoBusiness;
	
	public List<FuncionarioBean> obterTodos(){
		return integration.getFuncionarios(65);
	}

	public String gerarRelatorio(Integer idFuncionario, String de, String ate) {
		FuncionarioBean funcionario = integration.getFuncionario(idFuncionario);
		List<ConsultaCompletaBean> consultaCompleta = new ArrayList<ConsultaCompletaBean>();
		String caminho;
		
		consultaCompleta = apontamentoBusiness.obterApontamentosPeriodoPorIdFuncionario(funcionario.getId(), de, ate);
		
		caminho = gerarRelatorio.relatorioFuncionarioPeriodo(consultaCompleta, funcionario, de, ate);
		
		return caminho;
	}
}
