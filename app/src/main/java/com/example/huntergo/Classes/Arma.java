package com.example.huntergo.Classes;

public class Arma {

    private int id;
    private int dano;
    private int mao;
    private String nome;
    private String tipo;
    private int velocidade;

    public Arma() {
    }

    public Arma(int id, int dano, int mao, String nome, String tipo, int velocidade) {
        this.id = id;
        this.dano = dano;
        this.mao = mao;
        this.nome = nome;
        this.tipo = tipo;
        this.velocidade = velocidade;
    }

    public int getDano() {
        return dano;
    }

    public void setDano(int dano) {
        this.dano = dano;
    }

    public int getMao() {
        return mao;
    }

    public void setMao(int mao) {
        this.mao = mao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
