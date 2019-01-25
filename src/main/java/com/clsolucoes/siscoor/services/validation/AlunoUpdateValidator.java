package com.clsolucoes.siscoor.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.clsolucoes.siscoor.domain.Aluno;
import com.clsolucoes.siscoor.dto.AlunoDTO;
import com.clsolucoes.siscoor.repositories.AlunoRepository;
import com.clsolucoes.siscoor.resources.exception.FieldMessage;

public class AlunoUpdateValidator implements ConstraintValidator<AlunoUpdate, AlunoDTO> {

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private AlunoRepository repo;
	
	@Override
	public void initialize(AlunoUpdate ann) {
	}

	@Override
	public boolean isValid(AlunoDTO objDto, ConstraintValidatorContext context) {
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		List<FieldMessage> list = new ArrayList<>();
		
		Aluno aux = repo.findByEmailAluno(objDto.getEmail());
		if (aux != null && aux.getId()!=uriId) {
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

