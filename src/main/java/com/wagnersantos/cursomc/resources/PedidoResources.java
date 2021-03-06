package com.wagnersantos.cursomc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.wagnersantos.cursomc.domain.Pedido;
import com.wagnersantos.cursomc.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResources {

	@Autowired
	private PedidoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido objPedido = service.buscar(id);
		return ResponseEntity.ok().body(objPedido);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserirPedido(@Valid @RequestBody Pedido pedido) {
		pedido = service.inserirPedido(pedido);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pedido.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<Pedido>> buscaPorPagina(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhaPagina", defaultValue = "24") Integer linhaPagina,
			@RequestParam(value = "ordemPagina", defaultValue = "instante") String ordemPagina,
			@RequestParam(value = "direcao", defaultValue = "DESC") String direcao) {
		Page<Pedido> obj = service.buscaPorPagina(pagina, linhaPagina, ordemPagina, direcao);
		return ResponseEntity.ok().body(obj);
	}

}
