package br.com.verity.pause.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.verity.pause.bean.ConsultaApontamentosBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.business.ConsultaApontamentosBusiness;
import br.com.verity.pause.business.ControleDiarioBusiness;
import br.com.verity.pause.business.FuncionarioBusiness;

@Controller
@RequestMapping(value = "/consultar-apontamento")
public class ConsultarApontamentoController {

	@Autowired
	private ConsultaApontamentosBusiness consultaApontamentosBusiness;

	@Autowired
	private FuncionarioBusiness funcionarioBusiness;

	@Autowired
	private ControleDiarioBusiness controleDiarioBusiness;

	@RequestMapping(method = RequestMethod.GET)
	public String consultar(Model model) {
		List<FuncionarioBean> funcionarios = funcionarioBusiness.obterTodos();
		List<ConsultaApontamentosBean> consulta = consultaApontamentosBusiness.mesclarFuncionarioComControleDiario(
				funcionarios, controleDiarioBusiness.listSomaControleDiario(funcionarios));

		model.addAttribute("funcionariosBusca", funcionarios);
		model.addAttribute("funcionarios", consulta);

		return "apontamento/consultar";
	}

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
}