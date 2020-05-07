package br.com.jp.esloc.apilost.resources;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jp.esloc.apilost.config.LostResponse;
import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.services.PersonaService;

@RestController
@RequestMapping("/api/v1/users")
public class PersonaResource {
	
	private static final Logger log = LoggerFactory.getLogger(PersonaResource.class);
	
	@Autowired
	private PersonaService personaService;
	
	@GetMapping()
	public Page<Persona> findAll(@RequestParam(value="page", defaultValue = "1") Integer page,
			@RequestParam(value="size", defaultValue = "2") Integer size) {
		
		//public ResponseEntity<LostResponse<List<Persona>>> findAll() {
		LostResponse<List<Persona>> response = new LostResponse<List<Persona>>();
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<Persona> pessoal = this.personaService.findAll(PageRequest.of(page, size));
		
		pessoal.forEach(pessoa -> System.out.println(pessoa.getNome()));
		//response.setData(pessoal);
		
//		return ResponseEntity.status(HttpStatus.OK).body(response);
		return pessoal;
		
	}

}
