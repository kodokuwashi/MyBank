package com.lezardino.mybank.metier;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import com.lezardino.mybank.dao.CompteDao;
import com.lezardino.mybank.dao.impl.CompteDaoImpl;
import com.lezardino.mybank.erreur.ErreurFonctionnelle;
import com.lezardino.mybank.metier.impl.ServiceCompteImpl;
import com.lezardino.mybank.modele.Compte;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/testappcontext.xml" })
public class TestServiceCompte {

    @Rule
    public transient ExpectedException thrown = ExpectedException.none();

    @Autowired
    @InjectMocks
    private transient ServiceCompteImpl serviceCompte;

    @Mock
    private final transient CompteDao compteDao = new CompteDaoImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    /**
     * Test d'enregistrement d'un compte
     */
    @Test
    public void testEnregistrerCompte() {

        String libelle = "LivretA";
        String proprietaire = "Testeur";
        BigDecimal solde = new BigDecimal("10.50");
        Compte compte = new Compte(libelle, proprietaire, solde);

        serviceCompte.enregistrerCompte(compte);

        Mockito.verify(compteDao).enregistrerCompte(Mockito.eq(compte));
    }

    /**
     * Test de suppression d'un compte
     */
    @Test
    public void testSupprimerCompte() {

        String libelle = "LivretA";
        String proprietaire = "Testeur";
        BigDecimal solde = new BigDecimal("10.50");
        Compte compte = new Compte(libelle, proprietaire, solde);

        serviceCompte.supprimerCompte(compte);

        Mockito.verify(compteDao).supprimerCompte(Mockito.eq(compte));
    }

    /**
     * Test de recupération d'un compte inexistant
     *
     * @throws ErreurFonctionnelle : Erreur metier
     */
    @Test
    public void testRecupererCompteInexistant() throws ErreurFonctionnelle {

        thrown.expect(ErreurFonctionnelle.class);
        thrown.expectMessage("Le compte d'identifiant 1234 n'existe pas");

        Mockito.when(compteDao.recupererCompteById(Mockito.any(String.class))).thenReturn(null);
        serviceCompte.recupererCompte("1234");

    }

    /**
     * Test de recupération d'un compte existant
     *
     * @throws ErreurFonctionnelle : Erreur metier
     */
    @Test
    public void testRecupererCompteExistant() throws ErreurFonctionnelle {

        String libelle = "LivretA";
        String proprietaire = "Testeur";
        BigDecimal solde = new BigDecimal("10.50");
        Compte compteAttendu = new Compte(libelle, proprietaire, solde);

        Mockito.when(compteDao.recupererCompteById(Mockito.any(String.class))).thenReturn(compteAttendu);
        Compte compte = serviceCompte.recupererCompte("1234");

        assertThat(compte.getLibelle()).isEqualTo(libelle);
        assertThat(compte.getProprietaire()).isEqualTo(proprietaire);
        assertThat(compte.getSolde()).isEqualTo(solde);
    }

    /**
     * Test de recupération de la liste des comptes existant
     *
     * @throws ErreurFonctionnelle : Erreur metier
     */
    @Test
    public void testListAll() throws ErreurFonctionnelle {

        String libelle = "LivretA";
        String proprietaire = "Testeur";
        BigDecimal solde = new BigDecimal("10.50");
        Compte compteAttendu = new Compte(libelle, proprietaire, solde);
        compteAttendu.setIdCompte("2");

        String libelle2 = "CCP";
        BigDecimal solde2 = new BigDecimal("5.25");
        Compte compteAttendu2 = new Compte(libelle2, proprietaire, solde2);
        compteAttendu2.setIdCompte("3");

        List<Compte> listeCompteAttendu = new ArrayList<>();
        listeCompteAttendu.add(compteAttendu);
        listeCompteAttendu.add(compteAttendu2);

        Mockito.when(compteDao.listerComptes(Mockito.any(int.class), Mockito.any(int.class), Mockito.any(Direction.class)))
                .thenReturn(listeCompteAttendu);
        Mockito.when(compteDao.nombreComptes()).thenReturn(new Long(4));
        RSList<Compte> rsListeCompte = serviceCompte.listAll(1, 2, "ASC");

        assertThat(rsListeCompte).isNotNull();
        assertThat(rsListeCompte.getResultsCount()).isEqualTo(new Long(4));

        List<Compte> listeCompte = rsListeCompte.getItems();
        assertThat(listeCompte.size()).isEqualTo(2);
        assertThat(listeCompte.get(0).getIdCompte()).isEqualTo("2");
        assertThat(listeCompte.get(1).getIdCompte()).isEqualTo("3");



    }
}
