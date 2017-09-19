package br.com.verity.pause.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ConsultaApontamentosBean;
import br.com.verity.pause.bean.ConsultaCompletaBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.integration.SavIntegration;
import br.com.verity.pause.util.GerarRelatorioXlsx;

@Service
public class RelatorioBusiness {
	
	@Autowired
	private GerarRelatorioXlsx gerarRelatorio;
	
	@Autowired
	private ApontamentoBusiness apontamentoBusiness;
	
	@Autowired
	private SavIntegration sav;
	
	@Autowired
	private CustomUserDetailsBusiness userBusiness;
	
	public String gerarRelatorio(Integer idFuncionario, String de, String ate) {
		FuncionarioBean funcionario = sav.getFuncionario(idFuncionario);
		List<ConsultaCompletaBean> consultaCompleta = new ArrayList<ConsultaCompletaBean>();
		String caminho;
		
		consultaCompleta = apontamentoBusiness.obterApontamentosPeriodoPorIdFuncionario(funcionario.getId(), de, ate);
		
		caminho = gerarRelatorio.relatorioFuncionarioPeriodo(consultaCompleta, funcionario, de, ate);
		
		return caminho;
	}
	
	public String relatorioConsulta(List<ConsultaApontamentosBean> consultaApontamentosBean, Date de,
			Date ate) {
		UsuarioBean usuarioLogado = userBusiness.usuarioLogado(); 
		return gerarRelatorio.relatorioConsulta(consultaApontamentosBean, de, ate, usuarioLogado.getIdEmpresaSessao());
	}
}
