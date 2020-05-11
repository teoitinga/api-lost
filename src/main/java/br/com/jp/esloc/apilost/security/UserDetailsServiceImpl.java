package br.com.jp.esloc.apilost.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.services.PersonaService;

@Service(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private PersonaService personaService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		Persona user = this.personaService.findByLogin(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not found");	
		}
		//user.setSenha(encoder.encode("123"));
		//user = this.personaService.save(user);
		
			return user;// User.withUsername(username).password((user.getSenha())).roles("c").build();
		
	}
}
