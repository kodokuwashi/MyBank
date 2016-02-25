package com.lezardino.mybank.dao.impl;

import java.util.List;

import com.lezardino.mybank.constantes.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.lezardino.mybank.dao.CompteDao;
import com.lezardino.mybank.modele.Compte;

public class CompteDaoImpl implements CompteDao {

    /** bean d'accès à la base mongo */
    @Autowired
    private transient MongoOperations mongoOperations;

    @Override
    public void enregistrerCompte(Compte compte) {
        mongoOperations.save(compte);
    }

    @Override
    public void supprimerCompte(Compte compte) {
        mongoOperations.remove(compte);
    }

    @Override
    public Long nombreComptes() {
        final Query query = new Query();
        return mongoOperations.count(query, Compte.class);
    }

    @Override
    public List<Compte> listerComptes(Direction direction) {
        final Query query = new Query();
        query.with(new Sort(direction, Constantes.ChampCollectionCompte.IDCOMPTE));
        return mongoOperations.find(query, Compte.class);
    }

    @Override
    public List<Compte> listerComptes(int offset, int limit, Direction direction) {
        final Query query = new Query();
        query.skip(offset);
        query.limit(limit);

        query.with(new Sort(direction, Constantes.ChampCollectionCompte.IDCOMPTE));
        return mongoOperations.find(query, Compte.class);
    }

    @Override
    public List<Compte> listerComptesbyProprietaire(String proprietaire, Direction direction) {
        final Query query = new Query();
        query.with(new Sort(direction, Constantes.ChampCollectionCompte.IDCOMPTE));
        query.addCriteria(Criteria.where(Constantes.ChampCollectionCompte.PROPRIETAIRE).is(proprietaire));
        return mongoOperations.find(query, Compte.class);
    }

    @Override
    public List<Compte> listerComptesbyProprietaire(int offset, int limit, String proprietaire, Direction direction) {
        final Query query = new Query();
        query.skip(offset);
        query.limit(limit);

        query.with(new Sort(direction, Constantes.ChampCollectionCompte.IDCOMPTE));
        query.addCriteria(Criteria.where(Constantes.ChampCollectionCompte.PROPRIETAIRE).is(proprietaire));
        return mongoOperations.find(query, Compte.class);
    }

    @Override
    public Compte recupererCompteById(String idCompte) {
        final Query query = new Query();
        query.addCriteria(Criteria.where(Constantes.ChampCollectionCompte.IDCOMPTE).is(idCompte));
        return mongoOperations.findOne(query, Compte.class);
    }

    @Override
    public Compte recupererCompte(String libelle, String proprietaire) {
        final Query query = new Query();
        query.addCriteria(Criteria.where(Constantes.ChampCollectionCompte.LIBELLE)
                .is(libelle).and(Constantes.ChampCollectionCompte.PROPRIETAIRE).is(proprietaire));
        return mongoOperations.findOne(query, Compte.class);
    }

}
