package au.com.digitalspider.api.auth;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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

	private AuthenticationManager authenticationManager;
	private static final Logger LOG = Logger.getLogger(JWTLoginFilter.class);

	protected JWTLoginFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	public JWTLoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
		this(defaultFilterProcessesUrl);
		this.authenticationManager = authenticationManager;
		// return AbstractAuthenticationProcessingFilter (AntPathRequestMatcher(s))
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException {
		// Form based input
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		AccountCredentials cred = null;
		if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
			cred = new AccountCredentials(username, password);
		}
		else {
			// JSON based body input
			ObjectMapper objectMapper = new ObjectMapper();
			cred = objectMapper.readValue(request.getInputStream(), AccountCredentials.class);
		}

		LOG.debug("cred=" + cred);

		return authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						cred.getUsername(),
						cred.getPassword(),
						new ArrayList<GrantedAuthority>()));
	}

	@Override
	public void successfulAuthentication(HttpServletRequest req,
			HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
		JWTUtils.addAuthentication(res, ((SecurityUserDetails) auth.getPrincipal()).getUser());
	}
}
