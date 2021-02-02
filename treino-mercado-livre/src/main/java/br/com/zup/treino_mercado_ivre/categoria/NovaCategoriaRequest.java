package br.com.zup.treino_mercado_ivre.categoria;

import br.com.zup.treino_mercado_ivre.validators.ExistValue;
import br.com.zup.treino_mercado_ivre.validators.UniqueValue;

import javax.validation.constraints.NotBlank;

public class NovaCategoriaRequest {
    @NotBlank
    @UniqueValue(domainClass = Categoria.class, fieldName = "nome", message = "Categoria já cadastrada!")
    private String nome;
    @ExistValue(domainClass = Categoria.class, fieldName = "id", message = "Categoria Mão não encontrada!")
    private Long idCategoriaMae;

    public NovaCategoriaRequest(@NotBlank String nome, Long idCategoriaMae) {
        this.nome = nome;
        this.idCategoriaMae = idCategoriaMae;
    }

    public Long getIdCategoriaMae() {
        return idCategoriaMae;
    }

    public Categoria toCategoria(Categoria categoria){
        return new Categoria(nome,categoria);
    }

}
