package io.ucb.rafael.bluefood.infrastructure.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {
	
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() { 
		return new AuthenticationSuccessHandlerImpl();
	}
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.csrf().disable()
    		.authorizeRequests()
    		.antMatchers("/images/**", "/css/**", "/js/**", "/public/**", "/sbpay/**").permitAll()
    		.antMatchers("/cliente/**").hasRole(Role.CLIENTE.toString())
    		.antMatchers("/restaurante/**").hasRole(Role.RESTAURANTE.toString())
    		.anyRequest().authenticated()
    		.and()
    		.formLogin()
    			.loginPage("/login")
    			.failureUrl("/login-error")
    			.successHandler(authenticationSuccessHandler())
    			.permitAll()
			.and()
				.logout().logoutUrl("/logout")
				.permitAll();
    	
        return http.build();
    }
}
