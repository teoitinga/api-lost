package br.com.jp.esloc.apilost.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	private static final Logger log = LoggerFactory.getLogger(PersonaResource.class);
	
	@Autowired
	private PersonaService personaService;
	
	@GetMapping("/pessoal")
	public ResponseEntity<LostResponse<List<Persona>>> findAll() {
		LostResponse<List<Persona>> response = new LostResponse<List<Persona>>();
		
		Pageable pageable = PageRequest.of(0, 10);
		
		List<Persona> pessoal = this.personaService.findAll();
		
		//log.info("Quantidade de registros {} ", pessoal.size());
		//log.info("Registro 01 {} ", pessoal.get(0).size());
		response.setData(pessoal);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	@GetMapping("/home")
	public String ola() {
		return "Testando API lost";
	}

}
