package br.com.jp.esloc.apilost.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import br.com.jp.esloc.ApiLostApplication;
import br.com.jp.esloc.apilost.models.Persona;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class JwtService {
	
	@Value("${securityjwt.expiracao}")
	private String expiracao;
	@Value("${securityjwt.chave-assinatura}")
	private String chaveAssinatura;
	
	public String gerarToken(Persona persona) {
		long expString = Long.valueOf(this.expiracao);
		LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
		Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());
		
		HashMap<String, Object> claims = new HashMap<>();
		
		claims.put("nome", persona.getNome());
		claims.put("fone", persona.getFone());
		claims.put("roles", persona.getRoles());
		
		return Jwts.builder()
				.setSubject(String.valueOf(persona.getId()))
				.setExpiration(data)
	//			.setClaims(claims)
				.signWith(SignatureAlgorithm.HS512, chaveAssinatura)
				.compact();
	}
	private Claims obterClaims(String token)  throws ExpiredJwtException {
		
		return Jwts.parser().setSigningKey(chaveAssinatura)
		.parseClaimsJws(token).getBody();
	}
	public boolean tokenValid(String token) {
		try {
			Claims claims = obterClaims(token);
			Date dataExpiracao = claims.getExpiration();
			LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			return !LocalDateTime.now().isAfter(data);
			
		}catch(Exception ex) {
			return false;
		}
	}
	public String obterLogin(String token){
		return obterClaims(token).getSubject();
	}
	
}
