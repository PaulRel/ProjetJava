package univers;

import java.io.Serializable;

import jeu.Game;

/**
 * La classe Armes represente une arme dans le jeu. Elle peut être utilisee pour attaquer.
 */
public class Armes implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private String nom;
    private int degats;
    private int durabilite;

    /**
     * Constructeur pour creer une arme avec durabilite infinie.
     *
     * @param nom    Nom de l'arme.
     * @param degats Degâts infliges par l'arme.
     */
    public Armes(String nom, int degats) {
        this.nom = nom;
        this.degats = degats;
        this.durabilite = Integer.MAX_VALUE; // Durabilite infinie par defaut
    }

    /**
     * Constructeur pour creer une arme avec une durabilite specifiee.
     *
     * @param nom        Nom de l'arme.
     * @param degats     Degâts infliges par l'arme.
     * @param durabilite Durabilite de l'arme.
     */
    public Armes(String nom, int degats, int durabilite) {
        this.nom = nom;
        this.degats = degats;
        this.durabilite = durabilite;
    }

    /**
     * Methode utilisee dans la classe Inventaire pour afficher les armes dans la console.
     *
     * @return Une chaine de caracteres representant l'arme.
     * @see Inventaire
     */
    @Override
    public String toString() {
        return this.nom + ", ";
    }

    /**
     * Methode permettant a l'arme d'attaquer. 
     * Verifie si l'arme peut encore etre utilisee.
     * La durabilite diminue a chaque utilisation.
     *
     * @param estSurConsole Si le jeu est joue sur console
     * @param g Partie en cours.
     */
    public void attaquer(Game g, boolean estSurConsole) throws ErrDurabilite{
    	if (durabilite<0) {throw new ErrDurabilite(durabilite);}
        if (durabilite > 0) {
        	if (estSurConsole) {System.out.println("Attaque ! Dégâts : " + degats);}
        	else {g.getDescriptionArea().setText("Attaque ! Dégâts : " + degats);}
            durabilite -= 1;
        } else {
        	if (estSurConsole) {System.out.println("L'arme s'est cassée. Elle ne peut pas être utilisée.\n");}
        	else {g.getDescriptionArea().setText("L'arme s'est cassée. Elle ne peut pas être utilisée.");}
            g.getInventaire().retirerArme(this);
        }
    }

    /**
     * Affiche une description de l'arme dans la console.
     */
    public void description() {
        System.out.println("Vous avez obtenu une arme qui fait : " + degats + ". Vous pouvez l'utiliser 10 fois");
    }

    /**
     * Getter pour le nom de l'arme.
     *
     * @return Nom de l'arme.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter pour les degats infliges par l'arme.
     *
     * @return Degâts infliges par l'arme.
     */
    public int getDegats() {
        return degats;
    }

    /**
     * Getter pour la durabilite de l'arme.
     *
     * @return Durabilite de l'arme.
     */
    public int getDurabilite() {
        return durabilite;
    }
}
