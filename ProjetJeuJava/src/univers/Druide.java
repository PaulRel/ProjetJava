package univers;

/**
 * La classe Druide represente un personnage qui protege la nature en utilisant un poison comme arme principale.
 */
public class Druide extends PersoDeBase {

    private static final long serialVersionUID = 1L;
    private Armes poison;

    /**
     * Constructeur pour creer un Druide avec un poison.
     *
     * @param exp    Points d'experience du Druide.
     * @param health Points de vie du Druide.
     * @param poison Poison utilise par le Druide.
     */
    public Druide(int exp, int health, Armes poison) {
        super(exp, health + 5);
        this.poison = poison;
    }

    /**
     * Methode renvoyant une description du Druide.
     *
     * @return Description du Druide.
     */
    @Override
    public String getDescription() {
        return "Vous êtes un Druide : Le Druide, gardien de la nature, utilise une magie empoisonnée pour concocter des poisons redoutables, attaquant ses ennemis avec une puissance toxique. "
        		+ "Vous gagnez 5 points de vie !\nVous avez maintenant " + getHealth() + " points de vie et " + getExp() + " points d'expérience. Le poison a été ajouté à votre inventaire !";
    }

    /**
     * Getter pour le poison utilise par le Druide.
     *
     * @return Poison du Druide.
     */
    public Armes getPoison() {
        return poison;
    }

    /**
     * Methode renvoyant une representation sous forme de chaîne de caracteres du metier du Druide.
     *
     * @return Metier du Druide.
     */
    @Override
    public String toString() {
        return "Métier : Druide";
    }
}
