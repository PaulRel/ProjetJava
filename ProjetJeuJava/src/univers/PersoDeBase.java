package univers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import jeu.Game;
import jeu.Main;

/**
 * La classe abstraite PersoDeBase represente un personnage de base dans le jeu.
 * Elle contient des informations telles que la sante, l'experience et les pouvoirs.
 *
 * @see Serializable
 */
public abstract class PersoDeBase implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * Les niveaux possibles du personnage.
     */
    public enum Niveaux {apprenti, officier, maitre}

    private LinkedHashMap<Niveaux, Integer> expRequis = new LinkedHashMap<>();
    public enum Element {Feu, Air, Eau, Terre}

    private int health;
    private int exp;

    private String pv1;
    private String pv2;
    private String pv3;
    private String pv4;
    private String pv5;

    /**
     * Constructeur de la classe abstraite PersoDeBase.
     *
     * @param exp    L'experience initiale du personnage.
     * @param health La sante initiale du personnage.
     */
    public PersoDeBase(int exp, int health) {
        this.health = Math.max(0, Math.min(health, 100)); // Limite entre 0 et 100
        this.exp = Math.max(0, Math.min(exp, 100)); // Limite entre 0 et 100
        expRequis.put(Niveaux.maitre, 40);
        expRequis.put(Niveaux.officier, 20);
        expRequis.put(Niveaux.apprenti, 0);
        
        
    }
    
    
