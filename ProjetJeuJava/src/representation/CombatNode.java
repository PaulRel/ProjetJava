package representation;


import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.BorderFactory;

import jeu.Game;
import univers.Armes;
import univers.Ennemis;
import univers.ErrDurabilite;
import univers.Outils;
/**
 * Noeud representant un combat entre le joueur et un ennemi.
 * Le joueur peut choisir differentes actions pendant le combat.
 */

public class CombatNode extends InnerNode{
	private static final long serialVersionUID = 1L;
	Ennemis e;
	Node nextNode;
	Armes armechoisie;
	Outils outilchoisie;
	int expbonus;
	
	
	/**
     * Constructeur pour initialiser un nœud de combat.
     *
     * @param Description  Description du nœud de combat.
     * @param e            Ennemi à affronter.
     * @param nextNode     Prochain nœud après le combat.
     */
	public CombatNode(String Description, Ennemis e,Node nextNode, int expbonus) {
		super(Description);
		this.e=e;
		this.nextNode=nextNode;
		this.expbonus=expbonus;
	}

	
	/**
	 * Methode permettant au joueur de choisir une action pendant un combat avec un ennemi (dans la console)
	 * Pendant le combat, le joueur peut selectionner diverses options telles que l'utilisation d'une arme,
	 * d'un outil ou attaquer avec un pouvoir avec differentes actions associees à chacune d'elles.
	 * Le joueur choisit l'action à effectuer en saisissant un numero correspondant à l'action souhaitee.
	 *
	 * @param g L'instance de jeu representant l'interface graphique.
	 * @return Le prochain nœud après la fin du combat.
	 * @throws ErrDurabilite 
	 * 
	 */
	
