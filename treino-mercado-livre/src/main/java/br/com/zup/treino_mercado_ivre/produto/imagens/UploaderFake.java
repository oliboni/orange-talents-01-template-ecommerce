package br.com.zup.treino_mercado_ivre.produto.imagens;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Primary
public class UploaderFake implements Uploader{
    /**
     * @Param imagens
     * @Return lista com links do uploader das imagens
    */
    @Override
    public Set<String> envia(List<MultipartFile> imagens) {
        return imagens.stream()
                      .map(imagem-> "https://aws.io/"+imagem.getOriginalFilename())
                      .collect(Collectors.toSet());
    }
}
