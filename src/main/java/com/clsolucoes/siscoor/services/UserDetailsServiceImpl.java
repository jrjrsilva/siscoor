package com.clsolucoes.siscoor.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.clsolucoes.siscoor.domain.Aluno;
import com.clsolucoes.siscoor.repositories.AlunoRepository;
import com.clsolucoes.siscoor.security.UserSS;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AlunoRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Aluno alu = repo.findByEmailAluno(email);
		if (alu == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSS(alu.getId(), alu.getEmailAluno(), alu.getSenha(),alu.getPerfis());
	}
}
