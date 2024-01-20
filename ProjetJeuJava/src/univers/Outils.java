package univers;

import java.io.Serializable;


import jeu.Game;

/**
 * La classe Outils represente les outils utilisables par le joueur dans le jeu.
 *
 * @see Serializable
 */
public class Outils implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nom;
    private int expBonus;
    private int healthBonus;
    private int durabilite;

    /**
     * Constructeur de la classe Outils.
     *
     * @param nom         Le nom de l'outil.
     * @param expBonus    Le bonus d'experience fourni par l'outil.
     * @param healthBonus Le bonus de sante fourni par l'outil.
     */
    public Outils(String nom, int expBonus, int healthBonus) {
        this.nom = nom;
        this.expBonus = expBonus;
        this.healthBonus = healthBonus;
        this.durabilite = Integer.MAX_VALUE; // Durabilite infinie par defaut
    }

    /**
     * Surcharge du constructeur pour specifier une durabilite limitee.
     *
     * @param nom         Le nom de l'outil.
     * @param expBonus    Le bonus d'experience fourni par l'outil.
     * @param healthBonus Le bonus de sante fourni par l'outil.
     * @param durabilite  La durabilite de l'outil.
     */
    public Outils(String nom, int expBonus, int healthBonus, int durabilite) {
        this.nom = nom;
        this.expBonus = expBonus;
        this.healthBonus = healthBonus;
        this.durabilite = durabilite;
    }

    /**
     * Utilise l'outil en fournissant les bonus appropries au joueur.
     *
     * @param g              L'objet Game associe à l'interface graphique.
     * @param estSurConsole Indique si le jeu est en mode console.
     */
    public void utiliser(Game g, boolean estSurConsole) throws ErrDurabilite {
    	if (durabilite > 0) {
    		if (durabilite<0) {throw new ErrDurabilite(durabilite);}
    		if (this.healthBonus != 0) {
    			g.getElementJoueur().guerir(healthBonus, g, estSurConsole);
    		}
    		if (this.expBonus != 0) {
    			g.getElementJoueur().gagnerExp(expBonus, g, estSurConsole);
    		}
    		reduireDurabilite();
        }
        else {
        	if (estSurConsole) {System.out.println("Cet objet a atteint son nombre d'utilisation maximum. Il ne peut pas être utilisé.\n");}
        	else {g.getDescriptionArea().setText("Cet objet a atteint son nombre d'utilisation maximum. Il ne peut pas être utilisé.");}
            g.getInventaire().retirerOutil(this);
        }
    }
    

    /**
     * Retourne le nom de l'outil.
     *
     * @return Le nom de l'outil.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Retourne le bonus d'experience fourni par l'outil.
     *
     * @return Le bonus d'experience.
     */
    public int getExpBonus() {
        return expBonus;
    }

    /**
     * Retourne le bonus de sante fourni par l'outil.
     *
     * @return Le bonus de sante.
     */
    public int getHealthBonus() {
        return healthBonus;
    }

    /**
     * Retourne la durabilite restante de l'outil.
     *
     * @return La durabilite restante.
     */
    public int getDurabilite() {
        return durabilite;
    }

    /**
     * Retourne une representation textuelle de l'outil incluant la durabilite restante.
     *
     * @return Une chaîne de caractères representant l'outil avec sa durabilite.
     */
    public String toStringDurabilite() {
        return nom + " (Durabilité restante : " + (durabilite == Integer.MAX_VALUE ? "Infinie" : durabilite) + ")";
    }

    /**
     * Retourne la description de l'outil.
     *
     * @return La description de l'outil.
     */
    public String getDescription() {
        return nom;
    }

    /**
     * Reduit la durabilite de l'outil. La durabilite est reduite seulement si elle n'est pas infinie.
     */
    private void reduireDurabilite() {
        if (durabilite != Integer.MAX_VALUE) {
            durabilite -= 1;
        }
    }

    /**
     * Retourne une representation textuelle de l'outil.
     *
     * @return Une chaîne de caractères representant l'outil.
     */
    @Override
    public String toString() {
        return this.nom + ", ";
    }
}
