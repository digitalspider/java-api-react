package au.com.digitalspider.api.auth;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	private String corsUrl;

	private AuthenticationManager authenticationManager;

	private static final Logger LOG = LoggerFactory.getLogger(JWTLoginFilter.class);

	public JWTLoginFilter(String corsUrl, String defaultFilterProcessesUrl,
			AuthenticationManager authenticationManager) {
		super(defaultFilterProcessesUrl);
		super.setAuthenticationManager(authenticationManager);
		this.authenticationManager = authenticationManager;
		this.corsUrl = corsUrl;
		// return AbstractAuthenticationProcessingFilter (AntPathRequestMatcher(s))
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws JsonParseException, JsonMappingException, IOException {
		// Form based input
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		AccountCredentials cred = null;
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
			cred = new AccountCredentials(username, password);
		} else {
			// JSON based body input
			ObjectMapper objectMapper = new ObjectMapper();
			cred = objectMapper.readValue(request.getInputStream(), AccountCredentials.class);
		}

		LOG.debug("cred=" + cred);

		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(cred.getUsername(),
				cred.getPassword(), new ArrayList<GrantedAuthority>()));
	}

	@Override
	public void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException {
		String responseStringTemplate = "{\"status\": \"200\", \"data\": \"%s\"}";
		JWTUtils.addAuthentication(res, ((SecurityUserDetails) auth.getPrincipal()).getUser(), corsUrl,
				responseStringTemplate);
	}
}
