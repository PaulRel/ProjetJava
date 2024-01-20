package representation;
import org.junit.jupiter.api.Test;

import jeu.Game;
import univers.ErrDurabilite;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

/**
 * Classe de tests unitaires pour la classe DecisionNode.
 * Elle vise à tester les fonctionnalites associees à la manipulation des nœuds de decision.
 */
public class DecisionNodeTest {
	private DecisionNode decisionNode;
	/**
     * Initialise la decisionNode avant chaque test.
     *
     * @throws Exception Si une exception survient lors de la configuration.
     */
    
    @BeforeEach
    public void setUp() throws Exception{
        decisionNode = new DecisionNode("Description");
    }
    /**
     * Teste l'ajout de choix à un nœud de decision.
     * Verifie si les choix sont correctement ajoutes au nœud de decision.
     */
    @Test
    public void testAddChoice() {
    	//Arrange
        decisionNode = new DecisionNode("Test Node Description");
        
        // Ajout de choix de decision
        //Act
        decisionNode.addChoice("Choix 1", new DecisionNode("Next Node 1"), "Result 1");
        decisionNode.addChoice("Choix 2", new DecisionNode("Next Node 2"), "Result 2");
        //Assert
        assertEquals(2, decisionNode.getDecisionChoices().size());
        assertEquals("Choix 1",decisionNode.getDecisionChoices().get(0).getDescription());
        assertEquals("Choix 2",decisionNode.getDecisionChoices().get(1).getDescription());
        assertEquals("Result 1",decisionNode.getDecisionChoices().get(0).getResult());
    }
    /**
     * Teste la methode chooseNext pour un nœud de decision.
     * Verifie si le choix suivant correspond au nœud attendu après avoir choisi une option.
     *
     * @throws ErrDurabilite En cas d'erreur de durabilite.
     */
    @Test
    public void testChooseNext() throws ErrDurabilite {
    	//Initialisation des noeuds de test
    	//Arrange
        decisionNode = new DecisionNode("Test Node Description");
        decisionNode.addChoice("Choix 1", new TerminalNode("Next Node 1"), "Result 1");
        decisionNode.addChoice("Choix 2", new TerminalNode("Next Node 2"), "Result 2");

        // Simuler un choix valide
        //Act
        Node noeudsuivant = decisionNode.chooseNext(new Game(true, false));
        
        // Verifier si le nœud choisi correspond au nœud suivant attendu
        //Assert
        assertTrue(noeudsuivant instanceof TerminalNode);
        assertEquals("Next Node 1", ((TerminalNode) noeudsuivant).getDescription());
    }
}