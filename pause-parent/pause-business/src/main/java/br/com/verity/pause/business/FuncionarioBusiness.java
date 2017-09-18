package br.com.verity.pause.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ConsultaCompletaBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.converter.FuncionarioIntegrationConverter;
import br.com.verity.pause.integration.SavIntegration;
import br.com.verity.pause.util.GerarRelatorioXlsx;

@Service
public class FuncionarioBusiness {
	
	@Autowired
	private GerarRelatorioXlsx gerarRelatorio;
	
	@Autowired
	private ApontamentoBusiness apontamentoBusiness;
	
	@Autowired
	private CustomUserDetailsBusiness userBusiness;
	
	@Autowired
	private FuncionarioIntegrationConverter funcionarioConverter;
	
	@Autowired
	private SavIntegration sav;
	
	@Autowired
	private CustomUserDetailsBusiness customUser;
	
	public List<FuncionarioBean> obterTodos(){
		UsuarioBean usuarioLogado = customUser.usuarioLogado();
		return sav.getFuncionarios(usuarioLogado.getIdEmpresaSessao());
	}

	public String gerarRelatorio(Integer idFuncionario, String de, String ate) {
		FuncionarioBean funcionario = sav.getFuncionario(idFuncionario);
		List<ConsultaCompletaBean> consultaCompleta = new ArrayList<ConsultaCompletaBean>();
		String caminho;
		
		consultaCompleta = apontamentoBusiness.obterApontamentosPeriodoPorIdFuncionario(funcionario.getId(), de, ate);
		
		caminho = gerarRelatorio.relatorioFuncionarioPeriodo(consultaCompleta, funcionario, de, ate);
		
		return caminho;
	}

	public List<FuncionarioBean> listarFuncionariosPorEmpresaComPis() {
		UsuarioBean usuarioLogado = customUser.usuarioLogado();
		return sav.getFuncionarios(usuarioLogado.getIdEmpresaSessao());
	}

	public FuncionarioBean obterPorId(Integer id) {
		if(id == null) {
			UsuarioBean usuarioLogado = userBusiness.usuarioLogado();
			return funcionarioConverter.convertEntityToBean(usuarioLogado.getFuncionario());
		}else {
			return sav.getFuncionario(id);
		}
	}
	
	public List<FuncionarioBean> obterTodosComPis(){
		return sav.getListFuncionarios();
	}

}