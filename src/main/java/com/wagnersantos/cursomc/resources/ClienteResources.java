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

import com.wagnersantos.cursomc.domain.Cliente;
import com.wagnersantos.cursomc.dto.ClienteDTO;
import com.wagnersantos.cursomc.dto.NovoClienteDTO;
import com.wagnersantos.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResources {

	@Autowired
	private ClienteService serviceCli;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = serviceCli.buscarClienteId(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> buscarTodos() {
		List<Cliente> obj = serviceCli.buscarTodosCliente();
		List<ClienteDTO> clienteDTO = obj.stream().map(objConversor -> new ClienteDTO(objConversor))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(clienteDTO);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> atualizarCliente(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {
		Cliente obj = serviceCli.conversorDTO(clienteDTO);
		obj.setId(id);
		obj = serviceCli.atualizarCliente(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deletarCliente(@PathVariable Integer id) {
		serviceCli.deletarCliente(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> inserirCliente(@Valid @RequestBody NovoClienteDTO objDTO) {
		Cliente obj = serviceCli.conversorDTO(objDTO);
		obj = serviceCli.inserirCliente(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> buscaPorPagina(
			@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "linhaPagina", defaultValue = "24") Integer linhaPagina,
			@RequestParam(value = "ordemPagina", defaultValue = "nome") String ordemPagina,
			@RequestParam(value = "direcao", defaultValue = "ASC") String direcao) {
		Page<Cliente> obj = serviceCli.buscaPorPagina(pagina, linhaPagina, ordemPagina, direcao);
		Page<ClienteDTO> objDTO = obj.map(objConvertido -> new ClienteDTO(objConvertido));
		return ResponseEntity.ok().body(objDTO);
	}

}
