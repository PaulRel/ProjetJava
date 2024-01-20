package univers;

import jeu.Game;

/**
 * La classe Air représente un personnage de type "Air" dans le jeu.
 * Il possède des competences specifiques liees a cet element.
 * Cette classe etend la classe PersoDeBase.
 */
public class Air extends PersoDeBase {

    private static final long serialVersionUID = 1L;

    
    /**
     * Obtient une description du personnage de type Air.
     *
     * @return Une chaîne de caractères decrivant le personnage Air.
     */
	@Override
	public String getDescription() {
		return "Bienvenue dans le peuple de l'"+this.getElement()
		+".\nVous êtes "+this.getNiveaux()
		+".Vous avez pour l'instant "+this.getExp()+" point d'expérience et "+this.getHealth()+" points de vie."
		+"\nVous possédez les compétences : "+getPv1()+", "+getPv2()+" et "+getPv3()+"!\nVoici leurs descriptions :"
		+ "\nCréer un Nuage de Brume : Vous créez un nuage de brume épaisse pour vous dissimuler et rendre vos mouvements invisibles. Dégâts : 10\r\n"
		+ "Lancer une Lame d'Air : Vous façonnez une lame d'air tranchante pour attaquer vos ennemis à distance. Dégâts : 11\r\n"
		+ "Déclencher une Tornade : Vous invoquez une tornade destructrice pour balayer vos adversaires et tout sur son passage. Dégats : 12\r\n"
		+ "\nVous en obtiendrez d'avantage en obtenant des points d'expériences et en augmentant de niveau"
		+"\nVous partez donc à l'aventure, vous emportez de l'eau et un couteau. Vous trouverez d'autres objets lors de votre voyage";
	}

    /**
     * Constructeur de la classe Air.
     *
     * @param exp    Points d'experience initiaux du personnage.
     * @param health Points de vie initiaux du personnage.
     */
    public Air(int exp, int health) {
        super(exp, health);
        setPv1("Nuage");
        setPv2("Lame d'air");
        setPv3("Tornade");
        setPv4("Sillage d'air");
        setPv5("Tempête d'éclair");
    }

   
    /**
     * Obtient l'element associe au personnage (ici, Element.Air).
     *
     * @return L'element du personnage.
     */
    public Element getElement() {
        return Element.Air;
    }

    /**
     * Obtient le niveau du personnage de type "Air" (ici, Niveaux.apprenti).
     *
     * @return Le niveau du personnage.
     */
    public Niveaux getNiveaux() {
        return Niveaux.apprenti;
    }

    /**
     * Representation textuelle de l'objet (ici, "Air").
     *
     * @return Une chaîne de caractères representant l'objet.
     */
    public String toString() {
        return "Air";
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
    		System.out.println( "Sillage de l’air\n Vous êtes maintenant capable de manipuler les courants atmosphériques pour former une boule d'air comprimé."
    				+ " Cette sphère tourbillonnante engendre une puissante bourrasque, propulsant tout sur son passage. Dégâts : 20");
    		}
    		else {
    			g.getNiveauArea().setText(g.getNiveauArea().getText()+"Sillage de l’air\n Vous êtes maintenant capable de manipuler les courants atmosphériques pour former une boule d'air comprimé.\"\r\n"
    					+ " Cette sphère tourbillonnante engendre une puissante bourrasque, propulsant tout sur son passage. Dégâts : 20");
    		}
    	}
    		
    	if (i==5) {
    		if (estSurConsole) {
    			System.out.println( "Tempête d’éclair\nVous êtes maintenant capable de manipuler les courants atmosphériques pour former une boule d'air comprimé.\r\n"
    					+ " Cette sphère tourbillonnante engendre une puissante bourrasque, propulsant tout sur son passage. Dégâts : 30");
    		}
    	
    	else {g.getNiveauArea().setText(g.getNiveauArea().getText()+"Tempête d’éclair\nVous êtes maintenant capable de manipuler les courants atmosphériques pour former une boule d'air comprimé."
				+ " Cette sphère tourbillonnante engendre une puissante bourrasque, propulsant tout sur son passage. Dégâts : 30");}
    	}
    }
    	
}

