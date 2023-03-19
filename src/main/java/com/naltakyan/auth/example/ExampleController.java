package com.naltakyan.auth.example;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController
{

	@PreAuthorize("hasGlobalPermission('WRITE_USERS')")
	@GetMapping("/asd")
	public String example()
	{
		return "<h1>Example</h1>";
	}

}
