package com.wagnersantos.cursomc.resources.excecao;

import java.io.Serializable;

public class ErroPadrao implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer status;
	private String msgm;
	private Long tempoErro;
	public ErroPadrao(Integer status, String msgm, Long tempoErro) {
		super();
		this.status = status;
		this.msgm = msgm;
		this.tempoErro = tempoErro;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMsgm() {
		return msgm;
	}
	public void setMsgm(String msgm) {
		this.msgm = msgm;
	}
	public Long getTempoErro() {
		return tempoErro;
	}
	public void setTempoErro(Long tempoErro) {
		this.tempoErro = tempoErro;
	}
	
	
}
