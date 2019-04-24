package com.wagnersantos.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.wagnersantos.cursomc.domain.Pedido;

public interface EmailService {

	void enviarConfirmacaoEmail(Pedido pedido);

	void enviarEmail(SimpleMailMessage msgm);

}