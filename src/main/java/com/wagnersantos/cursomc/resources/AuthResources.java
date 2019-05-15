package com.wagnersantos.cursomc.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wagnersantos.cursomc.security.JWTUtil;
import com.wagnersantos.cursomc.security.UsuarioSS;
import com.wagnersantos.cursomc.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthResources {

	@Autowired
	private JWTUtil jwtUtil;
	
	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response){
		UsuarioSS user = UserService.authenticatador();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization","Bearer " + token);
		return ResponseEntity.noContent().build();
	}
}
