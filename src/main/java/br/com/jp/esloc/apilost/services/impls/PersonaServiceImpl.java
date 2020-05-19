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

import br.com.jp.esloc.apilost.domain.ClientePostDto;
import br.com.jp.esloc.apilost.domain.PersonaDto;
import br.com.jp.esloc.apilost.exceptions.PersonaNotFoundException;
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

	@Override
	public Page<Persona> findAll(Pageable page) {
		return this.personaRepository.findAll(page);
	}

	@Override
	public Optional<Persona> findById(Integer idPersona) throws PersonaNotFoundException {
		return Optional.of(this.personaRepository.findById(idPersona).orElseThrow(()-> new PersonaNotFoundException("{cliente.not.found}")));
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
	public Optional<Persona> findByLogin(String login) throws PersonaNotFoundException {
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

	
	@Override
	public Persona create(ClientePostDto cliente) {
		Persona vendedor = this.personaRepository.findById(cliente.getVendedor()).orElseThrow(()->new PersonaNotFoundException("{vendedor.not.found}"));
		
			return Persona.builder()
					.id(cliente.getId())
					.nome(cliente.getNome())
					.rg(cliente.getRg())
					.apelido(cliente.getApelido())
					.endereco(cliente.getEndereco())
					.fone(cliente.getFone())
					.usuario(vendedor.getId())
					.prazo(cliente.getPrazo())
					.state(cliente.getState())
					.categoria(cliente.getCategoria())
					.build();
		}
	@Override
	public PersonaDto create(Persona persona) {
		
		Persona vendedor = this.personaRepository.findById(persona.getId()).orElseThrow(()->new PersonaNotFoundException("{vendedor.not.found}"));
		
		return PersonaDto.builder()
				.id(persona.getId())
				.nome(persona.getNome())
				.rg(persona.getRg())
				.apelido(persona.getApelido())
				.endereco(persona.getEndereco())
				.fone(persona.getFone())
				.usuario(vendedor.getId())
				.prazo(persona.getPrazo())
				.state(persona.getState())
				.categoria(persona.getCategoria())
				.build();
	}

}
