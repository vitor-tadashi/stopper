package br.com.verity.pause.controller;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.verity.pause.bean.DespesaBean;
import br.com.verity.pause.bean.FuncionarioBean;
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
	public ModelAndView acessar(Model model) {

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
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro, tente novamente!");
		}
	}
	
	@RequestMapping(value = "/analisar", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView analisarDespesas(Model model, @RequestParam(value="fgFinaceiroGP") String fgFinanceiroGP) {
		try {
			FuncionarioBean funcionario = funcionarioBizz.obterPorId(null);
			model.addAttribute("despesas", despesaBizz.buscarDespesasParaAnalise(funcionario.getId(), fgFinanceiroGP)); 
			return new ModelAndView("despesa/analisarDespesa");
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("erroMsg", "Ocorreu um erro ao recuperar as despesas para análise, tente novamente!");
			return new ModelAndView("despesa/analisarDespesa");
		}
	}
	
	@RequestMapping(value = "/{idDespesa}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> buscarDespesa(@PathVariable Long idDespesa) {
		try {
			return ResponseEntity.ok(despesaBizz.buscarDespesa(idDespesa));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro, tente novamente!");
		}
	}
}
