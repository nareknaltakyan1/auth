package com.naltakyan.auth.domain.users.model;

import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "userpermission", uniqueConstraints = @UniqueConstraint(columnNames = { "userId", "permission",
	"organizationId" }, name = "user_organization_permission_unique"))
public abstract class UserPermission
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, updatable = false, insertable = false)
	private PermissionType type;
	@Enumerated(EnumType.STRING)
	protected Permission permission;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	protected User user;
	@Column(nullable = false)
	protected LocalDateTime created;

	public UserPermission()
	{
	}

	public UserPermission(PermissionType roleType)
	{
		this.type = roleType;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(final Long id)
	{
		Assert.notNull(id, "Id must be specified");
		this.id = id;
	}

	public Permission getPermission()
	{
		return permission;
	}

	public void setPermission(final Permission permission)
	{
		Assert.notNull(permission, "Permission must be specified");
		Assert.isTrue(permission.getType() == type, String.format("Permission must be of type %s", type));
		this.permission = permission;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(final User user)
	{
		Assert.notNull(user, "User must be specified");
		this.user = user;
	}

	public LocalDateTime getCreated()
	{
		return created;
	}

	public void setCreated(LocalDateTime created)
	{
		this.created = created;
	}

	public PermissionType getType()
	{
		return type;
	}
}