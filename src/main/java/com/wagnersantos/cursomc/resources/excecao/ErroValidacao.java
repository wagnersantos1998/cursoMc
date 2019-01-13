package com.wagnersantos.cursomc.resources.excecao;

import java.util.ArrayList;
import java.util.List;

public class ErroValidacao extends ErroPadrao {
	private static final long serialVersionUID = 1L;

	private List<CampoMensagemErro> erros = new ArrayList<>();
	
	public ErroValidacao(Integer status, String msgm, Long tempoErro) {
		super(status, msgm, tempoErro);

	}

	public List<CampoMensagemErro> getErros() {
		return erros;
	}

	public void adicionarErro(String nomeCampo, String mensagem) {
		erros.add(new CampoMensagemErro(nomeCampo, mensagem));
	}

}
