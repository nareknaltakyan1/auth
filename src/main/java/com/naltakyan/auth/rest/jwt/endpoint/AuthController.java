package com.naltakyan.auth.rest.jwt.endpoint;

import com.naltakyan.auth.domain.logintypes.model.LoginType;
import com.naltakyan.auth.domain.logintypes.service.InvalidLoginFlow;
import com.naltakyan.auth.domain.logintypes.service.UserLoginTypesService;
import com.naltakyan.auth.domain.users.model.User;
import com.naltakyan.auth.domain.users.service.UserService;
import com.naltakyan.auth.infrastructure.email.service.EmailService;
import com.naltakyan.auth.infrastructure.verifier.service.VerificationCodeGenerator;
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
	private final EmailService emailService;
	private final VerificationCodeGenerator verificationCodeGenerator;
	private final UserLoginTypesService userLoginTypesService;

	@Override
	public ResponseEntity<AccessTokenDto> authenticate(final AuthenticationRequest request) throws Exception
	{
		var principal = request.getUsername();
		var credentials = request.getPassword();
		final User user = userService.findByUsername(principal).orElseThrow();
		if (!userLoginTypesService.checkUserLoginFlow(user))
		{
			throw new InvalidLoginFlow();
		}
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(principal, credentials));
		log.debug("Login successful");
		final String token = jwtTokenUtil.generateToken(createJwtUser(user));
		final var accessTokenDto = createAccessToken(user, false, token);
		// userService.updateLastLogin(user.getId(), systemDateTimeService.getCurrentDateTime());
		return ResponseEntity.ok(accessTokenDto);
	}

	@Override
	public ResponseEntity<Void> getEmailCode(final GetCodeRequest dto)
	{
		var user = userService.findByUsername(dto.getUsername()).orElseThrow();
		var verificationCode = verificationCodeGenerator.generateVerificationCode();
		emailService.sendEmail(user.getEmail(), verificationCode, "Email verification Code");
		userLoginTypesService.updateValue(verificationCode, user, LoginType.EMAIL);
		return ResponseEntity.noContent().build();
	}

	@Override
	public ResponseEntity<Boolean> verifyEmailCode(final VerifyLoginRequest dto)
	{
		var user = userService.findByUsername(dto.getUsername()).orElseThrow();
		var correspondsTo = userLoginTypesService.checkVerificationCode(dto.getValue(), user, LoginType.EMAIL);
		return ResponseEntity.ok(correspondsTo);
	}

	@Override
	public ResponseEntity<Void> getSmsCode(final GetCodeRequest dto)
	{
		return null;
	}

	@Override
	public ResponseEntity<Boolean> verifySmsCode(final VerifyLoginRequest dto)
	{
		return null;
	}

	@Override
	public ResponseEntity<Void> getTgCode(final GetCodeRequest dto)
	{
		return null;
	}

	@Override
	public ResponseEntity<Boolean> verifyTgCode(final VerifyLoginRequest dto)
	{
		return null;
	}

	@Override
	public ResponseEntity<Boolean> verifyPin(final VerifyLoginRequest dto)
	{
		var user = userService.findByUsername(dto.getUsername()).orElseThrow();
		var correspondsTo = userLoginTypesService.checkVerificationCode(dto.getValue(), user, LoginType.PIN);
		return ResponseEntity.ok(correspondsTo);
	}

	@Override
	public ResponseEntity<Boolean> verifyQuestion(final VerifyLoginRequest dto)
	{
		var user = userService.findByUsername(dto.getUsername()).orElseThrow();
		var correspondsTo = userLoginTypesService.checkVerificationCode(dto.getValue(), user, LoginType.QUESTION);
		return ResponseEntity.ok(correspondsTo);
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
