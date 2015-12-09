package com.lezardino.mybank.enumeration;

public enum EtatVirement {
    CREE("CREE"), EFFECTUEE("EFFECTUEE");

    private final String nom;

    EtatVirement(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }
}
