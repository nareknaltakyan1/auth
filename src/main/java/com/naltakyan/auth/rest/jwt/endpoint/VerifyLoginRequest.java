package com.naltakyan.auth.rest.jwt.endpoint;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class VerifyLoginRequest
{

	String username;
	String value;
}
