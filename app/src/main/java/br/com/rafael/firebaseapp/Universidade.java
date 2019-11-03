package br.com.rafael.firebaseapp;

public class Universidade {

    private String descricao;
    private Long anoFundacao;

    public Universidade() {
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getAnoFundacao() {
        return anoFundacao;
    }

    public void setAnoFundacao(Long anoFundacao) {
        this.anoFundacao = anoFundacao;
    }
}
