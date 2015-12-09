package com.lezardino.mybank.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import com.lezardino.mybank.constantes.Constantes;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lezardino.mybank.modele.Compte;

/**
 * Classe de test des méthode mongo de la collection comptes
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/appcontext.xml" })
public class TestCompteDao {

    /** bean d'accès à la base mongo */
    @Autowired
    private CompteDao compteDao;

    /** bean d'accès à la base mongo */
    @Autowired
    private transient MongoOperations mongoOperations;

    /** Initialisation de la collection comptes */
    @Before
    public void setUp() {
        // initialisation de la base de données
        mongoOperations.dropCollection(Constantes.CollectionsMongo.COMPTE);
    }

    @Test
    public void testEnregistrerRecupererCompteDao() {

        String libelle = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compteAttendu = new Compte(libelle, proprietaire, solde);

        compteDao.enregistrerCompte(compteAttendu);
        Compte compte = compteDao.recupererCompteById(compteAttendu.getIdCompte());
        assertThat(compte).isNotNull();
        assertThat(compte.getIdCompte()).isEqualTo(compteAttendu.getIdCompte());
        assertThat(compte.getLibelle()).isEqualTo(libelle);
        assertThat(compte.getProprietaire()).isEqualTo(proprietaire);
        assertThat(compte.getSolde()).isEqualTo(solde);
        assertThat(compte.toString()).isEqualTo(
                "Compte{idCompte='" + compte.getIdCompte()
                + "', libelle='LivretA', proprietaire='Pierre', solde='10,50'}");

        compte = compteDao.recupererCompte(libelle, proprietaire);
        assertThat(compte).isNotNull();
        assertThat(compte.getIdCompte()).isEqualTo(compteAttendu.getIdCompte());
    }

    @Test
    public void testNombreCompteDao() {

        String libelle = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte = new Compte(libelle, proprietaire, solde);
        Compte compte2 = new Compte(libelle, proprietaire, solde);

        compteDao.enregistrerCompte(compte);
        compteDao.enregistrerCompte(compte2);
        Long resultCount = compteDao.nombreComptes();
        assertThat(resultCount).isEqualTo(2L);
    }

    @Test
    public void testListerCompteDao() {

        String libelle = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte1 = new Compte(libelle, proprietaire, solde);
        Compte compte2 = new Compte(libelle, proprietaire, solde);
        Compte compte3 = new Compte(libelle, proprietaire, solde);

        compteDao.enregistrerCompte(compte1);
        compteDao.enregistrerCompte(compte2);
        compteDao.enregistrerCompte(compte3);
        List<Compte> listeCompte = compteDao.listerComptes(Direction.ASC);
        assertThat(listeCompte).isNotNull();
        assertThat(listeCompte.size()).isEqualTo(3);

        listeCompte = compteDao.listerComptes(1, 2, Direction.ASC);
        assertThat(listeCompte).isNotNull();
        assertThat(listeCompte.size()).isEqualTo(2);
        assertThat(listeCompte.get(0)).isEqualTo(compte2);
        assertThat(listeCompte.get(1)).isEqualTo(compte3);
    }

    @Test
    public void testSupprimerCompteDao() {

        String libelle = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte = new Compte(libelle, proprietaire, solde);

        compteDao.enregistrerCompte(compte);
        Long resultCount = compteDao.nombreComptes();
        assertThat(resultCount).isEqualTo(1L);
        compteDao.supprimerCompte(compte);
        resultCount = compteDao.nombreComptes();
        assertThat(resultCount).isEqualTo(0L);
    }
}
