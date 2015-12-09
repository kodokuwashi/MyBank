package com.lezardino.mybank.dao;

import com.lezardino.mybank.constantes.Constantes;
import com.lezardino.mybank.enumeration.EtatVirement;
import com.lezardino.mybank.modele.Compte;
import com.lezardino.mybank.modele.Virement;
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
 * Classe de test des méthode mongo de la collection virement
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/appcontext.xml" })
public class TestVirementDao {

    /** bean d'accès à la base mongo */
    @Autowired
    private VirementDao virementDao;

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
        mongoOperations.dropCollection(Constantes.CollectionsMongo.VIREMENT);
    }

    @Test
    public void testEnregistrerRecupererVirementDao() {

        String libelle = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte1 = new Compte(libelle, proprietaire, solde);
        Compte compte2 = new Compte(libelle, proprietaire, solde);

        compteDao.enregistrerCompte(compte1);
        compteDao.enregistrerCompte(compte2);

        Virement virementAttendu = new Virement(compte1, compte2, new BigDecimal(10.25));
        virementDao.enregistrerVirement(virementAttendu);

        Virement virement= virementDao.recupererVirement(virementAttendu.getIdVirement());
        assertThat(virement).isNotNull();
        assertThat(virement.getIdVirement()).isEqualTo(virementAttendu.getIdVirement());
        assertThat(virement.getCompteCrediteur()).isEqualTo(compte2);
        assertThat(virement.getCompteDebiteur()).isEqualTo(compte1);
        assertThat(virement.getMontant()).isEqualTo(new BigDecimal(10.25));
        assertThat(virement.getEtat()).isEqualTo(EtatVirement.CREE);
        assertThat(virement.toString()).isEqualTo(
                "Virement{idVirement='" + virement.getIdVirement()
                + "', compteDebiteur=Compte{idCompte='" + compte1.getIdCompte()
                + "', libelle='LivretA', proprietaire='Pierre', solde='10,50'}"
                + ", compteCrediteur=Compte{idCompte='" + compte2.getIdCompte()
                + "', libelle='LivretA', proprietaire='Pierre', solde='10,50'}, montant='10,25', etat=CREE}");

    }

    @Test
    public void testNombreVirementDao() {

        String libelle = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte1 = new Compte(libelle, proprietaire, solde);
        Compte compte2 = new Compte(libelle, proprietaire, solde);

        Virement virement1 = new Virement(compte1, compte2, new BigDecimal(10.25));
        Virement virement2 = new Virement(compte1, compte2, new BigDecimal(11.25));

        virementDao.enregistrerVirement(virement1);
        virementDao.enregistrerVirement(virement2);
        Long resultCount = virementDao.nombreVirements();
        assertThat(resultCount).isEqualTo(2L);
    }

    @Test
    public void testListerVirementDao() {

        String libelle = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte1 = new Compte(libelle, proprietaire, solde);
        Compte compte2 = new Compte(libelle, proprietaire, solde);

        Virement virement1 = new Virement(compte1, compte2, new BigDecimal(10.25));
        Virement virement2 = new Virement(compte1, compte2, new BigDecimal(11.25));
        Virement virement3 = new Virement(compte1, compte2, new BigDecimal(12.25));

        virementDao.enregistrerVirement(virement1);
        virementDao.enregistrerVirement(virement2);
        virementDao.enregistrerVirement(virement3);
        List<Virement> listeVirement = virementDao.listerVirements(Direction.ASC);
        assertThat(listeVirement).isNotNull();
        assertThat(listeVirement.size()).isEqualTo(3);

        listeVirement = virementDao.listerVirements(1, 2, Direction.ASC);
        assertThat(listeVirement).isNotNull();
        assertThat(listeVirement.size()).isEqualTo(2);
        assertThat(listeVirement.get(0)).isEqualTo(virement2);
        assertThat(listeVirement.get(1)).isEqualTo(virement3);
    }

    @Test
    public void testSupprimerVirementDao() {

        String libelle = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(10.50);
        Compte compte1 = new Compte(libelle, proprietaire, solde);
        Compte compte2 = new Compte(libelle, proprietaire, solde);

        Virement virement = new Virement(compte1, compte2, new BigDecimal(10.25));

        virementDao.enregistrerVirement(virement);
        Long resultCount = virementDao.nombreVirements();
        assertThat(resultCount).isEqualTo(1L);
        virementDao.supprimerVirement(virement);
        resultCount = virementDao.nombreVirements();
        assertThat(resultCount).isEqualTo(0L);
    }
}
