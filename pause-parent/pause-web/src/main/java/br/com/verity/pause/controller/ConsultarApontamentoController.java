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
	public String filtrarConsulta(Integer idFunc, String de, String ate, Model model) {
		List<ConsultaApontamentosBean> consulta = new ArrayList<ConsultaApontamentosBean>();
		List<FuncionarioBean> funcionarios = funcionarioBusiness.obterTodos();
		List<FuncionarioBean> funcionariosConsulta = new ArrayList<FuncionarioBean>();
		FuncionarioBean funcionario = new FuncionarioBean();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date dtAte = new Date();
		Date dtDe = new Date(dtAte.getYear(), dtAte.getMonth(), 01);

		if (idFunc != null) {
			funcionariosConsulta.addAll(
					funcionarios.stream().filter(func -> func.getId().equals(idFunc)).collect(Collectors.toList()));
			model.addAttribute("idFuncBusca", idFunc);
		}
		try {
			if (!de.equals("")) {
				dtDe = format.parse(de);
			}
			if (!ate.equals("")) {
				dtAte = format.parse(ate);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		consulta = consultaApontamentosBusiness.mesclarFuncionarioComControleDiario((funcionariosConsulta.size() < 1) ? funcionarios : funcionariosConsulta,
				controleDiarioBusiness.listSomaControleDiarioPorPeriodo(
						(funcionariosConsulta.size() < 1) ? funcionarios : funcionariosConsulta,
								dtDe, dtAte));
		
		model.addAttribute("funcionariosBusca", funcionarios);
		model.addAttribute("funcionarios", consulta);
		model.addAttribute("de", (de.equals(""))?format.format(dtDe):de);
		model.addAttribute("ate", (ate.equals(""))?format.format(dtAte):ate);

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

		String caminho = relatorioBusiness.relatorioConsulta(consultaApontamentosBusiness.mesclarFuncionarioComControleDiario(
				funcionarios, controleDiarioBusiness.listSomaControleDiarioPorPeriodo(funcionarios, de, ate)), de, ate);
		return caminho;
	}
}