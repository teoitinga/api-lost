package br.com.jp.esloc.apilost.services.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jp.esloc.apilost.models.Role;
import br.com.jp.esloc.apilost.repositories.RoleRepository;
import br.com.jp.esloc.apilost.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public boolean isContaining() {
		return this.roleRepository.findAll().size()>0?true:false;
	}

	@Override
	public Role save(Role role) {
		return this.roleRepository.save(role);
	}

	@Override
	public Role getRoleAdmin() {
		return this.roleRepository.findByPermissao("ADMIN").orElse(null);
	}

	@Override
	public void deleteAll() {
		this.roleRepository.deleteAll();
		
	}

}
