package univers;

import jeu.Game;

/**
 * La classe Feu represente un personnage de type Feu dans le jeu.
 * Elle etend la classe PersoDeBase et implemente les caracteristiques specifiques
 * associees au peuple du Feu.
 *
 * @see PersoDeBase
 */
public class Feu extends PersoDeBase {

    private static final long serialVersionUID = 1L;
    /**
     * Constructeur de la classe Feu.
     *
     * @param exp    Le nombre de points d'experience du personnage.
     * @param health Le nombre de points de vie du personnage.
     */
	public Feu(int exp, int health) {
		super (exp, health);
		setPv1("Fumée"); setPv2("Braise");	setPv3("Lumière"); setPv4("Boule de feu"); setPv5("Souffle brulant");
	}
	
	/**
     * Retourne la description du personnage de type Feu.
     *
     * @return La description du personnage incluant son element, son niveau d'experience,
     *         son nombre de points de vie et ses competences specifiques.
     */
	@Override
	public String getDescription() {
		return "Bienvenue dans le peuple du "+this.getElement()
		+".\nVous êtes "+this.getNiveaux()
		+".Vous avez pour l'instant "+this.getExp()+" point d'expérience et "+this.getHealth()+" points de vie."
		+"\nVous possédez les compétences : "+getPv1()+", "+getPv2()+" et "+getPv3()+"!\nVoici leurs descriptions :"
		+"\nCréer de la Fumée : Vous utilisez votre pouvoir pour créer un écran de fumée dense qui obscurcit la vue de vos ennemis et vous permet de vous déplacer discrètement. Dégâts : 10"
		+"\nEnflammer la Braise : Vous enflammez des braises au sol pour repousser vos adversaires et créer une barrière de flammes protectrice. Dégâts : 11"
		+"\nFaire Luire la Lumière : Vous émettez une lumière aveuglante qui désoriente vos ennemis et les rend vulnérables. Dégâts : 12"
		+"\nVous en obtiendrez d'avantage en obtenant des points d'expériences et en augmentant de niveau"
		+"\nVous partez donc à l'aventure, vous emportez de l'eau et un couteau. Vous trouverez d'autres objets lors de votre voyage";
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
        		System.out.println( "Boule de feu.\nVous êtes maintenant capable de matérialiser une sphère de flammes tourbillonnantes."
    		+ "Une fois libérée, cette boule de feu embrase tout sur son passage, engendrant des flammes voraces capables de calciner tout ce qu'elles touchent. Dégâts : 20");
    		}
    		else {
    			g.getNiveauArea().setText(g.getNiveauArea().getText()+"Boule de feu.\nVous êtes maintenant capable de matérialiser une sphère de flammes tourbillonnantes."
    		    		+ "Une fois libérée, cette boule de feu embrase tout sur son passage, engendrant des flammes voraces capables de calciner tout ce qu'elles touchent. Dégâts : 20");
    		}
    	}
    	if (i==5) {
    		if (estSurConsole) {
        		System.out.println("Souffle brûlant.\nVous êtes maintenant capable de libérer une vague de chaleur intense, semblable au souffle d'un dragon. "
    				+ "Ce flux ardent consume et ravage tout sur son chemin, transformant tout en un brasier incandescent. Dégâts : 30");
    		}
    		else {
    			g.getNiveauArea().setText(g.getNiveauArea().getText()+"Souffle brûlant.\nVous êtes maintenant capable de libérer une vague de chaleur intense, semblable au souffle d'un dragon. "
        				+ "Ce flux ardent consume et ravage tout sur son chemin, transformant tout en un brasier incandescent. Dégâts : 30");
    		}
    	}
    }


    /**
     * Retourne l'élement associe au personnage de type Feu.
     *
     * @return L'element du personnage (Feu).
     */
    public Element getElement() {
        return Element.Feu;
    }

    /**
     * Retourne le niveau d'experience du personnage de type Feu.
     *
     * @return Le niveau d'experience du personnage (apprenti).
     */
    public Niveaux getNiveaux() {
        return Niveaux.apprenti;
    }

    /**
     * Retourne une representation textuelle du personnage de type Feu.
     *
     * @return Une chaîne de caractères indiquant le type de personnage (Feu).
     */
    @Override
    public String toString() {
        return "Feu";
    }
}
