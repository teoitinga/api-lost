package br.com.jp.esloc.apilost.services.impls;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
		
		//persona.setSenha(encoder.encode(persona.getSenha()));
		return this.personaRepository.save(persona);
	}

	@Override
	public Page<Persona> findAll(Pageable page) {
		return this.personaRepository.findAll(page);
	}

	@Override
	public Optional<Persona> findById(Integer idPersona) throws PersonaNotFound {
		return Optional.of(this.personaRepository.findById(idPersona).orElseThrow(()-> new PersonaNotFound("Cadastro não encontrado.")));
	}

	@Override
	public boolean isContaining() {
		return this.personaRepository.findAll().size()>0?true:false;
	}

	@Override
	public void delete(Persona p) {
		this.personaRepository.delete(p);
		
	}

	@Override
	public Persona findByLogin(String login) throws PersonaNotFound {
//		public Persona findByLogin(String login) throws PersonaNotFound {
		return this.personaRepository.findByLogin(Integer.parseInt(login));
	}

	public Page<Persona> search(
            String searchTerm,
            int page,
            int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "name");

        return personaRepository.search(
                searchTerm.toLowerCase(),
                pageRequest);
    }

    public Page<Persona> findAll() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "name");
        return new PageImpl<>(
        		personaRepository.findAll(), 
                pageRequest, size);
    }

	@Override
	public List<Persona> findAll(Example example) {
		return (List<Persona>) this.personaRepository.findAll(example);
	}

}
