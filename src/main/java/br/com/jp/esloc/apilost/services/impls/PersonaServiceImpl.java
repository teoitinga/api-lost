package br.com.jp.esloc.apilost.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.jp.esloc.apilost.exceptions.PersonaNotFound;
import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.repositories.PersonaRepository;
import br.com.jp.esloc.apilost.services.PersonaService;

@Service
public class PersonaServiceImpl implements PersonaService{
	
	@Autowired
	private PersonaRepository personaRepository;
	
	@Override
	public Persona save(Persona persona) {
		return this.personaRepository.save(persona);
	}
/**
	@Override
	public Page<Persona> findAll(Pageable pageable) {
		return this.personaRepository.findAll(pageable);
	}
	**/
	@Override
	public Page<Persona> findAll(Pageable page) {
		return this.personaRepository.findAll(page);
	}

	@Override
	public Persona findById(Integer idPersona) throws PersonaNotFound {
		return this.personaRepository.findById(idPersona).orElseThrow(()-> new PersonaNotFound("Cadastro não encontrado."));
	}

	@Override
	public boolean isContaining() {
		return this.personaRepository.findAll().size()>0?true:false;
	}

}
