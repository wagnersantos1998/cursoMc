package com.wagnersantos.cursomc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class StmpEmailService extends AbstractMailService {

	@Autowired
	private MailSender enviarEmail;

	private static final Logger LOG = LoggerFactory.getLogger(StmpEmailService.class);

	@Override
	public void enviarEmail(SimpleMailMessage msgm) {
		LOG.info(" Enviando email ... ");
		enviarEmail.send(msgm);
	}

}
