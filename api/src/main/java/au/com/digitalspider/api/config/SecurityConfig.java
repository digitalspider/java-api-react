package au.com.digitalspider.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import au.com.digitalspider.api.Constants;
import au.com.digitalspider.api.auth.JWTAuthenticationFilter;
import au.com.digitalspider.api.auth.JWTLoginFilter;

@Configuration
@EnableWebSecurity
//@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${spring.data.rest.basePath}")
	private String restApiBasePath = Constants.BASE_API;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
				.antMatcher("/**").authorizeRequests()
				.antMatchers("/", "/index.html", "/user/**", "/browser/**", "/public/**", "/swagger*/**",
						"/v2/**", "/images/**", "/js/**", "/css/**", "/webjars/**", "/favicon.ico")
				.permitAll()
				.antMatchers(restApiBasePath + "/**").authenticated()
				.antMatchers(restApiBasePath + "/admin").hasAuthority("ADMIN")
				.and()
				// .addFilterBefore(new CorsFilter(), SessionManagementFilter.class)
				.addFilterBefore(new JWTLoginFilter("/user/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
//		int encodingStrength = 16;
//		PasswordEncoder encoder = new BCryptPasswordEncoder(encodingStrength);
//		println(encoder.encode("test123"));
//		return encoder;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.expressionHandler(new DefaultWebSecurityExpressionHandler() {
			@Override
			protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication,
					FilterInvocation fi) {
				WebSecurityExpressionRoot root = (WebSecurityExpressionRoot) super.createSecurityExpressionRoot(
						authentication, fi);
				root.setDefaultRolePrefix(""); // remove the prefix ROLE_
				return root;
			}
		});
	}
}
