package br.com.jp.esloc.apilost.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.jp.esloc.apilost.domain.ClientePostDto;
import br.com.jp.esloc.apilost.domain.CredenciaisDto;
import br.com.jp.esloc.apilost.domain.PersonaDto;
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
@RequestMapping("/api/v1/users")
@Api("Api Cliente e usuarios")
@CrossOrigin
public class PersonaResource {

	@Autowired
	private JwtService jwtService;

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private PersonaService personaService;

	@Autowired
	private CompraService compraService;

	@GetMapping("{id}")
	@ApiOperation("Obter detalhes de cliente ou usuario por ID")
	@ApiResponses({ @ApiResponse(code = 200, message = "Cliente encontrado."),
			@ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado.") })
	public Persona getById(@PathVariable Integer id) {
		return this.personaService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado para o ID informado."));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation("Apaga registro de cliente especificado por ID")
	@ApiResponses({ @ApiResponse(code = 204, message = "Cliente deletado."),
			@ApiResponse(code = 404, message = "Cliente não encontrado para o ID informado.") })
	public void delete(@PathVariable @ApiParam("ID do cliente") Integer id) {
		this.personaService.findById(id).map(usuarioExistente -> {
			this.personaService.delete(usuarioExistente);
			return usuarioExistente;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado para o ID informado."));

	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation("Obtem registro de todos os clientes")
	@ApiResponses({ @ApiResponse(code = 200, message = "Lista de clientes obtidos com sucessoCliente "),
			@ApiResponse(code = 404, message = "Não foi possível obter uma lista de clientes.") })
	public Page<Persona> getAll() {
		return personaService.findAll();
	}

	@GetMapping("/filter")
	@ApiOperation("Aplica filtro em dados dos cliente")
	@ApiResponses({ @ApiResponse(code = 201, message = "Clientes encontrados."),
			@ApiResponse(code = 400, message = "Não foi possível encontrar o cliente especificado.") })
	public List<Persona> find(Persona filtro) {
		ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING);

		Example example = Example.of(filtro, matcher);
		return this.personaService.findAll(example);

	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation("Atualiza dados do cliente informado")
	@ApiResponses({ @ApiResponse(code = 201, message = "Dados modificados com sucesso."),
			@ApiResponse(code = 400, message = "Erro ao modificar dados do cliente.") })
	public void update(@PathVariable @ApiParam("ID do cliente") @Valid Integer id, @RequestBody Persona user) {

		this.personaService.findById(id).map(usuarioExistente -> {
			user.setId(usuarioExistente.getId());
			this.personaService.save(user);
			return usuarioExistente;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado para o ID informado."));
	}

	@PutMapping("pay/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation("Registra pagamento de todas as compras em aberto do cliente informado")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cliente quitou todos os débitos."),
			@ApiResponse(code = 400, message = "Erro ao registrar baixa em débitos do cliente.") })
	public void payDebito(@PathVariable @ApiParam("ID do cliente") Integer id) {

		this.personaService.findById(id).map(usuarioExistente -> {
			this.compraService.zerarDebitoDoCliente(id);
			return usuarioExistente;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado para o ID informado."));
	}

	@PostMapping("cliente")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation("Salva registro de clientes")
	@ApiResponses({ @ApiResponse(code = 201, message = "Cliente registrado com sucesso."),
			@ApiResponse(code = 400, message = "Erro ao registrar dados do cliente.") })
	public PersonaDto save(@RequestBody @Valid @ApiParam("JSON do cliente") ClientePostDto cliente) {
			Persona persona = this.personaService.create(cliente);
			return this.personaService.create(this.personaService.save(persona));

	}

}
