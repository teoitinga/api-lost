package br.com.jp.esloc.apilost.resources;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jp.esloc.apilost.domain.PersonaDto;
import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.responce.Response;
import br.com.jp.esloc.apilost.services.PersonaService;

@RestController
@RequestMapping("/api/v1/users")
public class PersonaResource {
	
	private static final Logger log = LoggerFactory.getLogger(PersonaResource.class);
	
	@Autowired
	private PersonaService personaService;

	@GetMapping()
	public ResponseEntity<Response<Page<PersonaDto>>> findAll(@RequestParam(value="page", defaultValue = "1") Integer page,
			@RequestParam(value="size", defaultValue = "2") Integer size) {
		
		Response<Page<PersonaDto>> response = new Response<Page<PersonaDto>>();
		
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<Persona> pessoal = this.personaService.findAll(PageRequest.of(page, size));

		Page<PersonaDto> pessoalDto = new PageImpl(pessoal.stream().map(PersonaDto::create).collect(Collectors.toList()));

		
		ModelMapper modelMapper = new ModelMapper();
		Page<PersonaDto> dto = new PageImpl(pessoal.stream().map(p -> modelMapper
				.map(p, PersonaDto.class)).collect(Collectors.toList()));
		
		//Page<PersonaDto> persona = modelMapper.map(pessoal, Page<PersonaDto>.class);
		
		response.setData(dto);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<Response<PersonaDto>> findById(@PathVariable("id") Integer id){
		
		Response<PersonaDto> response = new Response<PersonaDto>();
		
		Persona persona = this.personaService.findById(id);
		
		ModelMapper modelMapper = new ModelMapper();
		
		PersonaDto personaDto = modelMapper.map(persona, PersonaDto.class);
		
		response.setData(personaDto);
		
		return ResponseEntity.ok().body(response);
	}

}
