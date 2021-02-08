package br.com.zup.treino_mercado_ivre.produto;

import br.com.zup.treino_mercado_ivre.caracteristica.Caracteristica;
import br.com.zup.treino_mercado_ivre.caracteristica.DetalheCaracteristicasDto;
import br.com.zup.treino_mercado_ivre.imagens.DetalheImagemProdutoDto;
import br.com.zup.treino_mercado_ivre.imagens.ImagemProduto;
import br.com.zup.treino_mercado_ivre.opiniao.DetalheOpiniaoDto;
import br.com.zup.treino_mercado_ivre.opiniao.Opiniao;
import br.com.zup.treino_mercado_ivre.perguntas.DetalhePerguntaDto;
import br.com.zup.treino_mercado_ivre.perguntas.Pergunta;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

public class DetalheProdutoDto {
    private String nome;
    private BigDecimal valor;
    private String descricao;
    private Integer quantidade;
    private Set<DetalheCaracteristicasDto> caracteristicas;
    private Set<DetalheImagemProdutoDto> imagens;
    private Set<DetalheOpiniaoDto> opinioes;
    private Set<DetalhePerguntaDto> perguntas;

    public DetalheProdutoDto(Produto produto){
        this.nome = produto.getNome();
        this.valor = produto.getValor();
        this.descricao = produto.getDescricao();
        this.quantidade = produto.getQuantidade();
        this.caracteristicas = builderCaracteristicas(produto.getCaracteristicas());
        this.imagens = builderImagens(produto.getImagens());
        this.opinioes = builderOpinioes(produto.getOpinioes());
        this.perguntas = builderPerguntas(produto.getPerguntas());
    }

    private Set<DetalheCaracteristicasDto> builderCaracteristicas(Set<Caracteristica> caracteristicas){
        return caracteristicas.stream().map(DetalheCaracteristicasDto::new).collect(Collectors.toSet());
    }

    private Set<DetalheImagemProdutoDto> builderImagens(Set<ImagemProduto> imagens){
        return imagens.stream().map(DetalheImagemProdutoDto::new).collect(Collectors.toSet());
    }

    private Set<DetalheOpiniaoDto> builderOpinioes(Set<Opiniao> opinioes){
        return opinioes.stream().map(DetalheOpiniaoDto::new).collect(Collectors.toSet());
    }

    private Set<DetalhePerguntaDto> builderPerguntas(Set<Pergunta> perguntas){
        return perguntas.stream().map(DetalhePerguntaDto::new).collect(Collectors.toSet());
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Set<DetalheCaracteristicasDto> getCaracteristicas() {
        return caracteristicas;
    }

    public Set<DetalheImagemProdutoDto> getImagens() {
        return imagens;
    }

    public Set<DetalheOpiniaoDto> getOpinioes() {
        return opinioes;
    }

    public Set<DetalhePerguntaDto> getPerguntas() {
        return perguntas;
    }

    public Integer getTotalNotas() {
        return opinioes.stream().map(DetalheOpiniaoDto::getNota).reduce(0, Integer::sum);
    }

    public BigDecimal getMediaNotas() {
        if(opinioes.isEmpty()){
            return new BigDecimal("0");
        }

        return new BigDecimal(getTotalNotas()/opinioes.size());

    }
}
