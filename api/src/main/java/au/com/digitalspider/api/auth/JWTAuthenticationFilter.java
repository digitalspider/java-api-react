package au.com.digitalspider.api.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JWTAuthenticationFilter extends GenericFilterBean {

	@Value("${request.header.auth}")
	private String authHeader;
	@Value("${app.url.cors}")
	private String corsUrl;

	private static Logger LOG = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			LOG.debug("corsUrl=" + corsUrl);
			LOG.debug("authHeader=" + authHeader);
			String token = ((HttpServletRequest) request).getHeader(authHeader);
			LOG.debug(String.format("Header %s has token value: %s", authHeader, token));
			Authentication authentication = JWTUtils.getAuthentication(token);
			if (authentication == null) {
				LOG.debug("JWT Authentication is null. 401 response.");
				String jsonResponse = String.format("{\"error\": \"Missing parameter %s\"}", authHeader);
				((HttpServletResponse) response).getOutputStream().println(jsonResponse);
				((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		} catch (ExpiredJwtException e) {
			LOG.debug("JWT Authentication token is expired. 401 response.");
			LOG.error("Token has expired. " + e.getMessage());
			String jsonResponse = "{\"error\": \"Token has expired\"}";
			((HttpServletResponse) response).getOutputStream().println(jsonResponse);
			((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
	}
}