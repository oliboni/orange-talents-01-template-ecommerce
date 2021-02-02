package br.com.zup.treino_mercado_ivre.usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SenhaLimpa {
    @NotBlank
    private String senha;

    public SenhaLimpa(@NotBlank @Size(min = 6) String senha) {
        Assert.hasLength(senha,"Senha náo pode ser em branco");
        Assert.isTrue(senha.length() >= 6,"Senha deve conter no mínimo 6 caracteres");
        this.senha = senha;
    }

    public String hash(){
        return new BCryptPasswordEncoder().encode(senha);
    }
}
