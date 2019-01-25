package com.clsolucoes.siscoor.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.clsolucoes.siscoor.domain.Aluno;
//import com.clsolucoes.siscoor.domain.enums.TipoCliente;
import com.clsolucoes.siscoor.dto.AlunoNewDTO;
import com.clsolucoes.siscoor.repositories.AlunoRepository;
import com.clsolucoes.siscoor.resources.exception.FieldMessage;
import com.clsolucoes.siscoor.services.validation.utils.BR;

public class AlunoInsertValidator implements ConstraintValidator<AlunoInsert, AlunoNewDTO> {

	@Autowired
	private AlunoRepository repo;
	
	@Override
	public void initialize(AlunoInsert ann) {
	}

	@Override
	public boolean isValid(AlunoNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		if (!BR.isValidCPF(objDto.getCpfAluno())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}		

		Aluno aux = repo.findByEmailAluno(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}

