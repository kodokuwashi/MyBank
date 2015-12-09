//***********************************************************************************
//
// Systeme Technique RBRSVA
//
// (C) Copyright Bouygues Telecom 2015.
//
// Utilisation, reproduction et divulgation interdites
// sans autorisation ecrite de Bouygues Telecom.
//
// Projet        : RBRSVA
// Createur      : annicola
// Date creation : 23 fev. 2015
// version       : $Revision$
//
//************************************************************************************
package com.lezardino.mybank.metier;

import com.lezardino.mybank.erreur.ErreurFonctionnelle;
import com.lezardino.mybank.modele.Operation;
import com.lezardino.mybank.ressource.RSList;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Interface de la ressource Prix
 *
 * @author pisalaun
 *
 */
@Path("/operation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Component
public interface IServiceOperation {

    /**
     * Permet d'ajouter un compte
     *
     * @param operation : operation au format json à enregistrer
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    Response enregistrerOperation(@Valid @NotNull Operation operation);

    /**
     * Retourne une operation présente en base
     *
     * @param idOperation : identifiant de l'opération à retourner
     * @return Operation
     * @throws ErreurFonctionnelle : Erreur metier
     */
    @GET
    @Path("/{identifiant}")
    Operation recupererOperation(@Valid @NotNull @PathParam("identifiant") final String idOperation)
            throws ErreurFonctionnelle;

    /**
     * Retourne l'ensemble des opérations présents dans le référentiel pour un compte donnee
     *
     * @param offset : décalage pour la prise en compte des tranche de numeros
     * @param limit : nombre maximum de tranche de numeros à récupérer
     * @param idCompte : identifiant du compte
     * @return RSList<Operation>
     * @throws ErreurFonctionnelle : Erreur metier
     */
    @GET
    RSList<Operation> listAll(@DefaultValue("0") @QueryParam("offset") final Integer offset,
                                  @DefaultValue("0") @QueryParam("limit") final Integer limit,
                                     @Valid @NotNull final String idCompte) throws ErreurFonctionnelle;

    /**
     * Supprime l'opération présente en base
     *
     * @param operation : operation à supprimer
     */
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    Response supprimerCompte(@Valid @NotNull Operation operation);

}
