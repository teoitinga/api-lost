package br.com.jp.esloc.apilost.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jp.esloc.apilost.config.LostResponse;
import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.services.PersonaService;

@RestController
public class PersonaResource {
	@Autowired
	private PersonaService personaService;
	
	@GetMapping("/pessoal")
	public ResponseEntity<LostResponse<Page<Persona>>> findAll() {
		LostResponse<Page<Persona>> response = new LostResponse<Page<Persona>>();
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<Persona> pessoal = this.personaService.findAll(pageable);
		
		response.setData(pessoal);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	@GetMapping("/home")
	public String ola() {
		return "Testando API lost";
	}

}
