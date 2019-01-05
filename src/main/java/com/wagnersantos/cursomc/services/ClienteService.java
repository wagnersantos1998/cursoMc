package com.wagnersantos.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wagnersantos.cursomc.domain.Cliente;
import com.wagnersantos.cursomc.repositories.ClienteRepository;
import com.wagnersantos.cursomc.services.excecoes.ObjetoNaoEncontrado;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	public Cliente buscarCliente(Integer id) {
		Optional<Cliente> objCli = repo.findById(id);
		return objCli.orElseThrow(() -> new ObjetoNaoEncontrado("Objeto não encontrado! id:" + id + ", tipo: " + Cliente.class.getName()));
	}
	
}
