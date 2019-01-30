package com.wagnersantos.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wagnersantos.cursomc.domain.Produto;
import com.wagnersantos.cursomc.dto.ProdutoDTO;
import com.wagnersantos.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResources {

	@Autowired
	private ProdutoService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id){
		Produto objProduto = service.buscar(id);
		return ResponseEntity.ok().body(objProduto);
	}
	

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> buscaPorPagina(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="pagina", defaultValue="0") Integer pagina,
			@RequestParam(value="linhaPagina", defaultValue="24") Integer linhaPagina,
			@RequestParam(value="ordemPagina", defaultValue="nome") String ordemPagina,
			@RequestParam(value="direcao", defaultValue="ASC") String direcao){
		String nomeDecodificado = URL.decodificadorNomeEspaco(nome);
		List<Integer> ids = URL.conversorListaInteger(categorias);
		Page<Produto> obj = service.buscarPedidosNomes(nomeDecodificado, ids, pagina, linhaPagina, ordemPagina, direcao);
		Page<ProdutoDTO> objDTO = obj.map(objConvertido -> new ProdutoDTO(objConvertido));
		return ResponseEntity.ok().body(objDTO);
	}

}
