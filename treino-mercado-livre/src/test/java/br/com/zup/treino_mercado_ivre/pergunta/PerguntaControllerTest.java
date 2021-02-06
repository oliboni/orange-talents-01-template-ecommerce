package br.com.zup.treino_mercado_ivre.pergunta;

import br.com.zup.treino_mercado_ivre.autenticacao.NovaAutenticacaoRequest;
import br.com.zup.treino_mercado_ivre.autenticacao.TokenService;
import br.com.zup.treino_mercado_ivre.categoria.Categoria;
import br.com.zup.treino_mercado_ivre.categoria.CategoriaRepository;
import br.com.zup.treino_mercado_ivre.produto.Produto;
import br.com.zup.treino_mercado_ivre.produto.ProdutoBuilder;
import br.com.zup.treino_mercado_ivre.produto.ProdutoRepository;
import br.com.zup.treino_mercado_ivre.caracteristica.NovaCaracteristicaRequest;
import br.com.zup.treino_mercado_ivre.perguntas.NovaPerguntaRequest;
import br.com.zup.treino_mercado_ivre.perguntas.PerguntaRepository;
import br.com.zup.treino_mercado_ivre.usuario.SenhaLimpa;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import br.com.zup.treino_mercado_ivre.usuario.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class PerguntaControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper jsonMapper;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PerguntaRepository perguntaRepository;
    @Autowired
    TokenService tokenService;

    @Test
    public void deveCriarUmaPergunta() throws Exception {
        var usuario = getUsuario("email@email.com");
        var usuario2 = getUsuario("email2@gmail.com");
        var produto = getProduto(usuario);
        var token = getToken(usuario2.getUsername(),"123456");
        var novaPergunta = new NovaPerguntaRequest("PErgunta A");

        mockMvc.perform(post("/api/produtos/{id}/perguntas",produto.getId())
                        .content(jsonMapper.writeValueAsString(novaPergunta))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token))
            .andDo(print())
            .andExpect(status().isOk());

        assertEquals(1, perguntaRepository.findAll().size());
    }

    private Usuario getUsuario(String email){
        Usuario usuario = new Usuario(email,new SenhaLimpa("123456"));
        usuarioRepository.save(usuario);

        return usuario;
    }

    private Categoria getCategoria(){
        Categoria categoria = new Categoria("A",null);
        categoriaRepository.save(categoria);

        return categoria;
    }

    private Produto getProduto(Usuario usuario){
        Produto produto = new ProdutoBuilder().comCategoria(getCategoria())
                                              .comNome("AB").comDescricao("CD").comQuantidade(20)
                                              .comValor(new BigDecimal("30.0"))
                                              .comCaracteristicas(new NovaCaracteristicaRequest("A","B"))
                                              .comCaracteristicas(new NovaCaracteristicaRequest("C","D"))
                                              .comCaracteristicas(new NovaCaracteristicaRequest("E","F"))
                                              .comUsuario(usuario)
                                              .constroi();
        produtoRepository.save(produto);
        return produto;
    }

    private String getToken(String login, String senha){
        return "Bearer "+ tokenService.gerarToken(authManager.authenticate(new NovaAutenticacaoRequest(login,senha).toDadosLogin()));
    }
}
