package com.example.huntergo.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemInventario  {

    private int id;
    private int quantidade;
    private String tipo;
    private int image;

    public ItemInventario(int id, int quantidade, String tipo, int image) {
        this.id = id;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.image = image;
    }


    protected ItemInventario(Parcel in) {
        id = in.readInt();
        quantidade = in.readInt();
        tipo = in.readString();
        image = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }


}
