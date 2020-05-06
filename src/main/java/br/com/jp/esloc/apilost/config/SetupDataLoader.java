package br.com.jp.esloc.apilost.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.services.PersonaService;

@Configuration
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final Logger log = LoggerFactory.getLogger(SetupDataLoader.class);
	
	@Autowired
	private PersonaService personaService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		
		createPersonaIfNotExists();
		
	}
	private void createPersonaIfNotExists() {
		// criando registros para testar o sistema
		List<Persona> pessoal = null; 
		if (!personaService.isContaining()) {
			pessoal = new ArrayList<Persona>();
			pessoal.add(new Persona("José"));
			pessoal.add(new Persona("Maria"));
			pessoal.add(new Persona("Pedro"));
			pessoal.add(new Persona("Marculino"));
			
			log.info("Criando registro de pessoal{}", pessoal);
			pessoal.forEach(pessoa -> personaService.save(pessoa));
		}
		
	}

}
