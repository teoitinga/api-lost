package br.com.jp.esloc.apilost.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.jp.esloc.apilost.domain.CompraResponseDto;
import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.services.PersonaService;

@RestController
@RequestMapping("/home")
public class homeResource {
	@Autowired
	private PersonaService personaService;
	
	@GetMapping("{id}")
	public Persona getByClienteId(@PathVariable Integer id) {
		return this.personaService.findByLogin(String.valueOf(id));
	}
}
