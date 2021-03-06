package au.com.digitalspider.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.com.digitalspider.api.Constants;
import au.com.digitalspider.api.io.Response;
import au.com.digitalspider.api.model.User;
import au.com.digitalspider.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping(Constants.BASE_API + "/user")
@Api(value = "User Controller", description = "Operations pertaining to users")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "View a list of available users", response = List.class)
	@GetMapping("")
//	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> getAll() {
		try {
			List<User> users = userService.getAll();
			HttpStatus status = HttpStatus.OK;
			Response body = new Response(status.value(), "Get all articles", users);
			return ResponseEntity.status(status).body(body);
		} catch (Exception e) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			Response body = new Response(status.value(), e.getMessage());
			return ResponseEntity.status(status).body(body);
		}
	}

	@GetMapping("/username/{username}")
	private ResponseEntity<User> findByUsername(@PathVariable(value = "username") String username) {
		try {
			return ResponseEntity.ok(userService.findByUsername(username));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<User> findByEmail(@PathVariable(value = "email") String email) {
		try {
			return ResponseEntity.ok(userService.findByEmail(email));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/search/{searchTerm}")
	public Iterable<User> search(@PathVariable(value = "searchTerm") String searchTerm) {
		return userService.search(searchTerm);
	}

	@PostMapping("")
	public ResponseEntity<Response> create(@Valid @RequestBody User user) {
		try {
			User updatedUser = userService.create(user);
			HttpStatus status = HttpStatus.OK;
			Response body = new Response(status.value(), "Create new user", updatedUser);
			return ResponseEntity.status(status).body(body);
		} catch (IllegalArgumentException e) {
			HttpStatus status = HttpStatus.PRECONDITION_FAILED;
			Response body = new Response(status.value(), e.getMessage());
			return ResponseEntity.status(status).body(body);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> get(@PathVariable(value = "id") Long userId) {
		try {
			User user = userService.findById(userId);
			return ResponseEntity.ok(user);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<User> update(@PathVariable(value = "id") Long userId, @Valid @RequestBody User newUser) {
		try {
			User updatedUser = userService.update(userId, newUser);
			return ResponseEntity.ok().body(updatedUser);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<Void> delete(@PathVariable(value = "id") Long userId) {
		try {
			userService.delete(userId);
			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
