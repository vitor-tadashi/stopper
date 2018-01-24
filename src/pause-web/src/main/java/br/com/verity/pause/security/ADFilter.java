package br.com.verity.pause.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.naming.ServiceUnavailableException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.microsoft.aad.adal4j.AuthenticationContext;
import com.microsoft.aad.adal4j.AuthenticationResult;
import com.microsoft.aad.adal4j.ClientCredential;
import com.microsoft.aad.adal4j.UserInfo;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.openid.connect.sdk.AuthenticationErrorResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponse;
import com.nimbusds.openid.connect.sdk.AuthenticationResponseParser;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;

import br.com.verity.pause.MySimpleUrlAuthenticationSuccessHandler;
import br.com.verity.pause.bean.CustomUserDetails;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.integration.SavIntegration;

@Component
public class ADFilter extends OncePerRequestFilter {

	@Autowired
	private SavIntegration sav;

	@Autowired
	private MySimpleUrlAuthenticationSuccessHandler urlAuthenticationSuccessHandler;

	@Value("${clientId}")
	private String clientId;

	@Value("${clientSecret}")
	private String clientSecret;

	@Value("${tenant}")
	private String tenant;

	@Value("${authority}")
	private String authority;

	private static final String PRINCIPAL_SESSION_NAME = "principal";
	
	private static final List<String> NON_AUTH_END_POINTS = Collections.unmodifiableList(Arrays.asList("/403", "/error", "/403-azure","/WEB-INF/views/error/*"));
	
	private AntPathMatcher pathMatcher = new AntPathMatcher();
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	    return NON_AUTH_END_POINTS.stream().anyMatch(p -> {
	        return pathMatcher.match(p, request.getServletPath());
	    });
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		Authentication authentication = null;
		String requestUri = request.getRequestURI();
		try {
			String currentUri = request.getScheme() + "://" + request.getServerName()
					+ ("http".equals(request.getScheme()) && request.getServerPort() == 80
							|| "https".equals(request.getScheme()) && request.getServerPort() == 443 ? ""
									: ":" + request.getServerPort())
					+ requestUri;

			String fullUrl = currentUri + (request.getQueryString() != null ? "?" + request.getQueryString() : "");

			if (!isAuthenticated(request)) {
				if (containsAuthenticationData(request)) {
					Map<String, String> params = new HashMap<String, String>();
					for (String key : request.getParameterMap().keySet()) {
						params.put(key, request.getParameterMap().get(key)[0]);
					}
					AuthenticationResponse authResponse = AuthenticationResponseParser.parse(new URI(fullUrl), params);
					if (isAuthenticationSuccessful(authResponse)) {

						AuthenticationSuccessResponse oidcResponse = (AuthenticationSuccessResponse) authResponse;
						AuthenticationResult result = getAccessToken(oidcResponse.getAuthorizationCode(), currentUri);
						authentication = createSessionPrincipal(request, result);

						urlAuthenticationSuccessHandler.onAuthenticationSuccess(request, response, authentication);
						return;
					} else {
						AuthenticationErrorResponse oidcResponse = (AuthenticationErrorResponse) authResponse;
						throw new Exception(String.format("Request for auth code failed: %s - %s",
								oidcResponse.getErrorObject().getCode(),
								oidcResponse.getErrorObject().getDescription()));
					}
				} else {
					// not authenticated
					response.setStatus(302);
					response.sendRedirect(getRedirectUrl(currentUri));
					return;
				}
			} else {
				// if authenticated, how to check for valid session?
				AuthenticationResult result = getAuthSessionObject(request);

				if (result.getExpiresOnDate().before(new Date())) {
					result = getAccessTokenFromRefreshToken(result.getRefreshToken(), currentUri);
				}
				createSessionPrincipal(request, result);
			}
		} catch (Throwable exc) {
			exc.printStackTrace();
			response.setStatus(500);
			request.setAttribute("error", exc.getMessage());
			response.sendRedirect(((HttpServletRequest) request).getContextPath() + "/403-azure");
		}
		filterChain.doFilter(request, response);
	}

	private boolean isAuthenticated(HttpServletRequest request) {
		return request.getSession().getAttribute(PRINCIPAL_SESSION_NAME) != null;
	}

	private String getRedirectUrl(String currentUri) throws UnsupportedEncodingException {
		String redirectUrl = authority + this.tenant
				+ "/oauth2/authorize?response_type=code%20id_token&scope=openid&response_mode=form_post&redirect_uri="
				+ URLEncoder.encode(currentUri, "UTF-8") + "&client_id=" + clientId
				+ "&resource=https%3a%2f%2fgraph.windows.net" + "&nonce=" + UUID.randomUUID() + "&site_id=500879";
		logger.info("redirect url: " + redirectUrl);
		return redirectUrl;
	}

	private static boolean containsAuthenticationData(HttpServletRequest httpRequest) {
		return httpRequest.getMethod().equalsIgnoreCase("POST") && (httpRequest.getParameterMap().containsKey("error")
				|| httpRequest.getParameterMap().containsKey("id_token")
				|| httpRequest.getParameterMap().containsKey("code"));
	}

	private static boolean isAuthenticationSuccessful(AuthenticationResponse authResponse) {
		return authResponse instanceof AuthenticationSuccessResponse;
	}

	private AuthenticationResult getAccessToken(AuthorizationCode authorizationCode, String currentUri)
			throws Throwable {
		String authCode = authorizationCode.getValue();
		ClientCredential credential = new ClientCredential(clientId, clientSecret);
		AuthenticationContext context = null;
		AuthenticationResult result = null;
		ExecutorService service = null;
		try {
			service = Executors.newFixedThreadPool(1);
			context = new AuthenticationContext(authority + tenant + "/", true, service);
			Future<AuthenticationResult> future = context.acquireTokenByAuthorizationCode(authCode, new URI(currentUri),
					credential, null);
			result = future.get();
		} catch (ExecutionException e) {
			throw e.getCause();
		} finally {
			service.shutdown();
		}

		if (result == null) {
			throw new ServiceUnavailableException("authentication result was null");
		}
		return result;
	}

	private Authentication createSessionPrincipal(HttpServletRequest httpRequest, AuthenticationResult result)
			throws Exception {
		
		if (result != null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth == null) {
				UserInfo userAD = result.getUserInfo();
				UsuarioBean usuario = sav.getUsuarioAD(userAD.getUniqueId());
				CustomUserDetails userDetails = new CustomUserDetails(usuario);

				auth = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
			httpRequest.getSession().setAttribute(PRINCIPAL_SESSION_NAME, result);
			
			return auth;
		}
		return null;
	}

	private static AuthenticationResult getAuthSessionObject(HttpServletRequest request) {
		return (AuthenticationResult) request.getSession().getAttribute(PRINCIPAL_SESSION_NAME);
	}

	private AuthenticationResult getAccessTokenFromRefreshToken(String refreshToken, String currentUri)
			throws Throwable {
		AuthenticationContext context = null;
		AuthenticationResult result = null;
		ExecutorService service = null;
		try {
			service = Executors.newFixedThreadPool(1);
			context = new AuthenticationContext(authority + tenant + "/", true, service);
			Future<AuthenticationResult> future = context.acquireTokenByRefreshToken(refreshToken,
					new ClientCredential(clientId, clientSecret), null, null);
			result = future.get();
		} catch (ExecutionException e) {
			throw e.getCause();
		} finally {
			service.shutdown();
		}

		if (result == null) {
			throw new ServiceUnavailableException("authentication result was null");
		}
		return result;

	}
}
