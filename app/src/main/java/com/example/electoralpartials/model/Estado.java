package com.example.electoralpartials.model;

public class Estado {
    private String idEstado;
    private String nome;
    private Boolean escolhido = false;

    public Boolean getEscolhido() {
        return escolhido;

    }
    public void setEscolhido(Boolean escolhido) {
        this.escolhido = escolhido;

    }
    public String getId() {
        return idEstado;

    }
    public void setId(String id) {
        this.idEstado = id;

    }
    public String getNome() {
        return nome;

    }
    public void setNome(String nome) {
        this.nome = nome;

    }
}
