package com.lezardino.mybank.metier.impl;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lezardino.mybank.dao.CompteDao;
import com.lezardino.mybank.erreur.ErreurFonctionnelle;
import com.lezardino.mybank.metier.IServiceCompte;
import com.lezardino.mybank.modele.Compte;
import com.lezardino.mybank.ressource.RSList;

@Service("compte")
public class ServiceCompteImpl implements IServiceCompte {

    /** Acces à la collection Compte de mongo */
    @Autowired
    private transient CompteDao compteDao;

    /** pattern de l'url de la ressource compte */
    private static final String URI_PATTERN = "/compte?offset={0}&limit={1}&direction={2}";

    /**
     * Préfixe de l'URL pour les comptes créés
     */
    private static final String CREATED_COMPTE_PREFIX = "/compte/";

    /**
     * Préfixe de l'URL pour les comptes supprimé
     */
    private static final String DELETED_COMPTE_PREFIX = "/compte/supprime";

    /**
     * {@inheritDoc}
     */
    @Override
    public Response enregistrerCompte(final Compte compte) {

        compteDao.enregistrerCompte(compte);

        // Retour en 201 avec le bon header Location
        String resourceUri = CREATED_COMPTE_PREFIX + compte.getIdCompte();
        return Response.created(URI.create(resourceUri)).build();
    }

    @Override
    public Compte recupererCompte(@Valid @NotNull @PathParam("identifiant") String identifiant)
            throws ErreurFonctionnelle {
        final Compte compte = compteDao.recupererCompteById(identifiant);
        if (compte == null) {
            throw new ErreurFonctionnelle(ErreurFonctionnelle.CODE_ERREUR_DONNEEINCONNUE,
                    String.format("Le compte d'identifiant %s n'existe pas", identifiant), null);
        }
        return compte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RSList<Compte> listAll(final Integer offset, final Integer limit, final String stringDirection)
            throws ErreurFonctionnelle {
        long resultsCount = compteDao.nombreComptes();
        Direction direction = Direction.ASC;
        if(stringDirection.equals("DESC")){
            direction = Direction.DESC;
        }
        List<Compte> listeCompte = compteDao.listerComptes(offset, limit, direction);

        final RSList<Compte> rsCompte = new RSList<>(URI_PATTERN, resultsCount, listeCompte);
        rsCompte.setPreviousLink(offset, limit, direction);
        rsCompte.setSelfLink(offset, limit, direction);
        rsCompte.setNextLink(offset, limit, resultsCount, direction);

        return rsCompte;
    }

    @Override
    public Response supprimerCompte(final Compte compte) {
        compteDao.supprimerCompte(compte);

        // Retour en 201 avec le bon header Location
        String resourceUri = DELETED_COMPTE_PREFIX;
        return Response.created(URI.create(resourceUri)).build();
    }
}
