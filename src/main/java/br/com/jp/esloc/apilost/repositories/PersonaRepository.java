package br.com.jp.esloc.apilost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jp.esloc.apilost.models.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Integer>{

}
