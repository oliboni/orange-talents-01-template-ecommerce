package br.com.zup.treino_mercado_ivre.compra;

import br.com.zup.treino_mercado_ivre.produto.Produto;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive
    @NotNull
    private Integer quantidade;
    @NotNull
    @Valid
    @ManyToOne
    private Produto produto;
    @ManyToOne
    @Valid
    @NotNull
    private Usuario usuario;
    @NotNull
    @Enumerated
    private StatusCompra statusCompra;
    @NotNull
    @Enumerated
    private GatewayPagamento gateway;


    public Compra(@NotNull @Positive Integer quantidade,
                  @Valid @NotNull Produto produto,
                  @Valid @NotNull Usuario usuario,
                  @NotNull GatewayPagamento gateway) {
        this.quantidade = quantidade;
        this.produto = produto;
        this.usuario = usuario;
        this.gateway = gateway;
        this.statusCompra = StatusCompra.INICIADA;
    }

    public Long getId() {
        return id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public StatusCompra getStatusCompra() {
        return statusCompra;
    }

    public GatewayPagamento getGateway() {
        return gateway;
    }

}
