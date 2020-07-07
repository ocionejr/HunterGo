package com.example.huntergo.Classes;

public class ItensEquipados {

    private String maoDireita;
    private String maoEsquerda;
    private String armadura;

    public ItensEquipados(String maoDireita, String maoEsquerda, String armadura) {
        this.maoDireita = maoDireita;
        this.maoEsquerda = maoEsquerda;
        this.armadura = armadura;
    }

    public ItensEquipados() {
    }

    public String getMaoDireita() {
        return maoDireita;
    }

    public void setMaoDireita(String maoDireita) {
        this.maoDireita = maoDireita;
    }

    public String getMaoEsquerda() {
        return maoEsquerda;
    }

    public void setMaoEsquerda(String maoEsquerda) {
        this.maoEsquerda = maoEsquerda;
    }

    public String getArmadura() {
        return armadura;
    }

    public void setArmadura(String armadura) {
        this.armadura = armadura;
    }
}
