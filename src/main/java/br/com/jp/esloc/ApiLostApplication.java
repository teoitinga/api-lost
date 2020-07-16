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
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.jp.esloc.apilost.models.Persona;
import br.com.jp.esloc.apilost.models.Role;
import br.com.jp.esloc.apilost.services.PersonaService;
import br.com.jp.esloc.apilost.services.RoleService;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootApplication
public class ApiLostApplication extends SpringBootServletInitializer {
	

	@Autowired
	private PersonaService personaService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiLostApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Component
	public class CommandLineAppStartupRunner implements CommandLineRunner {
		@Override
		public void run(String... args) throws Exception {
			createPersonaIfNotExists();

		}
		private void createPersonaIfNotExists() {
			//personaService.deleteAll();
			log.info("Verificando se existe usuários cadastrados! {}", personaService.isContaining());
			// Cria permissões caso não exista as roles do usuario
			// criando registros para testar o sistema
			List<Persona> pessoal = null; 
			if (!personaService.isContaining()) {
				log.info("Nenhum usuário registrado...");
				log.info("Criando usuário padrão");
				Role ADMIN = roleService.getRoleAdmin();
				if(ADMIN == null) {
					ADMIN = Role.builder().permissao("ADMIN").build();
					ADMIN = roleService.save(ADMIN);
				}
				
				pessoal = new ArrayList<Persona>();
				String password = "jacare";
				Persona userMaster01 = Persona.builder()
						.apelido("Teo")
						.nome("João Paulo")
						.fone("33 99906-5029")
						.categoria("m")
						.debito(BigDecimal.ZERO)
						//.senha(new BCryptPasswordEncoder().encode(password))
						.senha(new BCryptPasswordEncoder().encode(password))
						.roles(Arrays.asList(ADMIN))
						.build();
				userMaster01 = personaService.save(userMaster01);
				log.info("Usuário criado ID:{}", userMaster01.getId());
				log.info("Usuário criado Nome:{}", userMaster01.getNome());
				log.info("Usuário criado Senha:{}", password);
				pessoal.add(userMaster01);
				
				//pessoal.forEach(pessoa -> personaService.save(pessoa));
			}
			
		}	
	}
}
