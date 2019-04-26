package com.wagnersantos.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.segredo}")
	private String segredo;

	@Value("${jwt.exp}")
	private long tempoExp;

	public String generateToken(String username) {
		return Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + tempoExp))
				.signWith(SignatureAlgorithm.HS512, segredo.getBytes()).compact();
	}
}
