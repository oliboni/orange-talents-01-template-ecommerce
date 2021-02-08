package br.com.zup.treino_mercado_ivre.compra;

public enum GatewayPagamento {
    PAGSEGURO("pagseguro.com?returnid="),
    PAYPAL("paypal.com/");

    private String url;

    GatewayPagamento(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlRetorno(Long idCompra, String urlRetornoPagamento){
        return this.url + idCompra + getTypeRedirect() + urlRetornoPagamento;
    }

    private String getTypeRedirect(){
        if (PAYPAL.equals(this)){
            return "?redirectUrl=";
        } else {
            return "&redirectUrl=";
        }
    }

}
