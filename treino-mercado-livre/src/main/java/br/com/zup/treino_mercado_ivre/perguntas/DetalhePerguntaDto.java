package br.com.zup.treino_mercado_ivre.perguntas;

import java.time.LocalDateTime;

public class DetalhePerguntaDto {
    private String titulo;
    private LocalDateTime instanteCriacao;
    private String emailUsuario;

    public DetalhePerguntaDto(Pergunta pergunta) {
        this.titulo = pergunta.getTitulo();
        this.instanteCriacao = pergunta.getInstanteCriacao();
        this.emailUsuario = pergunta.getUsuario().getUsername();
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getInstanteCriacao() {
        return instanteCriacao;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }
}
