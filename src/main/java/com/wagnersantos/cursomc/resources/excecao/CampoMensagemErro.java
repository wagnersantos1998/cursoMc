package com.wagnersantos.cursomc.resources.excecao;

import java.io.Serializable;

public class CampoMensagemErro implements Serializable{
	private static final long serialVersionUID = 1L;

	private String nomeCampo;
	private String mensagem;
	
	public CampoMensagemErro() {
	}

	public CampoMensagemErro(String nomeCampo, String mensagem) {
		super();
		this.nomeCampo = nomeCampo;
		this.mensagem = mensagem;
	}
	
	public String getNomeCampo() {
		return nomeCampo;
	}

	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
