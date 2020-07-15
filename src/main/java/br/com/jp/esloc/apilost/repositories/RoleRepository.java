package br.com.jp.esloc.apilost.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jp.esloc.apilost.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{

	Optional<Role> findByPermissao(String string);

}
