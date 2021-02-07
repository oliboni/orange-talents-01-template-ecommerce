package br.com.zup.treino_mercado_ivre.produto;

import br.com.zup.treino_mercado_ivre.categoria.Categoria;
import br.com.zup.treino_mercado_ivre.categoria.CategoriaRepository;
import br.com.zup.treino_mercado_ivre.imagens.NovasImagensRequest;
import br.com.zup.treino_mercado_ivre.imagens.Uploader;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import br.com.zup.treino_mercado_ivre.validators.ProibeCaracteristicasComNomesIguaisValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private Uploader uploaderFake;

    @InitBinder(value = "novoProdutoRequest")
    public void init(WebDataBinder binder){
        binder.addValidators(new ProibeCaracteristicasComNomesIguaisValidator());
    }

    @PostMapping
    public ResponseEntity<?> novoProduto(@RequestBody @Valid NovoProdutoRequest request,
                                         @AuthenticationPrincipal Usuario usuario){
        Categoria categoria = categoriaRepository.findById(request.getIdCategoria()).get();
        Produto produto = request.toProduto(categoria, produtoRepository, usuario);
        produtoRepository.save(produto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public DetalheProdutoDto mostraDetalhe(@PathVariable("id") Long id){
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!");
        }

        return new DetalheProdutoDto(produto.get());
    }

    @PostMapping("/{id}/imagens")
    public ResponseEntity<?> adicinaImagens(@PathVariable("id") Long id,
                                            @Valid NovasImagensRequest request,
                                            @AuthenticationPrincipal Usuario usuario){
        /*
        * 1)O produto existe?
        * 2)O produto pertence ao usuário
        * 3)Enviar imagens para onde será armazenado
        * 4)Pegar os links de todas as imagens
        * 5)Associar os links com o produto em questão
        */
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Produto não encontrado!");
        }
        if(!produto.get().produtoPertenceUsuario(usuario)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Produto não pertence ao usuário!");
        }


        Set<String> links = uploaderFake.envia(request.getImagens());
        produto.get().associaImagens(links);

        produtoRepository.save(produto.get());

        return ResponseEntity.ok().build();
    }
}
