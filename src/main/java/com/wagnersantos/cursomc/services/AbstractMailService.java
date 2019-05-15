package com.wagnersantos.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.wagnersantos.cursomc.domain.Cliente;
import com.wagnersantos.cursomc.domain.Pedido;

public abstract class AbstractMailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Override
	public void enviarConfirmacaoEmail(Pedido pedido) {
		SimpleMailMessage mensagemEmail = preparaSimpleMailMessageDoPedido(pedido);
		enviarEmail(mensagemEmail);
	}

	private SimpleMailMessage preparaSimpleMailMessageDoPedido(Pedido pedido) {
		SimpleMailMessage mensagemEmail = new SimpleMailMessage();
		mensagemEmail.setTo(pedido.getCliente().getEmail());
		mensagemEmail.setFrom(sender);
		mensagemEmail.setSubject("Pedido confirmado! Código" + pedido.getId());
		mensagemEmail.setSubject("Pedido confirmado! Código " + pedido.getId());
		mensagemEmail.setSentDate(new Date(System.currentTimeMillis()));
		mensagemEmail.setText(pedido.toString());
		return mensagemEmail;
	}

	@Override
	public void enviarNovaSenhaEmail(Cliente cliente, String novaSenha) {
		SimpleMailMessage mensagemEmail = preparaNovaSenhaEmail(cliente, novaSenha);
		enviarEmail(mensagemEmail);

	}

	protected SimpleMailMessage preparaNovaSenhaEmail(Cliente cliente, String novaSenha) {
		SimpleMailMessage mensagemEmail = new SimpleMailMessage();
		mensagemEmail.setTo(cliente.getEmail());
		mensagemEmail.setFrom(sender);
		mensagemEmail.setSubject("Solicitaçao de nova senha" );
		mensagemEmail.setSentDate(new Date(System.currentTimeMillis()));
		mensagemEmail.setText("Nova senha: " + novaSenha);
		return mensagemEmail;
	}
}
