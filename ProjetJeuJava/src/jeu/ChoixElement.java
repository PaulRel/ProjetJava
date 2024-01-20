package jeu;

import java.util.Scanner;

import representation.DecisionNode;
import univers.Air;
import univers.Eau;
import univers.Feu;
import univers.PersoDeBase;
import univers.Terre;
/**
 * Cette classe represente un noeud de decision dans un jeu.
 * Utile sseulement si le jeu se joue dans la console
 * Elle permet à l'utilisateur de choisir un element (Feu, Terre, Eau, Air) et renvoie l'element correspondant.
 */
public class ChoixElement extends DecisionNode{
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructeur
	 */
	public ChoixElement() {
		super("\nQuel élément voulez-vous? \n1. Feu\n2. Terre\n3. Eau\n4. Air\nTapez 1, 2, 3 ou 4 : ");
	}
	
	
	/**
	 * Cette methode permet à l'utilisateur de choisir un element (Feu, Terre, Eau, Air) et renvoie l'element correspondant.
	 * 
	 * @return Le personnage correspondant à l'element choisi par l'utilisateur.
	 */
	public PersoDeBase chooseElement() {
		PersoDeBase ElementJoueur= null;
		int choixElement;
		Scanner scanner = new Scanner(System.in);

		boolean choixValide = false;

		while (!choixValide) {
			if (scanner.hasNextInt()){
				choixElement = scanner.nextInt();

				switch (choixElement) {
            		case 1:
            			ElementJoueur = new Feu(0, 75);
            			choixValide = true;
            			break;
            		case 2:
            			ElementJoueur = new Terre(0, 75);
            			choixValide = true;
            			break;
            		case 3:
            			ElementJoueur  = new Eau(0, 75);
            			choixValide = true;
            			break;
            		case 4:
            			ElementJoueur = new Air(0, 75);
            			choixValide = true;
            			break;
            		default:
            		System.out.println("Choix invalide. Veuillez réessayer.");
            		break;
			}
		}
		else{
			System.out.println("Veuillez entrer un choix valide");
			scanner.next();
			}
		}
		System.out.println(ElementJoueur.getDescription());
		//scanner.close();
        return ElementJoueur;
	}
	
}