	@Override
	public Node chooseNext(Game g) throws ErrDurabilite {
		super.display();
		while (e.getHealth()>0 && g.getElementJoueur().getHealth()>0) {
			System.out.println("\nQue voulez-vous choisir pour le vaincre ?\n");
					
			System.out.println("1. Utiliser une arme de l'inventaire ?");
			System.out.println("2. Utiliser un outil de l'inventaire ?");
			System.out.println("3. "+ g.getElementJoueur().getPv1());
			System.out.println("4. "+ g.getElementJoueur().getPv2());
			System.out.println("5. "+ g.getElementJoueur().getPv3());
			List<String> pouvoirs = g.getElementJoueur().getPouvoirsDisponibles(g);
			if (pouvoirs.size()>3) {System.out.println("6. "+ g.getElementJoueur().getPv4());}
			if (pouvoirs.size()>4) {System.out.println("7. "+ g.getElementJoueur().getPv5());}
			
			Scanner scanner =new Scanner(System.in);
			boolean choixValide = false;
			while (!choixValide) {
				if (scanner.hasNextInt()){
					int choix = scanner.nextInt();
					System.out.println("\n------------------------------------------------------------------\n");
					switch(choix) {
					case 1:
						g.getInventaire().afficherInventaireArmes();				//AFFICHE LISTE NUMEROTEE DES ARMES
						armechoisie=g.getInventaire().choisirArme();				//RETOURNE L'ARME CHOISIE
						armechoisie.attaquer(g, true);                   //AFFICHE LES DEGATS DE L'ARME
						e.setHealth(e.getHealth()-armechoisie.getDegats()); //DIMINUE LES POINTS DE VIE DE L'ENNEMI
						System.out.println("\nL'ennemi a désormais "+e.getHealth()+" points de vie.\n");
						if (e.getHealth()>=0) {
						e.EnnemisAttaque(g, true);}
						if(g.getElementJoueur().getHealth()<=10) {System.out.println("Attention, vous êtes en danger, récupérez des points de vie avec une potion");}
						choixValide = true;
						break;
					case 2:
						g.getInventaire().afficherInventaireOutils();
						outilchoisie=g.getInventaire().choisirOutil();
						outilchoisie.utiliser(g, true);								//AUGMENTE POINTS DE VIE DU JOUEUR OU POINT EXP EN FONCTION DE L'OUTIL
						choixValide = true;
						break;
					case 3:
						e.setHealth(e.getHealth()-10);
						System.out.println("Attaque avec "+g.getElementJoueur().getPv1()+" ! Degâts : 10");
						System.out.println("\nL'ennemi a désormais "+e.getHealth()+" points de vie.\n");
						if (e.getHealth()>=0) {
						e.EnnemisAttaque(g, true);}
						choixValide = true;
						break;
					case 4:
						e.setHealth(e.getHealth()-11);
						System.out.println("Attaque avec "+g.getElementJoueur().getPv2()+" ! Degâts : 11");
						if (e.getHealth()>=0) {
						e.EnnemisAttaque(g, true);}
						System.out.println("\nL'ennemi a désormais "+e.getHealth()+" points de vie.\n");
						choixValide = true;
						break;
					case 5:
						e.setHealth(e.getHealth()-12);
						System.out.println("Attaque avec "+g.getElementJoueur().getPv3()+" ! Degâts : 12");
						System.out.println("\nL'ennemi a désormais "+e.getHealth()+" points de vie.\n");
						if (e.getHealth()>=0) {
						e.EnnemisAttaque(g, true);}
						choixValide = true;
						break;
					case 6:
						if (pouvoirs.size()>3) {
						e.setHealth(e.getHealth()-20);
						System.out.println("Attaque avec "+g.getElementJoueur().getPv4()+" ! Degâts : 20");
						System.out.println("\nL'ennemi a désormais "+e.getHealth()+" points de vie.\n");
						if (e.getHealth()>=0) {
						e.EnnemisAttaque(g, true);}
						choixValide = true;}
						break;
					case 7:
						if (pouvoirs.size()>4) {
						e.setHealth(e.getHealth()-30);
						System.out.println("Attaque avec "+g.getElementJoueur().getPv5()+" ! Degâts : 30");
						System.out.println("\nL'ennemi a désormais "+e.getHealth()+" points de vie.\n");
						if (e.getHealth()>=0) {
						e.EnnemisAttaque(g, true);}
						choixValide = true;}
						break;
					default:
						System.out.println("Choix invalide. Veuillez réessayer.");
						break;
					}
				}
			else {System.out.println("Veuillez entrer un choix valide");break;}
			}
			
		}
		if (expbonus>0) {
		g.getElementJoueur().gagnerExp(expbonus, g, true);}
		if (g.getElementJoueur().getHealth()>0) {
		return nextNode;}
		else {return null;}
	}
	
	
	
