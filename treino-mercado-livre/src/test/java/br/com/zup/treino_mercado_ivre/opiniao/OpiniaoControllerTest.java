package br.com.zup.treino_mercado_ivre.opiniao;

import br.com.zup.treino_mercado_ivre.autenticacao.NovaAutenticacaoRequest;
import br.com.zup.treino_mercado_ivre.autenticacao.TokenService;
import br.com.zup.treino_mercado_ivre.categoria.Categoria;
import br.com.zup.treino_mercado_ivre.categoria.CategoriaRepository;
import br.com.zup.treino_mercado_ivre.produto.Produto;
import br.com.zup.treino_mercado_ivre.produto.ProdutoBuilder;
import br.com.zup.treino_mercado_ivre.produto.ProdutoRepository;
import br.com.zup.treino_mercado_ivre.caracteristica.NovaCaracteristicaRequest;
import br.com.zup.treino_mercado_ivre.usuario.SenhaLimpa;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import br.com.zup.treino_mercado_ivre.usuario.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@Transactional
class OpiniaoControllerTest {
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private OpiniaoRepository opiniaoRepository;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper jsonMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authManager;



    private Categoria getCategoria(){
        Categoria categoria = new Categoria("Categoria 1",null);
        categoriaRepository.save(categoria);

        return categoria;
    }

    private Usuario getUsuario(String email){
        Usuario usuario = new Usuario(email,new SenhaLimpa("123456"));
        usuarioRepository.save(usuario);

        return usuario;
    }

    private Produto criaProduto(Usuario usuario){
        Produto produto = new ProdutoBuilder().comNome("Produto X")
                                              .comCategoria(getCategoria())
                                              .comCaracteristicas(new NovaCaracteristicaRequest("A","B"))
                                              .comCaracteristicas(new NovaCaracteristicaRequest("D","C"))
                                              .comCaracteristicas(new NovaCaracteristicaRequest("F","E"))
                                              .comDescricao("Descrição Y")
                                              .comQuantidade(10)
                                              .comValor(new BigDecimal("20.0"))
                                              .comUsuario(usuario)
                                              .constroi();
        produtoRepository.save(produto);
        return produto;
    }

    private String getToken(String login, String senha){
        return "Bearer "+tokenService.gerarToken(authManager.authenticate(new NovaAutenticacaoRequest(login,senha).toDadosLogin()));
    }
}