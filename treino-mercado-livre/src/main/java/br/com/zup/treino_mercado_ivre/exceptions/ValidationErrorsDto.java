package br.com.zup.treino_mercado_ivre.exceptions;

public class ValidationErrorsDto {
    private String campo;
    private String erro;

    public ValidationErrorsDto(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
