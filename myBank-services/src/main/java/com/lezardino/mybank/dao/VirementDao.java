package com.lezardino.mybank.dao;

import com.lezardino.mybank.modele.Compte;
import com.lezardino.mybank.modele.Virement;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;

/**
 * Interface d'accès aux virements stockés en base
 */
public interface VirementDao {

    /**
     * Enregistre en base un nouveau virement
     *
     * @param virement à enregistrer
     */
    void enregistrerVirement(final Virement virement);

    /**
     * Supprimer un virement de la base
     *
     * @param virement à supprimer
     */
    void supprimerVirement(final Virement virement);

    /**
     * Récupère le nombre de virement enregistré
     *
     * @return nombre de Virement
     */
    Long nombreVirements();

    /**
     * Récupère la liste des virements
     *
     * @return liste des Virements
     */
    List<Virement> listerVirements(final Direction direction);

    /**
     * Récupère la liste des virements
     *
     * @param offset de démarrage de la liste
     * @param limit sur le nombre de virements retournées
     * @param direction : selection du sens de tri
     * @return partie de la liste des virements
     */
    List<Virement> listerVirements(final int offset, final int limit, final Direction direction);

    /**
     * Récupère un virement en base par son identifiant unique
     *
     * @param idVirement : identifiant du virement
     * @return virement à récupérer
     */
    Virement recupererVirement(final String idVirement);

}
