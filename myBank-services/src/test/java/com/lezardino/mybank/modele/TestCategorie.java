package com.lezardino.mybank.modele;

import com.lezardino.mybank.utils.Utils;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class TestCategorie {

    @Test
    public void testCategorie() {
        String libelle = "Divers";
        BigDecimal solde = new BigDecimal(10.50);
        Categorie categorie = new Categorie(libelle, solde);

        assertThat(categorie.getLibelle()).isEqualTo(libelle);
        assertThat(Utils.bigDecimalToString(categorie.getSolde())).isEqualTo("10,50");
        assertThat(categorie.toString()).isEqualTo(
                "Categorie{libelle='Divers', solde='10,50'}");
    }

}
