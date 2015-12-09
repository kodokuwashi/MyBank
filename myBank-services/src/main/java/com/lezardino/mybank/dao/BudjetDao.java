package com.lezardino.mybank.dao;

import com.lezardino.mybank.modele.Budjet;
import com.lezardino.mybank.modele.Categorie;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

/**
 * Interface d'accès aux budjet stockés en base
 */
public interface BudjetDao {

    /**
     * Enregistre en base un nouveau budjet
     *
     * @param budjet à enregistrer
     */
    void enregistrerBudjet(final Budjet budjet);

    /**
     * Récupère un budjet en base par son identifiant unique
     *
     * @param libelle : identifiant du budjet
     * @return categorie à récupérer
     */
    Budjet recupererBudjet(final String libelle);

    /**
     * Supprimer un budjet de la base
     *
     * @param budjet à supprimer
     */
    void supprimerBudjet(final Budjet budjet);

    /**
     * Récupère le nombre de budjet enregistré
     *
     * @return nombre de Budjet
     */
    Long nombreBudjets();

    /**
     * Récupère la liste des budjets
     *
     * @return liste des Budjet
     */
    List<Budjet> listerBudjet(final Direction direction);

    /**
     * Récupère la liste des budjets
     *
     * @param offset de démarrage de la liste
     * @param limit sur le nombre de budjet retournés
     * @param direction : selection du sens de tri
     * @return partie de la liste des budjets
     */
    List<Budjet> listerBudjet(final int offset, final int limit, final Direction direction);

    /**
     * Récupère la liste des budjets de meme libelle
     *
     * @return liste des Budjet
     */
    List<Budjet> listerBudjetByLibelle(final String libelle, final Direction direction);

    /**
     * Récupère la liste des budjets de meme libelle
     *
     * @param offset de démarrage de la liste
     * @param limit sur le nombre de budjet retournés
     * @param direction : selection du sens de tri
     * @return partie de la liste des budjets
     */
    List<Budjet> listerBudjetByLibelle(String libelle, final int offset, final int limit, final Direction direction);

}
