package com.algamoney.algamoneyapi.token.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algamoney.algamoneyapi.config.property.AlgamoneyApiProperty;

@RestController
@RequestMapping("/tokens/")
public class TokenController {

	private AlgamoneyApiProperty algamoneyApiProperty;
	
	public TokenController(AlgamoneyApiProperty algamoneyApiProperty) {
		this.algamoneyApiProperty = algamoneyApiProperty;
	}

	@DeleteMapping("revoke")
	public void revoke(HttpServletRequest req, HttpServletResponse resp) {
		Cookie cookie = new Cookie("refreshToken", null);
		cookie.setHttpOnly(true);
		cookie.setSecure(algamoneyApiProperty.getSeguranca().isHabilitaHttps());
		cookie.setPath(req.getContextPath() + "/oauth/token");
		cookie.setMaxAge(0);
		
		resp.addCookie(cookie);
		resp.setStatus(HttpStatus.NO_CONTENT.value());
	}
	
}