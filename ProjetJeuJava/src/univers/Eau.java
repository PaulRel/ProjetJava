package univers;

import jeu.Game;

/**
 * La classe Eau represente un personnage lie a l'element de l'eau avec des competences specifiques.
 */
public class Eau extends PersoDeBase {

    private static final long serialVersionUID = 1L;

    /**
     * Constructeur pour creer un personnage lie a l'element de l'eau avec des competences specifiques.
     *
     * @param exp    Points d'experience du personnage.
     * @param health Points de vie du personnage.
     */
    public Eau(int exp, int health) {
        super(exp, health);
        setPv1("Vapeur d'eau");
        setPv2("Jet d'eau");
        setPv3("Pluie");
        setPv4("Boule de glace");
        setPv5("Souffle glacé");
    }

    /**
     * Methode renvoyant une description du personnage lie a l'element de l'eau.
     *
     * @return Description du personnage.
     */
    @Override
    public String getDescription() {
        return "Bienvenue dans le peuple de l'" + this.getElement()
                + ".\nVous êtes " + this.getNiveaux()
                + ".Vous avez pour l'instant " + this.getExp() + " point d'expérience et " + this.getHealth() + " points de vie."
                + "\nVous possédez les compétences : " + getPv1() + ", " + getPv2() + " et " + getPv3() +"!\nVoici leurs descriptions :"
        		+"\nCréer de la Vapeur d'Eau : Vous générez de la vapeur d'eau dense pour vous camoufler et attaquer vos ennemis. Dégâts : 10"
        		+"\nLancer un Jet d'Eau : Vous propulsez un puissant jet d'eau pour repousser vos adversaires et les déséquilibrer. Dégâts : 11"
        		+"\nDéclencher une Pluie Bienfaisante : Vous invoquez une pluie rafraîchissante qui affaiblit les ennemis. Dégâts : 12"
        		+"\nVous en obtiendrez d'avantage en obtenant des points d'expériences et en augmentant de niveau"
        		+"\nVous partez donc à l'aventure, vous emportez de l'eau et un couteau. Vous trouverez d'autres objets lors de votre voyage";
    }

    /**
     * Getter pour l'element associe au personnage.
     *
     * @return element associe au personnage.
     */
    public Element getElement() {
        return Element.Eau;
    }

    /**
     * Getter pour le niveau du personnage.
     *
     * @return Niveau du personnage.
     */
    public Niveaux getNiveaux() {
        return Niveaux.apprenti;
    }

    /**
     * Methode renvoyant une representation sous forme de chaîne de caractères du metier du personnage.
     *
     * @return Metier du personnage.
     */
    @Override
    public String toString() {
        return "Eau";
    }
    
    /**
     * Affiche ou met a jour la description des pouvoirs supplementaires en fonction de l'indice fourni.
     *
     * @param i              L'indice du pouvoir supplementaire.
     * @param g              L'objet Game actuel.
     * @param estSurConsole  Indique si l'execution se fait sur une console ou non.
     */
    public void descriptionPouvoirsSupp(int i, Game g, boolean estSurConsole) {
    	if (i==4) {
    		if (estSurConsole) {
        		System.out.println("Boule de glace.\nVous êtes maintenant capable de former une sphère gelée. "
    				+ "Une fois lancée, cette boule de glace se propage en gelant tout sur son passage, créant un froid glacial et figeant même l'air ambiant. Dégâts : 20");
    		}
    		else {
    			g.getNiveauArea().setText(g.getNiveauArea().getText()+"Boule de glace.\nVous êtes maintenant capable de former une sphère gelée. "
        				+ "Une fois lancée, cette boule de glace se propage en gelant tout sur son passage, créant un froid glacial et figeant même l'air ambiant. Dégâts : 20");
        	}
    	}
    	if (i==5) {
    		if (estSurConsole) {
        		System.out.println("Souffle glacé.\nVous êtes maintenant capable de libérer un jet de froid absolu tel un souffle venu des sommets enneigés, vous. "
        				+ "Ce souffle glacial gèle instantanément ce qu'il touche, transformant l’environnement en un paysage gelé et glacial. Dégâts : 30");
    		}
    		else {
    			g.getNiveauArea().setText(g.getNiveauArea().getText()+"Souffle glacé.\nVous êtes maintenant capable de libérer un jet de froid absolu tel un souffle venu des sommets enneigés, vous. "
    					+ "Ce souffle glacial gèle instantanément ce qu'il touche, transformant l’environnement en un paysage gelé et glacial. Dégâts : 30");
    		}
    	}
    }
}
