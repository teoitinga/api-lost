package br.com.jp.esloc.apilost.services.impls;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.jp.esloc.apilost.domain.ClientePostDto;
import br.com.jp.esloc.apilost.domain.ClientePutDto;
import br.com.jp.esloc.apilost.domain.PersonaDto;
import br.com.jp.esloc.apilost.domain.UserPutDto;
import br.com.jp.esloc.apilost.domain.UserViewDto;
import br.com.jp.esloc.apilost.exceptions.PersonaNotFoundException;
import br.com.jp.esloc.apilost.exceptions.RoleNotFoundException;
import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.models.Role;
import br.com.jp.esloc.apilost.repositories.PersonaRepository;
import br.com.jp.esloc.apilost.repositories.RoleRepository;
import br.com.jp.esloc.apilost.services.PersonaService;

@Service
public class PersonaServiceImpl implements PersonaService{
	
	@Autowired
	private PersonaRepository personaRepository;
	@Autowired
	private RoleRepository roleRepository;

	private PasswordEncoder bCryptPasswordEncoder;
	
	public PersonaServiceImpl(PasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public Persona save(Persona persona) {
		if(persona.getId() == null) {
			persona.setDebito(BigDecimal.ZERO);
		}
		String senha = persona.getSenha();
		if(senha != null) {
			senha = bCryptPasswordEncoder.encode(senha);
			persona.setSenha(senha);
		}
		System.out.println("Senha put: " + persona.getSenha());	
		System.out.println("Senha put equal: " + bCryptPasswordEncoder.matches("123456", persona.getSenha()));	
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
	public List<Persona> findClientes(){
		return this.personaRepository.findByCategoria("c");
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
	public Persona create(ClientePutDto cliente) {
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
				.build();
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
				.usuario(vendedor.getUsuario())
				.prazo(persona.getPrazo())
				.state(persona.getState())
				.categoria(persona.getCategoria())
				.dataCadastro(persona.getDataCadastro())
				.ultAtualizacao(persona.getUltAtualizacao())
				.senha(persona.getSenha())
				.debito(persona.getDebito())
				.build();
	}
	public Optional<List<PersonaDto>> toListPersonaDto(List<Persona> pessoal){
		return Optional.of(pessoal.stream()
				.map(persona->this.toPersonaDto(persona))
				.collect(Collectors.toList()));
	}
	public PersonaDto toPersonaDto(Persona persona) {
		return PersonaDto.builder()
				.id(persona.getId())
				.nome(persona.getNome())
				.apelido(persona.getApelido())
				.endereco(persona.getEndereco())
				.rg(persona.getRg())
				.fone(persona.getFone())
				.dataCadastro(persona.getDataCadastro())
				.usuario(persona.getUsuario())
				.prazo(persona.getPrazo())
				.state(persona.getState())
				.ultAtualizacao(persona.getUltAtualizacao())
				.senha(persona.getSenha())
				.categoria(persona.getCategoria())
				.debito(persona.getDebito())
				.build();
				
	}

	@Override
	public void deleteAll() {
		this.personaRepository.deleteAll();
	}

	@Override
	public List<UserViewDto> findAllUsers() {
		List<Object[]> lista = this.personaRepository.findOnlyUsers();
		return this.personaRepository.findOnlyUsers().stream().map(user->toUserViewDto(user))
				.collect(Collectors.toList());

	}
	
	private UserViewDto toUserViewDto(Object[] object) {
		try {
			return UserViewDto.builder()
					.id(Long.parseLong(String.valueOf(object[0])))
					.apelido(String.valueOf(object[1]))
					.role(String.valueOf(object[2]))
					.build();
			
		}catch(NumberFormatException ex) {
			return null;
		}
	}

	@Override
	public List<Role> findAllRoles() {
		return this.roleRepository.findAll();
	}

	@Override
	public Persona create(UserPutDto user) {
		//buscando ID do usuario atual
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication(); 
		Persona usuarioAtual = null;
		
		if(authentication != null){
			Object obj = authentication.getPrincipal();
			
			if (obj instanceof Persona){
				usuarioAtual = (Persona) obj;
			}		
		}		
		//obtem o perfil do usuario no qual se pretende modificar os registros
		Persona usuario = this.personaRepository.findById(user.getId()).orElseThrow(()->new PersonaNotFoundException("Usuário não encontrado no banco de dados."));
		
		//obtem a Role
		Role role = this.roleRepository.findByPermissao(user.getRole()).orElseThrow(()->new RoleNotFoundException());;

		return Persona.builder()
				.id(usuario.getId())
				.nome(user.getNome())
				.rg(user.getRg().toUpperCase())
				.apelido(user.getApelido())
				.endereco(user.getEndereco())
				.fone(usuario.getFone())
				.usuario(usuarioAtual.getId())
				.prazo(usuario.getPrazo())
				.state(usuario.getState())
				.senha(this.bCryptPasswordEncoder.encode(user.getSenha()))
				.categoria(usuario.getCategoria())
				.dataCadastro(usuario.getDataCadastro())
				.debito(usuario.getDebito())
				
				.roles(Arrays.asList(role))
			.build();
	}

}
