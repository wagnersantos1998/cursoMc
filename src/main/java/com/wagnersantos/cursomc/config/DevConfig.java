package com.wagnersantos.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.wagnersantos.cursomc.services.DBService;
import com.wagnersantos.cursomc.services.EmailService;
import com.wagnersantos.cursomc.services.StmpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String verificadorBancoDados;

	@Bean
	public boolean iniciadorBancoDeDados() throws ParseException {

		if (!"create".equals(verificadorBancoDados)) {
			return false;
		} else {
			dbService.inicializadorBancoDeDadosTest();
			return true;
		}
	}
	
	@Bean
	public EmailService emailService() {
		return new StmpEmailService();
	}
}
