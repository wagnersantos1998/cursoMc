package com.wagnersantos.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wagnersantos.cursomc.dto.EmailDTO;
import com.wagnersantos.cursomc.security.JWTUtil;
import com.wagnersantos.cursomc.security.UsuarioSS;
import com.wagnersantos.cursomc.services.AuthService;
import com.wagnersantos.cursomc.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthResources {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response){
		UsuarioSS user = UserService.authenticatador();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization","Bearer " + token);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO emailDTO){
		authService.enviarNovaSenha(emailDTO.getEmail());
		return ResponseEntity.noContent().build();
	}
}
