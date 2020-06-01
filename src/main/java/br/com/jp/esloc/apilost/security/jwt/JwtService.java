package br.com.jp.esloc.apilost.security.jwt;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.jp.esloc.apilost.models.Persona;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class JwtService {
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";
	
	static final String CLAIM_KEY_USERNAME = "sub";
	static final String CLAIM_KEY_ROLE = "role";
	static final String CLAIM_KEY_AUDIENCE = "audience";
	static final String CLAIM_KEY_CREATED = "created";	
	
	@Value("${securityjwt.expiracao}")
	private String EXPIRATION_TIME;
	
	@Value("${securityjwt.chave-assinatura}")
	private String SECRET_KEY;
	
	/**
	 * Retorna um novo token JWT com base nos dados do usuários.
	 * 
	 * @param user
	 * @return String
	 */
	public String obterToken(Persona user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, user.getUsername());
		user.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));
		claims.put(CLAIM_KEY_CREATED, new Date());
		if (user != null) {
			claims.put("nome", user.getNome());

		}

		return gerarToken(claims);
	}
	/**
	 * Gera um novo token JWT contendo os dados (claims) fornecidos.
	 * 
	 * @param claims
	 * @return String
	 */
	private String gerarToken(Map<String, Object> claims) {
		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(gerarDataExpiracao())
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
	}
	/**
	 * Verifica se um token JTW está expirado.
	 * 
	 * @param token
	 * @return boolean
	 */
	private boolean tokenExpirado(String token) {
		Date dataExpiracao = this.getExpirationDateFromToken(token);
		if (dataExpiracao == null) {
			return false;
		}
		return dataExpiracao.before(new Date());
	}
	/**
	 * Retorna a data de expiração de um token JWT.
	 * 
	 * @param token
	 * @return Date
	 */
	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}
	/**
	 * Retorna a data de expiração com base na data atual.
	 * 
	 * @return Date
	 */
	private Date gerarDataExpiracao() {
		long expString = Long.valueOf(this.EXPIRATION_TIME);
		LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
		Date dataExpira = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());
		return dataExpira;
	}
	public String geraToken(Persona persona) {
		
		long expString = Long.valueOf(this.EXPIRATION_TIME);
		
		LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
		Date dataExpira = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());
		Date dataGeracao = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

		Map<String, Object> claims = new HashMap<>();

		claims.put(CLAIM_KEY_USERNAME, persona.getId());
		claims.put(CLAIM_KEY_ROLE, persona.getRoles().get(0).getAuthority());
		claims.put(CLAIM_KEY_AUDIENCE, persona.getRoles().get(0).getAuthority());
		claims.put(CLAIM_KEY_CREATED, dataGeracao);
		
		if (persona != null) {
			claims.put("name", persona.getNome());

		}
		return Jwts.builder()
				.setSubject(persona.getUsername())
//				.setAudience(persona.getRoles())
//				.setClaims(claims)
				.setExpiration(dataExpira)
		.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
		.compact();
		
	}
	private Claims obterClaims(String token)  throws ExpiredJwtException {
		return Jwts.parser().setSigningKey(SECRET_KEY)
		.parseClaimsJws(token).getBody();
	}
	public String refreshToken(String token) {
		String refreshedToken;
		long expString = Long.valueOf(this.EXPIRATION_TIME);
		LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
		Date data = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());
		Date dataGeracao = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
		
		try {
			final Claims claims = getClaimsFromToken(token);
			claims.put(CLAIM_KEY_CREATED, data);
			claims.put(CLAIM_KEY_CREATED, dataGeracao);
			refreshedToken = this.doGenerateToken(claims);
			
		}catch(Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}
	private String doGenerateToken(Map<String, Object> claims) {
		
		Date dataGera = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
		LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(Long.valueOf(this.EXPIRATION_TIME));
		Date dataExpira = Date.from(dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant());
		
		claims.put(CLAIM_KEY_CREATED, dataGera);
		
		return Jwts.builder()
				.setClaims(claims)
				.setExpiration(dataExpira)
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();

	}
	public boolean canTokenBeRefreshed(String token) {
		return tokenValid(token);
	}
	public Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(SECRET_KEY)
					.parseClaimsJws(token)
					.getBody();
		}catch(Exception e) {
			claims = null;
		}
		return claims;
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
