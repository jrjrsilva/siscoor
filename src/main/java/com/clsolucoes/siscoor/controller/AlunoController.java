package com.clsolucoes.siscoor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.clsolucoes.siscoor.domain.Aluno;
import com.clsolucoes.siscoor.services.AlunoService;

//classe controladora
// mapear recurso nela

@Controller
@RequestMapping("/aluno")
public class AlunoController {
	@Autowired
	private AlunoService alunoService;

	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView modelAndView = new ModelAndView("aluno/listar");
		modelAndView.addObject("alunos",alunoService.findAll());
		return modelAndView;
	}

	@GetMapping("/novo")
	public ModelAndView novo(Aluno aluno) {
		ModelAndView modelAndView = new ModelAndView("aluno/cadastro");
		modelAndView.addObject(aluno);
		return modelAndView;
	}
	
	
}
