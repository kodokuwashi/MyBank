package com.lezardino.mybank.modele;

import com.lezardino.mybank.utils.Utils;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Map.Entry;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

public class TestBudjet {

    @Test
    public void testBudjet() {
        String libelleBudjet = "unBudjet";
        TreeMap<String, BigDecimal> soldeParCompteAttendu = new TreeMap<>();
        soldeParCompteAttendu.put("123", new BigDecimal(5.25));
        soldeParCompteAttendu.put("456", new BigDecimal(7.55));
        Budjet budjet = new Budjet(libelleBudjet, soldeParCompteAttendu);

        assertThat(budjet).isNotNull();
        assertThat(budjet.getLibelle()).isEqualTo(libelleBudjet);
        TreeMap<String, BigDecimal> soldeParCompte = budjet.getSoldeParCompte();
        assertThat(soldeParCompte).isNotNull();
        assertThat(soldeParCompte.size()).isEqualTo(2);
        assertThat(soldeParCompte.get("123")).isEqualTo(new BigDecimal(5.25));
        assertThat(soldeParCompte.get("456")).isEqualTo(new BigDecimal(7.55));
        assertThat(budjet.toString()).isEqualTo(
                "Budjet{libelle='unBudjet'"
                + ", soldeParCompte=[{idCompte='123':solde='5,25'}, {idCompte='456':solde='7,55'}]}");
    }

}
