package com.clsolucoes.siscoor.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.clsolucoes.siscoor.domain.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

	@Transactional(readOnly=true)
	Aluno findByEmailAluno(String emailAluno);
}
