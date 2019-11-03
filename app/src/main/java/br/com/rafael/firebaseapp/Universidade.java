package br.com.rafael.firebaseapp;

import com.google.firebase.firestore.Exclude;

public class Universidade {

    private String descricao;
    private Long anoFundacao;
    @Exclude
    public String id;

    public Universidade() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public boolean equals(Object objetoComparacao) {
        if (this == objetoComparacao)
            return true;
        if (objetoComparacao == null || getClass() != objetoComparacao.getClass())
            return false;

        Universidade that = (Universidade) objetoComparacao;
        return id.equals(that.id);
    }
}
