package com.finance.tracker.security;

import static com.finance.tracker.security.CustomSecurityConstants.ACTUATOR_URL;
import static com.finance.tracker.security.CustomSecurityConstants.API_DOCS;
import static com.finance.tracker.security.CustomSecurityConstants.LOGIN_URL;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class CustomWebSecurity {

	@Bean
	SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

		AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
				.getSharedObject(AuthenticationManagerBuilder.class);

		authenticationManagerBuilder.inMemoryAuthentication();

		AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().cors().and()
				.csrf().disable().authorizeHttpRequests()
				.requestMatchers(LOGIN_URL, ACTUATOR_URL, API_DOCS).permitAll()
				.anyRequest().authenticated().and()
				.addFilter(new CustomJWTAuthorizationFilter(authenticationManager))
				.authenticationManager(authenticationManager);

		httpSecurity.headers().frameOptions().disable();

		return httpSecurity.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE", "OPTIONS", "HEAD"));
		configuration.addAllowedHeader("*");
		configuration.addAllowedOrigin("*");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
