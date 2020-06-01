package br.com.jp.esloc.apilost.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.jp.esloc.apilost.exceptions.PasswordInValidException;
import br.com.jp.esloc.apilost.exceptions.PersonaNotFoundException;
import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.services.PersonaService;

@Service(value = "userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Persona loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.personaService.findByLogin(username)
				.orElseThrow(()->new PersonaNotFoundException("Usuario não encontrado."));

	}
	
	public Persona autenticar(Persona usuario) throws PasswordInValidException{
		Persona user = loadUserByUsername(String.valueOf(usuario.getId()));
		boolean passwordOk = bCryptPasswordEncoder.matches(usuario.getSenha(), user.getPassword());
		if(passwordOk) {
			return user;
		}else {
			throw new PasswordInValidException();
		}
	}
}
