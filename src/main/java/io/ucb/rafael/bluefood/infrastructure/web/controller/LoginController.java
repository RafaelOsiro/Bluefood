package io.ucb.rafael.bluefood.infrastructure.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
	
	@GetMapping(path = { "/login", "/" })
	public String funcLogin() {
		return "login";
	}
}