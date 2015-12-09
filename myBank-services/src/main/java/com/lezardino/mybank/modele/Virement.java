package com.lezardino.mybank.modele;

import com.lezardino.mybank.constantes.Constantes;
import com.lezardino.mybank.enumeration.EtatVirement;
import com.lezardino.mybank.utils.Utils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = Constantes.CollectionsMongo.VIREMENT)
public class Virement {

    @Id
    private String idVirement;

    private Compte compteDebiteur;

    private Compte compteCrediteur;

    private BigDecimal montant;

    private EtatVirement etat;

    public Virement(Compte compteDebiteur, Compte compteCrediteur, BigDecimal montant) {
        this.compteDebiteur = compteDebiteur;
        this.compteCrediteur = compteCrediteur;
        this.montant = montant;
        this.etat = EtatVirement.CREE;
    }

    public Virement() {
    }

    public String getIdVirement() {
        return idVirement;
    }

    public void setIdVirement(String idVirement) {
        this.idVirement = idVirement;
    }

    public Compte getCompteDebiteur() {
        return compteDebiteur;
    }

    public void setCompteDebiteur(Compte compteDebiteur) {
        this.compteDebiteur = compteDebiteur;
    }

    public Compte getCompteCrediteur() {
        return compteCrediteur;
    }

    public void setCompteCrediteur(Compte compteCrediteur) {
        this.compteCrediteur = compteCrediteur;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public EtatVirement getEtat() {
        return etat;
    }

    public void setEtat(EtatVirement etat) {
        this.etat = etat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Virement virement = (Virement) o;

        if (idVirement != null ? !idVirement.equals(virement.idVirement) : virement.idVirement != null) return false;
        if (compteDebiteur != null ? !compteDebiteur.equals(virement.compteDebiteur) : virement.compteDebiteur != null)
            return false;
        if (compteCrediteur != null ? !compteCrediteur.equals(virement.compteCrediteur) : virement.compteCrediteur != null)
            return false;
        if (montant != null ? !montant.equals(virement.montant) : virement.montant != null) return false;
        return etat == virement.etat;

    }

    @Override
    public String toString() {
        String toStringMontant = Utils.bigDecimalToString(this.montant);
        return "Virement{" +
                "idVirement='" + idVirement + '\'' +
                ", compteDebiteur=" + compteDebiteur +
                ", compteCrediteur=" + compteCrediteur +
                ", montant='" + toStringMontant +
                "', etat=" + etat +
                '}';
    }
}
