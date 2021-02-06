package br.com.zup.treino_mercado_ivre.produto;

import br.com.zup.treino_mercado_ivre.caracteristica.NovaCaracteristicaRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class NovoProdutoRequestTest {

    @DisplayName("Nao encontra mais de uma caracteristica igual")
    @ParameterizedTest
    @MethodSource("geradorTest1")
    void temCaracteristicasIguais(boolean espera, List<NovaCaracteristicaRequest> caracteristicas) {
        NovoProdutoRequest novoProdutoRequest = new NovoProdutoRequest("nome", BigDecimal.TEN, 1,caracteristicas,"Descrição",Long.parseLong("1"));
        assertEquals(espera,novoProdutoRequest.temCaracteristicasIguais());
    }
    static Stream<Arguments> geradorTest1(){
        return Stream.of(
                    Arguments.of(false,List.of()),
                    Arguments.of(false,List.of(new NovaCaracteristicaRequest("A", "B"))),
                    Arguments.of(false,List.of(new NovaCaracteristicaRequest("A", "B")),
                                               new NovaCaracteristicaRequest("C", "D")),
                    Arguments.of(true,List.of(new NovaCaracteristicaRequest("A", "B"),
                                              new NovaCaracteristicaRequest("A", "B")))
        );
    }
}