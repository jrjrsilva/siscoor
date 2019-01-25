package com.clsolucoes.siscoor.services;

import org.springframework.mail.SimpleMailMessage;

import com.clsolucoes.siscoor.domain.Aluno;
//import com.clsolucoes.siscoor.domain.Pedido;

public interface EmailService {

	//void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
	
	void sendNewPasswordEmail(Aluno aluno, String newPass);
}
