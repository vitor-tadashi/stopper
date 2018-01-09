package br.com.verity.pause.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.verity.pause.business.CustomUserDetailsBusiness;
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
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ModelAndView acessar(Model model, Integer idFuncionario) {
		System.out.println(funcionarioBizz.obterPorId(null).getId());
		
		model.addAttribute("tipoDespesas", tipoDespesaBizz.findAll());
		//model.addAttribute("projetos", projetoBizz.listProjetosPorFuncionarios(897));
		model.addAttribute("projetos", projetoBizz.listProjetosPorFuncionarios(funcionarioBizz.obterPorId(null).getId()));
		return new ModelAndView("despesa/despesa");
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> salvar(DespesaBean despesa, @RequestParam(value = "comprovante", required=false) MultipartFile comprovante) {
		System.out.println(despesa.getJustificativa());
		return ResponseEntity.ok("Salvo com sucesso!");
	}
}
