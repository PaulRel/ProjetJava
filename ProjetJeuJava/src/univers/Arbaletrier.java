package univers;

/**
 * La classe Arbaletrier represente un personnage de type "Arbaletrier" dans le jeu.
 * Il excelle dans l'attaque a distance, maniant une arbalete redoutable.
 * Cette classe etend la classe PersoDeBase.
 */
public class Arbaletrier extends PersoDeBase {

    private static final long serialVersionUID = 1L;
    private Armes arbalete;

    /**
     * Constructeur de la classe Arbaletrier.
     *
     * @param exp      Points d'experience initiaux du personnage.
     * @param health   Points de vie initiaux du personnage.
     * @param arbalete Arme associee à l'Arbaletrier.
     */
    public Arbaletrier(int exp, int health, Armes arbalete) {
        super(exp + 5, health);
        this.arbalete = arbalete;
    }

    /**
     * Obtient une description du personnage de type Arbaletrier.
     *
     * @return Une chaîne de caracteres decrivant le personnage Arbaletrier.
     */
    @Override
    public String getDescription() {
        return "Vous êtes un arbalétrier : Maître de la précision, l'Arbalétrier excelle dans l'art de l'attaque à distance, maniant une arbalète redoutable pour éliminer ses ennemis avec une précision mortelle. Vous gagnez 5 points d'expérience !\nVous avez maintenant "
                + getHealth() + " points de vie, " + getExp() + " points d'expérience. L'arbalète a été rajouté à votre inventaire !";
    }

    /**
     * Obtient l'arme associee à l'Arbaletrier.
     *
     * @return L'arme (arbalete) de l'Arbaletrier.
     */
    public Armes getArbalete() {
        return arbalete;
    }

    /**
     * Representation textuelle de l'objet (ici, "Metier : Arbaletrier").
     *
     * @return Une chaîne de caracteres representant l'objet.
     */
    @Override
    public String toString() {
        return "Métier : Arbalétrier";
    }
}
