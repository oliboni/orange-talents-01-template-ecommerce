package br.com.zup.treino_mercado_ivre.perguntas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {

//    @Query("Select p.titulo, p.instante_criacao from Pergunta p where p.produto.id = :id")
//    List<ListaPerguntaDto> findByProdutoId(@Param("id") Long id);

    @Query(value = "Select p.titulo from pergunta p where p.produto_id = :id", nativeQuery = true)
    List<ListaPerguntaDto> findByProdutoId(@Param("id") Long id);
}
