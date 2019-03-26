package com.wagnersantos.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.wagnersantos.cursomc.domain.Pedido;

public interface EmailService {

	void enviarConfirmacaoEmail(Pedido pedido);
	
	void enviarEmail(SimpleMailMessage msgm);
	
<<<<<<< HEAD
}
=======
}
>>>>>>> 4360cf24830cf2fd53d417672651ffd7973d2c1f
