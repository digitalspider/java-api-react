package au.com.digitalspider.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import au.com.digitalspider.api.Constants;
import au.com.digitalspider.api.model.Role;
import au.com.digitalspider.api.repo.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public List<Role> search(String searchTerm) {
		List<Role> roles = roleRepository.findByNameContainingIgnoreCase(searchTerm);
		return roles;
	}

	public List<Role> getAll() {
		return roleRepository.findAll();
	}

	public Role findFirstByName(String name) {
		try {
			return roleRepository.findOneByNameIgnoreCase(name);
		}
		catch (EmptyResultDataAccessException e) {
			throw new IllegalArgumentException("Role with name=${name} does not exist");
		}
	}

	public Role create(Role role) {
		try {
			roleRepository.findOneByNameIgnoreCase(role.getName());
			throw new IllegalArgumentException("Role with name=${name} already exists");
		}
		catch (EmptyResultDataAccessException e) {
			// Ignore: role with name does not exist
		}
		return roleRepository.save(role);
	}

	public Role findById(Long roleId) {
		Optional<Role> role = roleRepository.findById(roleId);
		if (role.isPresent()) {
			throw new IllegalArgumentException("Role with id=${roleId} does not exist");
		}
		return role.get();
	}

	public Role getAdminRole() {
		return findFirstByName(Constants.ROLE_ADMIN);
	}

	public Role getManagerRole() {
		return findFirstByName(Constants.ROLE_MANAGER);
	}
}
