package br.com.verity.pause.controller;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

			despesa = despesaBizz.salvaDespesa(despesa, comprovante);
			return ResponseEntity.ok(despesa);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Despesa não pôde ser salva, tente novamente!");
		}
	}

	@RequestMapping(value = "/analisar", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> analisarDespesaFuncionario(Long idDespesa, String fgFinanceiroGP, boolean despesaAprovada, String justificativa) {
		try {
			FuncionarioBean func = funcionarioBizz.obterPorId(null);
			despesaBizz.salvarAnaliseDespesa(idDespesa, func.getId().longValue(), fgFinanceiroGP, despesaAprovada, justificativa);
			return ResponseEntity.ok("Despesa " + (despesaAprovada ? " aprovada " : " rejeitada ") + " com sucesso!");
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
	
	@RequestMapping(value = "/arquivo/{idDespesa}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?>  downloadArquivo(@PathVariable Long idDespesa) {
		try {
			DespesaBean despesa = despesaBizz.buscarDespesa(idDespesa);
		    File file = new File(despesa.getCaminhoComprovante());
		    String fileName = file.getName().toUpperCase();
		    
		    HttpHeaders respHeaders = new HttpHeaders();
		    
		    setHeaderContentType(fileName, respHeaders);
		    
		    respHeaders.setContentLength(file.length());
		    respHeaders.setContentDispositionFormData("attachment", fileName);
		    
		    InputStreamResource isr = new InputStreamResource(new FileInputStream(file));
		    return new ResponseEntity<>(isr, respHeaders, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Erro ao recuperar o arquivo solicitado.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private void setHeaderContentType(String fileName, HttpHeaders respHeaders) {
		if (fileName.toUpperCase().endsWith(".JPG") || fileName.toUpperCase().endsWith(".JPEG")) {
			respHeaders.setContentType(MediaType.IMAGE_JPEG);
		} else if (fileName.toUpperCase().endsWith("GIF")) {
			respHeaders.setContentType(MediaType.IMAGE_GIF);
		} else if (fileName.toUpperCase().endsWith("PDF")) {
			respHeaders.setContentType(MediaType.APPLICATION_PDF);
		}
	}
}
