package br.com.zup.treino_mercado_ivre.email;

import br.com.zup.treino_mercado_ivre.perguntas.Pergunta;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
public class EnviaEmail{

    @Autowired
    private Mailer mailer;

    public void sendEmail(@NotNull @Valid Pergunta pergunta, @NotNull String url){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setText("Olá "+pergunta.getProduto().getUsuario().getUsername()+
//                ", foi feita uma nova pergunta para seu produto "+
//                pergunta.getProduto().getNome()+ ". para maiores detalhes acesse: "+ url);
//        message.setFrom(produto.getUsuario().getUsername());
//        message.setTo(produto.getUsuario().getUsername());
//        message.setSubject("Nova perguta em seu produto " + produto.getNome());


        var message = "<html> Olá " + pergunta.getProduto().getUsuario().getUsername() +
                        "<br><br>" +
                        " Foi feita uma pergunta para o seu produto " + pergunta.getProduto().getNome() +
                        "<br><br>" +
                        " Pergunta: " + pergunta.getTitulo() +
                        "<br><br>" +
                        " Para maiores informações, acesse: " + url +
                      "</html>";
        mailer.send(message,
                "Nova pergunta em seu produto " + pergunta.getProduto().getNome(),
                pergunta.getUsuario().getUsername(),
                "novaPergunta@ml.com",
                pergunta.getProduto().getUsuario().getUsername());
    }
}
