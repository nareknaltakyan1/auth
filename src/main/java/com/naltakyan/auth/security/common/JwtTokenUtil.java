package com.naltakyan.auth.security.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class JwtTokenUtil
{
	static final long TEST_TOKEN_VALIDITY_SECONDS = 3600000L;
	private static final String SECRET_KEY = "statickey!78#";

	public String extractUsername(String token)
	{
		return getClaimFromToken(token, Claims::getSubject);
	}

	public JwtUser getUserFromToken(final String token)
	{
		Claims claims = this.getClaims(token);
		String email = (String) claims.get("sub");
		Long id = Long.valueOf(claims.get("user_id").toString());
		String userName = (String) claims.getSubject();
		return new JwtUser(id, email, userName, this.getAuthoritiesFromClaims(claims));
	}

	private <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver)
	{
		Claims claims = this.getClaims(token);
		return claimsResolver.apply(claims);
	}

	public boolean isTokenExpired(final String token)
	{
		try
		{
			this.getClaimFromToken(token, Claims::getExpiration);
			return false;
		}
		catch (ExpiredJwtException var3)
		{
			return true;
		}
	}

	private Collection<? extends GrantedAuthority> getAuthoritiesFromClaims(final Claims claims)
	{
		return Arrays.stream(claims.get("scopes").toString().split(",")).filter(StringUtils::isNotBlank).map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());
	}

	private Claims getClaims(final String token)
	{
		try
		{
			return this.getClaimsWithStaticSigningKey(token);
		}
		catch (Throwable var4)
		{
			log.debug("An exception occurred getting claims for token '{}'", token, var4);
			throw var4;
		}
	}

	private Claims getClaimsWithStaticSigningKey(final String token)
	{
		log.trace("Retrieving claims using static signing key");
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	public String generateToken(final JwtUser user)
	{
		String authoritiesString = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
		return Jwts.builder().setSubject(user.getUsername()).claim("scopes", authoritiesString).claim("user_id", user.getId())
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY).setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + this.TEST_TOKEN_VALIDITY_SECONDS)).compact();
	}

	public long getTokenExpiryInMilliseconds()
	{
		return this.TEST_TOKEN_VALIDITY_SECONDS;
	}

	public Boolean validateToken(String token, UserDetails userDetails)
	{
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}
}
