package com.lezardino.mybank.dao;

import com.lezardino.mybank.constantes.Constantes;
import com.lezardino.mybank.modele.Categorie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de test des méthode mongo de la collection categories
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/appcontext.xml" })
public class TestCategorieDao {

    /** bean d'accès à la base mongo */
    @Autowired
    private CategorieDao categorieDao;

    /** bean d'accès à la base mongo */
    @Autowired
    private transient MongoOperations mongoOperations;

    /** Initialisation de la collection categorie */
    @Before
    public void setUp() {
        // initialisation de la base de données
        mongoOperations.dropCollection(Constantes.CollectionsMongo.CATEGORIE);
    }

    @Test
    public void testEnregistrerRecupererCategorieDao() {

        Categorie categorieAttendu = new Categorie("testCategorie", new BigDecimal(10.50));

        categorieDao.enregistrerCategorie(categorieAttendu);
        Categorie categorie = categorieDao.recupererCategorie(categorieAttendu.getLibelle());
        assertThat(categorie).isNotNull();
        assertThat(categorie.getLibelle()).isEqualTo("testCategorie");
        assertThat(categorie.getSolde()).isEqualTo(new BigDecimal(10.50));
        assertThat(categorie.toString()).isEqualTo(
                "Categorie{libelle='testCategorie', solde='10,50'}");
    }

    @Test
    public void testNombreCategorieDao() {

        Categorie categorieAttendu1 = new Categorie("testCategorie1", new BigDecimal(10.50));
        Categorie categorieAttendu2 = new Categorie("testCategorie2", new BigDecimal(10.50));

        categorieDao.enregistrerCategorie(categorieAttendu1);
        categorieDao.enregistrerCategorie(categorieAttendu2);
        Long resultCount = categorieDao.nombreCategories();
        assertThat(resultCount).isEqualTo(2L);
    }

    @Test
    public void testListerCategorieDao() {

        Categorie categorieAttendu1 = new Categorie("testCategorie1", new BigDecimal(10.50));
        Categorie categorieAttendu2 = new Categorie("testCategorie2", new BigDecimal(10.50));
        Categorie categorieAttendu3 = new Categorie("testCategorie3", new BigDecimal(10.50));

        categorieDao.enregistrerCategorie(categorieAttendu1);
        categorieDao.enregistrerCategorie(categorieAttendu2);
        categorieDao.enregistrerCategorie(categorieAttendu3);

        List<Categorie> listeCategorie = categorieDao.listerCategories(Direction.ASC);
        assertThat(listeCategorie).isNotNull();
        assertThat(listeCategorie.size()).isEqualTo(3);

        listeCategorie = categorieDao.listerCategorie(1, 2, Direction.ASC);
        assertThat(listeCategorie).isNotNull();
        assertThat(listeCategorie.size()).isEqualTo(2);
        assertThat(listeCategorie.get(0)).isEqualTo(categorieAttendu2);
        assertThat(listeCategorie.get(1)).isEqualTo(categorieAttendu3);
    }

    @Test
    public void testSupprimerCategorieDao() {

        Categorie categorieAttendu1 = new Categorie("testCategorie1", new BigDecimal(10.50));
        Categorie categorieAttendu2 = new Categorie("testCategorie2", new BigDecimal(10.50));

        categorieDao.enregistrerCategorie(categorieAttendu1);
        categorieDao.enregistrerCategorie(categorieAttendu2);

        Long resultCount = categorieDao.nombreCategories();
        assertThat(resultCount).isEqualTo(2L);
        categorieDao.supprimerCategorie(categorieAttendu1);
        resultCount = categorieDao.nombreCategories();
        assertThat(resultCount).isEqualTo(1L);
    }
}
