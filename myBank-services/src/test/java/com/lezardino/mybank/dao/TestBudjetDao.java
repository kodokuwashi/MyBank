package com.lezardino.mybank.dao;

import com.lezardino.mybank.constantes.Constantes;
import com.lezardino.mybank.modele.Budjet;
import com.lezardino.mybank.modele.Compte;
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
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de test des méthode mongo de la collection budjet
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/appcontext.xml" })
public class TestBudjetDao {

    /** bean d'accès à la base mongo */
    @Autowired
    private BudjetDao budjetDao;

    /** bean d'accès à la base mongo */
    @Autowired
    private CompteDao compteDao;

    /** bean d'accès à la base mongo */
    @Autowired
    private transient MongoOperations mongoOperations;

    /** Initialisation de la collection budjet */
    @Before
    public void setUp() {
        // initialisation de la base de données
        mongoOperations.dropCollection(Constantes.CollectionsMongo.BUDJET);
    }

    @Test
    public void testEnregistrerRecupererBudjetDao() {

        String libelle = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte = new Compte(libelle, proprietaire, solde);
        compteDao.enregistrerCompte(compte);

        TreeMap<String, BigDecimal> soldeParCompteAttendu = new TreeMap<>();
        soldeParCompteAttendu.put(compte.getIdCompte(), new BigDecimal(100.50));

        Budjet budjetAttendu = new Budjet("testBudjet", soldeParCompteAttendu);

        budjetDao.enregistrerBudjet(budjetAttendu);

        Budjet budjet = budjetDao.recupererBudjet(budjetAttendu.getLibelle());
        assertThat(budjet).isNotNull();
        assertThat(budjet).isEqualTo(budjetAttendu);

        assertThat(budjet.toString()).isEqualTo(
                "Budjet{libelle='testBudjet', soldeParCompte=[{idCompte='" + compte.getIdCompte()
                + "':solde='100,50'}]}");
    }

    @Test
    public void testErreurEnregistrerBudjetDao() {

        String libelle = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte1 = new Compte(libelle, proprietaire, solde);
        Compte compte2 = new Compte(libelle, proprietaire, solde);
        compteDao.enregistrerCompte(compte1);
        compteDao.enregistrerCompte(compte2);

        TreeMap<String, BigDecimal> soldeParCompteAttendu = new TreeMap<>();
        soldeParCompteAttendu.put(compte1.getIdCompte(), new BigDecimal(100.50));
        Budjet budjetAttendu = new Budjet("testBudjet", soldeParCompteAttendu);
        budjetDao.enregistrerBudjet(budjetAttendu);

        budjetAttendu = new Budjet("testBudjet1", soldeParCompteAttendu);
        budjetDao.enregistrerBudjet(budjetAttendu);

    }

    @Test
    public void testNombreBudjetDao() {

        String libelle = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte = new Compte(libelle, proprietaire, solde);
        compteDao.enregistrerCompte(compte);

        TreeMap<String, BigDecimal> soldeParCompteAttendu = new TreeMap<>();
        soldeParCompteAttendu.put(compte.getIdCompte(), new BigDecimal(100.50));

        Budjet budjet1 = new Budjet("testBudjet1", soldeParCompteAttendu);
        Budjet budjet2 = new Budjet("testBudjet2", soldeParCompteAttendu);

        budjetDao.enregistrerBudjet(budjet1);
        budjetDao.enregistrerBudjet(budjet2);
        Long resultCount = budjetDao.nombreBudjets();
        assertThat(resultCount).isEqualTo(2L);
    }

    @Test
    public void testListerBudjetDao() {

        String libelle = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte1 = new Compte(libelle, proprietaire, solde);
        Compte compte2 = new Compte(libelle, proprietaire, solde);
        compteDao.enregistrerCompte(compte1);
        compteDao.enregistrerCompte(compte2);

        TreeMap<String, BigDecimal> soldeParCompteAttendu1 = new TreeMap<>();
        soldeParCompteAttendu1.put(compte1.getIdCompte(), new BigDecimal(100.50));

        TreeMap<String, BigDecimal> soldeParCompteAttendu2 = new TreeMap<>();
        soldeParCompteAttendu2.put(compte1.getIdCompte(), new BigDecimal(75.50));

        TreeMap<String, BigDecimal> soldeParCompteAttendu3 = new TreeMap<>();
        soldeParCompteAttendu3.put(compte1.getIdCompte(), new BigDecimal(25.50));

        Budjet budjet1 = new Budjet("testBudjet1", soldeParCompteAttendu1);
        Budjet budjet2 = new Budjet("testBudjet2", soldeParCompteAttendu2);
        Budjet budjet3 = new Budjet("testBudjet3", soldeParCompteAttendu3);

        budjetDao.enregistrerBudjet(budjet1);
        budjetDao.enregistrerBudjet(budjet2);
        budjetDao.enregistrerBudjet(budjet3);

        List<Budjet> listeBudjet = budjetDao.listerBudjet(Direction.ASC);
        assertThat(listeBudjet).isNotNull();
        assertThat(listeBudjet.size()).isEqualTo(3);

        listeBudjet = budjetDao.listerBudjet(1, 2, Direction.ASC);
        assertThat(listeBudjet).isNotNull();
        assertThat(listeBudjet.size()).isEqualTo(2);
        assertThat(listeBudjet.get(0)).isEqualTo(budjet2);
        assertThat(listeBudjet.get(1)).isEqualTo(budjet3);

        listeBudjet = budjetDao.listerBudjet(Direction.ASC);
        assertThat(listeBudjet).isNotNull();
        assertThat(listeBudjet.size()).isEqualTo(3);

        listeBudjet = budjetDao.listerBudjet(1, 2, Direction.ASC);
        assertThat(listeBudjet).isNotNull();
        assertThat(listeBudjet.size()).isEqualTo(2);
        assertThat(listeBudjet.get(0)).isEqualTo(budjet2);
        assertThat(listeBudjet.get(1)).isEqualTo(budjet3);
    }

    @Test
    public void testSupprimerBudjetDao() {

        String libelle = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte = new Compte(libelle, proprietaire, solde);
        compteDao.enregistrerCompte(compte);

        TreeMap<String, BigDecimal> soldeParCompteAttendu = new TreeMap<>();
        soldeParCompteAttendu.put(compte.getIdCompte(), new BigDecimal(100.50));

        Budjet budjet = new Budjet("testBudjet1", soldeParCompteAttendu);

        budjetDao.enregistrerBudjet(budjet);
        Long resultCount = budjetDao.nombreBudjets();
        assertThat(resultCount).isEqualTo(1L);
        budjetDao.supprimerBudjet(budjet);
        resultCount = budjetDao.nombreBudjets();
        assertThat(resultCount).isEqualTo(0L);
    }
}
