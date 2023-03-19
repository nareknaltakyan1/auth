package com.naltakyan.auth.security;

import com.naltakyan.auth.domain.users.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService
{

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException
	{
		var userOptional = userRepository.findByUsername(username);
		userOptional.orElseThrow(() -> new UsernameNotFoundException(String.format("User not found by username: %s", username)));
		return userOptional.map(ApplicationUserDetails::new).get();
	}
}
