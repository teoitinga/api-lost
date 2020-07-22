package br.com.jp.esloc.apilost.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.jp.esloc.apilost.domain.CredenciaisDto;
import br.com.jp.esloc.apilost.domain.PersonaDto;
import br.com.jp.esloc.apilost.domain.TokenDto;
import br.com.jp.esloc.apilost.domain.UserPutDto;
import br.com.jp.esloc.apilost.domain.UserViewDto;
import br.com.jp.esloc.apilost.exceptions.PasswordInValidException;
import br.com.jp.esloc.apilost.exceptions.PersonaNotFoundException;
import br.com.jp.esloc.apilost.exceptions.UserNotAutenticatedException;
import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.models.Role;
import br.com.jp.esloc.apilost.security.UserDetailsServiceImpl;
import br.com.jp.esloc.apilost.security.jwt.JwtService;
import br.com.jp.esloc.apilost.services.PersonaService;
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

	@Autowired
	private PersonaService personaService;

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

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation("Obtem registro de todos os usuarios")
	@ApiResponses({ @ApiResponse(code = 200, message = "Lista de usuários com sucesso "),
			@ApiResponse(code = 404, message = "Não foi possível obter uma lista de usuários.") })
	public List<UserViewDto> getAll() {
		return this.personaService.findAllUsers();

	}

	@GetMapping("/roles")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation("Obtem registro de todas as roles registradas no sistemas")
	@ApiResponses({ @ApiResponse(code = 200, message = "Lista de roles obtida com sucesso "),
			@ApiResponse(code = 404, message = "Não foi possível obter uma lista de roles.") })
	public List<Role> getAllRoles() {
		return this.personaService.findAllRoles();

	}

	@PostMapping("usuario")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation("Salva registro de usuario")
	@ApiResponses({ @ApiResponse(code = 201, message = "Usuario registrado com sucesso."),
			@ApiResponse(code = 400, message = "Erro ao registrar dados do usuario.") })
	public PersonaDto save(@RequestBody @Valid @ApiParam("JSON do usuario") UserPutDto user) {
		Persona persona = this.personaService.create(user);

		return this.personaService.create(this.personaService.save(persona));

	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation("Atualiza dados do usuario informado")
	@ApiResponses({ @ApiResponse(code = 200, message = "Dados modificados com sucesso."),
			@ApiResponse(code = 404, message = "Erro ao modificar dados do usuario.") })
	public void update(@PathVariable @ApiParam("ID do usuário") Integer id,
			@Valid @RequestBody UserPutDto user) {
		
		user.setId(id);
		Persona newUser = this.personaService.create(user);

		this.personaService.save(newUser);

	}
}
