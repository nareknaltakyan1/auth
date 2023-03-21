package com.naltakyan.auth.rest.jwt.api;

import com.naltakyan.auth.rest.jwt.endpoint.AccessTokenDto;
import com.naltakyan.auth.rest.jwt.endpoint.AuthenticationRequest;
import com.naltakyan.auth.rest.jwt.endpoint.GetCodeRequest;
import com.naltakyan.auth.rest.jwt.endpoint.VerifyLoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public interface AuthApi
{

	@PostMapping("/authenticate")
	public ResponseEntity<AccessTokenDto> authenticate(@RequestBody final AuthenticationRequest request) throws Exception;

	@PostMapping("/email/code")
	ResponseEntity<Void> getEmailCode(@RequestBody final GetCodeRequest dto);

	@PostMapping("/email/verify")
	ResponseEntity<Boolean> verifyEmailCode(@RequestBody final VerifyLoginRequest dto);

	@PostMapping("/sms/code")
	ResponseEntity<Void> getSmsCode(@RequestBody final GetCodeRequest dto);

	@PostMapping("/sms/verify")
	ResponseEntity<Boolean> verifySmsCode(@RequestBody final VerifyLoginRequest dto);

	@PostMapping("/TgBot/code")
	ResponseEntity<Void> getTgCode(@RequestBody final GetCodeRequest dto);

	@PostMapping("/TgBot/verify")
	ResponseEntity<Boolean> verifyTgCode(@RequestBody final VerifyLoginRequest dto);

	@PostMapping("/pin/verify")
	ResponseEntity<Boolean> verifyPin(@RequestBody final VerifyLoginRequest dto);

	@PostMapping("/question/verify")
	ResponseEntity<Boolean> verifyQuestion(@RequestBody final VerifyLoginRequest dto);

}
