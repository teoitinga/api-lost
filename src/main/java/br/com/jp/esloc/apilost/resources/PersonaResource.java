package br.com.jp.esloc.apilost.resources;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.jp.esloc.apilost.domain.PersonaDto;
import br.com.jp.esloc.apilost.domain.PersonaPostDto;
import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.responce.Response;
import br.com.jp.esloc.apilost.services.PersonaService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class PersonaResource {
	
	@Autowired
	private PersonaService personaService;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	@GetMapping()
	public ResponseEntity<Response<Page<PersonaDto>>> findAll(@RequestParam(value="page", defaultValue = "0") Integer page,
			@RequestParam(value="size", defaultValue = "5") Integer size) {
		
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
	
	@PostMapping()
	public ResponseEntity<Response<PersonaDto>> create(@RequestBody PersonaPostDto persona){
		
		Response<PersonaDto> response = new Response<PersonaDto>();
		
		ModelMapper modelMapper = new ModelMapper();
		Persona p = modelMapper.map(persona, Persona.class);
		
		p.setSenha(encoder.encode(p.getSenha()));
		
		p = this.personaService.save(p);
		
		PersonaDto dto = modelMapper.map(p, PersonaDto.class);
		response.setData(dto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Response<PersonaDto>> update(@PathVariable("id") Integer id, @RequestBody PersonaPostDto persona){
		
		Response<PersonaDto> response = new Response<PersonaDto>();
		
		ModelMapper modelMapper = new ModelMapper();

		//obtem registro do usuario informado pelo id
		Persona p = this.personaService.findById(id);		
		p = modelMapper.map(persona, Persona.class);
		
		p = this.personaService.save(p);
		
		PersonaDto dto = modelMapper.map(p, PersonaDto.class);
		response.setData(dto);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Response<PersonaDto>> delete(@PathVariable("id") Integer id){
		Response<PersonaDto> response = new Response<PersonaDto>();
		ModelMapper modelMapper = new ModelMapper();
		
		//obtem registro do usuario informado pelo id
		Persona p = this.personaService.findById(id);
		
		PersonaDto dto = modelMapper.map(p, PersonaDto.class);
		//deletando usuario especificado
		this.personaService.delete(p);
		
		response.setData(dto);
		
		return ResponseEntity.status(HttpStatus.OK).body(response);	
	}

}
