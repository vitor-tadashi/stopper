package br.com.verity.pause.business;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ConsultaApontamentosBean;
import br.com.verity.pause.bean.ConsultaCompletaBean;
import br.com.verity.pause.bean.ControleDiarioBean;
import br.com.verity.pause.bean.ControleMensalBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.SobreAvisoBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.integration.SavIntegration;
import br.com.verity.pause.util.DataUtil;
import br.com.verity.pause.util.GerarRelatorioXlsx;

@Service
public class RelatorioBusiness {
	
	@Autowired
	private GerarRelatorioXlsx gerarRelatorio;
	
	@Autowired
	private ApontamentoBusiness apontamentoBusiness;
	
	@Autowired
	private ControleMensalBusiness consultaMensalBusiness;
	
	@Autowired
	private SavIntegration sav;
	
	@Autowired
	private CustomUserDetailsBusiness userBusiness;
	
	@Autowired
	private SobreAvisoBusiness sobreAvisoBusiness;
	
	@Autowired
	private FuncionarioBusiness funcionarioBusiness;
	
	@Autowired
	private ControleDiarioBusiness controleDiarioBusiness;
	
	@Autowired
	private ConsultaApontamentosBusiness consultaApontamentosBusiness;
	
	@Autowired
	private DataUtil dataUtil;
	
	public byte[] gerarRelatorio(Integer idFuncionario, String de, String ate) throws SQLException, ParseException {
		FuncionarioBean funcionario = sav.getFuncionario(idFuncionario);
		List<ConsultaCompletaBean> consultaCompleta = new ArrayList<ConsultaCompletaBean>();
		byte[] outArray ;
		String[] periodo = {dataUtil.inverterOrdem(de),dataUtil.inverterOrdem(ate)};
		
		consultaCompleta = apontamentoBusiness.obterApontamentosPeriodoPorIdFuncionario(funcionario.getId(), de, ate);
		List<ControleMensalBean> saldoDeHoras = consultaMensalBusiness.obterBancoESaldoPorIdFuncionario(
				funcionario.getId(),de);
		List<SobreAvisoBean> sobreAvisos = sobreAvisoBusiness.listarPorIdFuncionarioEPeriodo(idFuncionario, periodo);
		
		outArray  = gerarRelatorio.relatorioFuncionarioPeriodo(consultaCompleta, saldoDeHoras, sobreAvisos, funcionario, de, ate);
		
		return outArray ;
	}
	
	public byte[] relatorioConsulta(List<ConsultaApontamentosBean> consultaApontamentosBean, Date de,
			Date ate) {
		UsuarioBean usuarioLogado = userBusiness.usuarioLogado(); 
		return gerarRelatorio.relatorioConsulta(consultaApontamentosBean, de, ate, usuarioLogado.getIdEmpresaSessao());
	}
	
	public byte[] relatorioApontamentoDiario(String de, String ate) {
		UsuarioBean usuarioLogado = userBusiness.usuarioLogado(); 
		List<FuncionarioBean> funcionarios = new ArrayList<FuncionarioBean>();
		SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date dtDe = null;
		java.sql.Date dtAte = null;
		
		funcionarios = funcionarioBusiness.obterTodos();
		de = dataUtil.inverterOrdem(de);
		ate = dataUtil.inverterOrdem(ate);
		
		try {
			dtDe = new java.sql.Date(formataData.parse(de).getTime());
			dtAte = new java.sql.Date(formataData.parse(ate).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<ControleDiarioBean> controlesDiario = controleDiarioBusiness.obterApontamentosDiariosTodosFuncionarios(dtDe, dtAte);

		List<ConsultaApontamentosBean> consultaApontamentosBean = consultaApontamentosBusiness.setConsultaApontamentos(funcionarios, controlesDiario);

		return gerarRelatorio.relatorioApontamentoDiario(consultaApontamentosBean, dtDe, dtAte, usuarioLogado.getIdEmpresaSessao());
	}
}
