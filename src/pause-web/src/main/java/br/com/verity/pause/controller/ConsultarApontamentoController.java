package br.com.verity.pause.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.verity.pause.bean.ConsultaApontamentosBean;
import br.com.verity.pause.bean.ControleDiarioBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.business.ConsultaApontamentosBusiness;
import br.com.verity.pause.business.ControleDiarioBusiness;
import br.com.verity.pause.business.FuncionarioBusiness;
import br.com.verity.pause.business.RelatorioBusiness;

@Controller
@RequestMapping(value = "/consultar-apontamento")
public class ConsultarApontamentoController {

	@Autowired
	private ConsultaApontamentosBusiness consultaApontamentosBusiness;

	@Autowired
	private FuncionarioBusiness funcionarioBusiness;

	@Autowired
	private RelatorioBusiness relatorioBusiness;

	@Autowired
	private ControleDiarioBusiness controleDiarioBusiness;

	@PreAuthorize("hasRole('ROLE_CONSULTAR_BANCO')")
	@RequestMapping(method = RequestMethod.GET)
	public String consultar(Model model) {
		List<FuncionarioBean> funcionarios = funcionarioBusiness.obterTodos();

		model.addAttribute("funcionariosBusca", funcionarios);

		return "apontamento/consultar";
	}

	@PreAuthorize("hasRole('ROLE_CONSULTAR_BANCO')")
	@PostMapping(value = "/filtrar-consulta")
	public String filtrarConsulta(Integer idFuncionario, String periodoDe, String periodoAte, Model model) {
		List<ConsultaApontamentosBean> consulta = new ArrayList<ConsultaApontamentosBean>();
		List<FuncionarioBean> funcionarios = funcionarioBusiness.obterTodos();
		List<FuncionarioBean> filtro = funcionarioBusiness.obterTodos();
		List<ControleDiarioBean> controles = new ArrayList<ControleDiarioBean>();
		SimpleDateFormat padrao = new SimpleDateFormat("yyyy-MM-dd");
		Date dtAte = new Date();
		Date dtDe = new Date(dtAte.getYear(), dtAte.getMonth(), 01);

		dtDe = this.formatarData(periodoDe);
		dtAte = this.formatarData(periodoAte);

		funcionarios = this.filtrarFuncionariosPorId(funcionarios, idFuncionario);

		controles = controleDiarioBusiness.listSomaControleDiarioPorPeriodo(funcionarios, dtDe, dtAte);

		consulta = consultaApontamentosBusiness.mesclarFuncionarioComControleDiario(funcionarios, controles);

		
        if(periodoDe != null) {
        	periodoDe = (periodoDe.equals(""))?padrao.format(dtDe):periodoDe;
        }
        if(periodoAte != null) {
        	periodoAte = (periodoAte.equals(""))?padrao.format(dtAte):periodoAte;
        }

    	model.addAttribute("funcionariosBusca", filtro);
		model.addAttribute("funcionarios", consulta);
		model.addAttribute("de", periodoDe);
		model.addAttribute("ate", periodoAte);

		return "/apontamento/consultar";
	}

	@PreAuthorize("hasRole('ROLE_CONSULTAR_BANCO')")
	@RequestMapping(value = "gerar-relatorio-consulta", method = RequestMethod.POST)
	@ResponseBody
	public String gerarRelatorioConsulta(Integer idFuncionario, Date de, Date ate, HttpServletResponse response) {
		List<FuncionarioBean> funcionarios = new ArrayList<FuncionarioBean>();
		FuncionarioBean funcionario = new FuncionarioBean();
		if (idFuncionario == null) {
			funcionarios = funcionarioBusiness.obterTodos();
		} else {
			funcionario = funcionarioBusiness.obterPorId(idFuncionario);
			funcionarios.add(funcionario);
		}

		List<ControleDiarioBean> controleDiario = controleDiarioBusiness.listSomaControleDiarioPorPeriodo(funcionarios,
				de, ate);

		List<ConsultaApontamentosBean> consultaApontamentos = consultaApontamentosBusiness
				.mesclarFuncionarioComControleDiario(funcionarios, controleDiario);

		String caminho = relatorioBusiness.relatorioConsulta(consultaApontamentos, de, ate);

		return caminho;
	}

	private Date formatarData(String dataEntrada) {
		Date dataFormatada = new Date();
		SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
		formatador.setLenient(false);
		if (!dataEntrada.equals("") && dataEntrada != null) {
			try {
			
			dataFormatada = formatador.parse(dataEntrada);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return dataFormatada;
	}

	private List<FuncionarioBean> filtrarFuncionariosPorId(List<FuncionarioBean> funcionarios, Integer idFuncionario) {
		List<FuncionarioBean> funcionariosFiltrados = new ArrayList<FuncionarioBean>();
		
		if (idFuncionario != null && idFuncionario > 0) {

			funcionariosFiltrados.addAll(funcionarios.stream().filter(func -> func.getId().equals(idFuncionario))
					.collect(Collectors.toList()));
			
			funcionarios = funcionariosFiltrados;
		}
		
		return funcionarios;

	}
}