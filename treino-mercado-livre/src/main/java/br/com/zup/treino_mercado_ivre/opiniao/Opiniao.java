package br.com.zup.treino_mercado_ivre.opiniao;

import br.com.zup.treino_mercado_ivre.produto.Produto;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Opiniao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String titulo;

    @NotBlank
    @Column(nullable = false,length = 500)
    private String descricao;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private Integer nota;

    @NotNull
    @ManyToOne
    @Valid
    @JoinColumn(nullable = false)
    private Usuario usuario;

    @NotNull
    @ManyToOne
    @Valid
    @JoinColumn(nullable = false)
    private Produto produto;

    public Opiniao(@NotBlank String titulo, @NotBlank String descricao, @NotNull @Min(1) @Max(5) Integer nota, @NotNull @Valid Usuario usuario, @NotNull @Valid Produto produto) {
        Assert.isTrue(nota>=1,"Nota deve ser entre 1 e 5");
        Assert.isTrue(nota<=5,"Nota deve ser entre 1 e 5!");
        Assert.isTrue(titulo!=null,"Título não pode ser nulo");
        Assert.isTrue(descricao!=null,"Descrição não pode ser nulo");
        Assert.notNull(usuario,"O usuário não pode ser nulo!");
        Assert.notNull(produto,"O produto não pode ser nulo!");

        this.titulo = titulo;
        this.descricao = descricao;
        this.nota = nota;
        this.usuario = usuario;
        this.produto = produto;

        Assert.isTrue(!produto.produtoPertenceUsuario(usuario),"O usuário dono do produto não pode opinar sobre o mesmo!");
    }

    @Deprecated
    public Opiniao(){

    }

    public Long getId() {
        return id;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public Produto getProduto() {
        return produto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Opiniao)) return false;
        Opiniao opiniao = (Opiniao) o;
        return Objects.equals(getTitulo(), opiniao.getTitulo()) && Objects.equals(getDescricao(), opiniao.getDescricao()) && Objects.equals(getUsuario(), opiniao.getUsuario()) && Objects.equals(getProduto(), opiniao.getProduto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitulo(), getDescricao(), getUsuario(), getProduto());
    }
}
