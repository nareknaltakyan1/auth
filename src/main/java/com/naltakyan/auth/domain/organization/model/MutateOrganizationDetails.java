package com.naltakyan.auth.domain.organization.model;

public class MutateOrganizationDetails
{
	private final String name;

	private final boolean waitForSync;

	public MutateOrganizationDetails(final String name, final boolean waitForSync)
	{
		this.name = name;
		this.waitForSync = waitForSync;
	}

	public String getName()
	{
		return name;
	}

	public boolean isWaitForSync()
	{
		return waitForSync;
	}
}
