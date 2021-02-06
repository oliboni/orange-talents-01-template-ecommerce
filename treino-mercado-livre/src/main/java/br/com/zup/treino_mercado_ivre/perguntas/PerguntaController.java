package br.com.zup.treino_mercado_ivre.perguntas;

import br.com.zup.treino_mercado_ivre.email.EnviaEmail;
import br.com.zup.treino_mercado_ivre.produto.Produto;
import br.com.zup.treino_mercado_ivre.produto.ProdutoRepository;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class PerguntaController {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private PerguntaRepository perguntaRepository;
    @Autowired
    private EnviaEmail enviaEmail;

    @PostMapping("/api/produtos/{id}/perguntas")
    public ResponseEntity<?> criaPergunta(@PathVariable("id") Long id,
                                          @RequestBody @Valid NovaPerguntaRequest request,
                                          @AuthenticationPrincipal Usuario usuario,
                                          UriComponentsBuilder uriBuilder){
        Optional<Produto> produto = produtoRepository.findById(id);
        if (produto.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Produto não encontrado!");
        }
        if (produto.get().produtoPertenceUsuario(usuario)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário dono do produto não pode incluir uma pergunta ao produto!");
        }

        Pergunta pergunta = request.toPergunta(usuario,produto.get());
        perguntaRepository.save(pergunta);

        String urlProduto = uriBuilder.path("/api/produtos/{id}").buildAndExpand(id).toString();

        enviaEmail.sendEmail(pergunta, urlProduto);

        var listaPerguntas = perguntaRepository.findByProdutoId(id);

        return ResponseEntity.ok(listaPerguntas);
    }
}
