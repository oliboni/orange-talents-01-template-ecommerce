package br.com.zup.treino_mercado_ivre.opiniao;

import br.com.zup.treino_mercado_ivre.produto.Produto;
import br.com.zup.treino_mercado_ivre.produto.ProdutoRepository;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/opinioes")
public class OpiniaoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private OpiniaoRepository opiniaoRepository;

    @PostMapping("/{id}")
    public ResponseEntity<?> criarOpiniao(@PathVariable Long id,
                                          @RequestBody @Valid NovaOpiniaoRequest request,
                                          @AuthenticationPrincipal Usuario usuario){
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Produto não encontrado!");
        }
        if (produto.get().produtoPertenceUsuario(usuario)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"O usuário dono do produto não pode opinar sobre o mesmo!");
        }

        Opiniao opiniao = request.toOpiniao(usuario,produto.get());
        opiniaoRepository.save(opiniao);

        return ResponseEntity.ok().build();
    }
}
