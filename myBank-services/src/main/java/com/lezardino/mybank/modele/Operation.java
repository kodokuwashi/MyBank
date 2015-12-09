package com.lezardino.mybank.modele;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.lezardino.mybank.constantes.Constantes;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.lezardino.mybank.enumeration.EtatOperation;
import com.lezardino.mybank.enumeration.TypeOperation;
import com.lezardino.mybank.utils.Utils;

@Document(collection = Constantes.CollectionsMongo.OPERATION)
public class Operation {

    @Id
    private String idOperation;

    @DBRef
    private final Compte compte;

    @DBRef
    private Categorie categorie;

    private final BigDecimal montant;

    private final String libelle;

    private final TypeOperation typeOperation;

    private final LocalDate date;

    private EtatOperation etatOperation;

    public String getIdOperation() {
        return idOperation;
    }

    public void setIdOperation(String idOperation) {
        this.idOperation = idOperation;
    }

    public Compte getCompte() {
        return compte;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public String getLibelle() {
        return libelle;
    }

    public TypeOperation getTypeOperation() {
        return typeOperation;
    }

    public LocalDate getDate() {
        return date;
    }

    public EtatOperation getEtatOperation() {
        return etatOperation;
    }

    public void setEtatOperation(EtatOperation etatOperation) {
        this.etatOperation = etatOperation;
    }

    public Operation(Compte compte, Categorie categorie, BigDecimal montant, String libelle, TypeOperation typeOperation, LocalDate date) {
        this.compte = compte;
        this.categorie = categorie;
        this.montant = montant;
        this.libelle = libelle;
        this.typeOperation = typeOperation;
        this.date = date;
        this.etatOperation = EtatOperation.ATTENTE_PRELEVEMENT;
    }

    @Override
    public String toString() {
        String toStringMontant = Utils.bigDecimalToString(this.montant);
        return "Operation{" +
                "idOperation='" + idOperation + '\'' +
                ", compte=" + compte +
                ", categorie=" + categorie +
                ", montant='" + toStringMontant +
                "', libelle='" + libelle + '\'' +
                ", typeOperation=" + typeOperation +
                ", date=" + date +
                ", etatOperation=" + etatOperation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        if (idOperation != null ? !idOperation.equals(operation.idOperation) : operation.idOperation != null)
            return false;
        if (compte != null ? !compte.equals(operation.compte) : operation.compte != null) return false;
        if (categorie != null ? !categorie.equals(operation.categorie) : operation.categorie != null) return false;
        if (montant != null ? !montant.equals(operation.montant) : operation.montant != null) return false;
        if (libelle != null ? !libelle.equals(operation.libelle) : operation.libelle != null) return false;
        if (typeOperation != operation.typeOperation) return false;
        if (date != null ? !date.equals(operation.date) : operation.date != null) return false;
        return etatOperation == operation.etatOperation;

    }
}
