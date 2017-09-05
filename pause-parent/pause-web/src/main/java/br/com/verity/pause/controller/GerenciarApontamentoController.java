package br.com.verity.pause.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.TipoJustificativaBean;
import br.com.verity.pause.business.ApontamentoBusiness;
import br.com.verity.pause.business.FuncionarioBusiness;
import br.com.verity.pause.business.JustificativaBusiness;

@Controller
@RequestMapping(value = "/gerenciar-apontamento")
public class GerenciarApontamentoController {
	
	@Autowired
	private ApontamentoBusiness apontamentoBusiness;
	
	@Autowired
	private FuncionarioBusiness funcionarioBusiness;
	
	@Autowired
	private JustificativaBusiness justificativaBusiness;
	
	@RequestMapping(method = RequestMethod.GET)
	public String consultar(Model model) {
		//apontamentos(model,null);
		funcionarios(model);
		justificativas(model);
		
		return "apontamento/gerenciar";
	}
	@RequestMapping(value = "/apontar", method = RequestMethod.POST)
	public void apontar(@RequestBody ApontamentoBean apontamento){
		apontamentoBusiness.apontar(apontamento);
	}
	private void funcionarios(Model model) {
		List<FuncionarioBean>funcionarios = funcionarioBusiness.listarFuncionariosPorEmpresaComPis();
		model.addAttribute("funcionarios",funcionarios);
	}
	private void justificativas(Model model) {
		List<TipoJustificativaBean>justificativas = justificativaBusiness.listar();
		model.addAttribute("justificativas",justificativas);
	}
	private void apontamentos(Model model,String pis,String... periodo) {
		List<ApontamentoBean> apontamentos = apontamentoBusiness.listarApontamentos(pis,periodo);
		model.addAttribute("apontamentos",apontamentos);
	}
}
