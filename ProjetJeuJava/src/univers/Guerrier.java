package univers;

/**
 * La classe Guerrier represente un personnage de type Guerrier dans le jeu.
 * Elle etend la classe PersoDeBase et implemente les caracteristiques specifiques
 * associees au metier de Guerrier.
 *
 * @see PersoDeBase
 */
public class Guerrier extends PersoDeBase {

    private static final long serialVersionUID = 1L;
    private Armes marteaudeguerre;

    /**
     * Constructeur de la classe Guerrier.
     *
     * @param exp            Le nombre de points d'experience du personnage.
     * @param health         Le nombre de points de vie du personnage.
     * @param marteaudeguerre Le marteau de guerre associe au Guerrier.
     */
    public Guerrier(int exp, int health, Armes marteaudeguerre) {
        super(exp + 5, health);
        this.marteaudeguerre = marteaudeguerre;
    }

    /**
     * Retourne la description du personnage de type Guerrier.
     *
     * @return La description du personnage incluant son metier, son niveau d'experience,
     *         son nombre de points de vie et le marteau de guerre dans son inventaire.
     */
    @Override
    public String getDescription() {
        return "Vous êtes un Guerrier :  Doté d'une force brute, le Guerrier manie un marteau de guerre, brisant les rangs ennemis avec des coups puissants, incarnant la puissance indomptable sur le champ de bataille. "
        		+ "Vous gagnez 5 points d'expérience !\nVous avez maintenant " + getHealth() + " points de vie et " + getExp() + " points d'expérience. Le marteau de guerre a été rajouté à votre inventaire !";
    }

    /**
     * Retourne le marteau de guerre associe au Guerrier.
     *
     * @return Le marteau de guerre dans l'inventaire du Guerrier.
     */
    public Armes getMarteauDeGuerre() {
        return marteaudeguerre;
    }

    /**
     * Retourne une representation textuelle du metier du personnage (Guerrier).
     *
     * @return Une chaîne de caractères indiquant le metier du personnage (Guerrier).
     */
    @Override
    public String toString() {
        return "Métier : Guerrier";
    }
}
