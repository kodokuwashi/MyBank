package com.lezardino.mybank.enumeration;

public enum EtatOperation {
    ATTENTE_PRELEVEMENT("ATTPRE"), PRELEVE("PRE");

    private final String nom;

    EtatOperation(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }
}
