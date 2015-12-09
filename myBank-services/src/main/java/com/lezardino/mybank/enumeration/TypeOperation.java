package com.lezardino.mybank.enumeration;

public enum TypeOperation {
    CREDIT("CREDIT"), DEBIT("DEBIT");

    private final String nom;

    TypeOperation(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return this.nom;
    }
}
