package br.com.verity.pause.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.bean.ControleDiarioBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.TipoAfastamentoBean;
import br.com.verity.pause.bean.TipoJustificativaBean;
import br.com.verity.pause.business.AfastamentoBusiness;
import br.com.verity.pause.business.ApontamentoBusiness;
import br.com.verity.pause.business.ControleDiarioBusiness;
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
	
	@Autowired
	private AfastamentoBusiness afastamentoBusiness;
	
	@Autowired
	private ControleDiarioBusiness controleDiarioBusiness;
	
	@RequestMapping(method = RequestMethod.GET)
	public String consultar(Model model,String pis, String...periodo) {
		apontamentos(model,pis,periodo);
		funcionarios(model);
		justificativas(model);
		afastamentos(model);
		
		return "apontamento/gerenciar";
	}
	@RequestMapping(value = "/apontar", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> apontar(@RequestBody ApontamentoBean apontamento){
		apontamentoBusiness.apontar(apontamento);
		return new ResponseEntity<>("ok",HttpStatus.ACCEPTED);
	}
	private void funcionarios(Model model) {
		List<FuncionarioBean>funcionarios = funcionarioBusiness.listarFuncionariosPorEmpresaComPis();
		model.addAttribute("funcionarios",funcionarios);
	}
	private void justificativas(Model model) {
		List<TipoJustificativaBean>justificativas = justificativaBusiness.listar();
		model.addAttribute("justificativas",justificativas);
	}
	private void afastamentos(Model model) {
		List<TipoAfastamentoBean>afastamentos = afastamentoBusiness.listarTipoAfastamento();
		model.addAttribute("afastamentos",afastamentos);
	}
	private void apontamentos(Model model,String pis,String... periodo) {
		List<ControleDiarioBean> dias = controleDiarioBusiness.listarControleDiario(pis,periodo);
		model.addAttribute("periodo",periodo);
		model.addAttribute("dias",dias);
		model.addAttribute("pis",pis);
	}
}
