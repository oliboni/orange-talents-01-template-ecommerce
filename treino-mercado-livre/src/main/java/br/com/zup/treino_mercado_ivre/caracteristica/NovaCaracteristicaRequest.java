package br.com.zup.treino_mercado_ivre.caracteristica;

import br.com.zup.treino_mercado_ivre.produto.Produto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NovaCaracteristicaRequest {
    @NotBlank
    private String nome;
    @NotBlank
    private String valor;

    public NovaCaracteristicaRequest(@NotBlank String nome, @NotBlank String valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public String getValor() {
        return valor;
    }

    public Caracteristica toCaracteristica(@NotNull @Valid Produto produto){
        return new Caracteristica(nome,valor, produto);
    }
}
