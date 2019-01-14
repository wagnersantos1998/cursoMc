package com.wagnersantos.cursomc.services.validacoes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.wagnersantos.cursomc.domain.Cliente;
import com.wagnersantos.cursomc.dto.ClienteDTO;
import com.wagnersantos.cursomc.repositories.ClienteRepository;
import com.wagnersantos.cursomc.resources.excecao.CampoMensagemErro;

public class ValidadorAtualizarCliente implements ConstraintValidator<AtualizarCliente, ClienteDTO> {

	@Autowired
	ClienteRepository repo;

	@Autowired
	HttpServletRequest request;

	@Override
	public void initialize(AtualizarCliente ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));

		List<CampoMensagemErro> list = new ArrayList<>();

		Cliente auxEmail = repo.findByEmail(objDto.getEmail());
		if (auxEmail != null && !auxEmail.getId().equals(uriId)) {
			list.add(new CampoMensagemErro("email", "Email já existente"));
		}

		for (CampoMensagemErro e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getNomeCampo())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}