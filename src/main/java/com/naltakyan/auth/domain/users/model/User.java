package com.naltakyan.auth.domain.users.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class User
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String saltedPasswordHash;
	private String email;
	private String phoneNumber;
	private String tgUsername;
	@Enumerated(EnumType.STRING)
	private UserAccountState accountState;
	private Integer invalidLoginAttempts;
	@Column(nullable = false)
	private LocalDateTime created;
	private LocalDateTime lastLogin;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<UserPermission> permissions;

	public User(final String username, final String saltedPasswordHash, final String email, final String phoneNumber, final String tgUsername)
	{
		this.username = username;
		this.saltedPasswordHash = saltedPasswordHash;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.tgUsername = tgUsername;
		this.accountState = UserAccountState.CREATED;
		this.invalidLoginAttempts = 0;
	}
}
