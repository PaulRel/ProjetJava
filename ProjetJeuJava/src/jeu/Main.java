package jeu;


import java.util.Scanner;

import univers.ErrDurabilite;



/**
 * Classe principale du jeu.
 * Elle contient la methode main() pour lancer le jeu.
 * @see Game
 */
public class Main{

	public static void main(String[] args) throws ErrDurabilite {
		System.out.println("Voulez vous jouer\n1.Dans l'interface graphique ? (Recommandé, meilleure expérience de jeu)\n2.Dans la console ? (aucune image, aucun son)");
		int choix;
		Scanner scanner = new Scanner(System.in);

		if (scanner.hasNextInt()){
			choix = scanner.nextInt();
			if (choix==1) {new Game(false, false);}// Crée une nouvelle instance de la classe Game pour demarrer le jeu
			else if (choix==2) {afficherMenu();}
			else {System.out.println("Choix invalide. Veuillez réessayer.");scanner.next();}
		}
		else{
			System.out.println("Veuillez entrer un choix valide");
			scanner.next();
		}	
		//scanner.close(); Pour eviter de potentiels problèmes
	}
	
	
	
	//Cas pour le jeu dans la console sinon le menu est present en haut à gauche dans l'interface
	private static void afficherMenu() throws ErrDurabilite {
        int choix;
        do {
            System.out.println("-------------");
            System.out.println("Menu Principal");
            System.out.println("-------------");
            System.out.println("Tapez 0 pour quitter.");
            System.out.println("Tapez 1 pour lancer une nouvelle partie.");
            System.out.println("Tapez 2 pour reprendre une sauvegarde.");
            Scanner scanner = new Scanner(System.in);
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 0:
                    System.out.println("Au revoir !");
                    break;
                case 1:
                    new Game(true, false);
                    break;
                case 2:
                	new Game(true, true);
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez choisir une option valide.");
            }
        } while (choix != 0);
    }
	
	 
}