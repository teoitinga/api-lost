package br.com.jp.esloc.apilost.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LostWebMvcConfigurer implements WebMvcConfigurer {

	/*
	 * @Override public void addCorsMappings(CorsRegistry registry) {
	 * registry.addMapping("/**") .allowedMethods("*"); }
	 */

	@Override public void addCorsMappings(CorsRegistry registry) {
	  registry.addMapping("/**").allowedOrigins("*") 
	  .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "TRACE", "CONNECT") 
	  ; 
	  }

}
