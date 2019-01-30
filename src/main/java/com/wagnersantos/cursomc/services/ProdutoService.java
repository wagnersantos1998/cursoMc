package com.wagnersantos.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.wagnersantos.cursomc.domain.Categoria;
import com.wagnersantos.cursomc.domain.Produto;
import com.wagnersantos.cursomc.repositories.CategoriaRepository;
import com.wagnersantos.cursomc.repositories.ProdutoRepository;
import com.wagnersantos.cursomc.services.excecoes.ObjetoNaoEncontrado;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository repoCategoria;
	
	public Produto buscar(Integer id) {
		Optional<Produto> objPedido = repo.findById(id);
		return objPedido.orElseThrow(() -> new ObjetoNaoEncontrado(
				"Objeto não encontrado! id:" + id + ", tipo: " + Produto.class.getName()));
	}

	public Page<Produto> buscarPedidosNomes(String nome, List<Integer> ids, Integer pagina, Integer linhaPagina, String ordemPagina, String direcao) {
		PageRequest pageRequest = PageRequest.of(pagina, linhaPagina, Direction.valueOf(direcao), ordemPagina);
		List<Categoria> categorias = repoCategoria.findAllById(ids);
		return repo.buscarPedidosNomes(nome, categorias, pageRequest);
	}
	
}
