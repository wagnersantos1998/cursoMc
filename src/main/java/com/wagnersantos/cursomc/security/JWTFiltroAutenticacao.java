package com.wagnersantos.cursomc.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wagnersantos.cursomc.dto.CredenciasDTO;

public class JWTFiltroAutenticacao extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JWTUtil jWTUtil;

	public JWTFiltroAutenticacao(AuthenticationManager authenticationManager, JWTUtil jWTUtil) {
		this.authenticationManager = authenticationManager;
		this.jWTUtil = jWTUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			CredenciasDTO creds = new ObjectMapper().readValue(req.getInputStream(), CredenciasDTO.class);
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(),
					creds.getSenha(), new ArrayList<>());

			Authentication autenticador = authenticationManager.authenticate(authToken);
			return autenticador;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication autenticador) throws IOException, ServletException {
		String username = ((UsuarioSS) autenticador.getPrincipal()).getUsername();
		String token = jWTUtil.generateToken(username);
		res.addHeader("Authorization", "Bearer " + token);
	}
}
