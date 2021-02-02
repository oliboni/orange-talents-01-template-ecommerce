package br.com.zup.treino_mercado_ivre.categoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid NovaCategoriaRequest request){
        Categoria categoriaMae = null;

        if (request.getIdCategoriaMae() != null){
            categoriaMae = categoriaRepository.findById(request.getIdCategoriaMae()).get();
        }

        Categoria novaCategoria = request.toCategoria(categoriaMae);

        categoriaRepository.save(novaCategoria);
        System.out.println(novaCategoria);
        return ResponseEntity.ok().build();
    }
}
