package com.wagnersantos.cursomc.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wagnersantos.cursomc.domain.Categoria;
import com.wagnersantos.cursomc.domain.Cidade;
import com.wagnersantos.cursomc.domain.Cliente;
import com.wagnersantos.cursomc.domain.Endereco;
import com.wagnersantos.cursomc.domain.Estado;
import com.wagnersantos.cursomc.domain.ItemPedido;
import com.wagnersantos.cursomc.domain.Pagamento;
import com.wagnersantos.cursomc.domain.PagamentoComBoleto;
import com.wagnersantos.cursomc.domain.PagamentoComCartao;
import com.wagnersantos.cursomc.domain.Pedido;
import com.wagnersantos.cursomc.domain.Produto;
import com.wagnersantos.cursomc.domain.enums.EstadoPagamento;
import com.wagnersantos.cursomc.domain.enums.Perfil;
import com.wagnersantos.cursomc.domain.enums.TipoCliente;
import com.wagnersantos.cursomc.repositories.CategoriaRepository;
import com.wagnersantos.cursomc.repositories.CidadeRepository;
import com.wagnersantos.cursomc.repositories.ClienteRepository;
import com.wagnersantos.cursomc.repositories.EnderecoRepository;
import com.wagnersantos.cursomc.repositories.EstadoRepository;
import com.wagnersantos.cursomc.repositories.ItemPedidoRepository;
import com.wagnersantos.cursomc.repositories.PagamentoRepository;
import com.wagnersantos.cursomc.repositories.PedidoRepository;
import com.wagnersantos.cursomc.repositories.ProdutoRepository;

@Service
public class DBService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private BCryptPasswordEncoder pe;

	public void inicializadorBancoDeDadosTest() throws ParseException {

		Categoria cat1 = new Categoria(null, "Escritorio");
		Categoria cat2 = new Categoria(null, "Informatica");
		Categoria cat3 = new Categoria(null, "Cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Jardinagem");
		Categoria cat5 = new Categoria(null, "Vestuario");
		Categoria cat6 = new Categoria(null, "Perfumaria");
		Categoria cat7 = new Categoria(null, "Utensilios domesticos");

		Produto p1 = new Produto(null, "computador", 2000.00);
		Produto p2 = new Produto(null, "impressora", 800.00);
		Produto p3 = new Produto(null, "mouse", 80.00);
		Produto p4 = new Produto(null, "mesa de escritorio", 300.00);
		Produto p5 = new Produto(null, "toalha", 50.00);
		Produto p6 = new Produto(null, "colcha", 200.00);
		Produto p7 = new Produto(null, "tv true color", 1200.00);
		Produto p8 = new Produto(null, "rocadeira", 800.00);
		Produto p9 = new Produto(null, "abajour", 100.00);
		Produto p10 = new Produto(null, "patente", 180.00);
		Produto p11 = new Produto(null, "shampoo", 90.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		Estado est1 = new Estado(null, "minas gerais");
		Estado est2 = new Estado(null, "sao paulo");

		Cidade c1 = new Cidade(null, "uberlandia", est1);
		Cidade c2 = new Cidade(null, "sao paulo", est2);
		Cidade c3 = new Cidade(null, "campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cl1 = new Cliente(null, "Maria Silva", "aldoirpedrinho@gmail.com", "36378912377",
				TipoCliente.PESSOAFISICA, pe.encode("123"));
		cl1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Cliente cl2 = new Cliente(null, "Ana Teste", "wagner_santos@live.com", "36378912377", TipoCliente.PESSOAFISICA,
				pe.encode("123"));
		cl2.addPerfil(Perfil.ADMIN);
		cl2.getTelefones().addAll(Arrays.asList("14795045", "4562589"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 300", "jardin", "38220834", cl1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cl1, c2);
		Endereco e3 = new Endereco(null, "Avenida Floriano", "210", null, "Centro", "74577012", cl2, c2);

		
		cl1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cl2.getEnderecos().addAll(Arrays.asList(e3));

		clienteRepository.saveAll(Arrays.asList(cl1, cl2));

		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));

		SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, data.parse("30/09/2017 10:32"), cl1, e1);
		Pedido ped2 = new Pedido(null, data.parse("10/10/2017 19:35"), cl2, e2);

		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);

		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, data.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pgto2);

		cl1.getPedidos().addAll(Arrays.asList(ped1));
		cl2.getPedidos().addAll(Arrays.asList(ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgto1, pgto2));

		ItemPedido ip1 = new ItemPedido(p1, ped1, 1, 2000.00, 0.00);
		ItemPedido ip2 = new ItemPedido(p3, ped1, 2, 80.00, 0.00);
		ItemPedido ip3 = new ItemPedido(p2, ped2, 1, 800.00, 100.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

	}
}
