package com.clsolucoes.siscoor.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.clsolucoes.siscoor.domain.enums.Perfil;

import com.clsolucoes.siscoor.domain.Aluno;

import com.clsolucoes.siscoor.repositories.AlunoRepository;

@Service
public class DBService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private AlunoRepository alunoRepository;

	
	public void instantiateTestDatabase() throws ParseException {
		Aluno alu1 = new Aluno(null, "Maria Silva", "neidijanec@yahoo.com.br", "36378912377", pe.encode("123"));
		alu1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		alu1.addPerfil(Perfil.LIDER);
		
		Aluno alu2 = new Aluno(null, "Ana Costa", "neidijanec@gmail.com", "31628382740", pe.encode("123"));
		alu2.getTelefones().addAll(Arrays.asList("93883321", "34252625"));
		alu2.addPerfil(Perfil.ESTAGIARIO);
	
		alunoRepository.saveAll(Arrays.asList(alu1, alu2));
	}
}
