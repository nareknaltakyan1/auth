package com.naltakyan.auth.domain.users.service;

import com.naltakyan.auth.domain.logintypes.model.LoginType;
import com.naltakyan.auth.domain.logintypes.service.UserLoginTypesService;
import com.naltakyan.auth.domain.system.service.SystemDateTimeService;
import com.naltakyan.auth.domain.users.model.User;
import com.naltakyan.auth.domain.users.model.UserAccountState;
import com.naltakyan.auth.rest.users.endpoint.CreateUserDto;
import com.naltakyan.auth.rest.users.endpoint.UpdateUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("PMD.TooManyMethods")
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService
{

	private final UserRepository userRepository;
	private final UserLoginTypesService userLoginTypesService;

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
		var dateTime = systemDateTimeService.getCurrentDateTime();
		user.setCreated(dateTime);
		user = userRepository.save(user);
		user.setAccountState(UserAccountState.ACTIVE);
		userLoginTypesService.create(user, userDto.getLoginTypeList(), userDto.getPin(), userDto.getQuestion());
		return user;
	}

	@Transactional
	public User update(final Long id, UpdateUserDto userDto)
	{
		var user = findById(id);
		user.setEmail(userDto.getEmail());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setTgUsername(userDto.getTgUsername());
		user = userRepository.save(user);
		updateStaticLogins(user, userDto.getPin(), userDto.getQuestion());
		return user;
	}

	private void updateStaticLogins(final User user, final String pin, final String question)
	{
		if (Objects.nonNull(pin))
		{
			userLoginTypesService.updateStaticLogins(user, pin, LoginType.PIN);
		}
		if (Objects.nonNull(question))
		{
			userLoginTypesService.updateStaticLogins(user, question, LoginType.QUESTION);
		}
	}

	private User covert(final CreateUserDto createUserDto)
	{
		return new User(createUserDto.getUsername(), createUserDto.getPassword(), createUserDto.getEmail(), createUserDto.getPhoneNumber(),
			createUserDto.getTgUsername());
	}
}
