package br.com.zup.treino_mercado_ivre.opiniao;

import br.com.zup.treino_mercado_ivre.categoria.Categoria;
import br.com.zup.treino_mercado_ivre.produto.Produto;
import br.com.zup.treino_mercado_ivre.produto.ProdutoBuilder;
import br.com.zup.treino_mercado_ivre.produto.caracteristica.NovaCaracteristicaRequest;
import br.com.zup.treino_mercado_ivre.produto.opiniao.Opiniao;
import br.com.zup.treino_mercado_ivre.usuario.SenhaLimpa;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import java.math.BigDecimal;

public class OpiniaoTest {
    private  Usuario usuario;
    private  Produto produto;

    @BeforeEach
    private  void beforeEach(){
        this.usuario = new Usuario("email@email.com",new SenhaLimpa("123456"));
        this.produto = new ProdutoBuilder().comNome("Produto X")
                                           .comCategoria(new Categoria("teste",null))
                                           .comCaracteristicas(new NovaCaracteristicaRequest("A","B"))
                                           .comCaracteristicas(new NovaCaracteristicaRequest("D","C"))
                                           .comCaracteristicas(new NovaCaracteristicaRequest("F","E"))
                                           .comDescricao("Descrição Y")
                                           .comQuantidade(10)
                                           .comValor(new BigDecimal("20.0"))
                                           .comUsuario(usuario)
                                           .constroi();
    }

    @Test
    public void naoDeveCriarUmaOpiniaoComNota6(){
        assertThrows(IllegalArgumentException.class, ()->{
            Opiniao opiniao = new Opiniao("Titulo","Descicao",6,usuario,produto);
        });
    }
    @Test
    public void naoDeveCriarUmaOpiniaoComNotaNegativa(){
        assertThrows(IllegalArgumentException.class,
            ()->{ Opiniao opiniao = new Opiniao("Titulo","Descicao",-1,usuario,produto);
        });
    }

    @Test
    public void naoDeveCriarUmaOpiniaoComUsuarioDonoDoProduto(){
        assertThrows(IllegalArgumentException.class,
                ()->{ Opiniao opiniao = new Opiniao("Titulo","Descicao",5,usuario,produto);
        });
    }

    @Test
    public void deveCriarUmaOpiniao(){
        Usuario usuarioOpiniao = new Usuario("teste@gmail.com",new SenhaLimpa("123456"));
        Opiniao opiniao = new Opiniao("Titulo","Descicao",5,usuarioOpiniao,produto);

        assertAll(()-> assertEquals("Titulo",opiniao.getTitulo()),
                  ()-> assertEquals(usuarioOpiniao.getUsername(),opiniao.getUsuario().getUsername()));
    }
}
