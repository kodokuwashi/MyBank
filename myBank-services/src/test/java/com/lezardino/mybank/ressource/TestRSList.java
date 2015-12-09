package com.lezardino.mybank.ressource;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.lezardino.mybank.modele.Compte;

public class TestRSList {

    @Test
    public void testRSList() {
        /** Masque de l'URL */
        String URI_PATTERN = "/comptes?offset={0}&limit={1}&direction={2}";

        Compte compte1 = new Compte("compte1", "testeur", new BigDecimal("10.50"));
        Compte compte2 = new Compte("compte2", "testeur", new BigDecimal("8.22"));

        List<Compte> listeCompte = new ArrayList<>();
        listeCompte.add(compte1);
        listeCompte.add(compte2);

        Long resultCount = 6L;
        RSList<Compte> rsListCompte = new RSList<>(URI_PATTERN, resultCount, listeCompte);

        assertThat(rsListCompte.getItems().size()).isEqualTo(2);
        assertThat(rsListCompte.getResultsCount()).isEqualTo(6L);

        assertThat(rsListCompte.toString())
        .isEqualTo(
                "RSList{resultsCount=6, items=[Compte{idCompte='null', libelle='compte1', proprietaire='testeur', solde='10,50'}, Compte{idCompte='null', libelle='compte2', proprietaire='testeur', solde='8,22'}]} RSLink{uriPattern='/comptes?offset={0}&limit={1}&direction={2}', links=null}");
    }

}
