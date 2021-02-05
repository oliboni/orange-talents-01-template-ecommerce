package br.com.zup.treino_mercado_ivre.autenticacao;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.validation.constraints.NotBlank;

public class NovaAutenticacaoRequest {

    @NotBlank
    private String login;
    @NotBlank
    private String senha;

    public NovaAutenticacaoRequest(@NotBlank String login, @NotBlank String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public UsernamePasswordAuthenticationToken toDadosLogin() {
        return new UsernamePasswordAuthenticationToken(login,senha);
    }
}
