package com.lezardino.mybank.dao;

import com.lezardino.mybank.constantes.Constantes;
import com.lezardino.mybank.enumeration.EtatOperation;
import com.lezardino.mybank.enumeration.TypeOperation;
import com.lezardino.mybank.modele.Categorie;
import com.lezardino.mybank.modele.Compte;
import com.lezardino.mybank.modele.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Classe de test des méthode mongo de la collection operation
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/appcontext.xml" })
public class TestOperationDao {

    /** bean d'accès à la base mongo */
    @Autowired
    private OperationDao operationDao;

    /** bean d'accès à la base mongo */
    @Autowired
    private CompteDao compteDao;

    /** bean d'accès à la base mongo */
    @Autowired
    private CategorieDao categorieDao;

    /** bean d'accès à la base mongo */
    @Autowired
    private transient MongoOperations mongoOperations;

    /** Initialisation de la collection operation */
    @Before
    public void setUp() {
        // initialisation de la base de données
        mongoOperations.dropCollection(Constantes.CollectionsMongo.OPERATION);
        mongoOperations.dropCollection(Constantes.CollectionsMongo.COMPTE);
        mongoOperations.dropCollection(Constantes.CollectionsMongo.CATEGORIE);
    }

    @Test
    public void testEnregistrerRecupererOperationDao() {

        String libelleCompte = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(125.50);
        Compte compte = new Compte(libelleCompte, proprietaire, solde);
        compteDao.enregistrerCompte(compte);

        Categorie categorie = new Categorie("course", new BigDecimal(0));
        categorieDao.enregistrerCategorie(categorie);

        BigDecimal montant = new BigDecimal(15.25);

        LocalDate date = LocalDate.of(2015, 01, 01);

        String libelleOperation = "test enregistrement";
        Operation operationAttendu = new Operation(compte, categorie, montant, libelleOperation, TypeOperation.DEBIT, date);

        operationDao.enregistrerOperation(operationAttendu);
        Operation operation = operationDao.recupererOperation(operationAttendu.getIdOperation());
        assertThat(operation).isNotNull();
        assertThat(operation.getIdOperation()).isEqualTo(operationAttendu.getIdOperation());
        assertThat(operation.getCompte()).isNotNull();
        assertThat(operation.getCompte()).isEqualTo(compte);
        assertThat(operation.getCategorie()).isNotNull();
        assertThat(operation.getCategorie().getLibelle()).isEqualTo(categorie.getLibelle());
        assertThat(operation.getMontant()).isEqualTo(montant);
        assertThat(operation.getLibelle()).isEqualTo(libelleOperation);
        assertThat(operation.getTypeOperation()).isEqualTo(TypeOperation.DEBIT);
        assertThat(operation.getDate()).isEqualTo(date);
        assertThat(operation.getEtatOperation()).isEqualTo(EtatOperation.ATTENTE_PRELEVEMENT);
        assertThat(operation.toString()).isEqualTo(
                "Operation{idOperation='" + operation.getIdOperation()
                        + "', compte=Compte{idCompte='" + compte.getIdCompte() + "', libelle='LivretA'"
                        + ", proprietaire='Pierre', solde='125,50'}, categorie=Categorie{libelle='course', solde='0,00'}"
                        + ", montant='15,25', libelle='test enregistrement', typeOperation=DEBIT, date=2015-01-01"
                        + ", etatOperation=ATTENTE_PRELEVEMENT}");
    }

