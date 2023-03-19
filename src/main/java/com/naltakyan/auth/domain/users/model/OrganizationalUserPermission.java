package com.naltakyan.auth.domain.users.model;

import com.naltakyan.auth.domain.organization.model.Organization;
import org.springframework.util.Assert;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "ORGANIZATIONAL")
public class OrganizationalUserPermission extends UserPermission
{

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organizationId")
	private Organization organization;

	public OrganizationalUserPermission()
	{
		super(PermissionType.ORGANIZATIONAL);
	}

	public Organization getOrganization()
	{
		return organization;
	}

	public void setOrganization(final Organization organization)
	{
		Assert.notNull(organization, "Organization must be specified");
		this.organization = organization;
	}
}
