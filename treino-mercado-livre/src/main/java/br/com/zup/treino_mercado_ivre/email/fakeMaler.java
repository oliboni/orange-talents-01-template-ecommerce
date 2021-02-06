package br.com.zup.treino_mercado_ivre.email;

import org.springframework.stereotype.Component;

@Component
public class fakeMaler implements Mailer{
    @Override
    public void send(String body, String subject, String nameFrom, String from, String to) {
        System.out.println(body);
        System.out.println(subject);
        System.out.println(nameFrom);
        System.out.println(from);
        System.out.println(to);
    }
}
