package com.lezardino.mybank.constantes;

public class Constantes {

    /**
     * Liste des collections en base Mongo
     *
     */
    public static class CollectionsMongo {
        public static final String BUDJET = "budjet";
        public static final String CATEGORIE = "categorie";
        public static final String COMPTE = "compte";
        public static final String OPERATION = "operation";
        public static final String VIREMENT = "virement";
    }

    public static class ChampCollectionBudjet {
        public static final String IDBUDJET = "idBudjet";
        public static final String LIBELLE = "libelle";
        public static final String COMPTE = "compte";
        public static final String SOLDE = "solde";
    }


    public static class ChampCollectionCategorie {
        public static final String LIBELLE = "libelle";
        public static final String SOLDE = "solde";
    }

    public static class ChampCollectionCompte {
        public static final String IDCOMPTE = "idCompte";
        public static final String LIBELLE = "libelle";
        public static final String PROPRIETAIRE = "proprietaire";
        public static final String SOLDE = "solde";

    }

    public static class ChampCollectionOperation {
        public static final String IDOPERATION = "idOperation";
        public static final String COMPTE = "compte";
        public static final String CATEGORIE = "categorie";
        public static final String MONTANT = "montant";
        public static final String LIBELLE = "libelle";
        public static final String TYPEOPERATION = "typeOperation";
        public static final String DATE = "date";
        public static final String ETATOPERATION = "etatOperation";
    }

    public static class ChampCollectionVirement {
        public static final String IDVIREMENT = "idVirement";
        public static final String COMPTEDEBITEUR = "compteDebiteur";
        public static final String COMPTECREDITEUR = "compteCrediteur";
        public static final String MONTANT = "montant";
        public static final String ETAT = "etat";
    }
}
