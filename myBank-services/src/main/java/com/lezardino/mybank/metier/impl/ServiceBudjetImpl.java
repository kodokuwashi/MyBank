package com.lezardino.mybank.metier.impl;

import com.lezardino.mybank.dao.BudjetDao;
import com.lezardino.mybank.dao.CompteDao;
import com.lezardino.mybank.erreur.ErreurFonctionnelle;
import com.lezardino.mybank.metier.IServiceBudjet;
import com.lezardino.mybank.metier.IServiceCompte;
import com.lezardino.mybank.modele.Budjet;
import com.lezardino.mybank.modele.Compte;
import com.lezardino.mybank.ressource.RSList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/budjet")
@Service("budjet")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiceBudjetImpl implements IServiceBudjet {

    /** Acces à la collection Budjet de mongo */
    @Autowired
    private transient BudjetDao budjetDao;

    /** pattern de l'url de la ressource budjet */
    private static final String URI_PATTERN = "/budjet?offset={0}&limit={1}&direction={2}";

    /**
     * Préfixe de l'URL pour les budjet créés
     */
    private static final String CREATED_BUDJET_PREFIX = "/budjet/";

    /**
     * Préfixe de l'URL pour les budjets supprimé
     */
    private static final String DELETED_BUDJET_PREFIX = "/budjet/supprime";

    /**
     * {@inheritDoc}
     */
    @Override
    public Response enregistrerBudjet(final Budjet budjet) {

        this.budjetDao.enregistrerBudjet(budjet);

        // Retour en 201 avec le bon header Location
        String resourceUri = CREATED_BUDJET_PREFIX + budjet.getLibelle();
        return Response.created(URI.create(resourceUri)).build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Path("/{identifiant}")
    public Budjet recupererBudjet(@Valid @NotNull @PathParam("identifiant") String identifiant)
            throws ErreurFonctionnelle {
        final Budjet budjet = this.budjetDao.recupererBudjet(identifiant);
        if (budjet == null) {
            throw new ErreurFonctionnelle(ErreurFonctionnelle.CODE_ERREUR_DONNEEINCONNUE,
                    String.format("Le budjet d'identifiant %s n'existe pas", identifiant), null);
        }
        return budjet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RSList<Budjet> listAll(final Integer offset, final Integer limit, final String stringDirection)
            throws ErreurFonctionnelle {
        long resultsCount = this.budjetDao.nombreBudjets();
        Direction direction = Direction.ASC;
        if(stringDirection.equals("DESC")){
            direction = Direction.DESC;
        }
        List<Budjet> listeBudjet = this.budjetDao.listerBudjet(offset, limit, direction);

        final RSList<Budjet> rsBudjet = new RSList<>(URI_PATTERN, resultsCount, listeBudjet);
        String optionalParametres = "&direction=" + stringDirection;
        rsBudjet.setPreviousLink(offset, limit, optionalParametres);
        rsBudjet.setSelfLink(offset, limit, optionalParametres);
        rsBudjet.setNextLink(offset, limit, resultsCount, optionalParametres);

        return rsBudjet;
    }


    @Override
    public Response supprimerBudjet(final Budjet budjet) {
        this.budjetDao.supprimerBudjet(budjet);

        // Retour en 201 avec le bon header Location
        String resourceUri = DELETED_BUDJET_PREFIX;
        return Response.created(URI.create(resourceUri)).build();
    }
}
