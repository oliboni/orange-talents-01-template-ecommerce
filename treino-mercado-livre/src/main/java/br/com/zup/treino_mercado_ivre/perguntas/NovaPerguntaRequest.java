package br.com.zup.treino_mercado_ivre.perguntas;

import br.com.zup.treino_mercado_ivre.produto.Produto;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;

import javax.validation.constraints.NotBlank;
import java.util.Set;
import java.util.stream.Collectors;

public class NovaPerguntaRequest {
    @NotBlank
    private String titulo;

    public NovaPerguntaRequest(@NotBlank String titulo) {
        this.titulo = titulo;
    }

    @Deprecated
    public NovaPerguntaRequest(){

    }

    public String getTitulo() {
        return titulo;
    }

    public Pergunta toPergunta(Usuario usuario, Produto produto) {
        return new Pergunta(titulo,usuario,produto);
    }

    public Set<DetalhePerguntaDto> toResponse(Set<Pergunta> perguntas){
        return perguntas.stream().map(DetalhePerguntaDto::new).collect(Collectors.toSet());
    }
}
