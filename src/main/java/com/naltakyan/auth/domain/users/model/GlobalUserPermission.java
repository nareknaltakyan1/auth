package com.naltakyan.auth.domain.users.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "GLOBAL")
public class GlobalUserPermission extends UserPermission
{

	public GlobalUserPermission()
	{
		super(PermissionType.GLOBAL);
	}
}
