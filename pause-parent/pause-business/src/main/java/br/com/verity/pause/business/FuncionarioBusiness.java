package br.com.verity.pause.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return integration.getFuncionarios(2);
	}

	public void gerarRelatorio(Integer idFuncionario, String de, String ate) {
		FuncionarioBean funcionario = integration.getFuncionario(idFuncionario);
		
		funcionario.setApontamentos(apontamentoBusiness.obterApontamentosPeriodoPorPisFuncionario(funcionario.getPis(), de, ate));
		
		gerarRelatorio.relatorioFuncionarioPeriodo(funcionario, de, ate);
		
		System.out.println(funcionario.getNome());
	}

	public List<FuncionarioBean> listarFuncionariosPorEmpresaComPis() {
		return integration.getFuncionarios(2);
	}
}