    @Test
    public void testNombreOperationDao() {

        String libelleCompte = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(125.50);
        Compte compte1 = new Compte(libelleCompte, proprietaire, solde);
        compte1.setIdCompte("1");

        Compte compte2 = new Compte(libelleCompte, proprietaire, solde);
        compte2.setIdCompte("2");

        Categorie categorie = new Categorie("course", new BigDecimal(0));

        BigDecimal montant = new BigDecimal(15.25);

        LocalDate date = LocalDate.of(2015, 01, 01);

        String libelleOperation = "test enregistrement";
        Operation operationAttendu1 = new Operation(compte1, categorie, montant, libelleOperation, TypeOperation.DEBIT, date);
        Operation operationAttendu2 = new Operation(compte2, categorie, montant, libelleOperation, TypeOperation.DEBIT, date);
        Operation operationAttendu3 = new Operation(compte2, categorie, montant, libelleOperation, TypeOperation.DEBIT, date);

        operationDao.enregistrerOperation(operationAttendu1);
        operationDao.enregistrerOperation(operationAttendu2);
        operationDao.enregistrerOperation(operationAttendu3);

        Long resultCount = operationDao.nombreOperation();
        assertThat(resultCount).isEqualTo(3L);

        resultCount = operationDao.nombreOperation(compte1.getIdCompte());
        assertThat(resultCount).isEqualTo(1L);

        resultCount = operationDao.nombreOperation(compte2.getIdCompte());
        assertThat(resultCount).isEqualTo(2L);
    }

    @Test
    public void testSupprimerOperationDao() {

        String libelleCompte = "LivretA";
        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(125.50);
        Compte compte = new Compte(libelleCompte, proprietaire, solde);
        compte.setIdCompte("0");

        Categorie categorie = new Categorie("course", new BigDecimal(0));

        BigDecimal montant = new BigDecimal(15.25);

        LocalDate date = LocalDate.of(2015, 01, 01);

        String libelleOperation = "test enregistrement";
        Operation operationAttendu = new Operation(compte, categorie, montant, libelleOperation, TypeOperation.DEBIT, date);


        operationDao.enregistrerOperation(operationAttendu);
        Long resultCount = operationDao.nombreOperation();
        assertThat(resultCount).isEqualTo(1L);
        operationDao.supprimerOperation(operationAttendu);
        resultCount = operationDao.nombreOperation();
        assertThat(resultCount).isEqualTo(0L);
    }

    @Test
    public void testListerOperationDao() {

        String proprietaire = "Pierre";
        BigDecimal solde = new BigDecimal(125.50);
        Compte compte1 = new Compte("LivretA", proprietaire, solde);
        Compte compte2 = new Compte("CCP", proprietaire, solde);
        compteDao.enregistrerCompte(compte1);
        compteDao.enregistrerCompte(compte2);

        Categorie categorie = new Categorie("course", new BigDecimal(0));
        categorieDao.enregistrerCategorie(categorie);

        BigDecimal montant = new BigDecimal(15.25);

        LocalDate date = LocalDate.of(2015, 01, 01);

        Operation operationAttendu1 = new Operation(compte1, categorie, montant, "test enregistrement 1", TypeOperation.DEBIT, date);
        Operation operationAttendu2 = new Operation(compte1, categorie, montant, "test enregistrement 2", TypeOperation.DEBIT, date);
        Operation operationAttendu3 = new Operation(compte2, categorie, montant, "test enregistrement 3", TypeOperation.DEBIT, date);


        operationDao.enregistrerOperation(operationAttendu1);
        operationDao.enregistrerOperation(operationAttendu2);
        operationDao.enregistrerOperation(operationAttendu3);

        List<Operation> listeOperation = operationDao.listerOperations(Direction.ASC);
        assertThat(listeOperation.size()).isEqualTo(3);

        listeOperation = operationDao.listerOperations(1,2, Direction.ASC);
        assertThat(listeOperation.size()).isEqualTo(2);
        assertThat(listeOperation.get(0)).isEqualTo(operationAttendu2);
        assertThat(listeOperation.get(1)).isEqualTo(operationAttendu3);

        listeOperation = operationDao.listerOperationsByCompte(compte1.getIdCompte(), Direction.ASC);
        assertThat(listeOperation.size()).isEqualTo(2);

        listeOperation = operationDao.listerOperationsByCompte(compte1.getIdCompte(), 1,2, Direction.ASC);
        assertThat(listeOperation.size()).isEqualTo(1);
        assertThat(listeOperation.get(0)).isEqualTo(operationAttendu2);
    }
}
