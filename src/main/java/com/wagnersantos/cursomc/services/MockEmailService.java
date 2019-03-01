package com.wagnersantos.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractMailService{

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void enviarEmail(SimpleMailMessage msgm) {
		LOG.info("Simulando envio de email......");
		LOG.info(msgm.toString());
		LOG.info("Email enviado");
	}

}
