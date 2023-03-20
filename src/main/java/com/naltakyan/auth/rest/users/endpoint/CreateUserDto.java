package com.naltakyan.auth.rest.users.endpoint;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto
{

	String username;
	String password;
	String email;
	String phoneNumber;
	String tgUsername;
	String pin;
	String question;

}
