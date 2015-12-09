package com.lezardino.mybank.enumeration;

public enum TypeRSLink {
    LIEN_SELF("self"), LIEN_NEXT("next"), LIEN_PREVIOUS("previous");

    private final String nom;

    TypeRSLink(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }
}
