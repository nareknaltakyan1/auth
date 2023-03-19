package com.naltakyan.auth.security.common;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.opentracing.Span;
import io.opentracing.util.GlobalTracer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.naltakyan.auth.security.common.SecurityConstants.HEADER_STRING;
import static com.naltakyan.auth.security.common.SecurityConstants.TOKEN_PREFIX;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(final HttpServletRequest req, final HttpServletResponse res, final FilterChain chain)
		throws IOException, ServletException
	{
		clearSecurityContext();
		final String authToken = getAuthToken(req);
		JwtUser user = null;
		if (authToken != null)
		{
			user = getUserFromToken(authToken);
		}
		else
		{
			log.trace("Couldn't find 'Bearer' string, ignoring authorization header");
		}
		if (user != null)
		{
			log.trace("User {}:{} authenticated", user.getEmail(), user.getId());
			final JwtAuthenticationToken authentication = new JwtAuthenticationToken(user, authToken, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			final Span span = GlobalTracer.get().activeSpan();
			setContextValue("user", user.getEmail(), span);
		}
		try
		{
			chain.doFilter(req, res);
		}
		finally
		{
			clearSecurityContext();
			MDC.clear();
		}
	}

	private void setContextValue(final String name, final String value, final Span span)
	{
		MDC.put(name, value);
		if (span != null)
		{
			log.trace("Setting context value '{}'='{}' on {}", name, value, span);
			span.setTag(name, value);
		}
		else
		{
			log.debug("No active span");
		}
	}

	private void clearSecurityContext()
	{
		SecurityContextHolder.clearContext();
	}

	private JwtUser getUserFromToken(final String authToken)
	{
		JwtUser user = null;
		try
		{
			user = jwtTokenUtil.getUserFromToken(authToken);
		}
		catch (final ExpiredJwtException e)
		{
			log.debug("Token '{}' is expired", authToken);
		}
		catch (final MalformedJwtException e)
		{
			log.debug("Token '{}' is malformed", authToken);
		}
		catch (final UnsupportedJwtException e)
		{
			log.debug("Token '{}' is unsupported", authToken);
		}
		catch (final SignatureException e)
		{
			log.debug("Token '{}' has an invalid signature", authToken);
		}
		catch (final RuntimeException e)
		{
			log.warn("An error occurred during getting user from token", e);
		}
		return user;
	}

	private String getAuthToken(final HttpServletRequest req)
	{
		final String header = req.getHeader(HEADER_STRING);
		if (header != null && header.startsWith(TOKEN_PREFIX))
		{
			return header.replace(TOKEN_PREFIX, "");
		}
		return null;
	}
}
