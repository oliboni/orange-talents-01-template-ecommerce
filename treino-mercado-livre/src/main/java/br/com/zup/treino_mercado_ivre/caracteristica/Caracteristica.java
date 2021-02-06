package br.com.zup.treino_mercado_ivre.caracteristica;

import br.com.zup.treino_mercado_ivre.produto.Produto;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Caracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String valor;
    @ManyToOne
    private Produto produto;

    public Caracteristica(String nome, String valor, @NotNull @Valid Produto produto) {
        this.nome = nome;
        this.valor = valor;
        this.produto = produto;
    }
    @Deprecated
    public Caracteristica(){

    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getValor() {
        return valor;
    }

    public Produto getProduto() {
        return produto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Caracteristica)) return false;
        Caracteristica that = (Caracteristica) o;
        return Objects.equals(getNome(), that.getNome()) && Objects.equals(getProduto(), that.getProduto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getProduto());
    }
}
