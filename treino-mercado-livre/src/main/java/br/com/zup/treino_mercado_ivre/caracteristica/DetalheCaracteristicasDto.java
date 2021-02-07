package br.com.zup.treino_mercado_ivre.caracteristica;

public class DetalheCaracteristicasDto {
    private String nome;
    private String valor;

    public DetalheCaracteristicasDto(Caracteristica caracteristica) {
        this.nome = caracteristica.getNome();
        this.valor = caracteristica.getValor();
    }

    public String getNome() {
        return nome;
    }

    public String getValor() {
        return valor;
    }
}
