package com.lezardino.mybank.metier;

import com.lezardino.mybank.dao.BudjetDao;
import com.lezardino.mybank.dao.CompteDao;
import com.lezardino.mybank.dao.impl.BudjetDaoImpl;
import com.lezardino.mybank.dao.impl.CompteDaoImpl;
import com.lezardino.mybank.erreur.ErreurFonctionnelle;
import com.lezardino.mybank.metier.impl.ServiceBudjetImpl;
import com.lezardino.mybank.modele.Budjet;
import com.lezardino.mybank.modele.Compte;
import com.lezardino.mybank.ressource.RSList;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/testappcontext.xml" })
public class TestServiceBudjet {

    @Rule
    public transient ExpectedException thrown = ExpectedException.none();

    @Autowired
    @InjectMocks
    private transient ServiceBudjetImpl serviceBudjet;

    @Mock
    private final transient BudjetDao budjetDao = new BudjetDaoImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    /**
     * Test d'enregistrement d'un budjet
     */
    @Test
    public void testEnregistrerBudjet() {

        String libelle = "LivretA";
        BigDecimal solde = new BigDecimal("10.50");
        String idCompte = "TestBudjet";
        TreeMap<String, BigDecimal> soldeParCompte= new TreeMap<>();
        soldeParCompte.put(idCompte, solde);
        Budjet budjet = new Budjet(libelle, soldeParCompte);

        serviceBudjet.enregistrerBudjet(budjet);

        Mockito.verify(budjetDao).enregistrerBudjet(Mockito.eq(budjet));
    }

    /**
     * Test de suppression d'un budjet
     */
    @Test
    public void testSupprimerBudjet() {

        String libelle = "LivretA";
        BigDecimal solde = new BigDecimal("10.50");
        String idCompte = "TestBudjet";
        TreeMap<String, BigDecimal> soldeParCompte= new TreeMap<>();
        soldeParCompte.put(idCompte, solde);
        Budjet budjet = new Budjet(libelle, soldeParCompte);

        serviceBudjet.supprimerBudjet(budjet);

        Mockito.verify(budjetDao).supprimerBudjet(Mockito.eq(budjet));
    }

    /**
     * Test de recupération d'un budjet inexistant
     *
     * @throws ErreurFonctionnelle : Erreur metier
     */
    @Test
    public void testRecupererBudjetInexistant() throws ErreurFonctionnelle {

        thrown.expect(ErreurFonctionnelle.class);
        thrown.expectMessage("Le budjet d'identifiant 1234 n'existe pas");

        Mockito.when(budjetDao.recupererBudjet(Mockito.any(String.class))).thenReturn(null);
        serviceBudjet.recupererBudjet("1234");

    }

    /**
     * Test de recupération d'un budjet existant
     *
     * @throws ErreurFonctionnelle : Erreur metier
     */
    @Test
    public void testRecupererBudjetExistant() throws ErreurFonctionnelle {

        String libelle = "LivretA";
        BigDecimal solde = new BigDecimal("10.50");
        String idCompte = "TestBudjet";
        TreeMap<String, BigDecimal> soldeParCompteAttendu= new TreeMap<>();
        soldeParCompteAttendu.put(idCompte, solde);
        Budjet budjetAttendu = new Budjet(libelle, soldeParCompteAttendu);

        Mockito.when(budjetDao.recupererBudjet(Mockito.any(String.class))).thenReturn(budjetAttendu);
        Budjet budjet = serviceBudjet.recupererBudjet("1234");

        assertThat(budjet.getLibelle()).isEqualTo(libelle);
        TreeMap<String, BigDecimal> soldeParCompte = budjet.getSoldeParCompte();
        assertThat(soldeParCompte).isNotNull();
        assertThat(soldeParCompte.containsKey("TestBudjet")).isTrue();
        assertThat(soldeParCompte.get("TestBudjet")).isEqualTo(solde);
    }

    /**
     * Test de recupération de la liste des budjets existant
     *
     * @throws ErreurFonctionnelle : Erreur metier
     */
    @Test
    public void testListAll() throws ErreurFonctionnelle {

        String libelle = "2";
        BigDecimal solde = new BigDecimal("10.50");
        String idCompte = "TestBudjet";
        TreeMap<String, BigDecimal> soldeParCompteAttendu= new TreeMap<>();
        soldeParCompteAttendu.put(idCompte, solde);
        Budjet budjetAttendu = new Budjet(libelle, soldeParCompteAttendu);

        String libelle2 = "3";
        Budjet budjetAttendu2 = new Budjet(libelle2, soldeParCompteAttendu);

        List<Budjet> listeBudjetAttendu = new ArrayList<>();
        listeBudjetAttendu.add(budjetAttendu);
        listeBudjetAttendu.add(budjetAttendu2);

        Mockito.when(budjetDao.listerBudjet(Mockito.any(int.class), Mockito.any(int.class), Mockito.any(Direction.class)))
                .thenReturn(listeBudjetAttendu);
        Mockito.when(budjetDao.nombreBudjets()).thenReturn(new Long(4));
        RSList<Budjet> rsListeBudjet = serviceBudjet.listAll(1, 2, "ASC");

        assertThat(rsListeBudjet).isNotNull();
        assertThat(rsListeBudjet.getResultsCount()).isEqualTo(new Long(4));

        List<Budjet> listeBudjet = rsListeBudjet.getItems();
        assertThat(listeBudjet.size()).isEqualTo(2);
        assertThat(listeBudjet.get(0).getLibelle()).isEqualTo("2");
        assertThat(listeBudjet.get(1).getLibelle()).isEqualTo("3");



    }
}
