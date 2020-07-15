package br.com.jp.esloc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Component;

import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.models.Role;
import br.com.jp.esloc.apilost.services.PersonaService;

@SpringBootApplication
public class ApiLostApplication extends SpringBootServletInitializer {
	
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
			// Cria permissões caso não exista as roles do usuario
			// criando registros para testar o sistema
			List<Persona> pessoal = null; 
			if (!personaService.isContaining()) {
				Role ADMIN = Role.builder().permissao("ADMIN").build();
				
				pessoal = new ArrayList<Persona>();
				pessoal.add(Persona.builder()
						.apelido("Teo")
						.nome("João Paulo")
						.fone("33 99906-5029")
						.categoria("m")
						.debito(BigDecimal.ZERO)
						.senha("jacare")
						.roles(Arrays.asList(ADMIN))
						.build());
				
				pessoal.forEach(pessoa -> personaService.save(pessoa));
			}
			
		}	
	}
}
