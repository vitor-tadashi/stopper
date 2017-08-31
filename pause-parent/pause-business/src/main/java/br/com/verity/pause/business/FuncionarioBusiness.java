package br.com.verity.pause.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.integration.SavIntegration;

@Service
public class FuncionarioBusiness {
	
	@Autowired
	private SavIntegration integration;
	
	public List<FuncionarioBean> obterTodos(){
		return integration.getFuncionarios(2);
	}

	public void gerarRelatorio(Integer idFuncionario, String de, String ate) {
		FuncionarioBean funcionario = integration.getFuncionario(idFuncionario);
		
		
		
		
		System.out.println("aqui");
	}
}
