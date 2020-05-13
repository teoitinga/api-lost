package br.com.jp.esloc.apilost.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
			.antMatchers("/api/v1/users/**").hasRole("ADMIN")
			.antMatchers("/api/v1/compras/**").hasRole("ADMIN")
			.antMatchers("/userinfo/**").hasAnyRole("ADMIN", "USER")
			.anyRequest().authenticated()
		.and().formLogin()
		.and().httpBasic()
		;
	}
	
	/**
	 *
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		  //auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
		  
			
			  auth. inMemoryAuthentication().passwordEncoder(encoder)
			  .withUser("user").password(encoder.encode("123")).roles("USER") .and()
			  .withUser("admin").password(encoder.encode("123")).roles("USER", "ADMIN");
			 	  
			 		 
	}
}
