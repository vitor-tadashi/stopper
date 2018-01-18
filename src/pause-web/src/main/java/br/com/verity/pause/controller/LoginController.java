package br.com.verity.pause.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
	private static final String PRINCIPAL_SESSION_NAME = "principal";
	
	@Value("${logoutAd}")
	private String RESOURCE_LOGOUT_AD;

	@RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
	public String loginPage() {
		return "login/login";
	}

	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	public void logoutPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			request.getSession().setAttribute(PRINCIPAL_SESSION_NAME, null);
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		response.sendRedirect(RESOURCE_LOGOUT_AD);
	}
}