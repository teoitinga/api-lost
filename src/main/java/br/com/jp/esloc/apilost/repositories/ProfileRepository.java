package br.com.jp.esloc.apilost.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jp.esloc.apilost.models.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Integer>{

}
