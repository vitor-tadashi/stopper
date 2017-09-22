package br.com.verity.pause.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.verity.pause.bean.AfastamentoBean;
import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.bean.AtestadoBean;
import br.com.verity.pause.bean.ControleDiarioBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.SobreAvisoBean;
import br.com.verity.pause.bean.TipoAfastamentoBean;
import br.com.verity.pause.bean.TipoAtestadoBean;
import br.com.verity.pause.bean.TipoJustificativaBean;
import br.com.verity.pause.business.AfastamentoBusiness;
import br.com.verity.pause.business.ApontamentoBusiness;
import br.com.verity.pause.business.AtestadoBusiness;
import br.com.verity.pause.business.ControleDiarioBusiness;
import br.com.verity.pause.business.FuncionarioBusiness;
import br.com.verity.pause.business.JustificativaBusiness;
import br.com.verity.pause.business.SobreAvisoBusiness;
import br.com.verity.pause.exception.BusinessException;

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

	@Autowired
	private SobreAvisoBusiness sobreAvisoBusiness;

	@Autowired
	private AtestadoBusiness atestadoBusiness;

	@RequestMapping(method = RequestMethod.GET)
	public String consultar(SecurityContextHolderAwareRequestWrapper request, Model model, Integer idFuncionario,
			String... periodo) {
		apontamentos(model, idFuncionario, periodo);
		justificativas(model);
		sobreAvisos(model, idFuncionario, periodo);
		if (request.isUserInRole("ROLE_MULTI-EMPRESA")) {
			funcionarios(model);
			tipoAfastamentos(model);
			tipoAtestado(model);
			afastamentos(model, idFuncionario, periodo);
			atestados(model, idFuncionario, periodo);
		}
		return "apontamento/gerenciar";
	}

	@PreAuthorize("hasRole('ROLE_INSERIR_APONTAMENTO')")
	@PostMapping(value = "/apontar")
	@ResponseBody
	public ResponseEntity<?> apontar(@RequestBody ApontamentoBean apontamento) {
		ApontamentoBean apontamentoCriado = null;
		try {
			apontamentoCriado = apontamentoBusiness.apontar(apontamento);
		} catch (BusinessException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}catch (Exception e){
			return new ResponseEntity<String>("Houve um erro ao salvar apontamento. Tente novamente mais tarde.", HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(apontamentoCriado);
	}

	@PreAuthorize("hasRole('ROLE_INSERIR_APONTAMENTO')")
	@GetMapping(value = "/obter")
	@ResponseBody
	public ResponseEntity<ApontamentoBean> obter(@RequestParam Integer id) {
		ApontamentoBean apontamento = apontamentoBusiness.obterPorId(id);
		return new ResponseEntity<>(apontamento, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_REMOVER_APONTAMENTO')")
	@GetMapping(value = "/remover")
	@ResponseBody
	public ResponseEntity<String> remover(@RequestParam Integer id) {
		try {
			apontamentoBusiness.remover(id);
		} catch (BusinessException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e){
			return new ResponseEntity<String>("Houve um erro ao remover apontamento. Tenete novamente mais tarde.", HttpStatus.BAD_REQUEST);
		}
		
		return ResponseEntity.ok("Apontamento removido");
	}

	private void funcionarios(Model model) {
		List<FuncionarioBean> funcionarios = funcionarioBusiness.listarFuncionariosPorEmpresaComPis();
		model.addAttribute("funcionarios", funcionarios);
	}

	private void justificativas(Model model) {
		List<TipoJustificativaBean> justificativas = justificativaBusiness.listar();
		model.addAttribute("justificativas", justificativas);
	}

	private void tipoAfastamentos(Model model) {
		List<TipoAfastamentoBean> afastamentos = afastamentoBusiness.listarTipoAfastamento();
		model.addAttribute("tipoAfastamentos", afastamentos);
	}

	private void tipoAtestado(Model model) {
		List<TipoAtestadoBean> atestados = atestadoBusiness.listarTipoAtestado();
		model.addAttribute("tipoAtestado", atestados);
	}

	private void apontamentos(Model model, Integer idFuncionario, String... periodo) {
		List<ControleDiarioBean> dias = controleDiarioBusiness.listarControleDiario(idFuncionario, periodo);
		model.addAttribute("periodo", periodo);
		model.addAttribute("dias", dias);
		model.addAttribute("idFuncionario", idFuncionario);
	}

	private void sobreAvisos(Model model, Integer idFuncionario, String... periodo) {
		List<SobreAvisoBean> sobreAvisos = sobreAvisoBusiness.listarPorIdFuncionarioEPeriodo(idFuncionario, periodo);
		model.addAttribute("sobreAvisos", sobreAvisos);
	}

	private void afastamentos(Model model, Integer idFuncionario, String... periodo) {
		List<AfastamentoBean> afastamentos = afastamentoBusiness.listarPorIdFuncionarioEPeriodo(idFuncionario, periodo);
		model.addAttribute("afastamentos", afastamentos);
	}

	private void atestados(Model model, Integer idFuncionario, String... periodo) {
		List<AtestadoBean> atestados = atestadoBusiness.listarPorIdFuncionarioEPeriodo(idFuncionario, periodo);
		model.addAttribute("atestados", atestados);
	}
}
