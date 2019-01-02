package com.wagnersantos.cursomc.services.excecoes;

public class ObjetoNaoEncontrado extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ObjetoNaoEncontrado(String msgm) {
		super(msgm);
	}
	
	public ObjetoNaoEncontrado(String msgm, Throwable cause) {
		super(msgm, cause);
	}
	
}
