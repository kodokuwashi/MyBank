package com.lezardino.mybank.modele;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;

import com.lezardino.mybank.enumeration.EtatOperation;
import com.lezardino.mybank.enumeration.TypeOperation;

public class TestOperation {

    @Test
    public void testDebit() {
        String nom = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte = new Compte(nom, proprietaire, solde);
        compte.setIdCompte("0");

        Categorie categorie = new Categorie("course", new BigDecimal(0));

        BigDecimal montant = new BigDecimal(5.25);
        String libelle = "testDebit";

        LocalDate date = LocalDate.of(2015, 01, 01);
        Operation debit = new Operation(compte, categorie, montant, libelle, TypeOperation.DEBIT, date);
        debit.setIdOperation("0");

        assertThat(debit.getCompte().getIdCompte()).isEqualTo("0");
        assertThat(debit.getCategorie().getLibelle()).isEqualTo("course");
        assertThat(debit.getLibelle()).isEqualTo("testDebit");
        assertThat(debit.getMontant()).isEqualTo(new BigDecimal(5.25));
        assertThat(debit.getTypeOperation()).isEqualTo(TypeOperation.DEBIT);
        assertThat(debit.getDate()).isEqualTo(LocalDate.of(2015, 01, 01));
        assertThat(debit.getEtatOperation()).isEqualTo(EtatOperation.ATTENTE_PRELEVEMENT);
        assertThat(debit.toString())
        .isEqualTo(
                "Operation{idOperation='0', compte=Compte{idCompte='0', libelle='LivretA', proprietaire='Pierre', solde='10,50'}"
                        + ", categorie=Categorie{libelle='course', solde='0,00'}"
                        + ", montant='5,25', libelle='testDebit', typeOperation=DEBIT, date=2015-01-01, etatOperation=ATTENTE_PRELEVEMENT}");
    }

    @Test
    public void testCredit() {
        String nom = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte = new Compte(nom, proprietaire, solde);
        compte.setIdCompte("0");

        Categorie categorie = new Categorie("course", new BigDecimal(0));

        BigDecimal montant = new BigDecimal(5.25);
        String libelle = "testDebit";

        LocalDate date = LocalDate.of(2015, 01, 01);
        Operation credit = new Operation(compte, categorie, montant, libelle, TypeOperation.CREDIT, date);
        credit.setEtatOperation(EtatOperation.PRELEVE);
        credit.setIdOperation("0");

        assertThat(credit.getIdOperation()).isEqualTo("0");
        assertThat(credit.getCompte().getIdCompte()).isEqualTo("0");
        assertThat(credit.getCategorie().getLibelle()).isEqualTo("course");
        assertThat(credit.getLibelle()).isEqualTo("testDebit");
        assertThat(credit.getMontant()).isEqualTo(new BigDecimal(5.25));
        assertThat(credit.getTypeOperation()).isEqualTo(TypeOperation.CREDIT);
        assertThat(credit.getDate()).isEqualTo(LocalDate.of(2015, 01, 01));
        assertThat(credit.getEtatOperation()).isEqualTo(EtatOperation.PRELEVE);
        assertThat(credit.toString())
        .isEqualTo(
                "Operation{idOperation='0', compte=Compte{idCompte='0', libelle='LivretA', proprietaire='Pierre', solde='10,50'}"
                        + ", categorie=Categorie{libelle='course', solde='0,00'}"
                        + ", montant='5,25', libelle='testDebit', typeOperation=CREDIT, date=2015-01-01, etatOperation=PRELEVE}");
    }

}
