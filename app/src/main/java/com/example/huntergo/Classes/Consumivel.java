package com.example.huntergo.Classes;

public class Consumivel {

    private String efeito;
    private String nome;

    public Consumivel(String efeito, String nome) {
        this.efeito = efeito;
        this.nome = nome;
    }

    public String getEfeito() {
        return efeito;
    }

    public void setEfeito(String efeito) {
        this.efeito = efeito;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
