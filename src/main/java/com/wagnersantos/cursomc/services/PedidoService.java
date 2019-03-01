package com.wagnersantos.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wagnersantos.cursomc.domain.ItemPedido;
import com.wagnersantos.cursomc.domain.PagamentoComBoleto;
import com.wagnersantos.cursomc.domain.Pedido;
import com.wagnersantos.cursomc.domain.enums.EstadoPagamento;
import com.wagnersantos.cursomc.repositories.ClienteRepository;
import com.wagnersantos.cursomc.repositories.ItemPedidoRepository;
import com.wagnersantos.cursomc.repositories.PagamentoRepository;
import com.wagnersantos.cursomc.repositories.PedidoRepository;
import com.wagnersantos.cursomc.services.excecoes.ObjetoNaoEncontrado;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteRepository clienteRepository; 
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> objPedido = repo.findById(id);
		return objPedido.orElseThrow(
				() -> new ObjetoNaoEncontrado("Objeto não encontrado! id:" + id + ", tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido inserirPedido(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteRepository.getOne(pedido.getCliente().getId()));
		pedido.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pgto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, pedido.getInstante());
		}
		pedido = repo.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		for (ItemPedido ip : pedido.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.buscar(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(pedido);
		}
		itemPedidoRepository.saveAll(pedido.getItens());
		System.out.println(pedido);
		return pedido;
	}

}
