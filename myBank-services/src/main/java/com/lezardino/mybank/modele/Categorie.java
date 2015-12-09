package com.lezardino.mybank.modele;

import com.lezardino.mybank.constantes.Constantes;
import com.lezardino.mybank.utils.Utils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = Constantes.CollectionsMongo.CATEGORIE)
public class Categorie {

    @Id
    private String libelle;

    private BigDecimal solde;

    public Categorie(String libelle, BigDecimal solde) {
        this.libelle = libelle;
        this.solde = solde;
    }

    public Categorie() {
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    @Override
    public String toString() {
        String toStringSolde = Utils.bigDecimalToString(this.solde);
        return "Categorie{" +
                "libelle='" + libelle + '\'' +
                ", solde='" + toStringSolde +
                "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Categorie categorie = (Categorie) o;

        if (libelle != null ? !libelle.equals(categorie.libelle) : categorie.libelle != null) return false;
        return !(solde != null ? !solde.equals(categorie.solde) : categorie.solde != null);

    }
}
