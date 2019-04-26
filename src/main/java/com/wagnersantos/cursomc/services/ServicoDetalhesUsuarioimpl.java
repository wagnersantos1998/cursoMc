package com.wagnersantos.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wagnersantos.cursomc.domain.Cliente;
import com.wagnersantos.cursomc.repositories.ClienteRepository;
import com.wagnersantos.cursomc.security.UsuarioSS;

@Service
public class ServicoDetalhesUsuarioimpl implements UserDetailsService{

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = repo.findByEmail(email);
		if (cli == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UsuarioSS(cli.getId(),cli.getEmail(), cli.getSenha(), cli.getPerfis());
	}

}
