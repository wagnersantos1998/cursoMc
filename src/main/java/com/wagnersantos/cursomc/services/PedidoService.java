package com.wagnersantos.cursomc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wagnersantos.cursomc.domain.Cliente;
import com.wagnersantos.cursomc.domain.ItemPedido;
import com.wagnersantos.cursomc.domain.PagamentoComBoleto;
import com.wagnersantos.cursomc.domain.Pedido;
import com.wagnersantos.cursomc.domain.enums.EstadoPagamento;
import com.wagnersantos.cursomc.repositories.ItemPedidoRepository;
import com.wagnersantos.cursomc.repositories.PagamentoRepository;
import com.wagnersantos.cursomc.repositories.PedidoRepository;
import com.wagnersantos.cursomc.security.UsuarioSS;
import com.wagnersantos.cursomc.services.excecoes.ExcecaoAutorizacao;
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
	private ClienteService clienteService;

	@Autowired
	private EmailService emailService;

	private UsuarioSS user;

	public Pedido buscar(Integer id) {
		Optional<Pedido> objPedido = repo.findById(id);
		if (objPedido == null) {
			throw new ObjetoNaoEncontrado("Objeto não encontrado! id:" + id + ", tipo: " + Pedido.class.getName());
		}
		user = UserService.authenticatador();
		if (user == null || !objPedido.get().getCliente().getId().equals(user.getId())) {
			throw new ExcecaoAutorizacao("Acesso negado");
		}
		return objPedido.get();
	}

	@Transactional
	public Pedido inserirPedido(Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.setCliente(clienteService.buscarClienteId(pedido.getCliente().getId()));
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
		emailService.enviarConfirmacaoEmail(pedido);
		return pedido;
	}

	public Page<Pedido> buscaPorPagina(Integer pagina, Integer linhaPagina, String ordemPagina, String direcao) {
		UsuarioSS usuario = UserService.authenticatador();
		if (usuario == null) {
			throw new ExcecaoAutorizacao("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(pagina, linhaPagina, Direction.valueOf(direcao), ordemPagina);
		Cliente cliente = clienteService.buscarClienteId(usuario.getId());
		return repo.findByCliente(cliente, pageRequest);
	}
}
