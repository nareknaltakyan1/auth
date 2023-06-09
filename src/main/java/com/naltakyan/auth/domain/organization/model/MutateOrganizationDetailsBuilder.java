package com.naltakyan.auth.domain.organization.model;

public class MutateOrganizationDetailsBuilder
{
	private String name;

	private boolean waitForSync;

	public MutateOrganizationDetails build()
	{
		return new MutateOrganizationDetails(name, waitForSync);
	}

	public MutateOrganizationDetailsBuilder withName(String name)
	{
		this.name = name;
		return this;
	}

	public MutateOrganizationDetailsBuilder withWaitForSync(final boolean waitForSync)
	{
		this.waitForSync = waitForSync;
		return this;
	}
}
