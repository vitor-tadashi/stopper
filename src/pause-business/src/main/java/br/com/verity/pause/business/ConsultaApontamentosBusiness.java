package br.com.verity.pause.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ConsultaApontamentosBean;
import br.com.verity.pause.bean.ControleDiarioBean;
import br.com.verity.pause.bean.FuncionarioBean;

@Service
public class ConsultaApontamentosBusiness {
	
	public List<ConsultaApontamentosBean> mesclarFuncionarioComControleDiario(List<FuncionarioBean> funcionarios,
			List<ControleDiarioBean> controleDiario) {
		List<ConsultaApontamentosBean> consultaApontamentos = new ArrayList<ConsultaApontamentosBean>();
		ConsultaApontamentosBean consultaApontamento = null;
		
		for (FuncionarioBean funcionario : funcionarios) {
			consultaApontamento = new ConsultaApontamentosBean();
			consultaApontamento.setIdFuncionario(funcionario.getId());
			consultaApontamento.setNmFuncionario(funcionario.getNome());
			for (ControleDiarioBean controle : controleDiario) {
				if(funcionario.getId().equals(controle.getControleMensal().getIdFuncionario())){
					consultaApontamento.setControleDiario(controle);
				}
			}
			if(consultaApontamento.getControleDiario() != null){
				consultaApontamentos.add(consultaApontamento);
			}
		}
		
		return consultaApontamentos;
	}
	
	public List<ConsultaApontamentosBean> setConsultaApontamentos(List<FuncionarioBean> funcionarios,
			List<ControleDiarioBean> controleDiario) {
		List<ConsultaApontamentosBean> consultaApontamentos = new ArrayList<ConsultaApontamentosBean>();
		ConsultaApontamentosBean consultaApontamento = null;
		
		for (ControleDiarioBean controle : controleDiario) {
			consultaApontamento = new ConsultaApontamentosBean();
			consultaApontamento.setControleDiario(controle);
			for (FuncionarioBean funcionario : funcionarios) {
				if(funcionario.getId().equals(controle.getIdFuncionario())){
					consultaApontamento.setIdFuncionario(funcionario.getId());
					consultaApontamento.setNmFuncionario(funcionario.getNome());
				}
			}
			consultaApontamentos.add(consultaApontamento);
		}
		return consultaApontamentos;
	}
}