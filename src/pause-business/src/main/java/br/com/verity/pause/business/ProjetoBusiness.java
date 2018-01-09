package br.com.verity.pause.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ProjetoBean;
import br.com.verity.pause.integration.SavIntegration;

@Service
public class ProjetoBusiness {
	
	@Autowired
	SavIntegration savIntegration;
	
	public List<ProjetoBean> listProjetosPorFuncionarios(Integer idFuncionario) {
		
		List<ProjetoBean> projetos = savIntegration.listProjetosPorFuncionarios(idFuncionario);
		
		return projetos;
	}
}
