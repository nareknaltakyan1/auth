package com.naltakyan.auth.security;

import com.naltakyan.auth.domain.users.model.GlobalUserPermission;
import com.naltakyan.auth.domain.users.model.OrganizationalUserPermission;
import com.naltakyan.auth.domain.users.model.PermissionType;
import com.naltakyan.auth.domain.users.model.UserPermission;
import com.naltakyan.auth.security.common.SecurityConstants;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class PermissionToAuthorityConverter
{
	public static Set<? extends GrantedAuthority> convertPermissionsToGrantedAuthorities(final Collection<? extends UserPermission> permissions)
	{
		final Set<GrantedAuthority> authorities = new HashSet<>();
		if (permissions != null && !permissions.isEmpty())
		{
			permissions.forEach(permission -> processPermission(authorities, permission));
		}
		return authorities;
	}

	private static void processPermission(final Set<GrantedAuthority> authorities, final UserPermission permission)
	{
		if (permission.getType() == PermissionType.GLOBAL)
		{
			processGlobalPermission(authorities, (GlobalUserPermission) permission);
		}
		else
			if (permission.getType() == PermissionType.ORGANIZATIONAL)
			{
				processOrganizationalPermission(authorities, (OrganizationalUserPermission) permission);
			}
	}

	private static void processGlobalPermission(final Set<GrantedAuthority> authorities, GlobalUserPermission permission)
	{
		authorities.add(new SimpleGrantedAuthority(String.format(SecurityConstants.GLOBAL_PERMISSION_FORMAT, permission.getPermission().name())));
		if (permission.getPermission().hasAliases())
		{
			permission.getPermission().getAliases().stream()
				.forEach(alias -> authorities.add(new SimpleGrantedAuthority(String.format(SecurityConstants.GLOBAL_PERMISSION_FORMAT, alias))));
		}
	}

	private static void processOrganizationalPermission(final Set<GrantedAuthority> authorities, final OrganizationalUserPermission permission)
	{
		authorities.add(new SimpleGrantedAuthority(String.format(SecurityConstants.ORGANIZATIONAL_PERMISSION_FORMAT,
			permission.getPermission().name(), permission.getOrganization().getId())));
		if (permission.getPermission().hasAliases())
		{
			permission.getPermission().getAliases().stream().forEach(alias -> authorities.add(new SimpleGrantedAuthority(
				String.format(SecurityConstants.ORGANIZATIONAL_PERMISSION_FORMAT, alias, permission.getOrganization().getId()))));
		}
	}
}
