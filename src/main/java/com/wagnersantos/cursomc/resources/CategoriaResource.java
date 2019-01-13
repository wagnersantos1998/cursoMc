package com.wagnersantos.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.wagnersantos.cursomc.domain.Categoria;
import com.wagnersantos.cursomc.dto.CategoriaDTO;
import com.wagnersantos.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletarCategoria(@PathVariable Integer id){
		service.deletarCategoria(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {

		Categoria obj = service.buscarCategoriaId(id);
		
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> inserirCategoria(@Valid @RequestBody CategoriaDTO objDTO){
		Categoria obj = service.conversorDTO(objDTO);
		obj = service.inserirCategoria(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizarCategoria(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id){
		Categoria obj = service.conversorDTO(objDTO);
		obj.setId(id);
		obj = service.atualizarCategoria(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> buscarTodos(){
		List<Categoria> obj = service.buscarTodasCategorias();
		List<CategoriaDTO> objDTO = obj.stream().map(objConversor -> new CategoriaDTO(objConversor)).collect(Collectors.toList());
		return ResponseEntity.ok().body(objDTO);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> buscaPorPagina(
			@RequestParam(value="pagina", defaultValue="0") Integer pagina,
			@RequestParam(value="linhaPagina", defaultValue="24") Integer linhaPagina,
			@RequestParam(value="ordemPagina", defaultValue="nome") String ordemPagina,
			@RequestParam(value="direcao", defaultValue="ASC") String direcao){
		Page<Categoria> obj = service.buscaPorPagina(pagina, linhaPagina, ordemPagina, direcao);
		Page<CategoriaDTO> objDTO = obj.map(objConvertido -> new CategoriaDTO(objConvertido));
		return ResponseEntity.ok().body(objDTO);
	}
	
}