	//La suite concerne le cas d'une interface graphique (ig)
	
	
	//A chaque combat 2 pouvoirs sont choisis au hasard parmi la liste des pouvoirs du joueur
	private String pouvoirChoix3;
	private String pouvoirChoix4;
	

	
	/**
	 * Met à jour les options disponibles dans les boutons choix de l'interface graphique pendant un combat.
	 * 
	 * @param g L'instance de jeu representant l'interface graphique.
	 * @see CombatNode#choisirPouvoir
	 */
	public void afficherChoixIG(Game g) {
	    g.getChoix1().setText("Utiliser une arme de l'inventaire ?");
	    g.getChoix2().setText("Utiliser un outil de l'inventaire ?");

	    // Obtient les pouvoirs disponibles pour l'element joueur
	    List<String> pouvoirs = g.getElementJoueur().getPouvoirsDisponibles(g);
	    
	    // Choix aleatoire de 2 pouvoirs differents parmi les pouvoirs disponibles
	    pouvoirChoix3 = choisirPouvoir(pouvoirs);
	    pouvoirs.remove(pouvoirChoix3); // Retire le pouvoir pour ne pas le choisir à nouveau
	    pouvoirChoix4 = choisirPouvoir(pouvoirs);

	    // Definit le texte des boutons choix3 et choix4 avec les pouvoirs choisis
	    g.getChoix3().setText("Attaquer avec " + pouvoirChoix3);
	    g.getChoix4().setText("Utiliser " + pouvoirChoix4);
	}

	
	
	
	/**
	 * Choisit un pouvoir aleatoire parmi la liste de pouvoirs du joueur.
	 * @param pouvoirs
	 * @return pouvoir
	 */
	private String choisirPouvoir(List<String> pouvoirs) {
	    Random random = new Random();
	    int index = random.nextInt(pouvoirs.size());
	    return pouvoirs.get(index);
	}
	
	
	
	
	/**
	 * Represente le deroulement d'un combat dans l'interface graphique.
	 * Gère les differentes actions disponibles pendant le combat en fonction du choix du joueur.
	 * Les actions incluent l'utilisation d'une arme, d'un outil, ou d'un pouvoir (qui est choisi aleatoirement).
	 *
	 * @param g          L'instance de jeu representant l'interface graphique.
	 * @param choix      Le choix du joueur pendant le combat correspondant à une arme ou un outil de l'inventaire ou à un pouvoir.
	 * @throws ErrDurabilite 
	 * @see Ennemis#EnnemisAttaque(Game, boolean)
	 */
	public void combatIG(Game g, int choix) throws ErrDurabilite {
		
		switch(choix) {
		
		// Choix d'une arme
		case 1:		
			Armes armechoisieIG = g.getArmeChoisie();
			armechoisieIG.attaquer(g, false);              // Affiche les degâts de l'arme
			e.setHealth(e.getHealth()-armechoisieIG.getDegats()); // Diminue les points de vie de l'ennemi
			
			// Met à jour la description de l'interface graphique avec la sante restante de l'ennemi
			g.getDescriptionArea().setText(g.getDescriptionArea().getText()+"\nL'ennemi a désormais "+e.getHealth()+" points de vie.\n");
			e.EnnemisAttaque(g, false); 		// L'ennemi attaque et fait perdre des points de vie au joueur
			if(g.getElementJoueur().getHealth()<=10) {g.getHpPanel().setBorder(BorderFactory.createLineBorder(Color.red));}
			else {g.getHpPanel().setBorder(BorderFactory.createLineBorder(Color.white));}
			break;
			
			
		// Choix d'un outil
		case 2:
			Outils outilchoisieIG = g.getOutilchoisi(); //Recupèrre l'outil choisi de l'inventaire
			outilchoisieIG.utiliser(g, false);	// Augmente les points de vie du joueur ou l'experience en fonction de l'outil
			// Met à jour la description de l'interface graphique avec la sante actuelle de l'ennemi et un message d'attente car les outils ne peuvent pas faire perdre des points de vis à l'ennemi
			g.getDescriptionArea().setText(g.getDescriptionArea().getText()+"\nL'ennemi a "+e.getHealth()+" points de vie.\nQue voulez-vous choisir pour vaincre l'ennemi ?");
			break;
		
			
		//Choix d'un pouvoir qui a ete choisi au hasard pour ce combat
		//Il faut donc verifier lequel a ete choisi pour appliquer les degats correspondants
		// Applique les degâts en fonction du pouvoir selectionne
		case 3:
			if (pouvoirChoix3==g.getElementJoueur().getPv1()){
				e.setHealth(e.getHealth()-10);
				g.getDescriptionArea().setText("Attaque ! Degâts : 10");
			}
			if (pouvoirChoix3==g.getElementJoueur().getPv2()){
				e.setHealth(e.getHealth()-11);
				g.getDescriptionArea().setText("Attaque ! Degâts : 11");
			}
			if (pouvoirChoix3==g.getElementJoueur().getPv3()){
				e.setHealth(e.getHealth()-10);
				g.getDescriptionArea().setText("Attaque ! Degâts : 12");
			}
			if (pouvoirChoix3==g.getElementJoueur().getPv4()){
				e.setHealth(e.getHealth()-20);
				g.getDescriptionArea().setText("Attaque ! Degâts : 20");
			}
			if (pouvoirChoix3==g.getElementJoueur().getPv5()){
				e.setHealth(e.getHealth()-30);
				g.getDescriptionArea().setText("Attaque ! Degâts : 30");
			}
			
            // Met à jour la description de l'interface graphique avec la sante restante de l'ennemi après l'attaque
			g.getDescriptionArea().setText(g.getDescriptionArea().getText()+"\nL'ennemi a désormais "+e.getHealth()+" points de vie\n");
			
			// Si l'ennemi a encore des points de vie, il attaque à son tour, sinon le joueur a gagne le combat			
			if (e.getHealth()>0) {
				e.EnnemisAttaque(g, false);
				if(g.getElementJoueur().getHealth()<=10) {g.getHpPanel().setBorder(BorderFactory.createLineBorder(Color.red));}
				else {g.getHpPanel().setBorder(BorderFactory.createLineBorder(Color.white));}
			}
			else {
				g.getDescriptionArea().setText(g.getDescriptionArea().getText()+"Vous avez vaincu les ennemis");
				g.getChoixBoutonPanel().setVisible(false);
				g.getSuiteBouton().setVisible(true); // Affiche le bouton suite ">" pour passer au noeud suivant (à la place des boutons choix)
				g.setCurrentNode(g.getCurrentNode().chooseNextIG(g, 1)); //Met à jour le nœud actuel du jeu en choisissant le prochain nœud à explorer dans l'interface graphique après un combat.
			}
			break;
		
		// MEME CHOSE QUE CASE 3
		case 4:
			if (pouvoirChoix4==g.getElementJoueur().getPv1()){
				e.setHealth(e.getHealth()-10);
				g.getDescriptionArea().setText("Attaque ! Degâts : 10");
			}
			if (pouvoirChoix4==g.getElementJoueur().getPv2()){
				e.setHealth(e.getHealth()-11);
				g.getDescriptionArea().setText("Attaque ! Degâts : 11");
			}
			if (pouvoirChoix4==g.getElementJoueur().getPv3()){
				e.setHealth(e.getHealth()-12);
				g.getDescriptionArea().setText("Attaque ! Degâts : 12");
			}
			if (pouvoirChoix4==g.getElementJoueur().getPv4()){
				e.setHealth(e.getHealth()-20);
				g.getDescriptionArea().setText("Attaque ! Degâts : 20");
			}
			if (pouvoirChoix4==g.getElementJoueur().getPv5()){
				e.setHealth(e.getHealth()-30);
				g.getDescriptionArea().setText("Attaque ! Degâts : 30");
			}
			g.getDescriptionArea().setText(g.getDescriptionArea().getText()+"\nL'ennemi a désormais "+e.getHealth()+" points de vie\n");
			
			
			if (e.getHealth()>0) {
				e.EnnemisAttaque(g, false);
				if(g.getElementJoueur().getHealth()<=10) {g.getHpPanel().setBorder(BorderFactory.createLineBorder(Color.red));}
				else {g.getHpPanel().setBorder(BorderFactory.createLineBorder(Color.white));}
			}
			else {
				g.getDescriptionArea().setText(g.getDescriptionArea().getText()+"Vous avez vaincu l'ennemi.");
				g.getChoixBoutonPanel().setVisible(false);
				g.getSuiteBouton().setVisible(true);
				g.setCurrentNode(g.getCurrentNode().chooseNextIG(g, 1));
			}
			break;
		}
	}

	/**
	 * Selectionne le prochain nœud à explorer dans l'interface graphique appeler après la fin d'un combat.
	 *
	 * @param g          L'instance de jeu representant l'interface graphique.
	 * @param choix      Le choix specifique effectue par le joueur dans l'interface graphique.
	 * @return Le prochain nœud à explorer dans l'interface graphique après le combat.
	 */
	@Override
	public Node chooseNextIG(Game g, int choix) {
		g.getElementJoueur().gagnerExp(expbonus, g, false);
		return nextNode;
	}
	
	/**
	 * Renvoie l'ennemi associe à ce nœud de combat.
	 *
	 * @return L'ennemi associe à ce nœud de combat.
	 */
	public Ennemis getEnnemi() {
		return e;
	}
}