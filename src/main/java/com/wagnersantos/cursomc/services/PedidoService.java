package com.wagnersantos.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wagnersantos.cursomc.domain.Pedido;
import com.wagnersantos.cursomc.repositories.PedidoRepository;
import com.wagnersantos.cursomc.services.excecoes.ObjetoNaoEncontrado;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> objPedido = repo.findById(id);
		return objPedido.orElseThrow(() -> new ObjetoNaoEncontrado("Objeto não encontrado! id:" + id + ", tipo: " + Pedido.class.getName()));
	}
	
}
