package com.wagnersantos.cursomc.services.excecoes;

public class ExcecaoAutorizacao extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcecaoAutorizacao (String msgm) {
		super(msgm);
	}
	
	public ExcecaoAutorizacao (String msgm, Throwable cause) {
		super(msgm, cause);
	}
	
}
