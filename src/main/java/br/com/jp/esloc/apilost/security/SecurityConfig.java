package br.com.jp.esloc.apilost.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.jp.esloc.apilost.security.jwt.JwtAuthFilter;
import br.com.jp.esloc.apilost.security.jwt.JwtService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private JwtService jwtService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
				
				  .antMatchers("/api/v1/users/auth/**").permitAll()
				  .antMatchers("/api/v1/users/**").hasAnyAuthority( "ADMIN", "USER")
				  .antMatchers("/api/v1/compras/**").hasAnyAuthority( "ADMIN", "USER")

		.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
			.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
		;
	}
	
	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(this.jwtService, this.userDetailsService);
	}
	
    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
    	web.ignoring().antMatchers(
    			"/v2/api-docs",
    			"/configuration/ui",
    			"/swagger-resources/**",
    			"/configuration/security",
    			"/swagger-ui.html",
    			"/webjars/**"
    			);
    }
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
  
		  auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	 	  	  
			 		 
	}
}
