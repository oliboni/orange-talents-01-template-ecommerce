package br.com.zup.treino_mercado_ivre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = MailSenderAutoConfiguration.class)
public class TreinoMercadoLivreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreinoMercadoLivreApplication.class, args);
	}

}
