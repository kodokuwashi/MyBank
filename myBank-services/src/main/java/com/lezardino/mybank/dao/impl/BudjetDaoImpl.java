package com.lezardino.mybank.dao.impl;

import com.lezardino.mybank.dao.BudjetDao;
import com.lezardino.mybank.constantes.Constantes;
import com.lezardino.mybank.modele.Budjet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class BudjetDaoImpl implements BudjetDao {

    /** bean d'accès à la base mongo */
    @Autowired
    private transient MongoOperations mongoOperations;


    @Override
    public void enregistrerBudjet(Budjet budjet) {
        mongoOperations.save(budjet);
    }

    @Override
    public Budjet recupererBudjet(String libelle) {
        final Query query = new Query();
        query.addCriteria(Criteria.where(Constantes.ChampCollectionBudjet.LIBELLE).is(libelle));
        return mongoOperations.findOne(query, Budjet.class);
    }

    @Override
    public void supprimerBudjet(Budjet budjet) {
        mongoOperations.remove(budjet);
    }

    @Override
    public Long nombreBudjets() {
        final Query query = new Query();
        return mongoOperations.count(query, Budjet.class);
    }

    @Override
    public List<Budjet> listerBudjet(Direction direction) {
        final Query query = new Query();
        query.with(new Sort(direction, Constantes.ChampCollectionCategorie.LIBELLE));
        return mongoOperations.find(query, Budjet.class);
    }

    @Override
    public List<Budjet> listerBudjet(int offset, int limit, Direction direction) {
        final Query query = new Query();
        query.skip(offset);
        query.limit(limit);
        query.with(new Sort(direction, Constantes.ChampCollectionCategorie.LIBELLE));
        return mongoOperations.find(query, Budjet.class);
    }

    @Override
    public List<Budjet> listerBudjetByLibelle(String libelle, Direction direction) {
        final Query query = new Query();
        query.addCriteria(Criteria.where(Constantes.ChampCollectionBudjet.LIBELLE).is(libelle));
        query.with(new Sort(direction, Constantes.ChampCollectionCategorie.LIBELLE));
        return mongoOperations.find(query, Budjet.class);
    }

    @Override
    public List<Budjet> listerBudjetByLibelle(String libelle, int offset, int limit, Direction direction) {
        final Query query = new Query();
        query.skip(offset);
        query.limit(limit);
        query.addCriteria(Criteria.where(Constantes.ChampCollectionBudjet.LIBELLE).is(libelle));
        return mongoOperations.find(query, Budjet.class);
    }
}
