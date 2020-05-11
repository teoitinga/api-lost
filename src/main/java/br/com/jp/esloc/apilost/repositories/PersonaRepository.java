package br.com.jp.esloc.apilost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.jp.esloc.apilost.models.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer>{
	@Query("select p from Persona p where (p.id = :login) AND ( (p.categoria='m') OR (p.categoria='u') ) ")
	Persona findByLogin( @Param("login") Integer login );

}
