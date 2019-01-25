package com.clsolucoes.siscoor.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.clsolucoes.siscoor.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}
