package br.com.verity.pause.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.verity.pause.bean.FuncionalidadeBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.integration.SavIntegration;

@Controller
public class LoginController {

	@Autowired
	private SavIntegration integration;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "login/login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String multiEmpresa(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UsuarioBean usuarioLogado = (UsuarioBean) auth.getPrincipal();
		if (usuarioLogado.getIdEmpresaSessao() == null) {
			for (FuncionalidadeBean funcionalidade : usuarioLogado.getPerfis().get(0).getFuncionalidades()) {
				if (funcionalidade.getNome().equalsIgnoreCase("MultiEmpresa")
						|| funcionalidade.getNome().equalsIgnoreCase("Multi-Empresa")
						|| funcionalidade.getNome().equalsIgnoreCase("Multi Empresa")) {
					model.addAttribute("empresas", integration.getEmpresas());
					return "login/selecione-empresa";
				}
			}
		}
		return "home/index";
	}

	@RequestMapping(value = { "/selecionaMultiEmpresa" }, method = RequestMethod.POST)
	public String selecionaMultiEmpresa(Integer idEmpresaSessao, RedirectAttributes redirect) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UsuarioBean usuarioLogado = (UsuarioBean) auth.getPrincipal();
		
		usuarioLogado.setIdEmpresaSessao(idEmpresaSessao);

		Authentication newAuth = new UsernamePasswordAuthenticationToken(usuarioLogado, usuarioLogado.getSenha(), auth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(newAuth);

		return "redirect:/home";
	}
}