package br.com.jp.esloc;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.services.PersonaService;

@SpringBootApplication
public class ApiLostApplication {
	private static final Logger log = LoggerFactory.getLogger(ApiLostApplication.class);
	
	@Autowired
	private PersonaService personaService;

	public static void main(String[] args) {
		SpringApplication.run(ApiLostApplication.class, args);
	}
	@Component
	public class CommandLineAppStartupRunner implements CommandLineRunner {
		@Override
		public void run(String... args) throws Exception {
			//createPersonaIfNotExists();

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
				
				pessoal.forEach(pessoa -> personaService.save(pessoa));
			}
			
		}	
	}
}
