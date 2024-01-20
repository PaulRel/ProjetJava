package univers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import jeu.Game;

/**
 * La classe Inventaire represente l'inventaire d'un personnage dans le jeu.
 * Il contient des listes d'armes et d'outils.
 *
 * @see Serializable
 */
public class Inventaire implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<Armes> listeArmes = new ArrayList<>();
    private ArrayList<Outils> listeOutils = new ArrayList<>();

    /**
     * Ajoute un outil à la liste d'outils de l'inventaire.
     *
     * @param outil L'outil à ajouter.
     */
    public void ajouterOutil(Outils outil) {
        listeOutils.add(outil);
    }

    /**
     * Retire un outil de la liste d'outils de l'inventaire.
     *
     * @param outil L'outil à retirer.
     * @throws IllegalArgumentException Si l'outil n'existe pas dans l'inventaire.
     */
    public void retirerOutil(Outils outil) {
        if (!listeOutils.contains(outil)) {
            throw new IllegalArgumentException("Cet outil n'existe pas dans l'inventaire.");
        }
        listeOutils.remove(outil);
    }

    /**
     * Ajoute une arme à la liste d'armes de l'inventaire.
     *
     * @param arme L'arme à ajouter.
     */
    public void ajouterArme(Armes arme) {
        listeArmes.add(arme);
    }

    /**
     * Retire une arme de la liste d'armes de l'inventaire.
     *
     * @param arme L'arme à retirer.
     * @throws IllegalArgumentException Si l'arme n'existe pas dans l'inventaire.
     */
    public void retirerArme(Armes arme) {
        if (!listeArmes.contains(arme)) {
            throw new IllegalArgumentException("Cette arme n'existe pas dans l'inventaire.");
        }
        listeArmes.remove(arme);
    }

    /**
     * Affiche la liste d'outils de l'inventaire sous forme de chaîne de caractères.
     *
     * @return Une chaîne de caractères representant la liste d'outils.
     */
    public String afficherInventaireOutils() {
        String s = "\nDans votre inventaire se trouve : ";
        for (Outils outil : listeOutils) {
            s += outil.toString();
        }
        return s + "\n";
    }

    /**
     * Affiche la liste d'outils de l'inventaire sous forme graphique (pour une interface graphique).
     *
     * @param g L'objet Game associe à l'interface graphique.
     */
    public void afficherInventaireOutilsIG(Game g) {
    	for (int i = 0; i < Math.min(listeOutils.size(), 6); i++) {
    	    switch (i) {
    	        case 0:
    	            g.getItemBouton1().setText(listeOutils.get(i).getNom());    	            
    	            g.getItemBouton1().setToolTipText("Bonus de vie "+ listeOutils.get(i).getHealthBonus());    	            
    	            break;
    	            
    	        case 1:
    	            g.getItemBouton2().setText(listeOutils.get(i).getNom());
    	            if (listeOutils.get(i).getExpBonus() !=0) {
    	            	g.getItemBouton2().setToolTipText("Bonus d’expérience : "+ listeOutils.get(i).getExpBonus()+". ");
    	            }
    	            if (listeOutils.get(i).getHealthBonus() !=0) {
   	            	 	g.getItemBouton2().setToolTipText(g.getItemBouton2().getToolTipText()+"Bonus de vie "+ listeOutils.get(i).getHealthBonus());
    	            }   
    	            g.getItemBouton2().setToolTipText(g.getItemBouton2().getToolTipText()+". Utilisations restantes : "+ listeOutils.get(i).getDurabilite());
    	            break;
    	            
    	        case 2:
    	            g.getItemBouton3().setText(listeOutils.get(i).getNom());
    	            if (listeOutils.get(i).getExpBonus() !=0) {
   	            	 	g.getItemBouton3().setToolTipText("Bonus d’expérience : "+ listeOutils.get(i).getExpBonus());
    	            }
    	            if (listeOutils.get(i).getHealthBonus() !=0) {
    	            	g.getItemBouton3().setToolTipText("Bonus de vie "+ listeOutils.get(i).getHealthBonus());
    	            }   
    	            g.getItemBouton3().setToolTipText(g.getItemBouton3().getToolTipText()+". Utilisations restantes : "+ listeOutils.get(i).getDurabilite());
   	            
    	           
    	            break;
    	        case 3:
    	            g.getItemBouton4().setText(listeOutils.get(i).getNom());
    	            if (listeOutils.get(i).getExpBonus() !=0) {
    	            	g.getItemBouton4().setToolTipText("Bonus d’expérience : "+ listeOutils.get(i).getExpBonus());
    	            }
    	            if (listeOutils.get(i).getHealthBonus() !=0) {
    	            	g.getItemBouton4().setToolTipText("Bonus de vie "+ listeOutils.get(i).getHealthBonus());
    	            }   
    	            g.getItemBouton4().setToolTipText(g.getItemBouton4().getToolTipText()+". Utilisations restantes : "+ listeOutils.get(i).getDurabilite());
    	            break;
    	            
    	        case 4:
    	            g.getItemBouton5().setText(listeOutils.get(i).getNom());
    	            if (listeOutils.get(i).getExpBonus() !=0) {
    	            	g.getItemBouton5().setToolTipText("Bonus d’expérience : "+ listeOutils.get(i).getExpBonus());
    	            }
    	            if (listeOutils.get(i).getHealthBonus() !=0) {
    	            	g.getItemBouton5().setToolTipText("Bonus de vie "+ listeOutils.get(i).getHealthBonus());
    	            }   
    	            g.getItemBouton5().setToolTipText(g.getItemBouton5().getToolTipText()+"Utilisations restantes : "+ listeOutils.get(i).getDurabilite());
    	            break;
    	            
    	        case 5:
    	            g.getItemBouton6().setText(listeOutils.get(i).getNom());
    	            if (listeOutils.get(i).getExpBonus() !=0) {
    	            	g.getItemBouton6().setToolTipText("Bonus d’expérience : "+ listeOutils.get(i).getExpBonus());
    	            }
    	            if (listeOutils.get(i).getHealthBonus() !=0) {
    	            	g.getItemBouton6().setToolTipText("Bonus de vie "+ listeOutils.get(i).getHealthBonus());
    	            }   
   	            	g.getItemBouton6().setToolTipText(g.getItemBouton6().getToolTipText()+". Utilisations restantes : "+ listeOutils.get(i).getDurabilite());
    	            break;
    	        default:
    	            break; // Au-delà des 6 boutons, ne fait rien
    	    }
    	}
    }

    /**
     * Affiche la liste d'armes de l'inventaire sous forme de chaîne de caractères.
     *
     * @return Une chaîne de caractères representant la liste d'armes.
     */
    public String afficherInventaireArmes() {
        String s = "\nDans votre inventaire se trouve : ";
        for (Armes arme : listeArmes) {
            s += arme.toString();
        }
        return s + "\n";
    }

    /**
     * Affiche la liste d'armes de l'inventaire sous forme graphique (pour une interface graphique).
     *
     * @param g L'objet Game associe à l'interface graphique.
     */
    public void afficherInventaireArmesIG(Game g) {
    	for (int i = 0; i < Math.min(listeArmes.size(), 6); i++) {
    	    switch (i) {
    	        case 0:
    	            g.getItemBouton1().setText(listeArmes.get(i).getNom());
    	            g.getItemBouton1().setToolTipText("Degâts : "+ listeArmes.get(i).getDegats());
    	            break;
    	        case 1:
    	            g.getItemBouton2().setText(listeArmes.get(i).getNom());
    	            g.getItemBouton2().setToolTipText("Degâts : "+ listeArmes.get(i).getDegats());
    	            break;
    	        case 2:
    	            g.getItemBouton3().setText(listeArmes.get(i).getNom());
    	            g.getItemBouton3().setToolTipText("Degâts : "+ listeArmes.get(i).getDegats()+". Utilisations restantes : "+ listeArmes.get(i).getDurabilite());
    	            break;
    	        case 3:
    	            g.getItemBouton4().setText(listeArmes.get(i).getNom());
    	            g.getItemBouton4().setToolTipText("Degâts : "+ listeArmes.get(i).getDegats()+". Utilisations restantes : "+ listeArmes.get(i).getDurabilite());
    	            break;
    	        case 4:
    	            g.getItemBouton5().setText(listeArmes.get(i).getNom());
    	            g.getItemBouton5().setToolTipText("Degâts : "+ listeArmes.get(i).getDegats()+". Utilisations restantes : "+ listeArmes.get(i).getDurabilite());
    	            break;
    	        case 5:
    	            g.getItemBouton6().setText(listeArmes.get(i).getNom());
    	            g.getItemBouton6().setToolTipText("Degâts : "+ listeArmes.get(i).getDegats()+". Utilisations restantes : "+ listeArmes.get(i).getDurabilite());
    	            break;
    	        default:
    	            break; // Au-delà des 6 boutons, ne fait rien
    	    }
    	}
    }
    
    /**
     * Permet au joueur de choisir un outil de l'inventaire.
     *
     * @return L'outil choisi par le joueur.
     */
    public Outils choisirOutil() {
        System.out.println("Quel outil souhaitez-vous choisir :");
        
        //AFFICHE LES OUTILS SOUS FORME DE LISTE NUMEROTEE
        for (int i = 0; i < listeOutils.size(); i++) {
            System.out.println((i + 1) + ". " + listeOutils.get(i).getNom());
        }
        Scanner scanner = new Scanner(System.in);
        
        int choix = -1;
        while (choix < 1 || choix > listeOutils.size()) {
            try {
                System.out.print("Entrez le numéro correspondant à l'outil : ");
                choix = scanner.nextInt();
                System.out.println("\n------------------------------------------------------------------\n");
                if (choix < 1 || choix > listeOutils.size()) {
                    System.out.println("Choix invalide. Veuillez choisir un numéro valide.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrée invalide. Veuillez entrer un numéro.");
                scanner.next();
            }
        }
        Outils outilChoisi = listeOutils.get(choix - 1);
        
        //scanner.close();
        return outilChoisi;
    }
    
    
 // DANS LE CAS D'UNE INTERFACE GRAPHIQUE :
    
    /**
     * Permet au joueur de choisir un outil de l'inventaire (pour une interface graphique).
     *
     * @param nomOutil Le nom de l'outil à choisir.
     * @return L'outil choisi par le joueur.
     */
    public Outils choisirOutilIG(String nomOutil) {
    	for (Outils outil : listeOutils) {
            if (outil.getNom().equals(nomOutil)) {
                return outil; // Retourne l'objet Outil correspondant au nom
            }
        }
    	return null;
    }
    
    /**
     * Permet au joueur de choisir une arme de l'inventaire.
     *
     * @return L'arme choisie par le joueur.
     */
    public Armes choisirArme() {
    	Scanner scanner = new Scanner(System.in);
    	
    	//AFFICHE LES ARMES SOUS FORME DE LISTE NUMEROTEE
        System.out.println("Quelle arme souhaitez-vous choisir :");
        for (int i = 0; i < listeArmes.size(); i++) {
            System.out.println((i + 1) + ". " + listeArmes.get(i).getNom());
        }
        
        //VERIFICATION D'UNE ENTREE VALIDE
        int choix = -1;
        while (choix < 1 || choix > listeArmes.size()) {
            try {
                System.out.print("Entrez un nombre entre 1 et "+listeArmes.size());
                choix = scanner.nextInt();
                System.out.println("\n------------------------------------------------------------------\n");
                if (choix < 1 || choix > listeArmes.size()) {
                    System.out.println("Choix invalide. Entrez un numéro entre 1 et "+listeArmes.size());
                }
            } catch (InputMismatchException e) {
                System.out.println("Choxi invalide. Entrez un numéro entre 1 et "+listeArmes.size());
                scanner.next(); 
            }
        }
        Armes armeChoisi = listeArmes.get(choix - 1);
        
        //scanner.close();
        return armeChoisi;
    }
    
    
    // DANS LE CAS D'UNE INTERFACE GRAPHIQUE :
    
    /**
     * Permet au joueur de choisir une arme de l'inventaire (pour une interface graphique).
     *
     * @param nomArme Le nom de l'arme à choisir.
     * @return L'arme choisie par le joueur.
     */
    public Armes choisirArmeIG(String nomArme) {
    	for (Armes arme : listeArmes) {
            if (arme.getNom().equals(nomArme)) {
                return arme; // Retourne l'objet Arme correspondant au nom
            }
        }
    	return null;
    }

	public ArrayList<Armes> getListeArmes() {
		return listeArmes;
	}

	public ArrayList<Outils> getListeOutils() {
		return listeOutils;
	}
}
