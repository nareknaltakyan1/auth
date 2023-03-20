package com.naltakyan.auth.rest.jwt.api;

import com.naltakyan.auth.rest.jwt.endpoint.AccessTokenDto;
import com.naltakyan.auth.rest.jwt.endpoint.AuthenticationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthApi
{

	@PostMapping("/authenticate")
	public ResponseEntity<AccessTokenDto> authenticate(@RequestBody final AuthenticationRequest request) throws Exception;

	// @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
	// ResponseEntity<UserDto> createUser(@Valid @RequestBody final CreateUserDto dto);
}
