package representation;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import jeu.Game;
import univers.Armes;
import univers.Inventaire;
import univers.Outils;


/**
 * Represente un nœud de chance (ChanceNode) dans la structure du jeu.
 * 
 * Ce nœud peut soit offrir plusieurs choix menant à des resultats aleatoire, (si il a des elements dans la liste decisionChoices associes)
 * 
 * soit offre directement un resultat aleatoire après un noeud de decision (si il n'a qu'une liste ChanceCsq associee).
 *
 */
public class ChanceNode extends InnerNode{
	private static final long serialVersionUID = 1L;
	/**
	 * Liste des possibilites pour une situation donnee.
	 * Cette liste contient des objets de type ChancePossibilite.
	 * Elle represente les differentes issues possibles pour une situation
	 * @see ChancePossibilite
	 */
	private ArrayList<ChancePossibilite> ChanceCsq = new ArrayList<>();
	/**
	 * Represente une liste de choix de decision.
	 * Cette liste contient des objets de type DecisionChoix, chacun representant
	 * un choix disponible pour une decision à prendre.
	 * @see DecisionChoix
	 */
	private ArrayList<DecisionChoix> decisionChoices = new ArrayList<>();
	
	
	/**
	 * Constructeur pour creer un nœud de chance avec une description.
	 *
	 * @param description La description du nœud de chance.
	 */
	public ChanceNode (String description) {
		super(description);
	}
	
	
	/**
	 * Choisit le prochain nœud en fonction des possibilites de chance disponibles.
	 * Affiche le nœud actuel et selectionne aleatoirement le prochain nœud en fonction des choix possibles.
	 * Si un nouvel objet arme ou outil est disponible en recompense dans le nœud de chance selectionne, il est ajoute à l'inventaire.
	 *
	 *
	 * @return Le prochain nœud à explorer.
	 * @see Inventaire#ajouterArme(Armes)
	 * @see Inventaire#ajouterOutil(Outils)
	 */
	@Override
	public Node chooseNext(Game g) {
		super.display();
		//Verification si ChanceNode possède des choix ou non. 
		//Si c'est le cas, on les affiche
		//Sinon on choisit directement une consequence au hasard
		if (this.getDecisionChoices()!= null && !this.getDecisionChoices().isEmpty()){
			System.out.println("Que voulez-vous choisir?");
			Scanner scanner =new Scanner(System.in);
			while (true) {
				// Affiche les choix disponibles pour le joueur
				for (int i = 0; i < decisionChoices.size(); i++) {
					System.out.println((i + 1) + ". " + decisionChoices.get(i).getDescription());
				}
	    		if (scanner.hasNextInt()){
	    			int choix = scanner.nextInt();
	    				
	    			if (choix >= 1 && choix <= decisionChoices.size()) {
	    				System.out.println("\n------------------------------------------------------------------\n");
	    				break;
	    				//scanner.close(); // Ne pas fermer le scanner pour eviter des problèmes potentiels	
	    			} 
	    			else {System.out.println("Veuillez entrer un nombre entre 1 et "+decisionChoices.size());}
	    			}
	    		else{System.out.println("Veuillez entrer un choix valide");
	    		scanner.next();}
	        }
		}
		//Choix d'un resultat au hasard
		Random random = new Random();
	    int i = random.nextInt(ChanceCsq.size());
	    System.out.println(ChanceCsq.get(i).getDescription());
	    if (ChanceCsq.get(i).getNvArme() != null) {
			g.getInventaire().ajouterArme(ChanceCsq.get(i).getNvArme());
			System.out.println("\nVous obtenez en récompense un(e) "+ChanceCsq.get(i).getNvArme());	
        }
	    if (ChanceCsq.get(i).getNvOutil() != null) {
	    	g.getInventaire().ajouterOutil(ChanceCsq.get(i).getNvOutil());
	    	System.out.println("\nVous obtenez en récompense un(e) "+ChanceCsq.get(i).getNvOutil());	
        }
	    if (ChanceCsq.get(i).getExpbonus() > 0) {
			g.getElementJoueur().gagnerExp(ChanceCsq.get(i).getExpbonus(), g, true);
		}
		return ChanceCsq.get(i).getNextNode();
    }
	
	
	/**
	 * Ajoute une possibilite de chance à la liste des possibilites de chance.
	 *
	 * @param description La description de la possibilite de chance à ajouter.
	 * @param nextNode    Le prochain nœud associe à cette possibilite de chance.
	 * 
	 * @see ChancePossibilite
	 */
	public void addCsq(String description, Node nextNode) {
		ChanceCsq.add(new ChancePossibilite(description, nextNode));
    }
	
