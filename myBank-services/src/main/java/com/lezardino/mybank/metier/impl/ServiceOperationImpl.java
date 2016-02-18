package com.lezardino.mybank.metier.impl;

import com.lezardino.mybank.dao.OperationDao;
import com.lezardino.mybank.erreur.ErreurFonctionnelle;
import com.lezardino.mybank.metier.IServiceOperation;
import com.lezardino.mybank.modele.Operation;
import com.lezardino.mybank.ressource.RSList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("/operation")
@Service("operation")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServiceOperationImpl implements IServiceOperation {

    /** Acces à la collection Compte de mongo */
    @Autowired
    private transient OperationDao operationDao;

    /** pattern de l'url de la ressource compte */
    private static final String URI_PATTERN = "/operation?offset={0}&limit={1}&direction={2}";

    /**
     * Préfixe de l'URL pour les comptes créés
     */
    private static final String CREATED_OPERATION_PREFIX = "/operation/";

    /**
     * Préfixe de l'URL pour les comptes supprimé
     */
    private static final String DELETED_OPERATION_PREFIX = "/operation/supprime";

    /**
     * {@inheritDoc}
     */
    @Override
    public Response enregistrerOperation(final Operation operation) {

        this.operationDao.enregistrerOperation(operation);

        // Retour en 201 avec le bon header Location
        String resourceUri = CREATED_OPERATION_PREFIX + operation.getIdOperation();
        return Response.created(URI.create(resourceUri)).build();
    }

    @Override
    @Path("/{identifiant}")
    public Operation recupererOperation(String identifiant)
            throws ErreurFonctionnelle {
        final Operation operation = this.operationDao.recupererOperation(identifiant);
        if (operation == null) {
            throw new ErreurFonctionnelle(ErreurFonctionnelle.CODE_ERREUR_DONNEEINCONNUE,
                    String.format("L'operation d'identifiant %s n'existe pas", identifiant), null);
        }
        return operation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RSList<Operation> listAll(final Integer offset, final Integer limit, final String stringDirection)
            throws ErreurFonctionnelle {
        long resultsCount = this.operationDao.nombreOperation();
        Direction direction = Direction.ASC;
        if(stringDirection.equals("DESC")){
            direction = Direction.DESC;
        }
        List<Operation> listeOperation = this.operationDao.listerOperations(offset, limit, direction);

        final RSList<Operation> rsOperation = new RSList<>(URI_PATTERN, resultsCount, listeOperation);
        rsOperation.setPreviousLink(offset, limit, direction);
        rsOperation.setSelfLink(offset, limit, direction);
        rsOperation.setNextLink(offset, limit, resultsCount, direction);

        return rsOperation;
    }

    @Override
    public Response supprimerOperation(final Operation operation) {
        this.operationDao.supprimerOperation(operation);

        // Retour en 201 avec le bon header Location
        String resourceUri = DELETED_OPERATION_PREFIX;
        return Response.created(URI.create(resourceUri)).build();
    }
}
