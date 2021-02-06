package br.com.zup.treino_mercado_ivre.email;

import br.com.zup.treino_mercado_ivre.categoria.Categoria;
import br.com.zup.treino_mercado_ivre.categoria.CategoriaRepository;
import br.com.zup.treino_mercado_ivre.produto.Produto;
import br.com.zup.treino_mercado_ivre.produto.ProdutoBuilder;
import br.com.zup.treino_mercado_ivre.produto.ProdutoRepository;
import br.com.zup.treino_mercado_ivre.caracteristica.NovaCaracteristicaRequest;
import br.com.zup.treino_mercado_ivre.usuario.SenhaLimpa;
import br.com.zup.treino_mercado_ivre.usuario.Usuario;
import br.com.zup.treino_mercado_ivre.usuario.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class EnviaEmailTest {

    @Autowired
    private EnviaEmail enviaEmail;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    public void DeveEnviarEmail(){
//        enviaEmail.sendEmail(criaProduto(getUsuario("oliboni@unochapeco.edu.br")));
    }

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

}