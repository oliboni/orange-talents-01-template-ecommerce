package br.com.zup.treino_mercado_ivre.produto;

import br.com.zup.treino_mercado_ivre.categoria.Categoria;
import br.com.zup.treino_mercado_ivre.caracteristica.Caracteristica;
import br.com.zup.treino_mercado_ivre.caracteristica.NovaCaracteristicaRequest;
import br.com.zup.treino_mercado_ivre.imagens.ImagemProduto;
import br.com.zup.treino_mercado_ivre.opiniao.NovaOpiniaoRequest;
import br.com.zup.treino_mercado_ivre.opiniao.Opiniao;
import br.com.zup.treino_mercado_ivre.perguntas.NovaPerguntaRequest;
import br.com.zup.treino_mercado_ivre.perguntas.Pergunta;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime instanteCriacao;

    @NotNull
    @DecimalMin("1.0")
    @Column(nullable = false)
    private BigDecimal valor;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer quantidade;

    @NotEmpty
    @Size(min = 3)
    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private Set<Caracteristica> caracteristicas = new HashSet<>();

    @NotBlank
    @Size(max = 1000)
    @Column(length = 1000)
    private String descricao;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Categoria categoria;

    @NotNull
    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private Usuario usuario;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<ImagemProduto> imagens = new HashSet<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<Opiniao> opinioes = new HashSet<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<Pergunta> perguntas = new HashSet<>();

    public Produto(@NotBlank String nome,
                   @NotNull @DecimalMin("1.0") BigDecimal valor,
                   @NotNull @Min(0) Integer quantidade,
                   @Size(min = 3) Collection<NovaCaracteristicaRequest> caracteristicas,
                   @NotBlank @Size(max = 1000) String descricao,
                   @NotNull Categoria categoria,
                   @NotNull Usuario usuario) {
        Assert.notNull(nome, "O nome precisa ser informado!");
        Assert.isTrue(quantidade >= 0, "A quantidade precisa ser maior ou igual a zero!");
        Assert.isTrue(descricao.length() < 1000, "Devem ser informados no máximo 1000 caracteres na descrição!");
        Assert.notNull(categoria,"A categoria deve ser informada!");

        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.usuario = usuario;
        this.instanteCriacao = LocalDateTime.now();
        this.caracteristicas.addAll(caracteristicas.stream().map(caracteristica -> caracteristica.toCaracteristica(this)).collect(Collectors.toSet()));

        Assert.isTrue(this.caracteristicas.size() >= 3, "Todo produto precisa ter no mínimo 3 características!");
    }
    @Deprecated
    public Produto(){

    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Set<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Set<ImagemProduto> getImagens() {
        return imagens;
    }

    public Set<Opiniao> getOpinioes() {
        return opinioes;
    }

    public Set<Pergunta> getPerguntas() {
        return perguntas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Produto)) return false;
        Produto produto = (Produto) o;
        return Objects.equals(getNome(), produto.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome());
    }

    public void associaImagens(Set<String> links) {
        this.imagens.addAll(links.stream().map(link -> new ImagemProduto(this,link)).collect(Collectors.toSet()));
    }
    public void associaOpiniao(NovaOpiniaoRequest request, Usuario usuario){
        this.opinioes.add(request.toOpiniao(usuario,this));
    }
    public void associaPergunta(NovaPerguntaRequest request, Usuario usuario){
        this.perguntas.add(request.toPergunta(usuario,this));
    }

    public boolean produtoPertenceUsuario(Usuario usuario) {
        if (getUsuario().equals(usuario)){
            return true;
        }
        return false;
    }

    public boolean atualizaEstoque(@Positive Integer quantidade) {
        Assert.isTrue(quantidade > 0, "A quantidade deve ser maior que zero!");
        if (getQuantidade() >= quantidade){
            this.quantidade -= quantidade;
            return true;
        }

        return false;
    }
}
