package br.com.zup.treino_mercado_ivre.email;

import br.com.zup.treino_mercado_ivre.perguntas.Pergunta;
import br.com.zup.treino_mercado_ivre.produto.Produto;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
public class EnviaEmail{

    @Autowired
    private Mailer mailer;

    public void sendEmail(@NotNull @Valid Produto produto, @NotNull String url, @NotBlank String tituloPergunta, @NotNull @Valid Usuario usuario){
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setText("Olá "+pergunta.getProduto().getUsuario().getUsername()+
//                ", foi feita uma nova pergunta para seu produto "+
//                pergunta.getProduto().getNome()+ ". para maiores detalhes acesse: "+ url);
//        message.setFrom(produto.getUsuario().getUsername());
//        message.setTo(produto.getUsuario().getUsername());
//        message.setSubject("Nova perguta em seu produto " + produto.getNome());


        var message = "<html> Olá " + produto.getUsuario().getUsername() +
                        "<br><br>" +
                        " Foi feita uma pergunta para o seu produto " + produto.getNome() +
                        "<br><br>" +
                        " Pergunta: " + tituloPergunta +
                        "<br><br>" +
                        " Para maiores informações, acesse: " + url +
                      "</html>";
        mailer.send(message,
                "Nova pergunta em seu produto " + produto.getNome(),
                usuario.getUsername(),
                "novaPergunta@ml.com",
                produto.getUsuario().getUsername());
    }

    public void sendGenericEmail(@NotBlank String body, @NotBlank String subject, @NotBlank String nameFrom, @NotBlank @Email String from, @NotBlank @Email String to){
        mailer.send(body,subject,nameFrom,from,to);
    }
}
