package com.naltakyan.auth.domain.logintypes.model;

import com.naltakyan.auth.domain.users.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "users_logintypes")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class UserLoginTypes
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	protected User user;
	@Enumerated(EnumType.STRING)
	private LoginType loginType;
	private Long priority;
	private String value;
	private LocalDateTime setValueDate;
	private LocalDateTime lastLogin;
}