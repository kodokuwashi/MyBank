package com.lezardino.mybank.modele;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class TestVirement {

    @Test
    public void testVirement() {
        String nom = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte = new Compte(nom, proprietaire, solde);
        compte.setIdCompte("0");

        String nom2 = "CCP";
        BigDecimal solde2 = new BigDecimal(125.50);
        Compte compte2 = new Compte(nom2, proprietaire, solde2);
        compte2.setIdCompte("1");

        BigDecimal montant = new BigDecimal(25.50);
        Virement virement = new Virement(compte2, compte, montant);
        virement.setIdVirement("2");

        assertThat(virement).isNotNull();
        assertThat(virement.getIdVirement()).isEqualTo("2");
        assertThat(virement.getCompteDebiteur()).isNotNull();
        assertThat(virement.getCompteDebiteur().getIdCompte()).isEqualTo("1");
        assertThat(virement.getCompteCrediteur()).isNotNull();
        assertThat(virement.getCompteCrediteur().getIdCompte()).isEqualTo("0");
        assertThat(virement.getMontant()).isEqualTo(new BigDecimal(25.50));

        assertThat(virement.toString())
                .isEqualTo(
                        "Virement{idVirement='2', compteDebiteur=Compte{idCompte='1', libelle='CCP', proprietaire='Pierre', solde='125,50'}"
                                + ", compteCrediteur=Compte{idCompte='0', libelle='LivretA', proprietaire='Pierre', solde='10,50'}"
                                + ", montant='25,50', etat=CREE}");
    }
}
