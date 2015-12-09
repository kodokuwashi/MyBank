package com.lezardino.mybank.dao.impl;

import java.util.List;

import com.lezardino.mybank.constantes.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.lezardino.mybank.dao.OperationDao;
import com.lezardino.mybank.modele.Operation;

public class OperationDaoImpl implements OperationDao {

    /** bean d'accès à la base mongo */
    @Autowired
    private transient MongoOperations mongoOperations;

    @Override
    public void enregistrerOperation(Operation operation) {
        mongoOperations.save(operation);
    }

    @Override
    public Operation recupererOperation(String idOperation) {
        final Query query = new Query();
        query.addCriteria(Criteria.where(Constantes.ChampCollectionOperation.IDOPERATION).is(idOperation));
        return mongoOperations.findOne(query, Operation.class);
    }

    @Override
    public void supprimerOperation(Operation operation) {
        mongoOperations.remove(operation);
    }

    @Override
    public Long nombreOperation() {
        final Query query = new Query();
        return mongoOperations.count(query, Operation.class);
    }

    @Override
    public Long nombreOperation(String idCompte) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("compte.idCompte").is(idCompte));
        return mongoOperations.count(query, Operation.class);
    }

    @Override
    public List<Operation> listerOperations(Direction direction) {
        final Query query = new Query();
        query.with(new Sort(direction, Constantes.ChampCollectionOperation.IDOPERATION));
        return mongoOperations.find(query, Operation.class);
    }

    @Override
    public List<Operation> listerOperationsByCompte(String idCompte, Direction direction) {
        final Query query = new Query();
        query.addCriteria(Criteria.where("compte.idCompte").is(idCompte));
        query.with(new Sort(direction, Constantes.ChampCollectionOperation.IDOPERATION));
        return mongoOperations.find(query, Operation.class);
    }

    @Override
    public List<Operation> listerOperations(int offset, int limit, Direction direction) {
        final Query query = new Query();
        query.skip(offset);
        query.limit(limit);
        query.with(new Sort(direction, Constantes.ChampCollectionOperation.IDOPERATION));
        return mongoOperations.find(query, Operation.class);
    }

    @Override
    public List<Operation> listerOperationsByCompte(String idCompte, int offset, int limit,
            Direction direction) {
        final Query query = new Query();
        query.skip(offset);
        query.limit(limit);
        query.addCriteria(Criteria.where("compte.idCompte").is(idCompte));
        query.with(new Sort(direction, Constantes.ChampCollectionOperation.IDOPERATION));
        return mongoOperations.find(query, Operation.class);
    }
}
