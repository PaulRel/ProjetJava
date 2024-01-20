package univers;

/**
 * La classe Traqueur represente un personnage de type Traqueur dans le jeu.
 * Elle herite de la classe abstraite PersoDeBase.
 */
public class Traqueur extends PersoDeBase {

    private static final long serialVersionUID = 1L;
    private Armes dagueempoisonnee;

    /**
     * Constructeur de la classe Traqueur.
     *
     * @param exp              L'experience initiale du personnage.
     * @param health           La sante initiale du personnage.
     * @param dagueempoisonnee La dague empoisonnee associee au Traqueur.
     */
    public Traqueur(int exp, int health, Armes dagueempoisonnee) {
        super(exp + 2, health + 3);
        this.dagueempoisonnee = dagueempoisonnee;
    }

    /**
     * Retourne une description du personnage de type Traqueur.
     *
     * @return Une chaine de caracteres decrivant le personnage.
     */
    @Override
    public String getDescription() {
        return "Vous êtes un Traqueur : Expert en embuscades, le Traqueur manie une dague empoisonnée, s'approchant furtivement de sa cible pour infliger des blessures mortelles avant de disparaître dans l'ombre. "
        		+ "Vous gagnez 2 points d'expérience et 3 points de vie !\nVous avez maintenant " + getHealth() + " points de vie et " + getExp() + " points d'expérience. La dague empoisonnée a été rajoutée à votre inventaire !";
    }

    /**
     * Retourne la dague empoisonnee associee au Traqueur.
     *
     * @return La dague empoisonnee.
     */
    public Armes getDagueEmpoisonnee() {
        return dagueempoisonnee;
    }

    /**
     * Retourne une representation sous forme de chaine de caracteres du personnage de type Traqueur.
     *
     * @return Une chaine de caracteres representant le type Traqueur.
     */
    @Override
    public String toString() {
        return "Métier : Traqueur";
    }
}
