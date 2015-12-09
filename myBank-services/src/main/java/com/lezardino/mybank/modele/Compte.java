package com.lezardino.mybank.modele;

import java.math.BigDecimal;

import com.lezardino.mybank.constantes.Constantes;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.lezardino.mybank.utils.Utils;

@Document(collection = Constantes.CollectionsMongo.COMPTE)
public class Compte implements Comparable<Compte> {

    @Id
    private String idCompte;

    private String libelle;

    private String proprietaire;

    private BigDecimal solde;

    public Compte(String libelle, String proprietaire, BigDecimal solde) {
        super();
        this.libelle = libelle;
        this.proprietaire = proprietaire;
        this.solde = solde;
    }

    public Compte() {
    }

    public String getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(String idCompte) {
        this.idCompte = idCompte;
    }

    public BigDecimal getSolde() {
        return solde;
    }

    public void setSolde(BigDecimal solde) {
        this.solde = solde;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getProprietaire() {
        return proprietaire;
    }

    @Override
    public String toString() {
        String toStringSolde = Utils.bigDecimalToString(this.solde);
        return "Compte{" +
                "idCompte='" + idCompte + '\'' +
                ", libelle='" + libelle + '\'' +
                ", proprietaire='" + proprietaire + '\'' +
                ", solde='" + toStringSolde +
                "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Compte compte = (Compte) o;

        if (idCompte != null ? !idCompte.equals(compte.idCompte) : compte.idCompte != null) return false;
        if (libelle != null ? !libelle.equals(compte.libelle) : compte.libelle != null) return false;
        if (proprietaire != null ? !proprietaire.equals(compte.proprietaire) : compte.proprietaire != null)
            return false;
        return !(solde != null ? !solde.equals(compte.solde) : compte.solde != null);

    }

    /**
     *  On rend l'object compte comparable par son identifiant
     */
    @Override
    public int compareTo(Compte compte) {
        return this.idCompte.compareTo(compte.getIdCompte());
    }
}
