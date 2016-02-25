package com.lezardino.mybank.dao;

import java.util.List;

import com.lezardino.mybank.ressource.RSList;
import org.springframework.data.domain.Sort.Direction;

import com.lezardino.mybank.modele.Compte;

/**
 * Interface d'accès aux comptes stockés en base
 */
public interface CompteDao {

    /**
     * Enregistre en base un nouveau compte
     *
     * @param compte à enregistrer
     */
    void enregistrerCompte(final Compte compte);

    /**
     * Supprimer un compte de la base
     *
     * @param compte à supprimer
     */
    void supprimerCompte(final Compte compte);

    /**
     * Récupère le nombre de compte enregistré
     *
     * @return nombre de Compte
     */
    Long nombreComptes();

    /**
     * Récupère la liste des comptes
     *
     * @return liste des Comptes
     */
    List<Compte> listerComptes(final Direction direction);

    /**
     * Récupère la liste des comptes
     *
     * @param offset de démarrage de la liste
     * @param limit sur le nombre de comptes retournées
     * @param direction : selection du sens de tri
     * @return partie de la liste des comptes
     */
    List<Compte> listerComptes(final int offset, final int limit, final Direction direction);

    /**
     * Récupère la liste des comptes
     *
     * @param proprietaire : proprietaire des compte à lister
     * @param direction : selection du sens de tri
     * @return partie de la liste des comptes
     */
    List<Compte> listerComptesbyProprietaire(final String proprietaire, final Direction direction);

    /**
     * Récupère la liste des comptes
     *
     * @param offset de démarrage de la liste
     * @param limit sur le nombre de comptes retournées
     * @param proprietaire : proprietaire des compte à lister
     * @param direction : selection du sens de tri
     * @return partie de la liste des comptes
     */
    List<Compte> listerComptesbyProprietaire(final int offset, final int limit, final String proprietaire, final Direction direction);

    /**
     * Récupère un compte en base par son nom et son propriétaire
     *
     * @param libelle du compte
     * @param propriétaire du compte
     * @return compte à récupérer
     */
    Compte recupererCompte(final String libelle, final String propriétaire);

    /**
     * Récupère un compte en base par son identifiant unique
     *
     * @param idCompte : identifiant du compte
     * @return compte à récupérer
     */
    Compte recupererCompteById(final String idCompte);

}
