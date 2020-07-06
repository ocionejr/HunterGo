package com.example.huntergo.Classes;

public class Armadura {

    private int id;
    private int defesa;
    private String nome;
    private int velocidade;

    public Armadura() {
    }

    public Armadura(int id, int defesa, String nome, int velocidade) {
        this.id = id;
        this.defesa = defesa;
        this.nome = nome;
        this.velocidade = velocidade;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
