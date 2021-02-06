package br.com.zup.treino_mercado_ivre.imagens;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class NovasImagensRequest {
    @Size(min = 1)
    @NotNull
    private List<MultipartFile> imagens = new ArrayList<>();

    public NovasImagensRequest(@Size(min = 1) @NotNull List<MultipartFile> imagens) {
        this.imagens = imagens;
    }

    public List<MultipartFile> getImagens() {
        return imagens;
    }

}
