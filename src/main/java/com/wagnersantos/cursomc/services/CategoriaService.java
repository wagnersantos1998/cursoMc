package com.wagnersantos.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.wagnersantos.cursomc.domain.Categoria;
import com.wagnersantos.cursomc.dto.CategoriaDTO;
import com.wagnersantos.cursomc.repositories.CategoriaRepository;
import com.wagnersantos.cursomc.services.excecoes.ExcecaoIntegridadeDados;
import com.wagnersantos.cursomc.services.excecoes.ObjetoNaoEncontrado;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado(
				"Objeto não encontrado! id:" + id + ", tipo: " + Categoria.class.getName()));
	}

	public Categoria inserirCategoria(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Categoria atualizarCategoria(Categoria obj) {
		buscar(obj.getId());
		return repo.save(obj);
	}

	public void deletarCategoria(Integer id) {
		buscar(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new ExcecaoIntegridadeDados("Não é possivel excluir uma categoria que possui produtos");
		}
	}

	public List<Categoria> buscarTodos() {
		List<Categoria> obj = repo.findAll();
		return obj;
	}

	public Page<Categoria> buscaPorPagina(Integer pagina, Integer linhaPagina, String ordemPagina, String direcao) {
		PageRequest pageRequest = PageRequest.of(pagina, linhaPagina, Direction.valueOf(direcao), ordemPagina);
		return repo.findAll(pageRequest);
	}

	public Categoria conversorDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
	
}
