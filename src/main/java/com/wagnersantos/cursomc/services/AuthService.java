package com.wagnersantos.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wagnersantos.cursomc.domain.Cliente;
import com.wagnersantos.cursomc.repositories.ClienteRepository;
import com.wagnersantos.cursomc.services.excecoes.ObjetoNaoEncontrado;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmailService emailService;

	private Random rand = new Random();

	public void enviarNovaSenha(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjetoNaoEncontrado("Email nao encontrado");
		}

		String novaSenha = novaSenha();
		cliente.setSenha(pe.encode(novaSenha));

		clienteRepository.save(cliente);
		emailService.enviarNovaSenhaEmail(cliente, novaSenha);
	}

	private String novaSenha() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);

		} else if (opt == 2) { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);

		}
		return 0;
	}
}
