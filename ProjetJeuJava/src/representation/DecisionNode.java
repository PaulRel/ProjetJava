package representation;
import java.util.ArrayList;
import java.util.Scanner;

import jeu.Game;
import univers.Armes;
import univers.Outils;

/**
 * Represente un nœud de decision (DecisionNode) dans la structure du jeu.
 * Ce nœud offre plusieurs choix menant à differents resultats.
 * Le joueur doit prendre une decision entre ces choix.
 */
public class DecisionNode extends InnerNode{

	private static final long serialVersionUID = 1L;

	/**
	 * Liste des choix disponibles dans ce nœud de decision.
	 * Chaque element de cette liste represente une option de choix avec ses consequences et resultats possibles.
	 * @see DecisionChoix	 
	 */
	private ArrayList<DecisionChoix> decisionChoices = new ArrayList<>();
	
	public ArrayList<DecisionChoix> getDecisionChoices() {
		return decisionChoices;
	}
	
	
	/**
     * Initialise un nœud de decision avec une description specifique.
     *
     * @param Description La description du nœud de decision.
     */
	public DecisionNode(String Description) {
		super (Description);
	}

	
	/**
	 * Permet au joueur de choisir parmi les options disponibles dans ce noeud de decision.
	 * Cette methode affiche les choix disponibles, attend l'entree du joueur et renvoie le nœud suivant en fonction du choix effectue.
	 *
	 * @return Le nœud suivant en fonction du choix effectue par le joueur.
	 * @see DecisionChoix
	 */
	@Override
	public Node chooseNext(Game g) {
		super.display();
		System.out.println("Que voulez-vous choisir?");
		Scanner scanner =new Scanner(System.in);
		
		while (true) {
			 // Affiche les choix disponibles pour le joueur
			System.out.println("0. Sauvegarder la partie");	
			for (int i = 0; i < decisionChoices.size(); i++) {System.out.println((i + 1) + ". " + decisionChoices.get(i).getDescription());}
			if (scanner.hasNextInt()){
				int choix = scanner.nextInt();
				if (choix==0) {g.sauvegarderPartieSurConsole();}
				
				if (choix >= 1 && choix <= decisionChoices.size()) {
					System.out.println("\n------------------------------------------------------------------\n");
					System.out.println(decisionChoices.get(choix-1).getResult()+"\n");
					
					// Ajoute les nouvelles armes et outils obtenus suite au choix du joueur
					if (decisionChoices.get(choix-1).getNvArme() != null) {
						g.getInventaire().ajouterArme(decisionChoices.get(choix-1).getNvArme());
						System.out.println("\nVous obtenez en récompense un(e) "+decisionChoices.get(choix-1).getNvArme());	
	                }
					if (decisionChoices.get(choix-1).getNvOutil() != null) {
						g.getInventaire().ajouterOutil(decisionChoices.get(choix-1).getNvOutil());
						System.out.println("\nVous obtenez en récompense un(e) "+decisionChoices.get(choix-1).getNvOutil());	
	                }
					if (decisionChoices.get(choix-1).getExpBonus() > 0) {
						g.getElementJoueur().gagnerExp(decisionChoices.get(choix-1).getExpBonus(), g, true);
					}
					if (decisionChoices.get(choix-1).getExpBonus() < 0) {
						g.getElementJoueur().perdreExp(decisionChoices.get(choix-1).getExpBonus(), g, true);
					}
					//scanner.close(); // Ne pas fermer le scanner pour éviter des problèmes potentiels	
					
					//Renvoie le nœud suivant en fonction du choix du joueur
					return decisionChoices.get(choix - 1).getNextNode();
				} 
				else {System.out.println("Veuillez entrer un nombre entre 1 et "+decisionChoices.size());}
			}
			else{System.out.println("Veuillez entrer un choix valide");
			scanner.next();}
		}
	}

	/**
	 * Ajoute un choix au nœud de decision avec une description, un nœud suivant et un resultat.
	 * Ajoute ce choix dans la liste decisionChoices.
	 *
	 * @param choiceDescription La description du choix à ajouter.
	 * @param nextNode          Le nœud suivant en fonction de ce choix.
	 * @param result            Le resultat du choix.
	 * @see DecisionChoix
	 */
	public void addChoice(String choiceDescription, Node nextNode, String result) {
        decisionChoices.add(new DecisionChoix(choiceDescription, nextNode, result));
    }
	
	/**
	 * Ajoute un choix au nœud de decision avec une description, un nœud suivant, un resultat, une arme et/ou un outil en recompense.
	 * Ajoute ce choix dans la liste decisionChoices
	 *
	 * @param choiceDescription La description du choix à ajouter.
	 * @param nextNode          Le nœud suivant en fonction de ce choix.
	 * @param result            Le resultat du choix.
	 * @param NvArme            L'arme en recompense du choix, peut être null.
	 * @param NvOutil           L'outil en recompense du choix, peut être null.
	 * @see DecisionChoix
	 */
	public void addChoice(String choiceDescription, Node nextNode, Armes NvArme, Outils NvOutil, int expbonus, String result) {
        decisionChoices.add(new DecisionChoix(choiceDescription, nextNode, NvArme, NvOutil, expbonus, result));
    }

	
	
	//LA SUITE CONCERNE LE CAS D'UNE INTERFACE GRAPHIQUE (IG)
	
	/**
	 * Affiche les choix disponibles dans une interface graphique (IG) du jeu.
	 *
	 * @param g L'instance du jeu pour afficher les choix dans l'interface graphique.
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
	 * Choix du prochain nœud en fonction du choix du joueur dans une interface graphique (IG).
	 *
	 * @param g          L'instance du jeu utilisee pour gerer l'interface graphique.
	 * @param choix      Le choix effectue par le joueur.
	 * @return Le nœud suivant en fonction du choix du joueur.
	 */
	@Override
	public Node chooseNextIG(Game g, int choix) {
		//Affiche les consequences du choix du joueur
		if (decisionChoices.get(choix-1).getResult()=="Metier") {
			g.getResultArea().setText(g.getMetierJoueur().getDescription()); //Si le joueur choisit son metier, on affiche la description du metier selectionne
		}
		else{g.getResultArea().setText(decisionChoices.get(choix-1).getResult());}
		
		//Gère les recompenses pour le joueur (arme, outil ou points d'exp)
		if (decisionChoices.get(choix-1).getNvArme() != null) {
			g.getInventaire().ajouterArme(decisionChoices.get(choix-1).getNvArme());
			g.getResultArea().setText(g.getResultArea().getText()+"\nVous obtenez en récompense un(e) "+decisionChoices.get(choix-1).getNvArme());	
	    }
		if (decisionChoices.get(choix-1).getNvOutil() != null) {
			g.getInventaire().ajouterOutil(decisionChoices.get(choix-1).getNvOutil());
			g.getResultArea().setText(g.getResultArea().getText()+"\nVous obtenez en récompense un(e) "+decisionChoices.get(choix-1).getNvOutil());
	    }
		if (decisionChoices.get(choix-1).getExpBonus() > 0) {
			g.getElementJoueur().gagnerExp(decisionChoices.get(choix-1).getExpBonus(), g, false);
		}
		if (decisionChoices.get(choix-1).getExpBonus() < 0) {
			g.getElementJoueur().perdreExp(decisionChoices.get(choix-1).getExpBonus(), g, false);
		}
		//Retourne le nœud suivant en fonction du choix du joueur
		return decisionChoices.get(choix - 1).getNextNode();
		}

	}
