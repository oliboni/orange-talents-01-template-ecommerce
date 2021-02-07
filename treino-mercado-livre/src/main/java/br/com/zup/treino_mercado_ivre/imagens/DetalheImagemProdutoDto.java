package br.com.zup.treino_mercado_ivre.imagens;

public class DetalheImagemProdutoDto {
    private String link;

    public DetalheImagemProdutoDto(ImagemProduto imagemProduto) {
        this.link = imagemProduto.getLink();
    }

    public String getLink() {
        return link;
    }
}
