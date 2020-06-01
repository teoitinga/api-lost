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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/auth")
@Api("Api de Autenticação de usuarios")
//@CrossOrigin(origins = "*")
public class authentication {

	@Autowired
	private JwtService jwtService;

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsServiceImpl userDetailsService;

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation("Autenticação de usuário")
	@ApiResponses({ @ApiResponse(code = 200, message = "Usuário logadon com sucesso."),
		@ApiResponse(code = 400, message = "Erro ao registrar login de usuário.") })
	public TokenDto autenticar(@RequestBody @ApiParam("JSON do usuario") CredenciaisDto credenciais) {
		 
		try {
			Persona usuarioAutenticado = userDetailsService.autenticar(Persona.builder()
					.id(Integer.parseInt(credenciais.getLogin())).senha(credenciais.getSenha()).build());
			
			String token = this.jwtService.obterToken(usuarioAutenticado);
			
			System.out.println("token: Bearer " + token);
			return new TokenDto(String.valueOf(usuarioAutenticado.getId()), usuarioAutenticado.getNome(), token);

		} catch (PersonaNotFoundException | PasswordInValidException | NumberFormatException ex) {

			throw new UserNotAutenticatedException(ex.getMessage());

		}
	}
}
