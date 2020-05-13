package br.com.jp.esloc.apilost.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.jp.esloc.apilost.models.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer>{
	@Query("select p from Persona p where (p.id = :login) AND (NOT p.categoria='c')")
	Persona findByLogin( @Param("login") Integer login );

	@Query("select p from Persona p")
	Page<Persona> search(String lowerCase, PageRequest pageRequest);

}
