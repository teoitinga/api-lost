package br.com.jp.esloc.apilost.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jp.esloc.apilost.domain.ClientePostDto;
import br.com.jp.esloc.apilost.domain.ClientePutDto;
import br.com.jp.esloc.apilost.domain.PersonaDto;
import br.com.jp.esloc.apilost.domain.UserPutDto;
import br.com.jp.esloc.apilost.domain.UserViewDto;
import br.com.jp.esloc.apilost.exceptions.PersonaNotFoundException;
import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.models.Role;

public interface PersonaService {
	
	Persona save(Persona persona);
	boolean isContaining();
	Page<Persona> findAll(Pageable page);
	Page<Persona> findAll();
	List<Persona> findClientes();
	void delete(Persona p);
	Optional<Persona> findByLogin(String login) throws PersonaNotFoundException;
	Optional<Persona> findById(Integer idPersona) throws PersonaNotFoundException;
	List<Persona> findAll(Example example);
	Persona create(ClientePostDto cliente);
	PersonaDto create(Persona persona);
	Optional<List<PersonaDto>> toListPersonaDto(List<Persona> findClientes);
	Persona create(ClientePutDto cliente);
	void deleteAll();
	List<UserViewDto> findAllUsers();
	List<Role> findAllRoles();
	Persona create(@Valid UserPutDto user);
	
}
