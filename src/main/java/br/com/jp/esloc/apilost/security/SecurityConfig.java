package br.com.jp.esloc.apilost.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
				
				  .antMatchers("/api/v1/users/**").hasAuthority("ADMIN")
				  .antMatchers("/api/v1/compras/**").hasAuthority("USER")
				 
			.anyRequest().authenticated()
		.and()
		.httpBasic()
		;
	}
	
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		PasswordEncoder encoder = bCryptPasswordEncoder();
		
		//  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		  
		  auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());//.passwordEncoder(encoder.encode
		 		  
			/*
			 * auth.inMemoryAuthentication().passwordEncoder(encoder)
			 * .withUser("user").password(encoder.encode("123")).roles("USER") .and()
			 * .withUser("admin").password(encoder.encode("123")).roles("USER", "ADMIN");
			 */ 	 	  	  
			 		 
	}
}
