package com.algaworks.algafood.infrastructure.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService{
	
	
	@Override
	public void enviar(Mensagem mensagem) {
		String corpo = processarTemplate(mensagem);
		System.out.println("ENVIANDO E-MAIL FAKE DE TESTE...\n");
		log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
	}

}
