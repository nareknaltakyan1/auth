package com.naltakyan.auth.security;

import com.naltakyan.auth.security.common.JwtAuthenticationFilter;
import com.naltakyan.auth.security.common.JwtTokenUtil;
import com.naltakyan.auth.security.common.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{

	private final UserDetailsService userDetailsService;
	private JwtAuthenticationEntryPoint unauthorizedHandler;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception
	{
		auth.userDetailsService(userDetailsService).passwordEncoder(NoOpPasswordEncoder.getInstance());
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception
	{
		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/auth/authenticate", "/users/create").permitAll().anyRequest()
			.authenticated().and().exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and().sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}

	@Bean
	public JwtAuthenticationFilter authenticationTokenFilterBean()
	{
		return new JwtAuthenticationFilter();
	}

	@Bean
	public JwtTokenUtil jwtTokenUtil()
	{
		return new JwtTokenUtil();
	}

	@Bean
	public JwtAuthenticationEntryPoint jwtAuthenticationEntryPointBean() throws Exception
	{
		return new JwtAuthenticationEntryPoint();
	}

	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}
}
