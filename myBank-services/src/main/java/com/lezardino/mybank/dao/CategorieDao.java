package com.lezardino.mybank.dao;

import com.lezardino.mybank.modele.Categorie;
import com.lezardino.mybank.modele.Compte;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

/**
 * Interface d'accès aux categorie stockés en base
 */
public interface CategorieDao {

    /**
     * Enregistre en base une nouvell categorie
     *
     * @param categorie à enregistrer
     */
    void enregistrerCategorie(final Categorie categorie);

    /**
     * Récupère une categorie en base par son libelle unique
     *
     * @param libelle : libelle de la categorie
     * @return categorie à récupérer
     */
    Categorie recupererCategorie(final String libelle);

    /**
     * Supprimer une categorie de la base
     *
     * @param categorie à supprimer
     */
    void supprimerCategorie(final Categorie categorie);

    /**
     * Récupère le nombre de categorie enregistré
     *
     * @return nombre de Categorie
     */
    Long nombreCategories();

    /**
     * Récupère la liste des categories
     *
     * @return liste des Categories
     */
    List<Categorie> listerCategories(final Direction direction);

    /**
     * Récupère la liste des categories
     *
     * @param offset de démarrage de la liste
     * @param limit sur le nombre de categorie retournées
     * @param direction : selection du sens de tri
     * @return partie de la liste des categories
     */
    List<Categorie> listerCategorie(final int offset, final int limit, final Direction direction);

}
