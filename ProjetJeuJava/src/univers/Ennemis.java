package univers;

import java.io.Serializable;

import jeu.Game;

/**
 * L'enum Ennemis represente un ennemi dans le jeu, capable d'attaquer le joueur.
 */
public enum Ennemis implements Serializable {FAIBLE(15, 10), MOYEN(30, 20),FORT(80,40);

    private static final long serialVersionUID = 1L;
    private int health, degats;
   

    /**
     * Constructeur pour creer un ennemi avec une certaine quantite de points de vie, des degats et une cible (le personnage du joueur).
     *
     * @param health      Points de vie de l'ennemi.
     * @param degats      Degats infliges par l'ennemi.
     */
    Ennemis(int health, int degats) {
        this.health = health;
        this.degats = degats;
    }

    /**
     * Methode representant l'attaque de l'ennemi sur la cible.
     * L'ennemi inflige des degats aleatoires à la cible.
     */
    public void EnnemisAttaque(Game g, boolean estSurConsole) {
        int attaque = new java.util.Random().nextInt(degats); // L'ENNEMI FAIT DES DOMMAGES ENTRE 0 ET DEGATS-1
        g.getElementJoueur().prendreDesDegats(attaque, g, estSurConsole); // ON ENLEVE DES POINTS DE VIE AU JOUEUR        
    }

    /**
     * Getter pour les points de vie de l'ennemi.
     * Si les points de vie sont negatifs, on renvoie 0.
     *
     * @return Points de vie de l'ennemi.
     */
    public int getHealth() {
        if (health < 0) {
            return 0;
        }
        return health;
    }

    /**
     * Setter pour les points de vie de l'ennemi.
     *
     * @param health Nouvelle valeur des points de vie.
     */
    public void setHealth(int health) {
        this.health = health;
    }
}

