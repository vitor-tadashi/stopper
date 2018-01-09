package br.com.verity.pause;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {

		handle(request, response, authentication);
		clearAuthenticationAttributes(request);
	}

	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {

		String targetUrl = determineTargetUrl(authentication);
		String currentUri = request.getScheme() + "://" + request.getServerName()
		+ ("http".equals(request.getScheme()) && request.getServerPort() == 80
				|| "https".equals(request.getScheme()) && request.getServerPort() == 443 ? ""
						: ":" + request.getServerPort()) + request.getContextPath() +targetUrl;

		response.sendRedirect(currentUri);
	}

	protected String determineTargetUrl(Authentication authentication) {
		boolean isUser = false;
		boolean isAdmin = false;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_Multi-Empresa")
					|| grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_Multi Empresa")
					|| grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_MultiEmpresa")) {
				isAdmin = true;
				isUser = false;
				break;
			} else{
				isUser = true;
			}
		}

		if (isUser) {
			return "/gerenciar-apontamento";
		} else if (isAdmin) {
			return "/selecionaMultiEmpresa";
		} else {
			throw new IllegalStateException();
		}
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
