package br.com.zup.treino_mercado_ivre.produto;

import br.com.zup.treino_mercado_ivre.categoria.Categoria;
import br.com.zup.treino_mercado_ivre.produto.caracteristica.Caracteristica;
import br.com.zup.treino_mercado_ivre.produto.caracteristica.NovaCaracteristicaRequest;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import br.com.zup.treino_mercado_ivre.validators.ExistValue;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class NovoProdutoRequest {

    @NotBlank
    private String nome;
    @NotNull
    @DecimalMin("1.0")
    private BigDecimal valor;
    @NotNull
    @Min(0)
    private Integer quantidade;
    @NotEmpty
    @Size(min = 3)
    @Valid
    private List<NovaCaracteristicaRequest> caracteristicas = new ArrayList<>();
    @NotBlank
    @Size(max = 1000)
    private String descricao;
    @NotNull
    @ExistValue(domainClass = Categoria.class, fieldName = "id", message = "Categoria não encontrada!")
    private Long idCategoria;

    public NovoProdutoRequest(@NotBlank String nome,
                              @NotNull @DecimalMin("1.0") BigDecimal valor,
                              @NotNull @Min(0) Integer quantidade,
                              @NotEmpty @Size(min = 3) @Valid List<NovaCaracteristicaRequest> caracteristicas,
                              @NotBlank @Size(max = 1000) String descricao,
                              @NotNull Long idCategoria) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.caracteristicas.addAll(caracteristicas);
        this.descricao = descricao;
        this.idCategoria = idCategoria;
    }

    public Produto toProduto(Categoria categoria, ProdutoRepository repository, Usuario usuario){
        if (validaUsuarioComProduto(repository, usuario.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já possui produto cadastrado!");
        }


        return new Produto(nome,valor,quantidade,caracteristicas,descricao,categoria, usuario);
    }

    private boolean validaUsuarioComProduto(ProdutoRepository repository, Long idUsuario){
        Optional<Produto> produto = repository.findByUsuarioId(idUsuario);
        return produto.isPresent();
    }

    public boolean temCaracteristicasIguais(){
        HashSet<String> nomesIguais = new HashSet<>();
        for (NovaCaracteristicaRequest caracteristica:caracteristicas){
            if (!nomesIguais.add(caracteristica.getNome())){
                return true;
            }
        }

        return false;
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

    public Long getIdCategoria() {
        return idCategoria;
    }

    public List<NovaCaracteristicaRequest> getCaracteristicas() {
        return caracteristicas;
    }
}
