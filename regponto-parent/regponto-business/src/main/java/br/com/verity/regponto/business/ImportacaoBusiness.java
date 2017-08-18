package br.com.verity.regponto.business;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.regponto.bean.FuncionarioBean;
import br.com.verity.regponto.bean.HorasBean;
import br.com.verity.regponto.integration.SavIntegration;
import br.com.verity.regponto.util.ImportarTxt;

@Service
public class ImportacaoBusiness {
	
	@Autowired
	private ImportarTxt importar;
	
	@Autowired
	private SavIntegration integration;

	public List<FuncionarioBean> importarTxt(String caminho) {
		List<FuncionarioBean> funcionarios = new ArrayList<FuncionarioBean>();
		List<FuncionarioBean> funcionariosComHoras = new ArrayList<FuncionarioBean>();
		List<HorasBean> horas = new ArrayList<HorasBean>();
		
		funcionarios = integration.getFuncionarios();
		horas = importar.importar(caminho);

		for(FuncionarioBean bean : funcionarios){
			
			bean.setHoras(horas.stream().filter(hr -> hr.getPis().equals(bean.getPis())).collect(Collectors.toList()));
			
			
			if(bean.getHoras()!= null && bean.getHoras().size() > 0){
				funcionariosComHoras.add(bean);
			}
		}
		
		return funcionariosComHoras;
	}
}