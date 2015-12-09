package com.lezardino.mybank.modele;

import com.lezardino.mybank.constantes.Constantes;
import com.lezardino.mybank.utils.Utils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

@Document(collection = Constantes.CollectionsMongo.BUDJET)
public class Budjet {

    @Id
    private String libelle;

    private TreeMap<String, BigDecimal> soldeParCompte;

    public Budjet(String libelle, TreeMap<String, BigDecimal> soldeParCompte) {
        this.libelle = libelle;
        this.soldeParCompte = soldeParCompte;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public TreeMap<String, BigDecimal> getSoldeParCompte() {
        return soldeParCompte;
    }

    public void setSoldeParCompte(TreeMap<String, BigDecimal> soldeParCompte) {
        this.soldeParCompte = soldeParCompte;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Budjet{libelle='");
        stringBuilder.append(libelle);
        stringBuilder.append("', soldeParCompte=[");
        Iterator<Entry<String, BigDecimal>> soldeParCompteIterator = soldeParCompte.entrySet().iterator();
        while(soldeParCompteIterator.hasNext()) {
            Entry<String, BigDecimal> entry = soldeParCompteIterator.next();
            stringBuilder.append("{idCompte='");
            stringBuilder.append(entry.getKey());
            stringBuilder.append("':solde='");
            stringBuilder.append(Utils.bigDecimalToString(entry.getValue()));
            stringBuilder.append("'}");
            if (soldeParCompteIterator.hasNext()) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append("]}");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Budjet budjet = (Budjet) o;

        if (libelle != null ? !libelle.equals(budjet.libelle) : budjet.libelle != null) return false;
        return !(soldeParCompte != null ? !soldeParCompte.equals(budjet.soldeParCompte) : budjet.soldeParCompte != null);

    }
}
