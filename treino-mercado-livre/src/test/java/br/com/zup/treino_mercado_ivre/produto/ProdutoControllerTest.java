package br.com.zup.treino_mercado_ivre.produto;

import br.com.zup.treino_mercado_ivre.autenticacao.NovaAutenticacaoRequest;
import br.com.zup.treino_mercado_ivre.autenticacao.TokenService;
import br.com.zup.treino_mercado_ivre.categoria.Categoria;
import br.com.zup.treino_mercado_ivre.categoria.CategoriaRepository;
import br.com.zup.treino_mercado_ivre.produto.caracteristica.NovaCaracteristicaRequest;
import br.com.zup.treino_mercado_ivre.usuario.SenhaLimpa;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import br.com.zup.treino_mercado_ivre.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@Transactional
class ProdutoControllerTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void deveAdicionarImagens() throws Exception {
        Produto produto = getProduto();

        mockMvc.perform(multipart("/api/produtos/{id}/imagens",produto.getId())
                            .file(new MockMultipartFile("imagens","teste.png".getBytes()))
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization","Bearer "+getToken("aa@gmail.com","123456")))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void naoDeveAdicionarImagensDeUmProdutoNaoPertenceAoUsuario() throws Exception {
        Produto produto = getProduto();
        Usuario usuario = getUsuario("bb@gmail.com");

        mockMvc.perform(multipart("/api/produtos/{id}/imagens",produto.getId())
                .file(new MockMultipartFile("imagens","teste.png".getBytes()))
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","Bearer "+getToken(usuario.getUsername(),"123456")))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }



    private Categoria getCategoria(){
        Categoria categoria = new Categoria("Categoria 1",null);
        categoriaRepository.save(categoria);

        return categoria;
    }

    private Produto getProduto(){
        Produto produto = new ProdutoBuilder().comNome("Produto")
                                              .comValor(new BigDecimal("26.2"))
                                              .comQuantidade(10)
                                              .comCaracteristicas(new NovaCaracteristicaRequest("A", "B"))
                                              .comCaracteristicas(new NovaCaracteristicaRequest("C", "D"))
                                              .comCaracteristicas(new NovaCaracteristicaRequest("E", "F"))
                                              .comDescricao("Descição")
                                              .comCategoria(getCategoria())
                                              .comUsuario(getUsuario("aa@gmail.com"))
                                              .constroi();
        produtoRepository.save(produto);
        return produto;
    }

    private Usuario getUsuario(String email){
        Usuario usuario = new Usuario(email,new SenhaLimpa("123456"));
        usuarioRepository.save(usuario);

        return usuario;
    }

    private String getToken(String login, String senha){
        return tokenService.gerarToken(authManager.authenticate(new NovaAutenticacaoRequest(login,senha).toDadosLogin()));
    }
}