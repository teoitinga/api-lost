package br.com.jp.esloc.apilost.services;

import br.com.jp.esloc.apilost.models.Role;

public interface RoleService {
	boolean isContaining();
	Role save(Role role);
	Role getRoleAdmin();
	void deleteAll();
}
