package com.wagnersantos.cursomc.resources.excecao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.wagnersantos.cursomc.services.excecoes.ExcecaoAutorizacao;
import com.wagnersantos.cursomc.services.excecoes.ExcecaoIntegridadeDados;
import com.wagnersantos.cursomc.services.excecoes.ObjetoNaoEncontrado;

@ControllerAdvice
public class ExcecaoManipuladoraResource {

	@ExceptionHandler(ObjetoNaoEncontrado.class)
	public ResponseEntity<ErroPadrao> objetoNaoEncontrado(ObjetoNaoEncontrado e, HttpServletRequest request) {
		ErroPadrao erro = new ErroPadrao(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}

	@ExceptionHandler(ExcecaoIntegridadeDados.class)
	public ResponseEntity<ErroPadrao> IntegridadeDados(ExcecaoIntegridadeDados e, HttpServletRequest request) {
		ErroPadrao erro = new ErroPadrao(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroPadrao> validacao(MethodArgumentNotValidException e, HttpServletRequest request) {
		ErroValidacao erro = new ErroValidacao(HttpStatus.BAD_REQUEST.value(), "Erro de validação",
				System.currentTimeMillis());

		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			erro.adicionarErro(x.getField(), x.getDefaultMessage());
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(ExcecaoAutorizacao.class)
	public ResponseEntity<ErroPadrao> Autorizacao(ExcecaoAutorizacao e, HttpServletRequest request) {
		ErroPadrao erro = new ErroPadrao(HttpStatus.FORBIDDEN.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
	}
	
}
