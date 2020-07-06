package com.example.huntergo.Classes;

public class Consumivel {

    private int id;
    private String efeito;
    private String nome;

    public Consumivel() {
    }

    public Consumivel(int id, String efeito, String nome) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
