package br.com.zup.treino_mercado_ivre.produto;

import br.com.zup.treino_mercado_ivre.categoria.Categoria;
import br.com.zup.treino_mercado_ivre.produto.caracteristica.NovaCaracteristicaRequest;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProdutoBuilder {
    private String nome;

    private BigDecimal valor;

    private Integer quantidade;

    private Set<NovaCaracteristicaRequest> caracteristicas = new HashSet<NovaCaracteristicaRequest>();

    private String descricao;

    private Categoria categoria;

    private Usuario usuario;

    public ProdutoBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ProdutoBuilder comValor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public ProdutoBuilder comQuantidade(Integer qtde) {
        quantidade = qtde;
        return this;
    }

    public ProdutoBuilder comCaracteristicas(NovaCaracteristicaRequest caracteristicas) {
        this.caracteristicas.add(caracteristicas);
        return this;
    }

    public ProdutoBuilder comDescricao(String descição) {
        this.descricao = descição;

        return this;
    }

    public ProdutoBuilder comCategoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public ProdutoBuilder comUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public Produto constroi(){
        return new Produto(nome,valor,quantidade,caracteristicas,descricao,categoria,usuario);
    }
}
