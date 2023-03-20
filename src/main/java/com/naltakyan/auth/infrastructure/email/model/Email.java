package com.naltakyan.auth.infrastructure.email.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// @Entity
// @Table(name = "email")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Email
{

	// @Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String toEmail;
	private String toName;
	private String toSubject;
	private String data;
}
