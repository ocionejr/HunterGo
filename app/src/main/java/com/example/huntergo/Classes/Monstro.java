package com.example.huntergo.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Monstro implements Parcelable {

     private int ataque;
     private String nome;
     private String tipo;
     private int velocidade;
     private int vida;

    protected Monstro(Parcel in) {
        ataque = in.readInt();
        nome = in.readString();
        tipo = in.readString();
        velocidade = in.readInt();
        vida = in.readInt();
    }

    public Monstro(int ataque, String nome, String tipo, int velocidade, int vida) {
        this.ataque = ataque;
        this.nome = nome;
        this.tipo = tipo;
        this.velocidade = velocidade;
        this.vida = vida;
    }

    public Monstro() {
    }

    public static final Creator<Monstro> CREATOR = new Creator<Monstro>() {
        @Override
        public Monstro createFromParcel(Parcel in) {
            return new Monstro(in);
        }

        @Override
        public Monstro[] newArray(int size) {
            return new Monstro[size];
        }
    };

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
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

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(ataque);
        dest.writeString(nome);
        dest.writeString(tipo);
        dest.writeInt(velocidade);
        dest.writeInt(vida);
    }
}
