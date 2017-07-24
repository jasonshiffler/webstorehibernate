package com.jshiffler.webstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("john").password("pa55word").roles("USER");
		auth.inMemoryAuthentication().withUser("admin").password("root123").roles("USER","ADMIN");
	}

	// Configures the security settings for the application
	@Override
	protected void configure(HttpSecurity httpSecurity)throws Exception {
		
		httpSecurity.formLogin().loginPage("/login").usernameParameter("userId").passwordParameter("password");
		
		// Define the different login pages
		httpSecurity.formLogin().defaultSuccessUrl("/market/products/add").failureUrl("/login?error");
		httpSecurity.logout().logoutSuccessUrl("/login?logout");
		httpSecurity.exceptionHandling().accessDeniedPage("/login?accessDenied");
		
		// Define which roles can access which pages
		httpSecurity.authorizeRequests().antMatchers("/").permitAll();
		httpSecurity.authorizeRequests().antMatchers("/**/add").access("hasRole('ADMIN')");
		httpSecurity.authorizeRequests().antMatchers("/**/market/**").access("hasRole('USER')");
		
		// Protects against Cross Site Request Forgery Attacks
		// https://docs.spring.io/spring-security/site/docs/current/reference/html/csrf.html
		httpSecurity.csrf().disable();
		
		
	}
	
	
	
}
