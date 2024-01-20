package univers;

/**
 * La classe Chasseur represente un personnage specialise dans la chasse, utilisant un arc comme arme principale.
 */
public class Chasseur extends PersoDeBase {

    private static final long serialVersionUID = 1L;
    private Armes arc;

    /**
     * Constructeur pour creer un Chasseur avec un arc.
     *
     * @param exp    Points d'experience du Chasseur.
     * @param health Points de vie du Chasseur.
     * @param arc    Arc utilise par le Chasseur.
     */
    public Chasseur(int exp, int health, Armes arc) {
        super(exp + 2, health + 3);
        this.arc = arc;
    }

    /**
     * Methode renvoyant une description du Chasseur.
     *
     * @return Description du Chasseur.
     */
    @Override
    public String getDescription() {
        return "Vous êtes un Chasseur : Habile dans les bois, le Chasseur utilise un arc avec une expertise silencieuse. Il traque sa proie avec une précision mortelle, une ombre silencieuse parmi les feuilles. "
        		+ "Vous gagnez 2 points d'expérience et 3 points de vie !\nVous avez " + getHealth() + " points de vie et " + getExp() + " points d'expérience. L'arc a été ajouté à votre inventaire !";
    }

    /**
     * Getter pour l'arc utilise par le Chasseur.
     *
     * @return Arc du Chasseur.
     */
    public Armes getArc() {
        return arc;
    }

    /**
     * Methode renvoyant une representation sous forme de chaîne de caractères du metier du Chasseur.
     *
     * @return Metier du Chasseur.
     */
    @Override
    public String toString() {
        return "Métier : Chasseur";
    }
}
