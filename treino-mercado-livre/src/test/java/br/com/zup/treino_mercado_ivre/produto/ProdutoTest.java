package br.com.zup.treino_mercado_ivre.produto;

import br.com.zup.treino_mercado_ivre.categoria.Categoria;
import br.com.zup.treino_mercado_ivre.produto.caracteristica.NovaCaracteristicaRequest;
import br.com.zup.treino_mercado_ivre.usuario.SenhaLimpa;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {
    private static Categoria categoria;
    private static Usuario usuario;


    @BeforeAll
    private static void beforeAll(){
        categoria = new Categoria("categoria", null);
        usuario = new Usuario("email@email.com",new SenhaLimpa("123456"));
    }

    @DisplayName("um produto precisa de no mínimo 3 caracteristiscas")
    @ParameterizedTest
    @MethodSource("geradorTeste1")
    void test1(Collection<NovaCaracteristicaRequest> caracteristicas) throws Exception{
        Produto produto = new Produto("teste",BigDecimal.TEN,2,caracteristicas,"Descrição de teste",categoria,usuario);
    }

    static Stream<Arguments> geradorTeste1(){
        return Stream.of(
                Arguments.of(
                        List.of(new NovaCaracteristicaRequest("A", "B"),
                                new NovaCaracteristicaRequest("C", "D"),
                                new NovaCaracteristicaRequest("E", "F")
                        )
                ),
                Arguments.of(
                        List.of(new NovaCaracteristicaRequest("A", "B"),
                                new NovaCaracteristicaRequest("C", "D"),
                                new NovaCaracteristicaRequest("E", "F"),
                                new NovaCaracteristicaRequest("F", "H")
                        )
                )
        );
    }

    @DisplayName("um produto precisa de no mínimo 3 caracteristiscas")
    @ParameterizedTest
    @MethodSource("geradorTeste2")
    void test2(Collection<NovaCaracteristicaRequest> caracteristicas) throws Exception{
        Assertions.assertThrows(IllegalArgumentException.class,()->{
            Produto produto = new Produto("teste",BigDecimal.TEN,2,caracteristicas,"Descrição de teste",categoria,usuario);
        });
    }

    static Stream<Arguments> geradorTeste2(){
        return Stream.of(
                Arguments.of(
                        List.of(new NovaCaracteristicaRequest("A", "B"),
                                new NovaCaracteristicaRequest("C", "D")
                        )
                ),
                Arguments.of(
                        List.of(new NovaCaracteristicaRequest("A", "B")
                        )
                )
        );
    }

    @DisplayName("O produto tem que ser do usuário logado")
    @ParameterizedTest
    @MethodSource("geradorTeste3")
    void teste3(Collection<NovaCaracteristicaRequest> caracteristicas) throws Exception{
        Produto produto = new Produto("teste",BigDecimal.TEN,2,caracteristicas,"Descrição de teste",categoria,usuario);
        produto.produtoPertenceUsuario(usuario);
    }
    static Stream<Arguments> geradorTeste3(){
        return Stream.of(
                Arguments.of(
                        List.of(new NovaCaracteristicaRequest("A", "B"),
                                new NovaCaracteristicaRequest("C", "D"),
                                new NovaCaracteristicaRequest("E", "F")
                        )
                )
        );
    }

    @DisplayName("O produto não é do usuário logado")
    @ParameterizedTest
    @MethodSource("geradorTeste4")
    void teste4(Collection<NovaCaracteristicaRequest> caracteristicas) throws Exception{
        Usuario usuarioErrado = new Usuario("teste@teste.com",new SenhaLimpa("123456"));

        Produto produto = new Produto("teste",BigDecimal.TEN,2,caracteristicas,"Descrição de teste",categoria,usuario);
        assertEquals(false,produto.produtoPertenceUsuario(usuarioErrado));
    }
    static Stream<Arguments> geradorTeste4(){
        return Stream.of(
                Arguments.of(
                        List.of(new NovaCaracteristicaRequest("A", "B"),
                                new NovaCaracteristicaRequest("C", "D"),
                                new NovaCaracteristicaRequest("E", "F")
                        )
                )
        );
    }

}