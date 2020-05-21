package com.example.huntergo.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemInventario implements Parcelable {

    private String id;
    private int quantidade;
    private String tipo;
    private int image;

    public ItemInventario(String id, int quantidade, String tipo, int image) {
        this.id = id;
        this.quantidade = quantidade;
        this.tipo = tipo;
        this.image = image;
    }


    protected ItemInventario(Parcel in) {
        id = in.readString();
        quantidade = in.readInt();
        tipo = in.readString();
        image = in.readInt();
    }

    public static final Creator<ItemInventario> CREATOR = new Creator<ItemInventario>() {
        @Override
        public ItemInventario createFromParcel(Parcel in) {
            return new ItemInventario(in);
        }

        @Override
        public ItemInventario[] newArray(int size) {
            return new ItemInventario[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeInt(quantidade);
        dest.writeString(tipo);
        dest.writeInt(image);
    }
}
