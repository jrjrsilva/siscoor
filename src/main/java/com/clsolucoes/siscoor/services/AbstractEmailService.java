package com.clsolucoes.siscoor.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.clsolucoes.siscoor.domain.Aluno;
//import com.clsolucoes.siscoor.domain.Supervisor;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
//	@Override
//	public void sendOrderConfirmationEmail(Pedido obj) {
//		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
//		sendEmail(sm);
//	}
//
//	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
//		SimpleMailMessage sm = new SimpleMailMessage();
//		sm.setTo(obj.getCliente().getEmail());
//		sm.setFrom(sender);
//		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
//		sm.setSentDate(new Date(System.currentTimeMillis()));
//		sm.setText(obj.toString());
//		return sm;
//	}
	
	@Override
	public void sendNewPasswordEmail(Aluno aluno, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(aluno, newPass);
		sendEmail(sm);
	}
	
	protected SimpleMailMessage prepareNewPasswordEmail(Aluno aluno, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(aluno.getEmailAluno());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}
	
//	@Override
//	public void sendNewPasswordEmail(Supervisor supervisor, String newPass) {
//		SimpleMailMessage sm = prepareNewPasswordEmail(supervisor, newPass);
//		sendEmail(sm);
//	}
//	
//	protected SimpleMailMessage prepareNewPasswordEmail(Supervisor supervisor, String newPass) {
//		SimpleMailMessage sm = new SimpleMailMessage();
//		sm.setTo(supervisor.getEmail());
//		sm.setFrom(sender);
//		sm.setSubject("Solicitação de nova senha");
//		sm.setSentDate(new Date(System.currentTimeMillis()));
//		sm.setText("Nova senha: " + newPass);
//		return sm;
//	}
}