/**
 * Methode abstraite qui doit être implementee par les classes filles pour obtenir une description specifique.
 *
 * @return Une chaîne de caractères decrivant le personnage.
 */
	public abstract String getDescription();

	//Methodes d'accès aux pouvoirs
	public void setPv1(String pv1) {this.pv1 = pv1;}
	public void setPv2(String pv2) {this.pv2 = pv2;}
	public void setPv3(String pv3) {this.pv3 = pv3;}
	public void setPv4(String pv4) {this.pv4 = pv4;}
	public void setPv5(String pv5) {this.pv5 = pv5;}
	public String getPv1() {return pv1;}
	public String getPv2() {return pv2;}
	public String getPv3() {return pv3;}
	public String getPv4() {return pv4;}
	public String getPv5() {return pv5;}


    /**
     * Retourne une liste de pouvoirs disponibles en fonction du niveau d'experience du personnage.
     *
     * @param g L'objet Game associe à l'interface graphique.
     * @return Une liste de pouvoirs disponibles.
     */
    public ArrayList<String> getPouvoirsDisponibles(Game g) {
        ArrayList<String> pouvoirs = new ArrayList<>();
        pouvoirs.add(pv1);
        pouvoirs.add(pv2);
        pouvoirs.add(pv3);
        if (g.getElementJoueur().getExp()>=20) {pouvoirs.add(pv4);}
        if (g.getElementJoueur().getExp()>=40) {pouvoirs.add(pv5);}
        return pouvoirs;
    }

    /**
     * Retourne la sante actuelle du personnage.
     *
     * @return La sante actuelle.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Definit la sante du personnage, en la limitant entre 0 et 100.
     *
     * @param health La nouvelle valeur de sante.
     */
    public void setHealth(int health) {
        this.health = Math.max(0, Math.min(health, 100)); // Limite entre 0 et 100
    }

    /**
     * Retourne l'experience actuelle du personnage.
     *
     * @return L'experience actuelle.
     */
    public int getExp() {
        return exp;
    }

    /**
     * Definit l'experience du personnage, en la limitant entre 0 et 100.
     *
     * @param exp La nouvelle valeur d'experience.
     */
    public void setExp(int exp) {
        this.exp = Math.max(0, Math.min(exp, 100)); // Limite entre 0 et 100
    }
    /**
     * Infliction de degats au personnage. Reduit la sante et affiche un message correspondant.
     *
     * @param degats          Les degats à infliger.
     * @param g               L'objet Game associe à l'interface graphique.
     * @param estSurConsole   Indique si le jeu est en mode console.
     */
    public void prendreDesDegats(int degats, Game g, boolean estSurConsole) {
        health -= degats;
        if (estSurConsole) {
        	 if (health <= 0) {health = 0;}
            System.out.println("L'ennemi attaque vous perdez " + degats + " points de vie.\nVous avez désormais " + health + " points de vie.");
        } else {
            g.getDescriptionArea().setText(g.getDescriptionArea().getText() + "L'ennemi attaque vous perdez " + degats + " points de vie.");
            if (health <= 0) {health = 0;}
            g.getHpLabelNb().setText("" + health);
        }
        if (health <= 0) {
            health = 0;
            mourir(g, estSurConsole);
        }
    }

    /**
     * Guerison du personnage. Augmente la sante et affiche un message correspondant.
     *
     * @param soins           Les points de guerison à appliquer.
     * @param g               L'objet Game associe à l'interface graphique.
     * @param estSurConsole   Indique si le jeu est en mode console.
     */
    public void guerir(int soins, Game g, boolean estSurConsole) {
        health += soins;
        health = Math.min(health, 100); // Limite à 100 points de vie
        if (estSurConsole) {System.out.println("Vous gagnez "+soins+ " points de vie.\nVous avez désormais "+health+" points de vie.");}
        else {
        	g.getDescriptionArea().setText("Vous gagnez "+soins+ " points de vie.");
        	g.getHpLabelNb().setText(""+health);    	
        }
    }
    /**
     * Augmente le nombre de points d'experience du joueur et gère les modifications d'interface liees à cet ajout.
     *
     * @param points        Le nombre de points d'experience à ajouter.
     * @param g             L'instance actuelle du jeu (Game).
     * @param estSurConsole Indique si l'execution se fait sur une console ou non.
     */
    public void gagnerExp(int points, Game g, boolean estSurConsole) {
    	// Obtient le niveau actuel du joueur
    	Niveaux niveauActuel = getNiveauActuel(g);
    	
    	// Ajoute les points d'experience et limite à 100 points d'experience
        exp += points;
        exp = Math.min(exp, 100); // Limite à 100 points d'experience
        // Affiche les messages d'ajout d'experience selon le contexte (console ou interface graphique)
        if (estSurConsole) {System.out.println("Vous gagnez "+points+ " points d'expérience.\nVous avez désormais "+exp+" points d'expérience.");}
        else {
        	g.getResultArea().setText(g.getResultArea().getText()+"Vous gagnez "+points+" points d'expérience.");
    		g.getExpLabelNb().setText(""+exp);
        }
        // Verifie si le joueur a augmente de niveau
        Niveaux nouveauNiveau = getNiveauActuel(g);
        
        // Affiche le passage de niveau si le joueur a augmente de niveau
        if (nouveauNiveau != niveauActuel) {
            afficherPassageDeNiveau(nouveauNiveau, estSurConsole, g);
        }
    }
 
    /**
     * Affiche le passage à un nouveau niveau du joueur et attribue de nouveaux pouvoirs en fonction du niveau atteint.
     *
     * @param niveauActuel   Le nouveau niveau atteint par le joueur (Niveaux).
     * @param estSurConsole  Indique si l'execution se fait sur une console ou non.
     * @param g              L'instance actuelle du jeu (Game).
     */
    private void afficherPassageDeNiveau(Niveaux niveauActuel, boolean estSurConsole, Game g) {
    	if (estSurConsole) {
            System.out.println("Félicitations, vous êtes maintenant "+ niveauActuel+" !");
        } else {
        	g.getNiveauArea().setVisible(true);
            g.getNiveauArea().setText("Félicitations, vous êtes maintenant "+ niveauActuel+" ! Vous obtenez un nouveau pouvoir : ");
        }
    	if (niveauActuel==Niveaux.officier) {
    		if (g.getElementJoueur() instanceof Eau) {	
    			Eau Element=(Eau)g.getElementJoueur();
    			Element.descriptionPouvoirsSupp(4, g, estSurConsole);	
    		}
    		else if (g.getElementJoueur() instanceof Feu) {	
    			Feu Element=(Feu)g.getElementJoueur();
    			Element.descriptionPouvoirsSupp(4, g, estSurConsole);	
    		}
    		else if (g.getElementJoueur() instanceof Terre) {	
    			Terre Element=(Terre)g.getElementJoueur();
    			Element.descriptionPouvoirsSupp(4, g, estSurConsole);	
    		}
    		else if (g.getElementJoueur() instanceof Air) {	
    			Air Element=(Air)g.getElementJoueur();
    			Element.descriptionPouvoirsSupp(4, g, estSurConsole);	
    		}
    	}
    	else if (niveauActuel==Niveaux.maitre) {
    		if (g.getElementJoueur() instanceof Eau) {	
    			Eau Element=(Eau)g.getElementJoueur();
    			Element.descriptionPouvoirsSupp(5, g, estSurConsole);	
    		}
    		else if (g.getElementJoueur() instanceof Feu) {	
    			Feu Element=(Feu)g.getElementJoueur();
    			Element.descriptionPouvoirsSupp(5, g, estSurConsole);	
    		}
    		else if (g.getElementJoueur() instanceof Terre) {	
    			Terre Element=(Terre)g.getElementJoueur();
    			Element.descriptionPouvoirsSupp(5, g, estSurConsole);	
    		}
    		else if (g.getElementJoueur() instanceof Air) {	
    			Air Element=(Air)g.getElementJoueur();
    			Element.descriptionPouvoirsSupp(5, g, estSurConsole);	
    		}
    	}
		
	}

    /**
     * Diminue le nombre de points d'expérience du joueur et gère les modifications d'interface liées à cette perte.
     *
     * @param points        Le nombre de points d'expérience à retirer.
     * @param g             L'instance actuelle du jeu (Game).
     * @param estSurConsole Indique si l'exécution se fait sur une console ou non.
     */
	public void perdreExp(int points, Game g, boolean estSurConsole) {
        exp += points;
        if (exp < 0) {
            exp = 0;
        }
        // Affiche les messages de perte d'expérience selon le contexte (console ou interface graphique)
        if (estSurConsole) {System.out.println("Vous perdez "+points+ " points d'expérience.\nVous avez désormais "+exp+" points d'expérience.");}
        else {
        	g.getResultArea().setText("Vous perdez "+points+ " points d'expérience."+g.getResultArea().getText());
            g.getExpLabelNb().setText(""+exp);
        }
    }

    
    /**
     * Affiche un message indiquant que le joueur n'a plus de points de vie, annonçant la fin de la partie.
     * Crée une nouvelle instance de jeu pour permettre une nouvelle partie.
     * Masque la fenêtre du jeu actuelle.
     * 
     * @param g             L'instance actuelle du jeu (Game).
     * @param estSurConsole Indique si l'exécution se fait sur une console ou non.
     * @see Game
     */
    private void mourir(Game g, boolean estSurConsole) {
    	if (estSurConsole) {
    		System.out.println("Vous n'avez plus de points de vie. Vous avez perdu");
    		new Main();
    		}
    	else {
    	g.afficherFenetreResult();
    	g.getResultArea().setText("Vous n'avez plus de points de vie. Vous avez perdu");
    	g.afficherFenetreResult();
    	
    	}
    }
    
    
    /**
     * Determine le niveau actuel du joueur en fonction de son experience.
     *
     * @param g L'instance actuelle du jeu (Game).
     * @return le niveau actuel du joueur en fonction de son experience
     */    
    public Niveaux getNiveauActuel(Game g) {
        for (Map.Entry<Niveaux, Integer> entry : expRequis.entrySet()) {
            if (g.getElementJoueur().getExp()>= entry.getValue()) {
            	return entry.getKey();
            }
        }
        return Niveaux.apprenti; // Par defaut, le joueur est apprenti s'il n'a pas atteint le seuil pour un niveau superieur
    }

}	
