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
import com.lezardino.mybank.modele.Budjet;
import com.lezardino.mybank.ressource.RSList;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Interface de la ressource Budjet
 *
 * @author pisalaun
 *
 */
@Component
public interface IServiceBudjet {

    /**
     * Permet d'ajouter un budjet
     *
     * @param budjet : budjet au format json à enregistrer
     */
    @POST
    Response enregistrerBudjet(@Valid @NotNull Budjet budjet);

    /**
     * Retourne un budjet présent en base
     *
     * @param identifiant : identifiant du bujet à retourner
     * @return Budjet
     * @throws ErreurFonctionnelle : Erreur metier
     */
    @GET
    @Path("/{identifiant}")
    Budjet recupererBudjet(@Valid @NotNull @PathParam("identifiant") final String identifiant)
            throws ErreurFonctionnelle;

    /**
     * Retourne l'ensemble des budjets présents dans le référentiel
     *
     * @param offset : décalage pour la prise en compte des budjet
     * @param limit : nombre maximum de budjet à récupérer
     * @param stringDirection : ordre de tri des budjets
     * @return RSList<Budjet>
     * @throws ErreurFonctionnelle : Erreur metier
     */
    @GET
    RSList<Budjet> listAll(@DefaultValue("0") @QueryParam("offset") final Integer offset,
                           @DefaultValue("2") @QueryParam("limit") final Integer limit,
                           @DefaultValue("ASC") @QueryParam("direction") final String stringDirection) throws ErreurFonctionnelle;

    /**
     * Supprime un budjet présent en base
     *
     * @param budjet : budjet à supprimer
     */
    @DELETE
    Response supprimerBudjet(@Valid @NotNull Budjet budjet);

}
