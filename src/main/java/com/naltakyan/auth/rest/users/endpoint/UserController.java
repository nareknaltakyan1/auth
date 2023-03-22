package com.naltakyan.auth.rest.users.endpoint;

import com.naltakyan.auth.domain.users.model.User;
import com.naltakyan.auth.domain.users.service.UserService;
import com.naltakyan.auth.rest.users.api.UserApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi
{

	private final UserService userService;

	@Override
	public ResponseEntity<UserDto> getUserById(final Long id)
	{
		var user = userService.findById(id);
		var dto = covert(user);
		return ResponseEntity.ok(dto);
	}

	@Override
	public ResponseEntity<UserDto> createUser(final CreateUserDto createUserDto)
	{
		var user = userService.create(createUserDto);
		return ResponseEntity.ok(covert(user));
	}

	@Override
	public ResponseEntity<UserDto> updateUser(final Long id, final UpdateUserDto updateDto) {
		var user = userService.update(id, updateDto);
		return ResponseEntity.ok(covert(user));
	}

	private UserDto covert(final User user)
	{
		return UserDto.builder().id(user.getId()).username(user.getUsername()).email(user.getEmail()).phoneNumber(user.getPhoneNumber())
			.tgUsername(user.getTgUsername()).accountState(user.getAccountState()).created(user.getCreated()).lastLogin(user.getLastLogin()).build();
	}

}
