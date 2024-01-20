package univers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;


/**
 * Classe de tests pour la classe Inventaire.
 * Elle verifie les fonctionnalites d'ajout et de retrait d'objets (armes et outils) d'un inventaire.
 */
public class InventaireTest {
    private Inventaire inventaire;
    
    /**
     * Initialise un nouvel inventaire avant chaque test.
     */
    
    @BeforeEach
    void setUp() {
        inventaire = new Inventaire();
    }

    
    /**
     * Teste l'ajout et le retrait d'un outil à l'inventaire.
     * avec assertTrue et assertFalse
     */
    @Test
    void ajouterEtRetirerOutil() {
        Outils outil = new Outils("Tournevis", 2, 2);
        inventaire.ajouterOutil(outil);
        assertTrue(inventaire.getListeOutils().contains(outil));
        
        inventaire.retirerOutil(outil);
        assertFalse(inventaire.getListeOutils().contains(outil));
    }

    /**
     * Teste l'ajout et le retrait d'une arme à l'inventaire.
     * differement que pour le test pour Outil
     * avec asserEquals cette fois-ci
     */
    @Test
    void ajouterEtRetirerArme() {
        Armes arme = new Armes("Épée", 10);
        inventaire.ajouterArme(arme);
        assertEquals(inventaire.getListeArmes().contains(arme), true);
        
        inventaire.retirerArme(arme);
        assertEquals(inventaire.getListeArmes().contains(arme), false);
    }

    
    /**
     * Teste le retrait d'un outil inexistant de l'inventaire.
     * Une exception IllegalArgumentException est attendue.
     */
    @Test
    void retirerOutilInexistant() {
        Outils outil = new Outils("Clé", 2, 2);
        assertThrows(IllegalArgumentException.class, () -> inventaire.retirerOutil(outil));
        
    }
    
    /**
     * Teste le retrait d'une arme inexistante de l'inventaire.
     * Une exception IllegalArgumentException est attendue.
     */
    @Test
    void retirerArmeInexistante() {
        Armes arme = new Armes("Arc", 5);
        assertThrows(IllegalArgumentException.class, () -> inventaire.retirerArme(arme));
    }
    
    @AfterEach
    void cleanUp() {
        inventaire.getListeArmes().clear();
        inventaire.getListeOutils().clear();
    }
}