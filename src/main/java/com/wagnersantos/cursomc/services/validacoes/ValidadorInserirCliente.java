package com.wagnersantos.cursomc.services.validacoes;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.wagnersantos.cursomc.domain.Cliente;
import com.wagnersantos.cursomc.domain.enums.TipoCliente;
import com.wagnersantos.cursomc.dto.NovoClienteDTO;
import com.wagnersantos.cursomc.repositories.ClienteRepository;
import com.wagnersantos.cursomc.resources.excecao.CampoMensagemErro;
import com.wagnersantos.cursomc.services.validacoes.utils.BR;

public class ValidadorInserirCliente implements ConstraintValidator<InserirCliente, NovoClienteDTO> {

	@Autowired
	ClienteRepository repo;
	
	@Override
	public void initialize(InserirCliente ann) {
	}

	@Override
	public boolean isValid(NovoClienteDTO objDto, ConstraintValidatorContext context) {

		List<CampoMensagemErro> list = new ArrayList<>();

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfCnpj())) {
			list.add(new CampoMensagemErro("cpfCnpj", "CPF inválido"));
		}
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfCnpj())) {
			list.add(new CampoMensagemErro("CpfCnpj", "CNPJ inválido"));
		}

		Cliente auxEmail = repo.findByEmail(objDto.getEmail());
		Cliente auxCpfCnpj = repo.findByCpfCnpj(objDto.getCpfCnpj());
		
		if (auxEmail != null) {
			list.add(new CampoMensagemErro("email", "Email já existente"));
		}
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && auxCpfCnpj != null) {
			list.add(new CampoMensagemErro("cpfCnpj", "CPF já existente"));
		}
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && auxCpfCnpj != null) {
			list.add(new CampoMensagemErro("CpfCnpj", "CNPJ já existente"));
		}
		
		for (CampoMensagemErro e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMensagem()).addPropertyNode(e.getNomeCampo())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}