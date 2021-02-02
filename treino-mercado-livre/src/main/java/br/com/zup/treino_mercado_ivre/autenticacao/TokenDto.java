package br.com.zup.treino_mercado_ivre.autenticacao;

public class TokenDto {

    private String token;
    private String tipo;

    public TokenDto(String   token, String tipo) {
        this.token = token;
        this.tipo = tipo;
    }

    @Deprecated
    public TokenDto() {
    }

    public String getToken() {
        return token;
    }

    public String getTipo() {
        return tipo;
    }
}
