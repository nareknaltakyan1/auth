package com.naltakyan.auth.rest.jwt.endpoint;

import com.naltakyan.auth.domain.users.model.User;
import com.naltakyan.auth.domain.users.service.UserService;
import com.naltakyan.auth.rest.jwt.api.AuthApi;
import com.naltakyan.auth.security.common.JwtTokenUtil;
import com.naltakyan.auth.security.common.JwtUser;
import com.naltakyan.auth.security.PermissionToAuthorityConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController implements AuthApi
{

	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final JwtTokenUtil jwtTokenUtil;

	@Override
	public ResponseEntity<AccessTokenDto> authenticate(final AuthenticationRequest request) throws Exception
	{
		var principal = request.getUsername();
		var credentials = request.getPassword();
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(principal, credentials));
		log.debug("Login successful");
		final User user = userService.findByUsername(principal).orElseThrow();
		final String token = jwtTokenUtil.generateToken(createJwtUser(user));
		final var accessTokenDto = createAccessToken(user, false, token);
		// userService.updateLastLogin(user.getId(), systemDateTimeService.getCurrentDateTime());
		return ResponseEntity.ok(accessTokenDto);
	}

	private AccessTokenDto createAccessToken(final User user, final boolean includeRefreshToken, final String token)
	{
		final AccessTokenDto accessTokenDto;
		// if (includeRefreshToken)
		// {
		//// final Token refreshToken = tokenService.createRefreshToken(user.getId());
		//// accessTokenDto = createAccessTokenDto(token, refreshToken.getToken());
		// }
		// else
		// {
		accessTokenDto = createAccessTokenDto(token, null);
		// }
		return accessTokenDto;
	}

	private AccessTokenDto createAccessTokenDto(final String accessToken, final String refreshToken)
	{
		return new AccessTokenDto(accessToken, jwtTokenUtil.getTokenExpiryInMilliseconds() / 1000, refreshToken);
	}

	private JwtUser createJwtUser(final User user)
	{
		return new JwtUser(user.getId(), user.getEmail(), user.getUsername(),
			PermissionToAuthorityConverter.convertPermissionsToGrantedAuthorities(user.getPermissions()));
	}
}
