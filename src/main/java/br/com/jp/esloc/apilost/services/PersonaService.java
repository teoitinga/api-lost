package br.com.jp.esloc.apilost.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.jp.esloc.apilost.exceptions.PersonaNotFound;
import br.com.jp.esloc.apilost.models.Persona;

public interface PersonaService {
	
	Persona save(Persona persona);
	Persona findById(Integer idPersona) throws PersonaNotFound;
	Persona findByLogin(String login) throws PersonaNotFound;
	boolean isContaining();
	Page<Persona> findAll(Pageable page);
	void delete(Persona p);
	
	
}
