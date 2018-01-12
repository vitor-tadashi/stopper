package br.com.verity.pause.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.verity.pause.bean.ApontamentosPendentesBean;
import br.com.verity.pause.business.ApontamentosPendentesBusiness;

@Controller
@RequestMapping(value = "/apontamentos-pendentes")
public class ApontamentosPendentesController {

	@Autowired
	private ApontamentosPendentesBusiness apontamentosPendentesBusiness;

	@RequestMapping(method = RequestMethod.GET)
	public String listarApontamentosPendentes(Model model) throws SQLException {
		List<ApontamentosPendentesBean> pendentes = apontamentosPendentesBusiness.obterApontamentosPendentes();

		model.addAttribute("pendentes", pendentes);

		return "apontamento/pendentes";
	}
}