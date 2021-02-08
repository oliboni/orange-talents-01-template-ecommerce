package br.com.zup.treino_mercado_ivre.compra;

import br.com.zup.treino_mercado_ivre.produto.Produto;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import br.com.zup.treino_mercado_ivre.validators.ExistValue;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NovaCompraRequest {
    @NotNull
    @Positive
    private Integer quantidade;
    @NotNull
    @ExistValue(domainClass = Produto.class, fieldName = "id", message = "Produto não encontrado!")
    private Long idProduto;
    @NotNull
    private GatewayPagamento gateway;

    public NovaCompraRequest(@NotNull @Positive Integer quantidade,
                             @NotNull Long idProduto,
                             @NotNull GatewayPagamento gateway) {
        this.quantidade = quantidade;
        this.idProduto = idProduto;
        this.gateway = gateway;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Long getIdProduto() {
        return idProduto;
    }

    public GatewayPagamento getGateway() {
        return gateway;
    }

    public Compra toCompra(Produto produto, Usuario usuario) {
        return new Compra(quantidade,produto,usuario,gateway);
    }
}
