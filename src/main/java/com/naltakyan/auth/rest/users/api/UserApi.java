package com.naltakyan.auth.rest.users.api;

import com.naltakyan.auth.rest.users.endpoint.CreateUserDto;
import com.naltakyan.auth.rest.users.endpoint.UpdateUserDto;
import com.naltakyan.auth.rest.users.endpoint.UserDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/users")
public interface UserApi
{

	String IS_USER = "isUser(#id)";
	String IS_ADMIN = "hasGlobalPermission('ADMIN')";
	String IS_OR_USER_OR_ADMIN = "isUser or hasGlobalPermission('ADMIN')";

	@PreAuthorize(IS_USER)
	@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET, value = "/{id}")
	ResponseEntity<UserDto> getUserById(
		// @Parameter(name = "id", description = "The id of the user to retrieve")
		@PathVariable final Long id);

	@PostMapping("/create")
	ResponseEntity<UserDto> createUser(@RequestBody final CreateUserDto createUserDto);

	@PutMapping("/{id}")
	@PreAuthorize(IS_OR_USER_OR_ADMIN)
	ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") final Long id,@RequestBody final UpdateUserDto updateDto);

}
