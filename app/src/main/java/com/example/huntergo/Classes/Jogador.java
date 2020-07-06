package com.example.huntergo.Classes;

public class Jogador {

     private String classe;
     private int vida;
     private int mana;
     private int ataque;
     private int defesa;
     private int velocidade;
     private int podermagico;

    public Jogador(String classe, int vida, int mana, int ataque, int defesa, int velocidade, int podermagico) {
        this.classe = classe;
        this.vida = vida;
        this.mana = mana;
        this.ataque = ataque;
        this.defesa = defesa;
        this.velocidade = velocidade;
        this.podermagico = podermagico;
    }

    public Jogador() {
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefesa() {
        return defesa;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }

    public int getVelocidade() {
        return velocidade;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }

    public int getPodermagico() {
        return podermagico;
    }

    public void setPodermagico(int podermagico) {
        this.podermagico = podermagico;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
}
