package com.naltakyan.auth.domain.users.service;

import com.naltakyan.auth.domain.system.service.SystemDateTimeService;
import com.naltakyan.auth.domain.users.model.User;
import com.naltakyan.auth.rest.users.endpoint.CreateUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@SuppressWarnings("PMD.TooManyMethods")
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService
{

	private final UserRepository userRepository;

	// private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final SystemDateTimeService systemDateTimeService;

	public Optional<User> findByUsername(final String username)
	{
		return userRepository.findByUsername(username);
	}

	public User findById(final Long id)
	{
		final var bookingOptional = userRepository.findById(id);
		if (bookingOptional.isEmpty())
		{
			throw new EntityNotFoundException(String.format("User not found for ID %s", id));
		}
		return bookingOptional.get();
	}

	@Transactional
	public User create(CreateUserDto userDto)
	{
		var user = covert(userDto);
		user.setCreated(systemDateTimeService.getCurrentDateTime());
		return userRepository.save(user);
	}

	private User covert(final CreateUserDto createUserDto)
	{
		return new User(createUserDto.getUsername(), createUserDto.getPassword(), createUserDto.getEmail(), createUserDto.getPhoneNumber(),
			createUserDto.getTgUsername());
	}
}
