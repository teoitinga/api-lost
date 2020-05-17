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
import br.com.jp.esloc.apilost.exceptions.PersonaNotFound;
import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.security.UserDetailsServiceImpl;
import br.com.jp.esloc.apilost.security.jwt.JwtService;
import br.com.jp.esloc.apilost.services.CompraService;
import br.com.jp.esloc.apilost.services.PersonaService;

@RestController
@RequestMapping("/api/v1/users")
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
	
	/*
	 * @Autowired private final JwtService jwtService;
	 */
	
	@GetMapping("{id}")
	public Persona getById(@PathVariable Integer id) {
		return this.personaService.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Integer id) {
		this.personaService.findById(id)
		.map(
				usuarioExistente -> {
					this.personaService.delete(usuarioExistente);
					return usuarioExistente;
				}
				)		
		.orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));

	}
	@GetMapping
    public Page<Persona> getAll() {
        return personaService.findAll();
    }
	@GetMapping("/filter")
	public List<Persona> find(Persona filtro) {
		ExampleMatcher matcher = ExampleMatcher
									.matching()
									.withIgnoreCase()
									.withStringMatcher(StringMatcher.CONTAINING);
		
		Example example = Example.of(filtro, matcher);
		return this.personaService.findAll(example);
		
	}

	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable @Valid Integer id, 
			@RequestBody Persona user) {
		
		this.personaService.findById(id).map(
				usuarioExistente -> {
					user.setId(usuarioExistente.getId());
					this.personaService.save(user);
					return usuarioExistente;
				}
				).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
	}
	@PutMapping("pay/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void payDebito(@PathVariable Integer id) {
		
		this.personaService.findById(id).map(
				usuarioExistente -> {
					this.compraService.zerarDebitoDoCliente(id);
					return usuarioExistente;
				}
				).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));
	}


	@PostMapping("cliente")
	@ResponseStatus(HttpStatus.CREATED)
	public PersonaDto save(@RequestBody @Valid ClientePostDto cliente) {

		Persona persona = this.personaService.create(cliente);
		return this.personaService.create(this.personaService.save(persona));
		
	}
	@PostMapping("auth")
	public TokenDto autenticar(@RequestBody CredenciaisDto credenciais) {
		System.out.println("Usuario:" + credenciais.getLogin());
		System.out.println("senha informada:" + credenciais.getSenha());
		try {
			Persona usuarioAutenticado = userDetailsService.autenticar(
					Persona.builder()
						.id(Integer.parseInt(credenciais.getLogin()))
						.senha(credenciais.getSenha()).build()
					);
			System.out.println("Usuario auth:" + usuarioAutenticado);
			String token = this.jwtService.gerarToken(usuarioAutenticado);
			System.out.println("token:" + token);
			return new TokenDto(
						String.valueOf(usuarioAutenticado.getId()),
						usuarioAutenticado.getNome(),
						token);
			
		}catch(PersonaNotFound | PasswordInValidException ex) {
			
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
			
		}
	}
}
