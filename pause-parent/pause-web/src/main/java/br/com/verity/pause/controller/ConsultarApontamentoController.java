package br.com.verity.pause.controller;

import java.text.ParseException;
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
		List<ConsultaApontamentosBean> consulta = consultaApontamentosBusiness.mesclarFuncionarioComControleDiario(
				funcionarios, controleDiarioBusiness.listSomaControleDiario(funcionarios));

		model.addAttribute("funcionariosBusca", funcionarios);
		model.addAttribute("funcionarios", consulta);

		return "apontamento/consultar";
	}

	@PreAuthorize("hasRole('ROLE_CONSULTAR_BANCO')")
	@PostMapping(value = "/filtrar-consulta")
	public String filtrarConsulta(Integer idFuncionario, String periodoDe, String periodoAte, Model model) {
		List<ConsultaApontamentosBean> consulta = new ArrayList<ConsultaApontamentosBean>();
		List<FuncionarioBean> funcionarios = funcionarioBusiness.obterTodos();
		List<FuncionarioBean> funcionariosConsulta = new ArrayList<FuncionarioBean>();
		FuncionarioBean funcionario = new FuncionarioBean();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dtAte = new Date();
		Date dtDe = new Date(dtAte.getYear(), dtAte.getMonth(), 01);

		if (idFuncionario != null) {
			funcionariosConsulta.addAll(
					funcionarios.stream().filter(func -> func.getId().equals(idFuncionario)).collect(Collectors.toList()));
			model.addAttribute("idFuncBusca", idFuncionario);
		}
		try {
			if (periodoDe!=null && !periodoDe.equals("")) {
				dtDe = format.parse(periodoDe);
			}
			if (periodoAte!=null && !periodoAte.equals("")) {
				dtAte = format.parse(periodoAte);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		consulta = consultaApontamentosBusiness.mesclarFuncionarioComControleDiario((funcionariosConsulta.size() < 1) ? funcionarios : funcionariosConsulta,
				controleDiarioBusiness.listSomaControleDiarioPorPeriodo(
						(funcionariosConsulta.size() < 1) ? funcionarios : funcionariosConsulta,
								dtDe, dtAte));
		
		String dataDe = "";
		String dataAte = "";
		
		if(periodoDe != null) {
			dataDe = (periodoDe.equals(""))?format.format(dtDe):periodoDe;
		}
		
		if(periodoAte != null) {
			dataAte = (periodoAte.equals(""))?format.format(dtAte):periodoAte;
		}
		
		model.addAttribute("funcionariosBusca", funcionarios);
		model.addAttribute("funcionarios", consulta);
		model.addAttribute("de", dataDe);
		model.addAttribute("ate", dataAte);

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
		
		List<ControleDiarioBean> controleDiario = controleDiarioBusiness.listSomaControleDiarioPorPeriodo(funcionarios, de, ate);

		List<ConsultaApontamentosBean> consultaApontamentos = consultaApontamentosBusiness.mesclarFuncionarioComControleDiario(funcionarios, controleDiario);
		
		String caminho = relatorioBusiness.relatorioConsulta(consultaApontamentos, de, ate);
		
		return caminho;
	}
}