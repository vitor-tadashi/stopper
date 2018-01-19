package br.com.verity.pause.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.verity.pause.bean.FuncionalidadeBean;
import br.com.verity.pause.bean.UsuarioBean;

@Controller
public class MultiEmpresaController {
	
	@PreAuthorize("hasAnyRole('ROLE_INSERIR_APONTAMENTO', 'ROLE_MULTI-EMPRESA')")
	@RequestMapping(value = { "", "/", "/selecionaMultiEmpresa" }, method = RequestMethod.GET)
	public String multiEmpresa(Model model, RedirectAttributes redirect) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UsuarioBean usuarioLogado = (UsuarioBean) auth.getPrincipal();
		for (FuncionalidadeBean funcionalidade : usuarioLogado.getPerfis().get(0).getFuncionalidades()) {
			if (funcionalidade.getNome().equalsIgnoreCase("MultiEmpresa")
					|| funcionalidade.getNome().equalsIgnoreCase("Multi-Empresa")
					|| funcionalidade.getNome().equalsIgnoreCase("Multi Empresa")) {
				return "multi-empresa/multi-empresa";
			}
		}
		return "redirect:/gerenciar-apontamento";
	}

	@RequestMapping(value = { "/selecionaMultiEmpresa/{id}" }, method = RequestMethod.POST)
	public String selecionaMultiEmpresa(@PathVariable Integer id, RedirectAttributes redirect) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UsuarioBean usuarioLogado = (UsuarioBean) auth.getPrincipal();
		
		usuarioLogado.setIdEmpresaSessao(id);

		Authentication newAuth = new UsernamePasswordAuthenticationToken(usuarioLogado, usuarioLogado.getSenha(), auth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(newAuth);

		return "redirect:/importacao";
	}
}