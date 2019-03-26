package com.wagnersantos.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wagnersantos.cursomc.domain.Cidade;
import com.wagnersantos.cursomc.domain.Cliente;
import com.wagnersantos.cursomc.domain.Endereco;
import com.wagnersantos.cursomc.domain.enums.TipoCliente;
import com.wagnersantos.cursomc.dto.ClienteDTO;
import com.wagnersantos.cursomc.dto.NovoClienteDTO;
import com.wagnersantos.cursomc.repositories.ClienteRepository;
import com.wagnersantos.cursomc.repositories.EnderecoRepository;
import com.wagnersantos.cursomc.services.excecoes.ObjetoNaoEncontrado;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente buscarClienteId(Integer id) {
		Optional<Cliente> objCli = repo.findById(id);
		return objCli.orElseThrow(() -> new ObjetoNaoEncontrado(
				"Objeto não encontrado! id:" + id + ", tipo: " + Cliente.class.getName()));
	}

	public Cliente atualizarCliente(Cliente obj) {
		Cliente newObj = buscarClienteId(obj.getId());
		atualizarDados(newObj, obj);
		return repo.save(newObj);
	}

	public void deletarCliente(Integer id) {
		buscarClienteId(id);
		try {
			repo.deleteById(id);
		} catch (Exception e) {
			throw new DataIntegrityViolationException("Não é possivel excluir porque hã pedidos relacionados");
		}

	}

	public List<Cliente> buscarTodosCliente() {
		List<Cliente> obj = repo.findAll();
		return obj;
	}

	@Transactional
	public Cliente inserirCliente(Cliente obj) {
		obj.setId(null);
		repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Page<Cliente> buscaPorPagina(Integer pagina, Integer linhaPagina, String ordemPagina, String direcao) {
		PageRequest pageRequest = PageRequest.of(pagina, linhaPagina, Direction.valueOf(direcao), ordemPagina);
		return repo.findAll(pageRequest);
	}

	public Cliente conversorDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}

	public Cliente conversorDTO(NovoClienteDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfCnpj(),
				TipoCliente.toEnum(objDTO.getTipo()), pe.encode(objDTO.getSenha()));
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(),
				objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if (objDTO.getTelefone2() != null) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if (objDTO.getTelefone3() != null) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
		return cli;
	}

	private void atualizarDados(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
