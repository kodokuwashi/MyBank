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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import com.lezardino.mybank.erreur.ErreurFonctionnelle;
import com.lezardino.mybank.modele.Compte;
import com.lezardino.mybank.ressource.RSList;

/**
 * Interface de la ressource Prix
 *
 * @author pisalaun
 *
 */
@Component
public interface IServiceCompte {

    /**
     * Permet d'ajouter un compte
     *
     * @param compte : compte au format json à enregistrer
     */
    @POST
    Response enregistrerCompte(@Valid @NotNull Compte compte);

    /**
     * Retourne un compte présent en base
     *
     * @param identifiant : identifiant du compte à retourner
     * @return Compte
     * @throws ErreurFonctionnelle : Erreur metier
     */
    @GET
    Compte recupererCompte(@Valid @NotNull @PathParam("identifiant") final String identifiant)
            throws ErreurFonctionnelle;

    /**
     * Retourne l'ensemble des compte présents dans le référentiel
     *
     * @param offset : décalage pour la prise en compte des tranche de numeros
     * @param limit : nombre maximum de tranche de numeros à récupérer
     * @return RSList<Compte>
     * @throws ErreurFonctionnelle : Erreur metier
     */
    @GET
    RSList<Compte> listAll(@DefaultValue("0") @QueryParam("offset") final Integer offset,
           @DefaultValue("2") @QueryParam("limit") final Integer limit,
           @DefaultValue("ASC") @QueryParam("direction") final String stringDirection) throws ErreurFonctionnelle;

    /**
     * Supprime le compte présent en base
     *
     * @param compte : compte à supprimer
     */
    @DELETE
    Response supprimerCompte(@Valid @NotNull Compte compte);

}
