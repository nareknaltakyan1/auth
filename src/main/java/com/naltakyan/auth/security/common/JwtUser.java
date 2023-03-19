package com.naltakyan.auth.security.common;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JwtUser implements Serializable
{
	private static final long serialVersionUID = -7539034411628035050L;
	private final Long id;
	private final String email;
	private final String username;
	private final Set<? extends GrantedAuthority> authorities;

	public JwtUser(final Long id, final String email, final String username, final Collection<? extends GrantedAuthority> authorities)
	{
		this.id = id;
		this.email = email;
		this.username = username;
		Set<GrantedAuthority> temp = new HashSet<>();
		temp.addAll(authorities);
		this.authorities = Collections.unmodifiableSet(temp);
	}

	public Long getId()
	{
		return this.id;
	}

	public String getEmail()
	{
		return this.email;
	}

	public String getUsername()
	{
		return this.username;
	}

	public Set<? extends GrantedAuthority> getAuthorities()
	{
		return this.authorities;
	}
}