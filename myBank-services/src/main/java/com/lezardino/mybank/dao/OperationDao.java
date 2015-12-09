package com.lezardino.mybank.dao;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.lezardino.mybank.modele.Operation;

/**
 * Interface d'accès aux operations stockés en base
 */
public interface OperationDao {

    /**
     * Enregistre en base une nouvelle operation
     *
     * @param operation à enregistrer
     */
    void enregistrerOperation(final Operation operation);

    /**
     * Récupère une operation en base par son identifiant unique
     *
     * @param idOperation : identifiant de l'operation à récupérer
     * @return Operation récupérer
     */
    Operation recupererOperation(final String idOperation);

    /**
     * Supprimer une operation
     *
     * @param operation à supprimer
     */
    void supprimerOperation(final Operation operation);

    /**
     * Récupère le nombre d'operation enregistré
     *
     * @return nombre d'operation
     */
    Long nombreOperation();

    /**
     * Récupère le nombre d'operation enregistré pour un compte donné
     *
     * @param idCompte identifiant du compte
     * @return nombre d'operation pour le compte identifié
     */
    Long nombreOperation(String idCompte);

    /**
     * Récupère la liste des operations
     *
     * @return liste des Operations
     */
    List<Operation> listerOperations(final Direction direction);

    /**
     * Récupère la liste des operations pour un compte donné
     *
     * @param idCompte : identifiant du compte
     * @return liste des operations pour le compte donné
     */
    List<Operation> listerOperationsByCompte(final String idCompte, final Direction direction);

    /**
     * Récupère la liste des operations
     *
     * @param offset de démarrage de la liste
     * @param limit sur le nombre d'operation retournées
     * @param direction : selection du sens de tri
     * @return partie de la liste des operations
     */
    List<Operation> listerOperations(final int offset, final int limit, final Direction direction);

    /**
     * Récupère la liste des operations
     *
     * @param idCompte identifiant du compte
     * @param offset de démarrage de la liste
     * @param limit sur le nombre d'operation retournées
     * @param direction selection le sens de tri
     * @return partie de la liste des operations pour le compte donné
     */
    List<Operation> listerOperationsByCompte(String idCompte, final int offset, final int limit,
            final Direction direction);

}
