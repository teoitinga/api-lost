package br.com.jp.esloc.apilost.services;

import java.util.List;

import br.com.jp.esloc.apilost.exceptions.PersonaNotFound;
import br.com.jp.esloc.apilost.models.Persona;

public interface PersonaService {
	
	Persona save(Persona persona);
	//Page<Persona> findAll(Pageable pageable);
	Persona findById(Integer idPersona) throws PersonaNotFound;
	boolean isContaining();
	List<Persona> findAll();
	
}
