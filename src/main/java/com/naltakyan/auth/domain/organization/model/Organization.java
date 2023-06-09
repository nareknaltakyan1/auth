package com.naltakyan.auth.domain.organization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "organization", indexes = { @Index(name = "organization_updated_index", columnList = "updated") })
public class Organization
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(nullable = false)
	private LocalDateTime created;
	@Column(nullable = false)
	private LocalDateTime updated;

	public Long getId()
	{
		return id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public LocalDateTime getCreated()
	{
		return created;
	}

	public LocalDateTime getUpdated()
	{
		return updated;
	}

	public void setCreated(LocalDateTime created)
	{
		this.created = created;
	}

	public void setUpdated(LocalDateTime updated)
	{
		this.updated = updated;
	}
}
