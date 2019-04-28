package au.com.digitalspider.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import au.com.digitalspider.api.io.Error;
import au.com.digitalspider.api.model.User;
import au.com.digitalspider.api.service.UserService;
import io.swagger.annotations.Api;

@RestController
@CrossOrigin
@RequestMapping("/user")
@Api(value = "Unsecured (public) User Operations", description = "Register a new user, forgot password, etc")
public class UnsecuredUserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody User user) {
		try {
			return ResponseEntity.ok(userService.create(user));
		}
		catch (IllegalArgumentException e) {
			HttpStatus status = HttpStatus.PRECONDITION_FAILED;
			Error error = new Error(status.value(), e.getMessage());
			return ResponseEntity.status(status).body(error);
		}
		catch (Exception e) {
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			Error error = new Error(status.value(), e.getMessage());
			return ResponseEntity.status(status).body(error);
		}
	}
}
