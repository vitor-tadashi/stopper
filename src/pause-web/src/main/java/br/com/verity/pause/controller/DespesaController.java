package br.com.verity.pause.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.verity.pause.bean.DespesaBean;
import br.com.verity.pause.business.DespesaBusiness;
import br.com.verity.pause.business.FuncionarioBusiness;
import br.com.verity.pause.business.ProjetoBusiness;
import br.com.verity.pause.business.TipoDespesaBusiness;

@Controller
@RequestMapping(value = "/despesa")
public class DespesaController {

	@Autowired
	TipoDespesaBusiness tipoDespesaBizz;

	@Autowired
	ProjetoBusiness projetoBizz;

	@Autowired
	FuncionarioBusiness funcionarioBizz;

	@Autowired
	DespesaBusiness despesaBizz;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView acessar(Model model, Integer idFuncionario) {

		model.addAttribute("tipoDespesas", tipoDespesaBizz.findAll());
		model.addAttribute("projetos",
				projetoBizz.listProjetosPorFuncionarios(funcionarioBizz.obterPorId(null).getId()));
		model.addAttribute("despesasFuncionario", despesaBizz.listarDespesasPorFuncionario(funcionarioBizz.obterPorId(null).getId()));
		return new ModelAndView("despesa/despesa");
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> salvar(DespesaBean despesa,
			@RequestParam(value = "comprovante", required = false) MultipartFile comprovante) {
		try {

			despesaBizz.salvaDespesa(despesa, comprovante);
			return ResponseEntity.ok("Salvo com sucesso!");

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Despesa não pôde ser salva, tente novamente!");
		}
	}

	@RequestMapping(value = "/analisar", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> analisarDespesaFuncionario(Long idDespesa, Long idAprovador, String fgFinanceiroGP, boolean despesaAprovada) {
		try {
			despesaBizz.salvarAnaliseDespesa(idDespesa, idAprovador, fgFinanceiroGP, despesaAprovada);
			return ResponseEntity.ok("Despesa alterada com sucesso");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Despesa não pôde ser alterada, tente novamente!");
		}
	}
	
	@RequestMapping(value = "/analisar", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> analisarDespesas(Long idFuncionarioAprovador, String fgFinanceiroGP) {
		try {
			despesaBizz.buscarDespesasParaAnalise(idFuncionarioAprovador, fgFinanceiroGP);
			return ResponseEntity.ok("Salvo com sucesso!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Despesa não pode ser salva, tente novamente!");
		}
	}
}
