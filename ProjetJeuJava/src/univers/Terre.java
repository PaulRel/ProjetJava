package univers;

import jeu.Game;

/**
 * La classe Terre represente un personnage de type Terre dans le jeu.
 * Elle herite de la classe abstraite PersoDeBase.
 */
public class Terre extends PersoDeBase {

    private static final long serialVersionUID = 1L;

    /**
     * Constructeur de la classe Terre.
     *
     * @param exp    L'experience initiale du personnage.
     * @param health La sante initiale du personnage.
     */
	public Terre(int exp, int health) {
		super ( exp, health );
		setPv1("Nuage de poussière"); setPv2("Jeté de cailloux"); setPv3("Boue");setPv4("Balle de métal");setPv5("Tremblement de terre");
	}
	

    /**
     * Retourne une description du personnage de type Terre.
     *
     * @return Une chaîne de caractères decrivant le personnage.
     */
	@Override
	public String getDescription() {
		return "Bienvenue dans le peuple de la "+this.getElement()
		+".\nVous êtes "+this.getNiveaux()
		+".Vous avez pour l'instant "+this.getExp()+" point d'expérience."
		+"\nVous possédez les compétences : "+getPv1()+", "+getPv2()+" et "+getPv3()+"!\nVoici leurs descriptions :"
		+"\nCréer un Nuage de Poussière : Vous soulevez un nuage de poussière pour obscurcir la vue de vos ennemis et créer la confusion. Dégâts : 10"
		+"\nLancer des Cailloux : Vous utilisez votre pouvoir pour projeter des cailloux à grande vitesse, infligeant des dégâts à vos adversaires. Dégâts : 11"
		+"\nCréer de la Boue : Vous générer une boue collante pour ralentir et immobiliser vos ennemis. Dégâts : 12"
		+"\n\nVous en obtiendrez d'avantage en obtenant des points d'expériences et en augmentant de niveau"
		+"\nVous partez donc à l'aventure, vous emportez de l'eau et un couteau. Vous trouverez d'autres objets lors de votre voyage";
	}
	
	
	/**
	 * Affiche ou met à jour la description des pouvoirs supplementaires en fonction de l'indice fourni.
	 *
	 * @param i              L'indice du pouvoir supplementaire.
	 * @param g              L'objet Game actuel.
	 * @param estSurConsole  Indique si l'execution se fait sur une console ou non.
	 */
	public void descriptionPouvoirsSupp(int i, Game g, boolean estSurConsole) {
    	if (i==4) {
    		if (estSurConsole) {
        		System.out.println("Balle de métal\nVous êtes maintenant capable de matérialiser une sphère métallique concentrée. "
    				+ "Une fois lancée, cette balle de métal impacte violemment sa cible, créant des dommages importants. Dégâts : 20");
    		}
    		else {
    			g.getNiveauArea().setText(g.getNiveauArea().getText()+"Balle de métal\nVous êtes maintenant capable de matérialiser une sphère métallique concentrée. "
        				+ "Une fois lancée, cette balle de métal impacte violemment sa cible, créant des dommages importants. Dégâts : 20");
    		}
    	}
    	if (i==5) {
    		if (estSurConsole) {
        		System.out.println("Tremblement de terre.\nVous êtes maintenant capable de provoquer un séisme soudain et dévastateur. "
        				+ "Ce tremblement de terre secoue violemment le sol, créant des fissures et ébranlant tout ce qui se trouve à sa portée, semant le chaos et la destruction. Dégâts : 30");
    		}
    		else {
    			g.getNiveauArea().setText(g.getNiveauArea().getText()+"Tremblement de terre.\nVous êtes maintenant capable de provoquer un séisme soudain et dévastateur. "
    					+ "Ce tremblement de terre secoue violemment le sol, créant des fissures et ébranlant tout ce qui se trouve à sa portée, semant le chaos et la destruction. Dégâts : 30");
    		}
    	}
	}

    
    /**
     * Retourne l'element associe au personnage (Terre).
     *
     * @return L'element Terre.
     */
    public Element getElement() {
        return Element.Terre;
    }

    /**
     * Retourne le niveau du personnage (apprenti).
     *
     * @return Le niveau Apprenti.
     */
    public Niveaux getNiveaux() {
        return Niveaux.apprenti;
    }

    /**
     * Retourne une representation sous forme de chaîne de caractères du personnage de type Terre.
     *
     * @return Une chaîne de caractères representant le type Terre.
     */
    @Override
    public String toString() {
        return "Terre";
    }

}
