package jeu;

import java.util.Scanner;
import representation.DecisionNode;
import univers.Arbaletrier;
import univers.Armes;
import univers.Chasseur;
import univers.Druide;
import univers.Guerrier;
import univers.Inventaire;
import univers.PersoDeBase;
import univers.Traqueur;

/**
 * Cette classe represente un noeud de decision.
 * Utile sseulement si le jeu se joue dans la console
 * Elle permet à l'utilisateur de choisir un metier (chasseur, druide, arbaletrier, guerrier, traqueur ) et renvoie le metier correspondant.
 * Elle ajoute egalement l'arme correspondant au metier à l'inventaire du joueur.
 */
public class ChoixMetier extends DecisionNode{
	private static final long serialVersionUID = 1L;

	public ChoixMetier() {
		super("\nQue voulez-vous choisir? ");
	}
	/**
	 * Permet au joueur de choisir un metier parmi une liste preetablie.
	 * Affiche les options de metiers disponibles.
	 * Demande à l'utilisateur de saisir un nombre correspondant au metier choisi.
	 * 
	 * @param g Le jeu en cours.
	 * @return L'instance du metier choisi par le joueur.
	 */
	public PersoDeBase choisirMetier1(Game g) {
		System.out.println("\n1. Arc\n2. Poison \nTapez 1 ou 2 : ");
		PersoDeBase MetierJoueur= null;
		int choixMetier;
		Scanner scanner = new Scanner(System.in);		
		boolean choixValide = false;

		while (!choixValide) {
			if (scanner.hasNextInt()){
			choixMetier = scanner.nextInt();
			switch (choixMetier) {
            	case 1:
            		Armes arc = new Armes("Arc", 10);
            		MetierJoueur = new Chasseur(g.getElementJoueur().getExp(), g.getElementJoueur().getHealth(), arc);
            		g.getInventaire().ajouterArme(arc);
					choixValide = true;
            		break;
            	case 2:
            		Armes poison = new Armes("Poison", 10);
    			    MetierJoueur = new Druide(g.getElementJoueur().getExp(), g.getElementJoueur().getHealth(), poison);
    			    g.getInventaire().ajouterArme(poison);
            		choixValide = true;
            		break;
            	default:
            		System.out.println("Choix invalide. Veuillez réessayer.");
            		break;
			}
		
		}
		else{System.out.println("Veuillez entrer un choix valide");scanner.next();;}
		}
		//scanner.close();
        return MetierJoueur;
	}
	/**
	 * Permet au joueur de choisir un metier parmi une liste preetablie.
	 * Affiche les options de metiers disponibles.
	 * Demande à l'utilisateur de saisir un nombre correspondant au metier choisi.
	 * 
	 * @param g Le jeu en cours.
	 * @return L'instance du metier choisi par le joueur.
	 */
	public PersoDeBase choisirMetier2(Game g) {
		System.out.println("\n1. Arbalete\n2. Marteau de guerre\n3. Dague empoisonnee\nTapez 1, 2 ou 3 : ");
		PersoDeBase MetierJ= null;
		int choixMetier;
		Scanner scanner = new Scanner(System.in);
	
		boolean choixValide = false;

		while (!choixValide) {
			if (scanner.hasNextInt()){
				choixMetier = scanner.nextInt();
				switch (choixMetier) {
					case 1:
						Armes arbalete = new Armes("Arbalète", 10);
						MetierJ = new Arbaletrier(g.getElementJoueur().getExp(), g.getElementJoueur().getHealth(), arbalete);
						g.getInventaire().ajouterArme(arbalete);
						choixValide = true;
						break;
					case 2:
						Armes marteau = new Armes("Marteau de guerre", 10);
						MetierJ = new Guerrier(g.getElementJoueur().getExp(), g.getElementJoueur().getHealth(), marteau);
						g.getInventaire().ajouterArme(marteau);
						choixValide = true;
						break;
					case 3:
						Armes dague = new Armes("Dague empoisonnée", 10);
						MetierJ = new Traqueur(g.getElementJoueur().getExp(), g.getElementJoueur().getHealth(), dague);
						g.getInventaire().ajouterArme(dague);
						choixValide = true;
						break;
					default:
						System.out.println("Choix invalide. Veuillez réessayer.");
						break;
				}
			}
	
			else{System.out.println("Veuillez entrer un choix valide");scanner.next();}
		}
		//scanner.close();
		return MetierJ;
	}
}
