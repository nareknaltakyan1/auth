package com.naltakyan.auth.domain.users.model;

public enum PermissionType
{
	GLOBAL, ORGANIZATIONAL;

	public boolean isGlobal()
	{
		return this == GLOBAL;
	}

	public boolean isOrganizational()
	{
		return this == ORGANIZATIONAL;
	}
}
