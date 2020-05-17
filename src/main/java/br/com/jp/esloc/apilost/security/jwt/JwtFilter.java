package br.com.jp.esloc.apilost.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.security.UserDetailsServiceImpl;

public class JwtFilter extends OncePerRequestFilter {

	private JwtService service;

	private UserDetailsServiceImpl UserDetailsServiceImpl;
	
	public JwtFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsServiceImpl) {
		this.service = jwtService;
		this.UserDetailsServiceImpl = userDetailsServiceImpl;
		
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {
		String authorization = request.getHeader("Authorization");
		if(authorization!=null && authorization.startsWith("Bearer")) {
			String token = authorization.split(" ")[1];
			boolean isValid = service.tokenValid(token);
			
			if(isValid) {
				String usuarioLogado = service.obterLogin(token);
				Persona usuario = UserDetailsServiceImpl.loadUserByUsername(usuarioLogado);
				UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
				user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(user);
			}
		}
		filterChain.doFilter(request, response);
	}

}
