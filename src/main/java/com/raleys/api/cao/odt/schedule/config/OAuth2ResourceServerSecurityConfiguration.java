package com.raleys.api.cao.odt.schedule.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.okta.spring.boot.oauth.Okta;

/**
 * @author abhay.thakur Configuration class for okta security
 *
 */
@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2ResourceServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	public RestApiAuthenticationFilter authenticationTokenFilterBean()  {
		return new RestApiAuthenticationFilter();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors();
		http.authorizeRequests().antMatchers("/swagger-ui/**","/actuator/**").permitAll()
				// all other requests
				.anyRequest().authenticated().and().oauth2ResourceServer().jwt();
		Okta.configureResourceServer401ResponseBody(http);

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/swagger-ui.html","/swagger-ui/**", "/v3/api-docs/**","/v3/api-docs.*/**");
	}
}
