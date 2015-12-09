package com.lezardino.mybank.dao.impl;

import com.lezardino.mybank.dao.VirementDao;
import com.lezardino.mybank.constantes.Constantes;
import com.lezardino.mybank.modele.Virement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class VirementDaoImpl implements VirementDao {

    /** bean d'accès à la base mongo */
    @Autowired
    private transient MongoOperations mongoOperations;

    @Override
    public void enregistrerVirement(Virement virement) {
        mongoOperations.save(virement);
    }

    @Override
    public void supprimerVirement(Virement virement) {
        mongoOperations.remove(virement);
    }

    @Override
    public Long nombreVirements() {
        final Query query = new Query();
        return mongoOperations.count(query, Virement.class);
    }

    @Override
    public List<Virement> listerVirements(Direction direction) {
        final Query query = new Query();
        query.with(new Sort(direction, Constantes.ChampCollectionVirement.IDVIREMENT));
        return mongoOperations.find(query, Virement.class);
    }

    @Override
    public List<Virement> listerVirements(int offset, int limit, Direction direction) {
        final Query query = new Query();
        query.skip(offset);
        query.limit(limit);
        query.with(new Sort(direction, Constantes.ChampCollectionVirement.IDVIREMENT));
        return mongoOperations.find(query, Virement.class);
    }

    @Override
    public Virement recupererVirement(String idVirement) {
        final Query query = new Query();
        query.addCriteria(Criteria.where(Constantes.ChampCollectionVirement.IDVIREMENT).is(idVirement));
        return mongoOperations.findOne(query, Virement.class);
    }
}
