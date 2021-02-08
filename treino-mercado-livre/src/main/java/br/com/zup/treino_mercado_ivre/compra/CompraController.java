package br.com.zup.treino_mercado_ivre.compra;

import br.com.zup.treino_mercado_ivre.email.EnviaEmail;
import br.com.zup.treino_mercado_ivre.produto.Produto;
import br.com.zup.treino_mercado_ivre.produto.ProdutoRepository;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private EnviaEmail enviaEmail;

    @PostMapping()
    public ResponseEntity<?> criaNovaCompra(@RequestBody @Valid NovaCompraRequest request,
                                            @AuthenticationPrincipal Usuario usuario,
                                            UriComponentsBuilder uriBuilder) throws BindException {
        Produto produto = produtoRepository.findById(request.getIdProduto()).get();
        if(produto.atualizaEstoque(request.getQuantidade())){
            Compra compra = request.toCompra(produto,usuario);
            compraRepository.save(compra);
            String urlRetorno = null;

            if (compra.getGateway().equals(GatewayPagamento.PAYPAL)){
                urlRetorno = uriBuilder.path("/retorno-paypal/{id}").buildAndExpand(compra.getId()).toString();
            }else {
                urlRetorno = uriBuilder.path("/retorno-pagseguro/{id}").buildAndExpand(compra.getId()).toString();
            }

            enviaEmail.sendGenericEmail("Olá " + produto.getUsuario().getUsername() + ", um novo pedido foi feito para seu produto " + produto.getNome(),
                                        "Novo pedido para produto " + produto.getNome(),
                                        usuario.getUsername(),
                                        "pedidos@ml.com",
                                        produto.getUsuario().getUsername());
            System.out.println(compra.getGateway().getUrlRetorno(compra.getId(),urlRetorno));

            return ResponseEntity.status(HttpStatus.FOUND)
                                 .header(HttpHeaders.LOCATION, request.getGateway().getUrlRetorno(compra.getId(),urlRetorno))
                                 .build();
        }

        BindException exception = new BindException(request, "NovaCompraRequest");
        exception.rejectValue("quantidade", null,"Não foi possível realizar a compra, estoque menor que a quantidade solicitado");

        throw exception;
    }
}
