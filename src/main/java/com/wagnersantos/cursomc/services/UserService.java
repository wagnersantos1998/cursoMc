package com.wagnersantos.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.wagnersantos.cursomc.security.UsuarioSS;

public class UserService {

	public static UsuarioSS authenticatador() {
		try {
			return (UsuarioSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		} catch (Exception e) {
			return null;
		}
	}

}
