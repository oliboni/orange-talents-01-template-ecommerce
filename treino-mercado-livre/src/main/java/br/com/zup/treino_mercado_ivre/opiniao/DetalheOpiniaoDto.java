package br.com.zup.treino_mercado_ivre.opiniao;

public class DetalheOpiniaoDto {
    private String titulo;
    private String descricao;
    private Integer nota;
    private String emailUsuario;

    public DetalheOpiniaoDto(Opiniao opiniao) {
        this.titulo = opiniao.getTitulo();
        this.descricao = opiniao.getDescricao();
        this.nota = opiniao.getNota();
        this.emailUsuario = opiniao.getUsuario().getUsername();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getNota() {
        return nota;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }
}
