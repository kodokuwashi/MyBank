package com.lezardino.mybank.dao.impl;

import com.lezardino.mybank.dao.CategorieDao;
import com.lezardino.mybank.constantes.Constantes;
import com.lezardino.mybank.modele.Categorie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class CategorieDaoImpl implements CategorieDao {

    /** bean d'accès à la base mongo */
    @Autowired
    private transient MongoOperations mongoOperations;


    @Override
    public void enregistrerCategorie(Categorie categorie) {
        mongoOperations.save(categorie);
    }

    @Override
    public Categorie recupererCategorie(String libelle) {
        final Query query = new Query();
        query.addCriteria(Criteria.where(Constantes.ChampCollectionCategorie.LIBELLE).is(libelle));
        return mongoOperations.findOne(query, Categorie.class);
    }

    @Override
    public void supprimerCategorie(Categorie categorie) {
        mongoOperations.remove(categorie);
    }

    @Override
    public Long nombreCategories() {
        final Query query = new Query();
        return mongoOperations.count(query, Categorie.class);
    }

    @Override
    public List<Categorie> listerCategories(Direction direction) {
        final Query query = new Query();
        query.with(new Sort(direction, Constantes.ChampCollectionCategorie.LIBELLE));
        return mongoOperations.find(query, Categorie.class);
    }

    @Override
    public List<Categorie> listerCategorie(int offset, int limit, Direction direction) {
        final Query query = new Query();
        query.skip(offset);
        query.limit(limit);

        query.with(new Sort(direction, Constantes.ChampCollectionCategorie.LIBELLE));
        return mongoOperations.find(query, Categorie.class);
    }
}
