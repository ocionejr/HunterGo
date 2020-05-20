package com.example.huntergo.Classes;

public class ItemInventario {

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
}