	/**
	 * Ajoute une possibilite de chance à la liste des possibilites de chance avec des recompenses d'arme et d'outil.
	 *
	 * @param description La description de la possibilite de chance à ajouter.
	 * @param nextNode    Le prochain nœud associe à cette possibilite de chance.
	 * @param NvArme      La nouvelle arme associee à cette possibilite de chance, peut être null.
	 * @param NvOutil     Le nouvel outil associe à cette possibilite de chance, peut être null.
	 * @param exp		Le nombre de point d'experience bonus associe à cette possibilite de chance, peut être egal à 0
	 * 
	 */
	public void addCsq(String description, Node nextNode, Armes NvArme, Outils NvOutil, int exp) {
		ChanceCsq.add(new ChancePossibilite(description, nextNode, NvArme, NvOutil, exp));
    }
	
	
	/**
	 * Ajoute un choix à la liste des choix de decision.
	 *
	 * @param choiceDescription La description du choix à ajouter.
	 * 
	 * @see DecisionChoix
	 */
	public void addChoice(String choiceDescription) {
        decisionChoices.add(new DecisionChoix(choiceDescription));
    }
	
	
	/**
	 * Met à jour les boutons choix dans l'interface graphique.
	 * 
	 * Si plus de deux choix sont disponibles pour le nœud, les affecte aux composants correspondants.
	 * 
	 * @param g L'instance de jeu representant l'interface graphique.
	 * 
	 * @see DecisionChoix
	 */
	public void afficherChoixIG(Game g) {	
		g.getChoix1().setText(decisionChoices.get(0).getDescription());
		g.getChoix2().setText(decisionChoices.get(1).getDescription());
		//VERIFIER QU'IL EXISTE PLUS DE 2 CHOIX POUR LE NOEUD
		if (decisionChoices.size()>2) {g.getChoix3().setText(decisionChoices.get(2).getDescription());}
		else {g.getChoix3().setText("");}
		if (decisionChoices.size()>3) {g.getChoix4().setText(decisionChoices.get(3).getDescription());}
		else {g.getChoix4().setText("");}		
	}

	
	/**
	 * Choix du prochain nœud pour l'interface graphique en fonction des possibilites de chance disponibles.
	 * Selectionne aleatoirement le prochain nœud parmi les choix possibles et met à jour l'interface graphique.
	 * Si un nouvel objet arme ou outil est disponible dans le nœud de chance selectionne, il est ajoute à l'inventaire.
	 *
	 * @param g          L'instance de jeu representant l'interface graphique.
	 * @param choix      Le choix effectue par le joueur (1, 2, 3 ou 4).
	 * @return Le prochain nœud à explorer.
	 * 
	 * @see Inventaire#ajouterArme(Armes)
	 * @see Inventaire#ajouterOutil(Outils)
	 */
	@Override
	public Node chooseNextIG(Game g, int choix) {
		Random random = new Random();
	    int i = random.nextInt(ChanceCsq.size());
	    g.getResultArea().setText(ChanceCsq.get(i).getDescription());
	    if (ChanceCsq.get(i).getNvArme() != null) {
	    	g.getInventaire().ajouterArme(ChanceCsq.get(i).getNvArme());
	    	g.getResultArea().setText(g.getResultArea().getText()+"\nVous obtenez en récompense un(e) "+ChanceCsq.get(i).getNvArme());
        }
	    if (ChanceCsq.get(i).getNvOutil() != null) {
	    	g.getInventaire().ajouterOutil(ChanceCsq.get(i).getNvOutil());
	    	g.getResultArea().setText(g.getResultArea().getText()+"\nVous obtenez en récompense un(e) "+ChanceCsq.get(i).getNvOutil());
        }
	    if (ChanceCsq.get(i).getExpbonus() > 0) {
			g.getElementJoueur().gagnerExp(ChanceCsq.get(i).getExpbonus(), g, false);
		}
		return ChanceCsq.get(i).getNextNode();
    }

	
	/**
	 * Renvoie la liste des choix disponibles pour ce nœud de decision.
	 *
	 * @return La liste des choix disponibles sous forme d'objets DecisionChoix.
	 */
	public ArrayList<DecisionChoix> getDecisionChoices() {
		return decisionChoices;
	}

}
