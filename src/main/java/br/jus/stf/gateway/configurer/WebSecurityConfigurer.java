package br.jus.stf.gateway.configurer;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 * @author Rodrigo Barreiros
 * 
 * @since 1.0.0
 * @since 01.07.2016
 */
@EnableResourceServer
@Configuration
public class WebSecurityConfigurer extends ResourceServerConfigurerAdapter {
	
	private TokenExtractor tokenExtractor = new BearerTokenExtractor();

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.antMatcher("/**").authorizeRequests()
				.antMatchers("/discovery/api/routes", "/discovery/api/commands", "/discovery/api/queries/**", "/documents/api/onlyoffice/baseUrl", "/userauthentication/oauth/token", "/**/bundle.js*", "/manage/**", "/*/manage/info", "/apidocs/**", "/*/api-docs/**").permitAll()
				.anyRequest().authenticated()
			.and()
				.csrf().ignoringAntMatchers("/userauthentication/oauth/token").csrfTokenRepository(new CustomHttpSessionCsrfTokenRepository())
			.and()
				.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
	}
	
	private Filter csrfHeaderFilter() {
		return new OncePerRequestFilter() {
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
				CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
				if (csrf != null) {
					Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
					String token = csrf.getToken();
					if (cookie == null || token != null && !token.equals(cookie.getValue())) {
						cookie = new Cookie("XSRF-TOKEN", token);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
				filterChain.doFilter(request, response);
			}
		};
	}

	class CustomHttpSessionCsrfTokenRepository implements CsrfTokenRepository {
		
		private HttpSessionCsrfTokenRepository wrapped = new HttpSessionCsrfTokenRepository();
		
		public CustomHttpSessionCsrfTokenRepository() {
			wrapped.setHeaderName("X-XSRF-TOKEN");
		}

		@Override
		public CsrfToken generateToken(HttpServletRequest request) {
			CsrfToken token = wrapped.generateToken(request);
			
			Authentication authToken = tokenExtractor.extract(request);
			
			if (authToken != null) {
				return new DefaultCsrfToken(token.getHeaderName(), token.getParameterName(), authToken.getPrincipal().toString());
			}
			
			return token;
		}

		@Override
		public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
			wrapped.saveToken(token, request, response);
		}

		@Override
		public CsrfToken loadToken(HttpServletRequest request) {
			return wrapped.loadToken(request);
		}
	}
}
