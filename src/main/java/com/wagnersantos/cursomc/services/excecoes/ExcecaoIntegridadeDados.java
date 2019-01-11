package com.wagnersantos.cursomc.services.excecoes;

public class ExcecaoIntegridadeDados extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ExcecaoIntegridadeDados (String msgm) {
		super(msgm);
	}
	
	public ExcecaoIntegridadeDados (String msgm, Throwable cause) {
		super(msgm, cause);
	}
	
}
