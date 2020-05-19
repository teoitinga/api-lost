package br.com.jp.esloc.apilost.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.jp.esloc.apilost.domain.CredenciaisDto;
import br.com.jp.esloc.apilost.domain.TokenDto;
import br.com.jp.esloc.apilost.exceptions.PasswordInValidException;
import br.com.jp.esloc.apilost.exceptions.PersonaNotFoundException;
import br.com.jp.esloc.apilost.exceptions.UserNotAutenticatedException;
import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.security.UserDetailsServiceImpl;
import br.com.jp.esloc.apilost.security.jwt.JwtService;
import br.com.jp.esloc.apilost.services.CompraService;
import br.com.jp.esloc.apilost.services.PersonaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/auth")
@Api("Api de Autenticação de usuarios")
@CrossOrigin
public class authentication {

	@Autowired
	private JwtService jwtService;

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsServiceImpl userDetailsService;

	/*
	 * @Autowired private PersonaService personaService;
	 * 
	 * @Autowired private CompraService compraService;
	 */

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation("Autenticação de usuário")
	@ApiResponses({ @ApiResponse(code = 200, message = "Usuário logadon com sucesso."),
		@ApiResponse(code = 400, message = "Erro ao registrar login de usuário.") })
	public TokenDto autenticar(@RequestBody @ApiParam("JSON do usuario") CredenciaisDto credenciais) {
		
		  System.out.println("Usuario:" + credenciais.getLogin());
		  System.out.println("senha informada:" + credenciais.getSenha());
		 
		try {
			Persona usuarioAutenticado = userDetailsService.autenticar(Persona.builder()
					.id(Integer.parseInt(credenciais.getLogin())).senha(credenciais.getSenha()).build());
			System.out.println("Usuario auth:" + usuarioAutenticado);
			String token = this.jwtService.gerarToken(usuarioAutenticado);
			System.out.println("token:" + token);
			return new TokenDto(String.valueOf(usuarioAutenticado.getId()), usuarioAutenticado.getNome(), token);

		} catch (PersonaNotFoundException | PasswordInValidException ex) {

			throw new UserNotAutenticatedException(ex.getMessage());

		}
	}
}
