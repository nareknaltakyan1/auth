package com.naltakyan.auth.example;

import com.naltakyan.auth.infrastructure.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExampleController
{

	private final EmailService emailService;

	@PreAuthorize("hasGlobalPermission('READ_USERS')")
	@GetMapping("/asd")
	public String example()
	{
		// emailService.sendEmail();
		return "<h1>Example</h1>";
	}

}
