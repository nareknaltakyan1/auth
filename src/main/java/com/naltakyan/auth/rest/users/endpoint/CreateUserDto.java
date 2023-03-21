package com.naltakyan.auth.rest.users.endpoint;

import com.naltakyan.auth.domain.logintypes.model.LoginType;
import lombok.Builder;
import lombok.Value;

import java.util.List;

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
	List<LoginType> loginTypeList;

}
