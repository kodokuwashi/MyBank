package com.lezardino.mybank.modele;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;

public class TestCompte {

    @Test
    public void testCompte() {
        String libelle = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte = new Compte(libelle, proprietaire, solde);
        compte.setIdCompte("0");
        assertThat(compte.getIdCompte()).isEqualTo("0");
        assertThat(compte.getLibelle()).isEqualTo(libelle);
        assertThat(compte.getProprietaire()).isEqualTo(proprietaire);
        assertThat(compte.getSolde()).isEqualTo(solde);
        assertThat(compte.toString()).isEqualTo(
                "Compte{idCompte='0', libelle='LivretA', proprietaire='Pierre', solde='10,50'}");
    }

}
