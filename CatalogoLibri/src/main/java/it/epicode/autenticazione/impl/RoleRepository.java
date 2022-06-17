package it.epicode.autenticazione.impl;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {
	
	public Role findByRoleName( ERole nomeRuolo);
	
}
