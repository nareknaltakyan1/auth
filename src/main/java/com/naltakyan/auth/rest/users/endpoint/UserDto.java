package com.naltakyan.auth.rest.users.endpoint;

import com.naltakyan.auth.domain.users.model.UserAccountState;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class UserDto
{
	Long id;
	String username;
	String email;
	String phoneNumber;
	String tgUsername;
	UserAccountState accountState;
	LocalDateTime created;
	LocalDateTime lastLogin;
}
