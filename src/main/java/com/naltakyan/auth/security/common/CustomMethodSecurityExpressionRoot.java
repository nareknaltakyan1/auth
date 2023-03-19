package com.naltakyan.auth.security.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import static com.naltakyan.auth.security.common.SecurityConstants.GLOBAL_PERMISSION_FORMAT;
import static com.naltakyan.auth.security.common.SecurityConstants.ORGANIZATIONAL_PERMISSION_FORMAT;
import static com.naltakyan.auth.security.common.SecurityConstants.ORGANIZATIONAL_PERMISSION_PREFIX;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations
{
	private static final Logger LOG = LoggerFactory.getLogger(CustomMethodSecurityExpressionRoot.class);

	private Object filterObject;
	private Object returnObject;

	public CustomMethodSecurityExpressionRoot(final Authentication authentication)
	{
		super(authentication);
	}

	public boolean hasGlobalPermission(final String permission)
	{
		final Authentication authentication = getAuthentication();
		if (authentication == null)
		{
			LOG.trace("User is not logged in");
			return false;
		}
		final String fullPermission = String.format(GLOBAL_PERMISSION_FORMAT, permission);
		for (final GrantedAuthority grantedAuth : authentication.getAuthorities())
		{
			if (grantedAuth.getAuthority().equals(fullPermission))
			{
				LOG.trace("User has global permission {}", permission);
				return true;
			}
		}
		LOG.trace("User does not have global permission {}", permission);
		return false;
	}

	public boolean hasOrgPermission(final String permission)
	{
		final Authentication authentication = getAuthentication();
		if (authentication == null)
		{
			LOG.trace("User is not logged in");
			return false;
		}
		final String prefix = String.format("%s%s", ORGANIZATIONAL_PERMISSION_PREFIX, permission);
		for (final GrantedAuthority grantedAuth : authentication.getAuthorities())
		{
			if (grantedAuth.getAuthority().startsWith(prefix))
			{
				LOG.trace("User has org permission {}", permission);
				return true;
			}
		}
		LOG.trace("User does not have org permission {}", permission);
		return false;
	}

	public boolean hasOrgPermission(final String permission, final Long organizationId)
	{
		final Authentication authentication = getAuthentication();
		if (authentication == null || organizationId == null)
		{
			LOG.trace("User is not logged in or no organization id was specified");
			return false;
		}
		final String fullPermission = String.format(ORGANIZATIONAL_PERMISSION_FORMAT, permission, organizationId);
		for (final GrantedAuthority grantedAuth : authentication.getAuthorities())
		{
			if (grantedAuth.getAuthority().equals(fullPermission))
			{
				LOG.trace("User has org permission {}", permission);
				return true;
			}
		}
		LOG.trace("User does not have org permission {}", permission);
		return false;
	}

	public boolean isUser(final Long userId)
	{
		final Authentication authentication = getAuthentication();
		if (authentication == null || userId == null)
		{
			LOG.trace("User is not logged in");
			return false;
		}
		JwtUser user = (JwtUser) authentication.getPrincipal();
		LOG.trace("User with id {} is logged in", userId);
		return userId.equals(user.getId());
	}

	public boolean isOrgMember(final Long organizationId)
	{
		final Authentication authentication = getAuthentication();
		if (authentication == null || organizationId == null)
		{
			LOG.trace("User is not logged in or no organization id was specified");
			return false;
		}
		return hasOrganizationalPermission(organizationId, authentication);
	}

	private boolean hasOrganizationalPermission(final Long organizationId, final Authentication authentication)
	{
		for (GrantedAuthority grantedAuthority : authentication.getAuthorities())
		{
			final String permission = grantedAuthority.getAuthority();
			if (permission.startsWith(ORGANIZATIONAL_PERMISSION_PREFIX) && organizationId.equals(getOrganizationIdFromPermission(permission)))
			{
				LOG.trace("User has at least 1 org permission for org {}", organizationId);
				return true;
			}
		}
		LOG.trace("User does not have any permissions for org {}", organizationId);
		return false;
	}

	private Long getOrganizationIdFromPermission(final String role)
	{
		try
		{
			return Long.valueOf(role.substring(role.indexOf(':') + 1));
		}
		catch (NumberFormatException | IndexOutOfBoundsException ex)
		{
			LOG.error("Unable to obtain organization id from role '{}'", role);
			return null;
		}
	}

	@Override
	public void setFilterObject(final Object filterObject)
	{
		this.filterObject = filterObject;
	}

	@Override
	public Object getFilterObject()
	{
		return filterObject;
	}

	@Override
	public void setReturnObject(final Object returnObject)
	{
		this.returnObject = returnObject;
	}

	@Override
	public Object getReturnObject()
	{
		return returnObject;
	}

	@Override
	public Object getThis()
	{
		return this;
	}

}
