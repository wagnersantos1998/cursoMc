package com.wagnersantos.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

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
<<<<<<< HEAD
		mensagemEmail.setSubject("Pedido confirmado! Código" + pedido.getId());
=======
		mensagemEmail.setSubject("Pedido confirmado! Código " + pedido.getId());
>>>>>>> 4360cf24830cf2fd53d417672651ffd7973d2c1f
		mensagemEmail.setSentDate(new Date(System.currentTimeMillis()));
		mensagemEmail.setText(pedido.toString());
		return mensagemEmail;
	}

}
