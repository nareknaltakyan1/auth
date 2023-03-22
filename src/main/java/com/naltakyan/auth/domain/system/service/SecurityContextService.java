package com.naltakyan.auth.domain.system.service;

import com.naltakyan.auth.security.common.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class SecurityContextService
{

	public Long getCurrentUserId()
	{
		final var jwtUser = getCurrentUser();
		return Objects.nonNull(jwtUser) ? jwtUser.getId() : null;
	}

	public JwtUser getCurrentUser()
	{
		final var authentication = SecurityContextHolder.getContext().getAuthentication();
		return Objects.nonNull(authentication) ? (JwtUser) authentication.getPrincipal() : null;
	}
}
