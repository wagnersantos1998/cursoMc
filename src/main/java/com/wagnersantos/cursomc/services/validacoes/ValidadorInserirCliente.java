package com.wagnersantos.cursomc.services.validacoes;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wagnersantos.cursomc.domain.enums.TipoCliente;
import com.wagnersantos.cursomc.dto.NovoClienteDTO;
import com.wagnersantos.cursomc.resources.excecao.CampoMensagemErro;
import com.wagnersantos.cursomc.services.validacoes.utils.BR;

public class ValidadorInserirCliente implements ConstraintValidator<InserirCliente, NovoClienteDTO> {

	@Override
	public void initialize(InserirCliente ann) {
	}

	@Override
	public boolean isValid(NovoClienteDTO objDto, ConstraintValidatorContext context) {

		List<CampoMensagemErro> list = new ArrayList<>();

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfCnpj()) {
			list.add(new CampoMensagemErro("CPF", "Campo cpf inválido"));
		}
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfCnpj())) {
			list.add(new CampoMensagemErro("CNPJ", "Campo cnpj inválido"));
		}

		for (CampoMensagemErro e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getNomeCampo())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}