package br.com.zup.treino_mercado_ivre.opiniao;

import br.com.zup.treino_mercado_ivre.produto.Produto;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NovaOpiniaoRequest {
    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer nota;

    public NovaOpiniaoRequest(@NotBlank String titulo, @NotBlank String descricao, @NotNull @Min(1) @Max(5) Integer nota) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.nota = nota;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getNota() {
        return nota;
    }

    public Opiniao toOpiniao(Usuario usuario, Produto produto){
        return new Opiniao(titulo,descricao,nota,usuario,produto);
    }
}
