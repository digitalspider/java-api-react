package au.com.digitalspider.api.auth;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import au.com.digitalspider.api.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtils {
	private static Long expiration = 1L; // 1 hour
	private static String secret = "Kotlin"; // TODO: Change this!
	private static String header = "Authorization";
	private static String url = "http://localhost:8080";

	private static Logger LOG = Logger.getLogger(JWTUtils.class);

	private static String createJwt(User user) {
		Map<String, Object> claims = new HashMap<String, Object>();
		claims.put("roles", user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()));
		String token = Jwts.builder().setClaims(claims).setSubject(user.getUsername())
				.setExpiration(new Date(new Date().getTime() + TimeUnit.HOURS.toMillis(expiration)))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
		LOG.debug("new token=" + token);
		return token;
	}

	public static void addAuthentication(HttpServletResponse response, User user) throws IOException {
		String jwt = createJwt(user);
		response.setHeader("Access-Control-Allow-Origin", url);
		response.getWriter().write(jwt);
		response.getWriter().flush();
		response.getWriter().close();
	}

	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(header);
		if (StringUtils.isEmpty(token)) {
			return null;
		}
		if (token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		LOG.debug("token=" + token);

		Claims tokenBody = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

		LOG.debug("tokenBody=" + tokenBody);
		String username = tokenBody.getSubject();

		@SuppressWarnings("unchecked")
		List<String> roles = (List<String>) tokenBody.get("roles");

		LinkedList<GrantedAuthority> res = new LinkedList<>();
		for (String role : roles) {
			res.add(new SimpleGrantedAuthority(role));
		}

		LOG.debug(username + " logged in with authorities " + res);
		return new UsernamePasswordAuthenticationToken(username, null, res);
	}
}
