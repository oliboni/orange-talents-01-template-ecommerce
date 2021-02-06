package br.com.zup.treino_mercado_ivre.perguntas;


import br.com.zup.treino_mercado_ivre.produto.Produto;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
public class Pergunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    private LocalDateTime instanteCriacao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;

    public Pergunta(@NotBlank String titulo, Usuario usuario, Produto produto) {
        this.titulo = titulo;
        this.usuario = usuario;
        this.produto = produto;
        this.instanteCriacao = LocalDateTime.now();
    }

    @Deprecated
    public Pergunta(){

    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDateTime getInstanteCriacao() {
        return instanteCriacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Produto getProduto() {
        return produto;
    }
}
