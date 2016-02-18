package com.lezardino.mybank.metier;

import com.lezardino.mybank.dao.OperationDao;
import com.lezardino.mybank.dao.impl.OperationDaoImpl;
import com.lezardino.mybank.enumeration.TypeOperation;
import com.lezardino.mybank.erreur.ErreurFonctionnelle;
import com.lezardino.mybank.metier.impl.ServiceOperationImpl;
import com.lezardino.mybank.modele.Categorie;
import com.lezardino.mybank.modele.Compte;
import com.lezardino.mybank.modele.Operation;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/testappcontext.xml" })
public class TestServiceOperation {

    @Rule
    public transient ExpectedException thrown = ExpectedException.none();

    @Autowired
    @InjectMocks
    private transient ServiceOperationImpl serviceOperation;

    @Mock
    private final transient OperationDao operationDao = new OperationDaoImpl();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    /**
     * Test d'enregistrement d'une operation
     */
    @Test
    public void testEnregistrerOperation() {

        String libelle = "LivretA";
        String proprietaire = "Testeur";
        BigDecimal solde = new BigDecimal("10.50");
        Compte compte = new Compte(libelle, proprietaire, solde);
        compte.setIdCompte("1234");

        Categorie categorie = new Categorie("Categorie", new BigDecimal("5.25"));
        BigDecimal montant = new BigDecimal("3.75");
        String libelleOp = "Test enregistrer opeation";
        LocalDate date = LocalDate.of(2016,02,18);
        Operation operation = new Operation(compte, categorie, montant, libelleOp, TypeOperation.CREDIT, date);

        serviceOperation.enregistrerOperation(operation);

        Mockito.verify(operationDao).enregistrerOperation(Mockito.eq(operation));
    }

    /**
     * Test de suppression d'une operation
     */
    @Test
    public void testSupprimerOperation() {

        String libelle = "LivretA";
        String proprietaire = "Testeur";
        BigDecimal solde = new BigDecimal("10.50");
        Compte compte = new Compte(libelle, proprietaire, solde);
        compte.setIdCompte("1234");

        Categorie categorie = new Categorie("Categorie", new BigDecimal("5.25"));
        BigDecimal montant = new BigDecimal("3.75");
        String libelleOp = "Test enregistrer operation";
        LocalDate date = LocalDate.of(2016,02,18);
        Operation operation = new Operation(compte, categorie, montant, libelleOp, TypeOperation.CREDIT, date);

        serviceOperation.supprimerOperation(operation);

        Mockito.verify(operationDao).supprimerOperation(Mockito.eq(operation));
    }

    /**
     * Test de recupération d'une operation inexistante
     *
     * @throws ErreurFonctionnelle : Erreur metier
     */
    @Test
    public void testRecupererOperationInexistante() throws ErreurFonctionnelle {

        thrown.expect(ErreurFonctionnelle.class);
        thrown.expectMessage("L'operation d'identifiant 1234 n'existe pas");

        Mockito.when(operationDao.recupererOperation(Mockito.any(String.class))).thenReturn(null);
        serviceOperation.recupererOperation("1234");

    }

    /**
     * Test de recupération d'une operation existante
     *
     * @throws ErreurFonctionnelle : Erreur metier
     */
    @Test
    public void testRecupererOperationExistante() throws ErreurFonctionnelle {

        String libelle = "LivretA";
        String proprietaire = "Testeur";
        BigDecimal solde = new BigDecimal("10.50");
        Compte compte = new Compte(libelle, proprietaire, solde);
        compte.setIdCompte("1234");

        Categorie categorie = new Categorie("Categorie", new BigDecimal("5.25"));
        BigDecimal montant = new BigDecimal("3.75");
        String libelleOp = "Test enregistrer operation";
        LocalDate date = LocalDate.of(2016,02,18);
        Operation operationAttendu = new Operation(compte, categorie, montant, libelleOp, TypeOperation.CREDIT, date);

        Mockito.when(operationDao.recupererOperation(Mockito.any(String.class))).thenReturn(operationAttendu);
        Operation operation = serviceOperation.recupererOperation("5678");

        assertThat(operation.getCompte().getIdCompte()).isEqualTo("1234");
        assertThat(operation.getCategorie()).isEqualTo(categorie);
        assertThat(operation.getMontant()).isEqualTo(montant);
        assertThat(operation.getLibelle()).isEqualTo(libelleOp);
        assertThat(operation.getTypeOperation()).isEqualTo(TypeOperation.CREDIT);
        assertThat(operation.getDate()).isEqualTo(date);

    }

    /**
     * Test de recupération de la liste des operations existante
     *
     * @throws ErreurFonctionnelle : Erreur metier
     */
    @Test
    public void testListAll() throws ErreurFonctionnelle {

        String libelle = "LivretA";
        String proprietaire = "Testeur";
        BigDecimal solde = new BigDecimal("10.50");
        Compte compte = new Compte(libelle, proprietaire, solde);
        compte.setIdCompte("2");

        Categorie categorie = new Categorie("Categorie", new BigDecimal("5.25"));
        BigDecimal montant = new BigDecimal("3.75");
        String libelleOp = "Test enregistrer operation";
        LocalDate date = LocalDate.of(2016,02,18);
        Operation operationAttendu = new Operation(compte, categorie, montant, libelleOp, TypeOperation.CREDIT, date);
        operationAttendu.setIdOperation("2");
        Operation operationAttendu2 = new Operation(compte, categorie, montant, libelleOp, TypeOperation.CREDIT, date);
        operationAttendu2.setIdOperation("3");

        List<Operation> listeOperationAttendu = new ArrayList<>();
        listeOperationAttendu.add(operationAttendu);
        listeOperationAttendu.add(operationAttendu2);

        Mockito.when(operationDao.listerOperations(Mockito.any(int.class), Mockito.any(int.class), Mockito.any(Direction.class)))
                .thenReturn(listeOperationAttendu);
        Mockito.when(operationDao.nombreOperation()).thenReturn(new Long(4));
        RSList<Operation> rsListeOperation = serviceOperation.listAll(1, 2, "ASC");

        assertThat(rsListeOperation).isNotNull();
        assertThat(rsListeOperation.getResultsCount()).isEqualTo(new Long(4));

        List<Operation> listeOperation = rsListeOperation.getItems();
        assertThat(listeOperation.size()).isEqualTo(2);
        assertThat(listeOperation.get(0).getIdOperation()).isEqualTo("2");
        assertThat(listeOperation.get(1).getIdOperation()).isEqualTo("3");



    }
}
