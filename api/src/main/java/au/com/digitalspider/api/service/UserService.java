package au.com.digitalspider.api.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import au.com.digitalspider.api.Constants;
import au.com.digitalspider.api.auth.SecurityUserDetails;
import au.com.digitalspider.api.model.Role;
import au.com.digitalspider.api.model.User;
import au.com.digitalspider.api.repo.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleService roleService;

	private static final Logger LOG = Logger.getLogger(UserService.class);

	public User init(User user) {
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		try {
			User user = findByUsername(username);
			SecurityUserDetails securityUserDetails = new SecurityUserDetails(user);
			return securityUserDetails;
		}
		catch (IllegalArgumentException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
	}

	public List<User> search(String searchTerm) {
		List<User> users = userRepository.findAll();
		final String searchTermLc = searchTerm.toLowerCase();
		return users.stream().filter(user -> user.getEmail().toLowerCase().indexOf(searchTermLc) > 0 ||
				user.getUsername().toLowerCase().indexOf(searchTermLc) > 0).map(user -> init(user)).collect(Collectors.toList());
	}

	public List<User> getAll() {
		if (isAdmin()) {
			return userRepository.findAll().stream().map(userInStream -> init(userInStream)).collect(Collectors.toList());
		}
		else {
			return Arrays.asList(getCurrentUser()).stream().map(userInStream -> init(userInStream)).collect(Collectors.toList());
		}
	}

	public User findByUsername(String username) {
		try {
			User user = userRepository.findOneByUsernameIgnoreCase(username);
			if (user == null) {
				return user;
			}
			return init(user);
		}
		catch (EmptyResultDataAccessException e) {
			throw new IllegalArgumentException(String.format("User with username %s already exists", username));
		}
	}

	public User findByEmail(String email) {
		try {
			User user = userRepository.findOneByEmailIgnoreCase(email);
			if (user == null) {
				return user;
			}
			return init(user);
		}
		catch (EmptyResultDataAccessException e) {
			throw new IllegalArgumentException(String.format("User with email %s already exists", email));
		}
	}

	public User create(User user) {
		User existingUser = findByUsername(user.getUsername());
		if (existingUser != null) {
			throw new IllegalArgumentException(String.format("User with username %s already exists", user.getUsername()));
		}
		existingUser = findByEmail(user.getEmail());
		if (existingUser != null) {
			throw new IllegalArgumentException(String.format("User with email %s already exists", user.getEmail()));
		}
		LOG.debug("Saving user: " + user);
		return init(userRepository.save(user));
	}

	public User findById(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			throw new IllegalArgumentException(String.format("User with id=%d does not exist", userId));
		}
		return user.get();
	}

	public User update(Long userId, User user) {
		Optional<User> existingUser = userRepository.findById(userId);
		if (existingUser.isPresent()) {
			throw new IllegalArgumentException(String.format("User with id=%d does not exist", userId));
		}
		User updatedUser = existingUser.get();
		updatedUser.setUsername(user.getUsername());
		updatedUser.setEmail(user.getEmail());
		updatedUser.setRoles(user.getRoles());
		return init(userRepository.save(updatedUser));
	}

	public User delete(Long userId) {
		Optional<User> existingUser = userRepository.findById(userId);
		if (existingUser.isPresent()) {
			throw new IllegalArgumentException(String.format("User with id=%d does not exist", userId));
		}
		User updatedUser = existingUser.get();
		updatedUser.setDeletedAt(LocalDateTime.now());
		return userRepository.save(updatedUser);
	}

	public boolean isUserInRole(User user, Role role) {
		return user != null && role != null && user.getRoles().contains(role);
	}

	public boolean isAdmin(User user) {
		Role role = roleService.getAdminRole();
		return isUserInRole(user, role);
	}

	public boolean isManager(User user) {
		Role role = roleService.getManagerRole();
		return isUserInRole(user, role);
	}

	public boolean isManager() {
		return hasRole(Constants.ROLE_MANAGER);
	}

	public boolean isAdmin() {
		return hasRole(Constants.ROLE_ADMIN);
	}

	public boolean hasRole(String roleName) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (StreamSupport.stream(auth.getAuthorities().spliterator(), false).filter(role -> role.getAuthority().equalsIgnoreCase(roleName)).count() > 0) {
			return true;
		}
		return false;
	}

	public User getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = findByUsername(auth.getName());
		LOG.debug("current user=" + user);
		return user;
	}
}
