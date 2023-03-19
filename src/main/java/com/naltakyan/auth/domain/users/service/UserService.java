package com.naltakyan.auth.domain.users.service;

import com.naltakyan.auth.domain.users.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@SuppressWarnings("PMD.TooManyMethods")
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService
{

	private final UserRepository userRepository;

	public Optional<User> findByUsername(final String username)
	{
		return userRepository.findByUsername(username);
	}
}
