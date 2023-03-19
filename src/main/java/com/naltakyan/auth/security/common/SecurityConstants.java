package com.naltakyan.auth.security.common;

public class SecurityConstants
{
	public static final String SIGNING_KEY_ENV_VAR = "JWT_SIGNING_KEY";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String AUTHORITIES_KEY = "scopes";
	public static final String GLOBAL_PERMISSION_PREFIX = "GLOBAL_";
	public static final String GLOBAL_PERMISSION_FORMAT = "GLOBAL_%s";
	public static final String ORGANIZATIONAL_PERMISSION_PREFIX = "ORG_";
	public static final String ORGANIZATIONAL_PERMISSION_FORMAT = "ORG_%s:%d";
	public static final long PASSWORD_RESET_EXPIRY_SECONDS = 1800L;

	public SecurityConstants()
	{
	}
}
