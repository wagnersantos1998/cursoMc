package com.wagnersantos.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wagnersantos.cursomc.domain.Categoria;
import com.wagnersantos.cursomc.repositories.CategoriaRepository;
import com.wagnersantos.cursomc.services.excecoes.ObjetoNaoEncontrado;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjetoNaoEncontrado("Objeto não encontrado! id:" + id + ", tipo: " + Categoria.class.getName()));
	}
}
