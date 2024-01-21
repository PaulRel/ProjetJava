package jeu;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import representation.ChanceNode;
import representation.CombatNode;
import representation.DecisionNode;
import representation.EnigmeNode;
import representation.Node;
import representation.TerminalNode;
import representation.decorators.ImageNode;
import representation.decorators.SoundNode;
import univers.Air;
import univers.Arbaletrier;
import univers.Armes;
import univers.Chasseur;
import univers.Druide;
import univers.Eau;
import univers.Ennemis;
import univers.ErrDurabilite;
import univers.Feu;
import univers.Guerrier;
import univers.Outils;
import univers.PersoDeBase;
import univers.Terre;
import univers.Traqueur;
import univers.Inventaire;

/**
 * La classe Game represente le moteur principal du jeu.
 * Elle gere le deroulement du jeu, les elements du joueur, l'interface utilisateur, etc.
 */
public class Game {
	/**
	 * Represente le nœud actuel dans le jeu.
	 */
	private Node currentNode;
	/**
	 * Represente l'element  du joueur.
	 */
	private PersoDeBase ElementJoueur;
	/**
	 * Represente l'inventaire du joueur.
	 */
	private Inventaire Inventaire;
	/**
	 * Represente le metier du joueur.
	 */
	private PersoDeBase MetierJoueur;
	
	/**
	 * Arme choisie par le joueur lors d'un CombatNode
	 */
	private Armes armechoisie;
	/**
	 * Outil choisi par le joueur lors d'un CombatNode
	 */
	private Outils outilchoisi;
	/**
	 * Reponse fournie par l'utilisateur lors d'un EnigmeNode
	 */
	private String repUtilisateur;
	/**
	 * Clip utilise pour la lecture audio dans le jeu
	 */
	private Clip clip;
	/**
	 * Elements de l'interface
	 */
	private TerminalNode Defaite;
	private JFrame f, f1;
	private JMenuItem m1, m2, m3;
	private JMenu menu;
	private JPanel IP, titrePanel, ChoixBoutonPanel,choixElementPanel, playerPanel, inventairePanel, hpPanel;
	private JLabel ImageLabel, iconLabel, metierLabel, titreLabel, hpLabelNb, expLabelNb;
	private JButton startBouton, loadBouton, suiteBouton1, feu, terre, eau, air;
	private JButton suiteBouton, enigmeBouton, choix1, choix2, choix3, choix4, itemBouton1, itemBouton2, itemBouton3, itemBouton4, itemBouton5, itemBouton6;
	private JTextArea debutArea, descriptionArea, resultArea, remarqueArea, niveauArea;
	private JTextField enigmeRep;
	private Font titreFond = new Font("Times New Roman", Font.BOLD, 60);
	private Font normalFond= new Font("Times New Roman", Font.PLAIN, 20);


	/**
     * Constructeur de la classe Game.
     * Initialise le jeu en fonction des parametres specifies.
     *
     * @param estSurConsole               Indique si le jeu est execute en mode console ou non.
     * @param reprendreSauvegardeSurConsole Indique si une sauvegarde doit être reprise en mode console.
     * @throws ErrDurabilite              Exception liee à la durabilite des armes et outils.
     */
	public Game(boolean estSurConsole, boolean reprendreSauvegardeSurConsole) throws ErrDurabilite {	
		//Cas ou le jeu se joue sur console
		if (estSurConsole) {
			if (reprendreSauvegardeSurConsole) {reprendrePartieSurConsole();}
			//Si le joueur ne reprend pas une partie sauvegardee
			else {
				// CHOIX ELEMENT
				Debut debut=new Debut();
				debut.display();
				ChoixElement CE= new ChoixElement();
				CE.display();
				ElementJoueur = CE.chooseElement();
				creerJeu(true, false);
			}	
		}
		
		//Cas ou le jeu se joue sur l'interface graphique
		else {
		//Choix de l'element	
		DecisionNode ChoixElement = new DecisionNode("\nQuel élément voulez-vous?");
		currentNode=ChoixElement;
		
		
		initialiserInterfaceDebut();
		sondebut();

		//Action du bouton suite : affichage des choix pour choisir l'element entre feu, terre, eau et air
		suiteBouton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ElementJoueur!= null) {
					currentNode=null;
					f1.setVisible(false);
					try {
						creerJeu(false, false);
					} catch (ErrDurabilite e1) {
						e1.printErr();
					}
				}
				actionSuiteBouton1();			
		}
		});
		
			
		//Action du bouton start : affiche une description de l'histoire du jeu
		startBouton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				titrePanel.setVisible(false);
				startBouton.setVisible(false);
				loadBouton.setVisible(false);
				IP.setVisible(false);
				descriptionArea.setVisible(false);  		

				debutArea.setVisible(true);
				suiteBouton1.setVisible(true);	//Affiche bouton >
    			clip.stop();
			}
		});
			
		//Bouton pour reprendre une partie sauvegardee
		loadBouton.addActionListener(new ActionListener() {
			@Override
				public void actionPerformed(ActionEvent e) {
			          try {
						reprendrePartie();
					} catch (ErrDurabilite e1) {
						e1.printErr();
					}
				}
		});
				
	
		
		//Affiche une description de l'histoire du jeu
		Debut debut = new Debut();
		debutArea = new JTextArea(debut.getDescription());
		afficherDebut();
		
				
		//Si le joueur clique sur le bonton "feu"
		feu.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				ElementJoueur = new Feu(0, 75);    //Creation d'une instance feu
				actionBoutonsDebut();
				
				}	
		});
		
		terre.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				ElementJoueur = new Terre(0, 75);
				actionBoutonsDebut();
			}
		});
		
		eau.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				ElementJoueur = new Eau(0, 75);
				actionBoutonsDebut();
			}
		});
		
		
		air.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				ElementJoueur = new Air(0, 75);
				actionBoutonsDebut();
			}
		});
		
		
		f1.setVisible(true);
		
		
		//L'element du joueur initialise, on peut creer les noeuds
		
		}
	}
	
	/**
	 * <b>Initialise et lance le jeu</b> <br>
	 * Initialise tous les noeuds, les armes, les outils ou encore l'inventaire.
	 * <br>Configure egalement les ActionListener pour les boutons qui declenchent les actions du jeu, permettant ainsi son fonctionnement.
	 * @param estSurConsole               Indique si le jeu est execute en mode console ou non.
     * @param reprendreSauvegardeSurConsole Indique si une sauvegarde doit être reprise en mode console.
	 * @throws ErrDurabilite Une exception levee en cas d'erreur de durabilite
	 */
	
	public void creerJeu(boolean estSurConsole, boolean reprendreSauvegardeSurConsole) throws ErrDurabilite {
		
		Inventaire=new Inventaire();
		
		//ARME DE DURABILITE INFINIE
		Armes Couteau = new Armes("Couteau", 3);
		
		//OUTIL DE DURABILITE INFINI, POINT D'EXP BONUS 0,POINT DE VIE 2
		Outils Eau=new Outils("Eau", 0, 2);

		Inventaire.ajouterArme(Couteau);
		Inventaire.ajouterOutil(Eau);
		
		Outils PlumeOiseau=new Outils("Plume d'oiseau", 5,5,3);
		Outils EaudeGuerison=new Outils("Eau de guérison",5 ,2,3);
		Outils PotionBleue=new Outils ("Potion bleue", 2,5,2);
		Outils Feuille=new Outils ("Feuille d'or", 0,8,2);
						
		Armes Epee = new Armes("Epee", 15,3);
		Armes Lance = new Armes("Lance", 10, 3);
		Armes Boomerang = new Armes("Boomerang", 10,3);
		Armes Hache = new Armes("Hache", 8, 3);
		
		Ennemis Monstres= Ennemis.FAIBLE;  //HEALTH, DEGATS
		Ennemis MonstreLac=Ennemis.MOYEN;
		Ennemis Grotte=Ennemis.MOYEN;
		Ennemis Final=Ennemis.FORT;
		
		
		DecisionNode LaForetMysterieuse	= new DecisionNode("\nVous partez à l’aventure dans une forêt pleine de mystères.\nA l’entrée: vous vous retrouvez face à un choix difficile, d’un côté se trouve un sentier étroit et obscur et de l’autre un sentier plus large et inondé de lumière.");
		DecisionNode ChoixTresor1		= new DecisionNode("Malheureusement, vous ne pouvez choisir qu’un seul élément. Ce choix déterminera votre métier.");
		DecisionNode ChoixTresor2		= new DecisionNode("Les créatures ont abandonné des objets mais vous n’avez le temps que d’en prendre un seul avant que d’autres n’arrivent. Ce choix est déterminant pour la suite de votre aventure.");
		CombatNode Combat1				= new CombatNode("Il faut les combattre, les ennemis ont " +Monstres.getHealth() +" points de vie.", Monstres, ChoixTresor2, 3);
		DecisionNode RencontreImprevue	= new DecisionNode("Au cours de votre exploration, vous rencontrez un personnage mystérieux\n "
				+ "vêtu de capes et de voiles qui semble très bien maitriser l'élément " + ElementJoueur.toString()+"\nIl vous propose d’améliorer la maitrise de votre élément en effectuant 2 missions. Acceptez-vous son offre ou décidez-vous de poursuivre votre chemin dans la forêt?\n");
		
		DecisionNode CreatureLegendaire	= new DecisionNode(""); //CETTE DESCRIPTION EST MODIFIEE PAR LA SUITE A L'AIDE D'UN SETTER
		ChanceNode Oiseau				= new ChanceNode("");
		DecisionNode TerresPolluees		= new DecisionNode("Vous continuez votre périple et vous remarquez qu'une zone de la forêt est en train de mourir à cause du Maître des Ténèbres. Il est nécessaire de purifier la terre pour restaurer l'équilibre de la forêt.");
		ChanceNode Purification2		=new ChanceNode("");
		
		CombatNode Purification			= new CombatNode("Votre 2e mission consiste à purifier une cascade d'eau corrompue par le Maître des Ténèbres. Il faut les combattre, les ennemis ont " +MonstreLac.getHealth() +" points de vie.", MonstreLac, Purification2,5);
		DecisionNode CristalArgente		= new DecisionNode("Vous devez grimper au sommet d'une cascade éclairée par la lueur de la lune pour collecter une larme de cristal argenté. ");
		DecisionNode EscaladerParoi		= new DecisionNode("Mais comment voulez-vous procéder ?");
		DecisionNode PouvoirsCascade	= new DecisionNode("Quel pouvoir voulez-vous choisir ?");
		ChanceNode GrotteCachee			= new ChanceNode("");	
		EnigmeNode EnigmeCristal		= new EnigmeNode("Vous avancez. Un piédestal attire votre attention. Vous remarquez une inscription : \n"
				+ "« Je grandis sans vie, une lueur qui s'élance,\r\n"
				+ "Pas de poumon pourtant, je danse dans l'errance,\r\n"
				+ "L'eau me fait mourir, mais l'air me fait vivre,\r\n"
				+ "Qui suis-je donc, ce phénomène à saisir et à délivrer ? "
				+ "Avez vous la réponse ? (Vous avez 3 essais en tout)", Purification, "C’est la bonne réponse ! Quel perspicacité !" , "Mauvaise réponse. Réessayez. Réfléchissez bien, en quoi ce monde est-il spécial ?", Boomerang, null, 5);
		CombatNode CombatGrotte			= new CombatNode("Il faut les combattre, les ennemis ont " +Grotte.getHealth() +" points de vie.", Grotte, EnigmeCristal, 10);
		
		EnigmeNode DecoderParchemin = new EnigmeNode("L’homme vous remet un parchemin ancien et déclare :\r\n"
				+ "Quatre symboles anciens, quatre chemins à tracer,\r\n"
				+ "Pour avancer, déchiffrez-les.\r\n"
				+ "Eau, Terre, Air, Feu, chacun a son dessin, \r\n"
				+ "Saisis le bon choix pour suivre ton destin.\r\n (Vous avez 3 essais en tout)", Purification, "Bonne réponse", "Mauvaise réponse, regardez bien le parchemin", null, PotionBleue, 5);
		DecisionNode GardienneForet		= new DecisionNode("Alors que vous progressez dans la forêt, vous rencontrez la Gardienne de la Forêt, une créature puissante qui protège cet endroit pouvant vous donnez un avantage. ");
		ChanceNode Impressionner		= new ChanceNode("Quelle pouvoir voulez-vous utilisez pour lui en mettre plein les yeux ?");
		DecisionNode AutelSacre			= new DecisionNode("Vous vous enfoncez plus profondément dans la Forêt. Vous pouvez entendre le murmure des arbres et le chant des rivières. \nBientôt, vous arrivez à un ancien autel de pierre orné de symboles mystiques et entouré d'une aura de pouvoir.");
		ChanceNode GagnerConfiance		= new ChanceNode("Vous parlez à la Gardienne avec respect et bienveillance, en montrant que vous comprenez l'importance de la protection de la forêt : ");
		CombatNode Combat2				= new CombatNode("Il faut combattre le monstre du Lac", MonstreLac, AutelSacre, 5);
		DecisionNode CheneAncien		= new DecisionNode("Vous apprenez l'existence d'un chêne millénaire au cœur de la forêt. Voulez-vous tenter de le trouver ? ");
		ChanceNode CheneAncien2			= new ChanceNode("Vous repérer une feuille d'or au milieu des branchages. \nVous pouvez retenter plusieurs fois le même choix");
		
		TerminalNode Epilogue			= new TerminalNode("Vous devenez une légende, honoré pour avoir sauvé le monde des ténèbres. Les peuples reconnaissants chantent vos exploits et se souviendront longtemps de votre courage."
				+ "Vous laissez derrière vous un monde rétabli. Vous restez le gardien de l'équilibre, prêt à guider ce monde vers un avenir lumineux."
				+ "\n\nMerci d'avoir entrepris ce voyage épique à travers les éléments. Que votre aventure continue à illuminer chaque chemin que vous emprunterez.");
		Defaite 						= new TerminalNode("Vous n'avez plus de points de vie. Vous avez perdu");
		
		CombatNode CombatFinal			= new CombatNode("L’Esprit de la Forêt vous révèle que le Maître des Ténèbres se trouve dans un lieu autrefois oublié, mais désormais imprégné de pouvoir maléfique.\n"
				+"Guidé par cette nouvelle information, vous vous dirigez vers le repaire du Maître des Ténèbres, déterminé à mettre un terme à ses sombres desseins et à sauver le monde de sa menace imminente."
				+ "Il est temps d’affronter le Maître des Ténèbres. Il a "+Final.getHealth()+" points de vie et il peut faire jusqu'à 40 dégâts. Une bataille épique s’annonce. Réussirez-vous à sauver le monde ? (Pensez à sauvegarder la partie dans le menu en haut)"
				, Final, Epilogue, 0);
		
		EnigmeNode EspritForet			= new EnigmeNode("Vous vous dirigez vers un endroit sacré où réside l'Esprit de la Forêt. Il vous demande de résoudre une énigme qui est le seul moyen de gagner sa confiance. \r\n"
				+ "« Je suis né il y a longtemps, je grandis sans jamais vieillir, et je reviens souvent à la vie. Je cours toute la journée mais je ne sais pas marcher. \nJe suis parfois entouré d'arbres, mais je ne suis pas une forêt. Je murmure dans le vent, mais je n'ai pas de bouche. » \nQui suis-je ? (Vous avez 3 essais en tout)", 
				CombatFinal, "Vous avez trouvé la bonne réponse.", "Mauvaise réponse. Premier Indice : Regardez autour de vous ! Autre Indice : Augmenter le son !", Epee, null,10);	
		
		
		LaForetMysterieuse.addChoice("Emprunter le sentier large ",ChoixTresor1,"Il vous mène à un champ de fleurs sauvages multicolores aux parfums envoûtants où vous découvrez un coffre au trésor.");
		LaForetMysterieuse.addChoice("Emprunter le sentier étroit",Combat1, null, null, 1,"Vous avancez prudemment à travers les arbres centenaires, sentant l'énergie de la terre sous vos pieds. Cependant, alors que vous avancez, vous êtes attaqué par un groupe de créatures de la forêt. ");
		
		
		ChoixTresor1.addChoice("Arc", RencontreImprevue, "Metier");
		ChoixTresor1.addChoice("Poison", RencontreImprevue, "Metier");
		
		ChoixTresor2.addChoice("Arbalète", RencontreImprevue, "Metier");
		ChoixTresor2.addChoice("Marteau de guerre", RencontreImprevue, "Metier");
		ChoixTresor2.addChoice("Dague empoisonnée", RencontreImprevue, "Metier");

		
		CristalArgente.addChoice("Escalader la Paroi de la Cascade",EscaladerParoi, null, null, 10, "Vous décidez d'escalader directement la paroi de la cascade. ");
		CristalArgente.addChoice("Chercher une Grotte Cachée derrière la cascade.",GrotteCachee, "Vous choisissez de chercher un passage secret derrière la cascade…");

		EscaladerParoi.addChoice("Faire appel à vos Pouvoirs Élémentaires ",PouvoirsCascade, "Choisissez bien");
		EscaladerParoi.addChoice("Utiliser une arme",Purification, "Vous utilisez votre couteau. Vous vous accrochez aux parois rocheuses et progresser en taillant des prises. Vous avez réussi à accéder au sommet.");
	
		Purification2.addCsq("Vous avez fini les 2 missions demandées par l'homme.", TerresPolluees, Hache, null, 10);
		
		Oiseau.addCsq("Il y réside un gardien. En engageant la conversation avec lui, vous apprenez l’emplacement actuel de la créature légendaire. Vous rendez à ce lieu. L'oiseau majestueux est là, endormi dans son nid. Ses plumes scintillent sous le soleil. Vous êtes émerveillé."
				+ "\nBien joué, vous avez retrouvé la créature légendaire. Il semblerait que les plumes de cet oiseau soient très puissantes. Vous en ramassez une, peut-être cela vous sera utile par la suite. Vous avez fini les 2 missions demandées par l'homme.", TerresPolluees, Hache, PlumeOiseau, 10);
		Oiseau.addCsq("En parcourant le sanctuaire, vous remarquez une peinture sur le mur qui illustre le domaine de la créature légendaire. C’est là que la bête de l'élément "+ElementJoueur.toString() +" se manifeste le plus souvent. Vous vous aventurez dans ce lieu et le trouvez assoupi."
				+ "\nBien joué, vous avez retrouvé la créature légendaire. Il semblerait que les plumes de cet oiseau soient très puissantes. Vous en ramassez une, peut-être cela vous sera utile par la suite. Vous avez fini les 2 missions demandées par l'homme.", TerresPolluees, Hache, PlumeOiseau, 10);
		Oiseau.addCsq("En pénétrant dans le sanctuaire, vous tombez sur un autel mystérieux. En suivant les instructions gravées sur la pierre, vous accomplissez un rituel sacré qui fait apparaître la créatue légendaire pour quelques instants. Vous êtes ébloui par la splendeur de cet animal fabuleux."
				+ "\nBien joué, vous avez retrouvé la créature légendaire. Il semblerait que les plumes de cet oiseau soient très puissantes. Vous en ramassez une, peut-être cela vous sera utile par la suite. Vous avez fini les 2 missions demandées par l'homme.", TerresPolluees, Hache, PlumeOiseau, 10);
		
		GrotteCachee.addCsq("Ici un passage ! Vous avancez prudemment dans l’obscurité mais un monstre de feu vous barre la route. ", CombatGrotte);
		GrotteCachee.addCsq("Ici un passage ! ", EnigmeCristal);
		
		
		
		
		List<String> pouvoirs = ElementJoueur.getPouvoirsDisponibles(this);
		
//		 Ici les choix des noeuds dependent de l'element choisi au depart
		if (ElementJoueur instanceof Feu) {
			AutelSacre.addChoice("Utiliser votre Pouvoir Élémentaire",CheneAncien, "Vous invoquez une flamme sur l'autel, révélant des inscriptions anciennes. \nCes inscriptions révèlent un indice sur une de vos prochaines aventures : \nvous remarquez un chêne aux milles feuilles d’or.");
			EnigmeCristal.setNextNode(CreatureLegendaire);
			PouvoirsCascade.addChoice(pouvoirs.get(0), PouvoirsCascade, "Ce pouvoir n’a aucune utilité dans votre situation. ");
			PouvoirsCascade.addChoice(pouvoirs.get(1), PouvoirsCascade, "Ce pouvoir n’a aucune utilité dans votre situation. ");
			PouvoirsCascade.addChoice(pouvoirs.get(2), EnigmeCristal, "La lumière vous permet de mieux percevoir les prises sûres. ");
			if (pouvoirs.size()>3) {
				PouvoirsCascade.addChoice(pouvoirs.get(3), PouvoirsCascade, "Ce pouvoir n’a aucune utilité dans votre situation. ");
			}
			
			CristalArgente.setDescription("L’homme vous explique que le feu est lié à la purification et à la renaissance."
					+ "\nPour progresser dans la maitrise du feu, vous devrez accomplir 2 tâches : \nretrouver un cristal argenté et une créature légendaire, le Phénix. "+CristalArgente.getDescription());
			RencontreImprevue.addChoice("Accepter l'Offre ",CristalArgente, null, EaudeGuerison, 10,"Vous êtes récompensé");
			
			CreatureLegendaire.setDescription("Votre 2e mission consiste à partir à la recherche d'une créature légendaire liée au feu, le Phénix. Comment voulez-vous le localiser ?");
			CreatureLegendaire.addChoice("Chercher des signes de son passage  ",TerresPolluees,Hache, PlumeOiseau, 10, "Grace à votre Sens du Feu, vous repérez et suivez les signes de chaleur intense et des flammes inhabituelles. Le Phénix est là, endormi sur un nid. Ses plumes scintillent sous le soleil. Vous êtes émerveillé."
					+ "\nBien joué, vous avez retrouvé le Phénix. Il semblerait que les plumes de ce Phénix soient très puissantes. Vous en ramassez une, peut-être cela vous sera utile par la suite. Vous avez fini les 2 missions demandées par l'homme.");
			CreatureLegendaire.addChoice("Observer les Phénomènes Thermiques",TerresPolluees, Hache, PlumeOiseau, 10, "Vous suivez les anomalies thermiques, les distorsions de la chaleur qui signalent la présence du Phénix. Le Phénix est là, endormi dans un nid. Ses plumes scintillent sous le soleil."
					+ "\nBien joué, vous avez retrouvé le Phénix. Il semblerait que les plumes de ce Phénix soient très puissantes. Vous en ramassez une, peut-être cela vous sera utile par la suite. Vous avez fini les 2 missions demandées par l'homme.");
			CreatureLegendaire.addChoice("Explorer des Lieux Sacrés où il a été aperçu",Oiseau, "Vous entrez dans un sanctuaire situé au milieu de la forêt.");
		}
		else if (ElementJoueur instanceof Eau) {
			AutelSacre.addChoice("Utiliser votre Pouvoir Élémentaire",CheneAncien, "Vous lancez de l'eau sur l'autel, faisant apparaître des reflets magiques\n qui vous dévoilent un chêne aux milles feuilles d’or aux pouvoirs mystiques.  \nC’est un indice sur une de vos prochaines aventures.");
			PouvoirsCascade.addChoice("Utiliser votre maitrise de l'eau", EnigmeCristal, "Vous arrivez à limiter le débit d’eau pendant un court instant. Vous permettant de grimper. ");
			PouvoirsCascade.addChoice("Utiliser un outil", EnigmeCristal,"Aucun outil dans votre inventaire n'est adéquate");
			CristalArgente.setDescription("L’homme explique que l'eau symbolise la guérison et l'équilibre. "
					+ "\nPour progresser dans la maitrise de l’eau, vous avez 2 missions : \n retrouver un cristal argenté et purifier une cascade.\n"
					+CristalArgente.getDescription());
			RencontreImprevue.addChoice("Accepter l'Offre  ",CristalArgente, null, EaudeGuerison, 10, "Vous êtes récompensés");
			
		}
		else if (ElementJoueur instanceof Terre) {
			AutelSacre.addChoice("Utiliser votre Pouvoir Élémentaire",CheneAncien, "Vous faites pousser des plantes autour de l'autel, révélant une racine ancienne qui contient des informations sur un objet magique : vous remarquez un chêne aux milles feuilles d’or. ");
			DecoderParchemin.setDescription("L’homme explique que la terre est liée à la résilience et à la protection. Pour progresser dans la maitrise de la terre, vous devez accomplir 2 tâches : décoder un parchemin et purifier la nature. \n"
					+ DecoderParchemin.getDescription());
			RencontreImprevue.addChoice("Accepter l'Offre  ",DecoderParchemin, null, EaudeGuerison, 10, "Vous êtes récompensés");
			
		}
		else if (ElementJoueur instanceof Air) {
			AutelSacre.addChoice("Utiliser votre Pouvoir Élémentaire",CheneAncien, "Vous invoquez un tourbillon d'air autour de l'autel, faisant tourner des parchemins anciens. Ces parchemins vous révèlent des prophéties mystérieuses liées à une de vos futures quêtes : vous apercevez un chêne aux milles feuilles d’or.");
			EnigmeCristal.setNextNode(CreatureLegendaire);
			PouvoirsCascade.addChoice(pouvoirs.get(0), PouvoirsCascade, "Ce pouvoir n’a aucune utilité dans votre situation. ");
			PouvoirsCascade.addChoice(pouvoirs.get(1), EnigmeCristal, "Vous créez de légères crevasses dans la paroi qui forment des prises sûres pour vous agripper et grimper.");
			PouvoirsCascade.addChoice(pouvoirs.get(2), PouvoirsCascade, "Ce pouvoir n’a aucune utilité dans votre situation. ");
			if (pouvoirs.size()>3) {
				PouvoirsCascade.addChoice(pouvoirs.get(3), PouvoirsCascade, "Ce pouvoir n’a aucune utilité dans votre situation. ");
			}
			CristalArgente.setDescription("L’homme explique que l'air est lié à la connaissance et à la communication. "
					+ "\nPour progresser dans la maitrise de l'air, vous devez accomplir 2 tâches : \n: retrouver un cristal argenté et purifier la nature."+CristalArgente.getDescription());
			
			RencontreImprevue.addChoice("Accepter l'Offre  ",CristalArgente,null, EaudeGuerison,10,"Vous êtes récompensés");
			
			CreatureLegendaire.setDescription("Votre 2e mission consiste à partir à la recherche d'une créature légendaire liée à l'air, l'Oiseau des Nuages. Comment voulez-vous le localiser ?");
			CreatureLegendaire.addChoice("Chercher des signes de son  passage",TerresPolluees ,Hache, PlumeOiseau, 10, "Grace à votre maîtrise de l’air vous suivez les courants d'air particuliers qui indiquent la présence de l’Oiseau des Nuages. L’Oiseau des Nuages est là, endormi dans son nid. Ses plumes scintillent sous le soleil. Vous êtes émerveillé."
					+ "\nBien joué, vous avez retrouvé l'Oiseau des Nuages. Il semblerait que les plumes de cet oiseau soient très puissantes. Vous en ramassez une, peut-être cela vous sera utile par la suite. Vous avez fini les 2 missions demandées par l'homme.");
			CreatureLegendaire.addChoice("Observer les Schémas Célestes ",TerresPolluees, Hache, PlumeOiseau, 10, "Vous étudiez les schémas célestes et suivez les mouvements des nuages. Vous le trouvez, il est là, endormi dans son nid. Ses plumes scintillent sous le soleil."
					+ "\nBien joué, vous avez retrouvé l'Oiseau des Nuages. Il semblerait que les plumes de cet oiseau soient très puissantes. Vous en ramassez une, peut-être cela vous sera utile par la suite. Vous avez fini les 2 missions demandées par l'homme.");
			CreatureLegendaire.addChoice("Explorer des Lieux Sacrés où a été déjà aperçu l'Oiseau des Nuages",Oiseau, "Bien joué, vous avez retrouvé l'Oiseau des Nuages, il semblerait que les plumes de l'Oiseau des Nuages soient très puissantes.\nVous en ramassez une, peut-être cela vous sera utile par la suite. Vous avez fini les 2 missions demandées par l'homme.");
						
		}
		
		RencontreImprevue.addChoice("Refuser l'Offre  ",GardienneForet,null, null, -3, "Vous décidez de poursuivre votre chemin sans son aide.");
		AutelSacre.addChoice("Ignorer l'Autel",EspritForet, "Vous poursuivez votre route");
		
		GardienneForet.addChoice("L'Impressionner ",Impressionner, "Allez-vous réussir à l'impressionner ?");
			
		Impressionner.addChoice("Utiliser "+ElementJoueur.getPv2());
		Impressionner.addChoice("Improviser une danse");
		Impressionner.addChoice("Jongler avec vos armes");		
		Impressionner.addCsq("Quel talent ! Elle vous offre une lance pour récompenser votre performance.", AutelSacre, Lance, null, 0);
		Impressionner.addCsq("Elle n’a pas été absolument convaincue mais elle vous laisse passer. ", AutelSacre);
		Impressionner.addCsq("Vous ne l'avez pas convaincue, réessayer, peut-être cela fonctionnera la prochaine fois. ", Impressionner);
		
		
		GardienneForet.addChoice("Gagner sa confiance",GagnerConfiance, "");
		
		GagnerConfiance.addChoice("Merci, Gardienne, de protéger la forêt si fidèlement");
		GagnerConfiance.addChoice("Je respecte votre mission de préserver la forêt");	
		GagnerConfiance.addChoice("Vous faites un travail admirable, Gardienne");
		
		GagnerConfiance.addCsq("Bien joué, vous avez réussi, elle vous offre une lance", TerresPolluees, Lance, null,0);
		GagnerConfiance.addCsq("Elle n’a pas été absolument convaincue mais elle vous laisse passer", TerresPolluees);
		GagnerConfiance.addCsq("Vous ne l'avez pas convaincue, réessayer, peut-être cela fonctionnera la prochaine fois", GagnerConfiance);

		
		TerresPolluees.addChoice("Utiliser votre sens des éléments",AutelSacre, "Vous avez purifié ces terres. Bravo!");
		TerresPolluees.addChoice("Trouver une Source de Pureté",Combat2, "Vous avez trouvé une source cachée qui est le cœur de la forêt et vous voulez prélevez de son eau pure. \nMais un monstre apparait. ");	
		TerresPolluees.addChoice("Demander de l'Aide à un Guérisseur ",CheneAncien,null, null, -3, "Vous demandez à un guérisseur qui vous aide à sauver cette partie de la forêt.");	

		CheneAncien.addChoice("Oui",CheneAncien2, "Vous réussissez à le localiser.");
		CheneAncien.addChoice("Non",EspritForet, "Vous poursuivez votre route");
		
		CheneAncien2.addChoice("Vous tentez de grimper à l'arbre");
		CheneAncien2.addChoice("Vous utilisez un objet de l’inventaire d'armes. ");
		CheneAncien2.addChoice("Vous secouez l'arbre");
		
		//Nextnode au hasard entre ces 2 possibilites
		CheneAncien2.addCsq("Cela ne marche pas. Réessayez", CheneAncien2);
		CheneAncien2.addCsq("Bien joué vous avez récupéré une feuille d'or", EspritForet, null, Feuille, 0);
		
		//Reponses possibles pour l'EnigmeNode EspritForet
		EspritForet.addReponsePossible("riviere");
		EspritForet.addReponsePossible("river");//en anglais
		EspritForet.addReponsePossible("rivière");
		EspritForet.addReponsePossible("fleuve");
		EspritForet.addReponsePossible("ruisseau");
		EspritForet.addReponsePossible("cours d'eau");
		EspritForet.addReponsePossible("cours d eau");
		EspritForet.addReponsePossible("cours deau");
		EspritForet.addReponsePossible("eau");
		EspritForet.addReponsePossible("water");
		
		
		EnigmeCristal.addReponsePossible("feu");
		EnigmeCristal.addReponsePossible("le feu");
		EnigmeCristal.addReponsePossible("un feu");
		EnigmeCristal.addReponsePossible("flamme");
		EnigmeCristal.addReponsePossible("fire");
		
		DecoderParchemin.addReponsePossible("terre");
		DecoderParchemin.addReponsePossible("la terre");
		DecoderParchemin.addReponsePossible("earth");
		
	 
		
		//Cas ou le jeu se joue sur console
		if (estSurConsole) {
			if (reprendreSauvegardeSurConsole==false) {
			currentNode = LaForetMysterieuse;
			currentNode = currentNode.chooseNext(this);
			//CHOIX DU METIER
			if (currentNode==ChoixTresor1) {
				currentNode.display();
				ChoixMetier M= new ChoixMetier();
				M.display();
				MetierJoueur=M.choisirMetier1(this);
			}
			else {
				currentNode=currentNode.chooseNext(this);
				currentNode.display();
				ChoixMetier M= new ChoixMetier();
				M.display();
				MetierJoueur=M.choisirMetier2(this);
				}
			currentNode=RencontreImprevue;
			System.out.println(MetierJoueur.getDescription());
			System.out.println("Vous poursuivez votre chemin dans la forêt.");
			}
			//BOUCLE QUI PERMET AU JEU DE FONCTIONNER dans la console
			while (!(currentNode instanceof TerminalNode)) {
				currentNode = currentNode.chooseNext(this);
			}
			currentNode.display(); //AFFICHE LA DESCRIPTION DU TERMINAL NODE		
		}
		
		
		//Tout le code suivant concerne le cas ou le jeu se joue dans l'INTERFACE GRAPHIQUE
		
		else {			
			//Initialisation des images
	        ImageNode autelsacre = new ImageNode(AutelSacre, ".//images//autelsacre.png", ImageLabel);
	        ImageNode combat1 = new ImageNode(Combat1, ".//images//monstreforet.png", ImageLabel);
	        ImageNode cascade = new ImageNode(Purification, ".//images//cascade.png", ImageLabel);
	        ImageNode terrespolluees = new ImageNode(TerresPolluees, ".//images//terrespolluees.png", ImageLabel);
	        ImageNode gardienneforet = new ImageNode(GardienneForet, ".//images//foret3.png", ImageLabel);
	        ImageNode cheneancien = new ImageNode(CheneAncien, ".//images//cheneancien.png", ImageLabel);
	        ImageNode decoderparchemin = new ImageNode(DecoderParchemin, ".//images//parchemin.png", ImageLabel);
	        ImageNode rencontreimprevue = new ImageNode(RencontreImprevue, ".//images//rencontre_imprevue.png", ImageLabel);
	        ImageNode choixtresor1 = new ImageNode(ChoixTresor1, ".//images//choixmetier1.png", ImageLabel);
	        ImageNode choixtresor2 = new ImageNode(ChoixTresor2, ".//images//choixmetier2.png", ImageLabel);
	        ImageNode creaturelegendaire = new ImageNode(CreatureLegendaire, ".//images//creature_legendaire.png", ImageLabel);
	        ImageNode cristalargente = new ImageNode(CristalArgente, ".//images//cristal_argente.png", ImageLabel);
	        ImageNode grottecachee = new ImageNode(GrotteCachee, ".//images//grotte.png", ImageLabel);
	        ImageNode enigmecristal = new ImageNode(Epilogue, ".//images//piedestral.png", ImageLabel);
	        ImageNode combatfinal = new ImageNode(CombatFinal, ".//images//finalboss.png", ImageLabel);
	        ImageNode epilogue = new ImageNode(Epilogue, ".//images//perso.png", ImageLabel);
	        SoundNode nodeWithSound = new SoundNode(LaForetMysterieuse, ".//sons//foret_mysterieuse.wav");
	        SoundNode cristalargenteSon = new SoundNode(CristalArgente, ".//sons//night_forest.wav");
	        SoundNode enigmecristalSon = new SoundNode(EnigmeCristal, ".//sons//birds_singing.wav");
	        
	        //Cas d'une nouvelle partie sinon currentnode est déjà initialisé
	        if (currentNode==null) {
	        	currentNode=LaForetMysterieuse;
	        	//Autre manière de changer l'image sans utiliser ImageNode
	        	ImageIcon foret = new ImageIcon(".//images//foret1.png");
	      		ImageLabel.setIcon(foret);
	        	nodeWithSound.display();
	        	InitialisationInterface();
	        }
	        //Cas d'une partie reprise
	        else{
	        	InitialisationInterface();
	        	//Affichage des images selon les noeuds
	        	if (currentNode.equals(Combat1)) {combat1.display();}
				if (currentNode.equals(AutelSacre)) {autelsacre.display();}
				if (currentNode.equals(RencontreImprevue)) {rencontreimprevue.display();}
				if (currentNode.equals(TerresPolluees)) {terrespolluees.display();}
				if (currentNode.equals(CheneAncien)) {cheneancien.display();}					
				if (currentNode.equals(ChoixTresor1)) {choixtresor1.display();}
				if (currentNode.equals(ChoixTresor2)) {choixtresor2.display();}
				if (currentNode.equals(CristalArgente)) {
					cristalargenteSon.display();
					cristalargente.display();
				}
				if (currentNode.equals(EnigmeCristal)) {
					enigmecristalSon.display();
					enigmecristal.display();}
				if (currentNode.equals(GrotteCachee)) {grottecachee.display();}
				if (currentNode.equals(CreatureLegendaire)) {creaturelegendaire.display();}
				if (currentNode.equals(Purification)) {cascade.display();
				resultArea.setText(resultArea.getText()+"");}
				if (currentNode.equals(EspritForet)) {gardienneforet.display();}
				if (currentNode.equals(DecoderParchemin)) {decoderparchemin.display();}
				if (currentNode.equals(CombatFinal)) {combatfinal.display();}
				
				
        	
				if  (currentNode instanceof DecisionNode) {
					descriptionArea.setFont(normalFond);
					remarqueArea.setVisible(false);
					niveauArea.setVisible(false);
					((DecisionNode) currentNode).afficherChoixIG(Game.this);	
				}
				else if (currentNode instanceof CombatNode) {					
					descriptionArea.setFont(new Font("Times New Roman", Font.PLAIN, 15));
					((CombatNode) currentNode).afficherChoixIG(Game.this);
				}
				else if (currentNode instanceof ChanceNode) {					
					//Verification si ChanceNode possède des choix ou non. 
					//Si c'est le cas, on les affiche
					//Sinon on passe au noeud suivant en appuyant sur ">"
					ChanceNode ChanceNode=(ChanceNode)currentNode;
					if (ChanceNode.getDecisionChoices()!= null && !ChanceNode.getDecisionChoices().isEmpty()){
						((ChanceNode) currentNode).afficherChoixIG(Game.this);}
					
					else {
						currentNode=currentNode.chooseNextIG(Game.this, 0);
						afficherFenetreResult();
					}
				}
				else if (currentNode instanceof EnigmeNode) {
					ChoixBoutonPanel.setVisible(false);
					descriptionArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
					enigmeRep.setVisible(true); enigmeBouton.setVisible(true);
				}
	        }

             
        	
		
		//Ajout d'une action sur le bouton suite ">"
        
		suiteBouton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				titrePanel.setVisible(false);	//N'affiche pas le titre
				IP.setVisible(true); 			//Affiche l'image
				resultArea.setVisible(false); 	//Enlève le résultat d'un noeud
				descriptionArea.setText(currentNode.getDescription());	//Initialise la description du noeud
				descriptionArea.setVisible(true);		//Affiche cette descriptio
				ChoixBoutonPanel.setVisible(true);		//Affiche les boutons choix
				suiteBouton.setVisible(false);			//Enlève le bouton suite ">"
				inventairePanel.setVisible(false);		//N'affiche pas l'inventaire
				clip.stop();
				
				//Affichage des images selon les noeuds
				if (currentNode.equals(AutelSacre)) 		{autelsacre.display();}
				if (currentNode.equals(RencontreImprevue)) 	{rencontreimprevue.display();}
				if (currentNode.equals(TerresPolluees)) 	{terrespolluees.display();}
				if (currentNode.equals(CheneAncien)) 		{cheneancien.display();}					
				if (currentNode.equals(ChoixTresor1)) 		{choixtresor1.display();}
				if (currentNode.equals(ChoixTresor2)) 		{choixtresor2.display();}
				if (currentNode.equals(CristalArgente)) {
					cristalargenteSon.display();
					cristalargente.display();}
				if (currentNode.equals(EnigmeCristal)) {
					enigmecristalSon.display();
					enigmecristal.display();}
				if (currentNode.equals(CreatureLegendaire)) {creaturelegendaire.display();}
				if (currentNode.equals(Purification)) 		{cascade.display();}
				if (currentNode.equals(Combat1)) 			{combat1.display();}
				if (currentNode.equals(GrotteCachee)) 		{grottecachee.display();}
				if (currentNode.equals(EspritForet)) 		{gardienneforet.display();enigmecristalSon.display();}
				if (currentNode.equals(DecoderParchemin)) 	{decoderparchemin.display();}
				if (currentNode.equals(CombatFinal))		{combatfinal.display();}
				
				if (Game.this.getElementJoueur().getHealth()<=0) {f.setVisible(false);}
				
				//Verification du type du noeud pour le prochain affichage
				if  (currentNode instanceof DecisionNode) {				
					descriptionArea.setFont(normalFond);
					remarqueArea.setVisible(false);
					niveauArea.setVisible(false);
					((DecisionNode) currentNode).afficherChoixIG(Game.this);
				}
				
				else if (currentNode instanceof CombatNode) {			
					descriptionArea.setFont(new Font("Times New Roman", Font.PLAIN, 15));
					((CombatNode) currentNode).afficherChoixIG(Game.this);
				}
				else if (currentNode instanceof ChanceNode) {					
					//Verification si ChanceNode possède des choix ou non. 
					//Si c'est le cas, on les affiche
					//Sinon on passe au noeud suivant en cliquant sur ">"
					ChanceNode ChanceNode=(ChanceNode)currentNode;
					if (ChanceNode.getDecisionChoices()!= null && !ChanceNode.getDecisionChoices().isEmpty()){
						((ChanceNode) currentNode).afficherChoixIG(Game.this);}
					else {
						currentNode=currentNode.chooseNextIG(Game.this, 0);
						afficherFenetreResult();
					}
				}
				
				else if (currentNode instanceof EnigmeNode) {
					remarqueArea.setVisible(false);
					niveauArea.setVisible(false);
					ChoixBoutonPanel.setVisible(false);
					descriptionArea.setFont(new Font("Times New Roman", Font.PLAIN, 12));
					enigmeRep.setVisible(true); enigmeBouton.setVisible(true);
					
				}
				else if (currentNode instanceof TerminalNode) {
					niveauArea.setVisible(false);
					descriptionArea.setFont(normalFond);
					ChoixBoutonPanel.setVisible(false);
					Game.this.descriptionArea.setBounds(50, 400, 620, 210);
					Game.this.remarqueArea.setVisible(false);
					epilogue.display();
				}
							
				nodeWithSound.stopSound();
				
			}
		});
		
		
		
		
		
		//Affichage lors de l'ouverture de l'interface graphique
		if  (currentNode instanceof DecisionNode) {
			((DecisionNode) currentNode).afficherChoixIG(this);
		}
		
		//Si reprise d'une partie à partir d'un CombatNode
		if  (currentNode instanceof CombatNode) {
			((CombatNode) currentNode).afficherChoixIG(this);
		}
		else if (currentNode instanceof ChanceNode) {					
			//Verification si ChanceNode possède des choix ou non. 
			//Si c'est le cas, on les affiche
			//Sinon on passe au noeud suivant en appuyant sur ">"
			ChanceNode ChanceNode=(ChanceNode)currentNode;
			if (ChanceNode.getDecisionChoices()!= null && !ChanceNode.getDecisionChoices().isEmpty()){
				((ChanceNode) currentNode).afficherChoixIG(Game.this);}
		}
		
		
		
		//Action suite au clic sur les boutons des choix
		choix1.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				titrePanel.setVisible(false);	
				if  (currentNode instanceof DecisionNode) {
					//Initialisation des métiers
					if (choix1.getText()=="Arc") {
						Armes arc = new Armes("Arc", 10);
	            		MetierJoueur = new Chasseur(ElementJoueur.getExp(), ElementJoueur.getHealth(), arc);
	            		Inventaire.ajouterArme(arc);
	            		MajIGMetier();
					}
					if (choix1.getText()=="Arbalète") {
						Armes arbalete = new Armes("Arbalète", 10);
					    MetierJoueur = new Arbaletrier(ElementJoueur.getExp(), ElementJoueur.getHealth(), arbalete);
					    Inventaire.ajouterArme(arbalete);
					    MajIGMetier();
					}
					
					afficherFenetreResult();
					currentNode =currentNode.chooseNextIG(Game.this, 1);
				} 

				else if (currentNode instanceof CombatNode) {
					niveauArea.setVisible(false);
					remarqueArea.setVisible(true);
					inventairePanel.setVisible(true);
					itemBouton2.setText("");itemBouton3.setText("");itemBouton4.setText("");itemBouton5.setText("");itemBouton6.setText("");
					Inventaire.afficherInventaireArmesIG(Game.this); //Affiche les armes dans JPanel Inventaire
				}
				else if (currentNode instanceof ChanceNode) {
					afficherFenetreResult();
					currentNode =currentNode.chooseNextIG(Game.this, 1);
				}
				enigmecristalSon.stopSound();
				cristalargenteSon.stopSound();
			}
		});
		
		choix2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				titrePanel.setVisible(false);
				if (currentNode instanceof DecisionNode) {
					if (choix2.getText()=="Poison") {
						Armes poison = new Armes("Poison",10);
	    			    MetierJoueur = new Druide(ElementJoueur.getExp(), ElementJoueur.getHealth(), poison);
	    			    Inventaire.ajouterArme(poison);
	    			    MajIGMetier();
					}
					if (choix2.getText()=="Marteau de guerre") {
						Armes marteau = new Armes("Marteau de guerre", 10);
		 			    MetierJoueur = new Guerrier(ElementJoueur.getExp(), ElementJoueur.getHealth(), marteau);
		 			    Inventaire.ajouterArme(marteau);
		 			    MajIGMetier();
					}
					afficherFenetreResult();  		
					currentNode =currentNode.chooseNextIG(Game.this, 2); // Obtient le prochain nœud pour le choix 1
	    		}
				else if (currentNode instanceof CombatNode) {
					niveauArea.setVisible(false);
					remarqueArea.setVisible(true);
					inventairePanel.setVisible(true);
	    			itemBouton2.setText("");itemBouton3.setText("");itemBouton4.setText("");itemBouton5.setText("");itemBouton6.setText("");
	    			Inventaire.afficherInventaireOutilsIG(Game.this);
	    		}
				else if (currentNode instanceof ChanceNode) {
					afficherFenetreResult();
	    			currentNode=currentNode.chooseNextIG(Game.this, 0);
	    		}
				enigmecristalSon.stopSound();
				cristalargenteSon.stopSound();
			}
		});
		choix3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				titrePanel.setVisible(false);
				if (currentNode instanceof DecisionNode) {
					if (choix3.getText()=="Dague empoisonnée") {
						Armes dague = new Armes("Dague Empoisonnée", 10);
					    MetierJoueur = new Traqueur(ElementJoueur.getExp(), ElementJoueur.getHealth(), dague);
					    Inventaire.ajouterArme(dague);
					    MajIGMetier();
					}
					if (!choix3.getText().isEmpty()) {
						afficherFenetreResult();
						currentNode =currentNode.chooseNextIG(Game.this, 3); // Obtient le prochain nœud pour le choix 1
					}
				}
				else if (currentNode instanceof CombatNode) {
					
					if (((CombatNode)currentNode).getEnnemi().getHealth()>0) {
						inventairePanel.setVisible(false);
						remarqueArea.setVisible(false);
						try {
							((CombatNode)currentNode).combatIG(Game.this, 3);
						} catch (ErrDurabilite e1) {
							e1.printErr();
						}	
					}
					else {currentNode=currentNode.chooseNextIG(Game.this, 1);}
				}
				
				else if (currentNode instanceof ChanceNode) {
					if (!choix3.getText().isEmpty()) {
						afficherFenetreResult();
						currentNode=currentNode.chooseNextIG(Game.this, 0);
					}
	    		}
				enigmecristalSon.stopSound();
				cristalargenteSon.stopSound();
			}
		});
		choix4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				titrePanel.setVisible(false);
				if (currentNode instanceof DecisionNode) {
					if (!choix4.getText().isEmpty()) {
						afficherFenetreResult();
						currentNode =currentNode.chooseNextIG(Game.this, 4); // Obtient le prochain nœud pour le choix 1
		    		}
				}
				else if (currentNode instanceof CombatNode) {
					if (((CombatNode)currentNode).getEnnemi().getHealth()>0) {
						inventairePanel.setVisible(false);
						remarqueArea.setVisible(false);
						try {
							((CombatNode)currentNode).combatIG(Game.this, 4);
						} catch (ErrDurabilite e1) {
							e1.printErr();
						}	
						}
					else {currentNode=currentNode.chooseNextIG(Game.this, 1);}	
				}
				else if (currentNode instanceof ChanceNode) {
					if (!choix4.getText().isEmpty()) {
						afficherFenetreResult();
						currentNode=currentNode.chooseNextIG(Game.this, 0);
					}
		    	}
				enigmecristalSon.stopSound();
				cristalargenteSon.stopSound();
			}
		});
		
		
		
		
		//Que se passe-t-il losqu'on clique sur un objet d'un des inventaires ?
		itemBouton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				String nomObjet = itemBouton1.getText();
				armechoisie = Inventaire.choisirArmeIG(nomObjet);// Récupère l'objet Arme correspondant au nom
				outilchoisi=Inventaire.choisirOutilIG(nomObjet);
				try {
					actionBoutonsInventaire();
				} catch (ErrDurabilite e1) {
					e1.printErr();
				}
			}
		});
					
		itemBouton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				String nomObjet = itemBouton2.getText();
				armechoisie = Inventaire.choisirArmeIG(nomObjet);// Récupère l'objet Arme correspondant au nom
				outilchoisi=Inventaire.choisirOutilIG(nomObjet);
				try {
					actionBoutonsInventaire();
				} catch (ErrDurabilite e1) {
					e1.printErr();
				}
				
			}
		});
		itemBouton3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				String nomObjet = itemBouton3.getText();
				armechoisie = Inventaire.choisirArmeIG(nomObjet);// Récupère l'objet Arme correspondant au nom
				outilchoisi=Inventaire.choisirOutilIG(nomObjet);
				try {
					actionBoutonsInventaire();
				} catch (ErrDurabilite e1) {
					e1.printErr();
				}
				
			}
		});		
		itemBouton4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				String nomObjet = itemBouton4.getText();
				armechoisie = Inventaire.choisirArmeIG(nomObjet);// Récupère l'objet Arme correspondant au nom
				outilchoisi=Inventaire.choisirOutilIG(nomObjet);
				try {
					actionBoutonsInventaire();
				} catch (ErrDurabilite e1) {
					e1.printErr();
				}
				
			}
		});		
		itemBouton5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				String nomObjet = itemBouton5.getText();
				armechoisie = Inventaire.choisirArmeIG(nomObjet);// Récupère l'objet Arme correspondant au nom
				outilchoisi=Inventaire.choisirOutilIG(nomObjet);
				try {
					actionBoutonsInventaire();
				} catch (ErrDurabilite e1) {
					e1.printErr();
				}
				
			}
		});
		itemBouton6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {				
				String nomObjet = itemBouton6.getText();
				armechoisie = Inventaire.choisirArmeIG(nomObjet);// Récupère l'objet Arme correspondant au nom
				outilchoisi=Inventaire.choisirOutilIG(nomObjet);
				try {
					actionBoutonsInventaire();
				} catch (ErrDurabilite e1) {
					e1.printErr();
				}
				
			}
		});	
		}
	}

	

	
	/**
	 * <b>Initialise l'interface graphique du jeu au démarrage.</b><br>
	 * Cree et configure les composants de l'interface, 
	 * tels que le titre, une image, le bouton Nouvelle Partie, le bouton Parties Sauvegardees, un bouton de suite, le Panel des choix ou encore le resultat du noeud
	 */
	public void initialiserInterfaceDebut() {
		//Initialisation de l'interface graphique
		f1=new JFrame("Jeu");
		f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f1.setSize(700,725);
		f1.getContentPane().setBackground(Color.black);
		f1.setLayout(null);
		
		//Affichage du titre du jeu
		
		titrePanel= new JPanel();
		titrePanel.setBounds(50,100,620,200);//creer un rectangle pour mettre le titre
		titrePanel.setOpaque(false);
		titreLabel= new JLabel("Le voyage des Elements");
		titreLabel.setForeground(Color.white);
		titreLabel.setFont(titreFond);
		titrePanel.add(titreLabel);
		titrePanel.setVisible(true);
		f1.add(titrePanel);
		
		
		     
		//Affichage d'une image 
		IP = new JPanel();
		IP.setBounds(0,0,700,400);
		IP.setBackground(Color.black);
		ImageIcon icon = new ImageIcon(".//images//depart.png");
		ImageLabel=new JLabel(icon);
		ImageLabel.setBackground(Color.black);
		IP.setVisible(true);
        IP.add(ImageLabel);
        f1.add(IP);
        
        //Affichage d'un bouton
        suiteBouton1 = new JButton(">");
		suiteBouton1.setBackground(Color.black);//fond du bouton
		suiteBouton1.setForeground(Color.white);
		suiteBouton1.setBounds(250, 500, 200, 100);
		suiteBouton1.setVisible(false);
		f1.add(suiteBouton1);
		
		
		//Bouton pour commencer une nouvelle partie
		startBouton= new JButton("Nouvelle Partie");
		startBouton.setBounds(250,500,200,50);
		startBouton.setBackground(Color.black);//fond du bouton
		startBouton.setForeground(Color.white); //couleur du texte
		startBouton.setFont(normalFond);
		startBouton.setFocusPainted(false);
		f1.add(startBouton);
	
		
		//Bouton pour reprendre une partie sauvegardee
		loadBouton= new JButton("Parties sauvegardées");
		loadBouton.setBounds(250, 550, 200, 50);
		loadBouton.setBackground(Color.black);//fond du bouton
		loadBouton.setForeground(Color.white); //couleur du texte
		loadBouton.setFont(normalFond);
		loadBouton.setFocusPainted(false);
		f1.add(loadBouton);
		
		
		
		//Affichage description d'un noeud		
		descriptionArea = new JTextArea("");
		descriptionArea.setBounds(50, 400, 615, 120);
		descriptionArea.setBackground(Color.black);
		descriptionArea.setForeground(Color.white);
		descriptionArea.setFont(normalFond);
		descriptionArea.setLineWrap(true); //renvoie a la ligne si depasse la taille
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setEditable(false);
		f1.add(descriptionArea);
			    
      	//Affichage de la consequence du choix
		resultArea= new JTextArea("Result");
		resultArea.setBounds(20, 10, 600, 500);
		resultArea.setBackground(Color.black);
		resultArea.setForeground(Color.white);
		resultArea.setFont(normalFond);
		resultArea.setLineWrap(true); //renvoie a la ligne si depasse la taille
		resultArea.setWrapStyleWord(true);
		resultArea.setEditable(false);
		resultArea.setVisible(false);
		f1.add(resultArea);
		
		
		//Boutons des choix possibles pour le joueur
		choixElementPanel=new JPanel();
		choixElementPanel.setBounds(10, 540, 650, 100);
		choixElementPanel.setBackground(Color.black);
		choixElementPanel.setLayout(new GridLayout(2,2));
		feu = new JButton("Feu");
		feu.setBackground(Color.black); feu.setForeground (Color.white);
		terre = new  JButton("Terre");
		terre.setBackground(Color.black); terre.setForeground (Color.white);
		eau = new JButton("Eau");
		eau.setBackground(Color.black); eau.setForeground (Color.white);
		air = new JButton("Air");
		air.setBackground(Color.black);air.setForeground (Color.white);
		
		choixElementPanel.add(feu);
		choixElementPanel.add(terre);
		choixElementPanel.add(eau);
		choixElementPanel.add(air);
		choixElementPanel.setVisible(false);
		f1.add(choixElementPanel);	
	}
	
	
	
	/**
	 * Configure la zone de texte utilisee au debut du jeu pour introduire l'histoire globale du jeu.
	 */
	public void afficherDebut() {
		debutArea.setBounds(20, 10, 600, 500);
		debutArea.setBackground(Color.black);
		debutArea.setForeground(Color.white);
		debutArea.setFont(normalFond);
		debutArea.setLineWrap(true);
		debutArea.setWrapStyleWord(true);
		f1.add(debutArea);
		debutArea.setVisible(false);
	}
		
	/**
	 * Initialise les actions associees aux boutons au debut du jeu du choix entre les 4 elements. <br>
	 * Affiche une description de l'element choisi dans la zone de resultat et affiche le bouton pour passer au nœud suivant.
	 */
	public void actionBoutonsDebut() {
		resultArea.setText(ElementJoueur.getDescription()); //Initialisation de la description de l'element feu dans le jtextarea
		resultArea.setVisible(true);		//Affichage de la description
		suiteBouton1.setVisible(true);				//Affichage du bouton >		
		IP.setVisible(false);						//Disparition de l'image
		choixElementPanel.setVisible(false);			//Disparition des boutons de choix
		descriptionArea.setVisible(false);			//Disparition de la description du noeud
	}
	
	/**
	 * Actions associees au bouton de suite (suiteBouton1). <br>
	 * Cette methode masque ou affiche des elements de l'interface.
	 * Elle affiche une nouvelle image (IP), la description du nouveau nœud et les boutons de choix.
	 */
	public void actionSuiteBouton1() {
		titrePanel.setVisible(false);
		resultArea.setVisible(false);
		descriptionArea.setText(currentNode.getDescription());
		descriptionArea.setVisible(true); //"Quel element voulez-vous?"
		IP.setVisible(true); //L'image
		choixElementPanel.setVisible(true); //Les 4 elements
		suiteBouton1.setVisible(false);
		debutArea.setVisible(false);
	}
	
	/**
	 * <b>Affiche la fenêtre de resultat en masquant certains elements de l'interface.</b><p>
	 * Cette methode affiche la zone de resultat ainsi que le bouton pour passer au nœud suivant
	 * et masque le titre, l'image principale, la zone de description, les choix de boutons.</p>
	 */
	public void afficherFenetreResult() {
		titrePanel.setVisible(false);
		niveauArea.setVisible(false);
		IP.setVisible(false);
		suiteBouton.setVisible(true);
		descriptionArea.setVisible(false);
		ChoixBoutonPanel.setVisible(false);
		resultArea.setVisible(true);
	}
	
	/**
	 * Mise a jour des composants de l'interface graphique avec le metier du joueur
	 */
	public void MajIGMetier() {
		ElementJoueur.setHealth(MetierJoueur.getHealth());
	    ElementJoueur.setExp(MetierJoueur.getExp());
		hpLabelNb.setText(""+ElementJoueur.getHealth());
		expLabelNb.setText(""+ElementJoueur.getExp());
		metierLabel.setText(String.valueOf(MetierJoueur));
	}
		
	
	
	
	
	/**
	 * <p><b>Initialise l'interface graphique du jeu.</b></p>
	 * Cette methode configure les differents elements de l'interface, tels que le titre, l'image,
	 * l'element du joueur, son metier, les choix, l'inventaire...
	 */
	public void InitialisationInterface(){
		f=new JFrame("Jeu");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Affiche l'ecran du jeu avec une image, la description et les choix pour le noeud actuel
				
		//Affichage image
        f.add(IP);
		
		
		//Affichage de l'element du joueur (une image de l'element) et de son metier
        playerPanel=new JPanel();
		playerPanel.setBounds(700, 15, 350, 50);
		playerPanel.setBackground(Color.black);
		playerPanel.setLayout(new GridLayout(1,2));
		playerPanel.setVisible(true);
		
		
		ImageIcon air_icon = new ImageIcon(".//images//element//air_icon.png");
        ImageIcon eau_icon = new ImageIcon(".//images//element//eau_icon.png");
        ImageIcon terre_icon = new ImageIcon(".//images//element//terre_icon.png");
        ImageIcon feu_icon = new ImageIcon(".//images//element//feu_icon.png");
        
		//Element du joueur
		iconLabel=new JLabel(air_icon);
        playerPanel.add(iconLabel);
        if (ElementJoueur instanceof Feu) {iconLabel.setIcon(feu_icon);}
        if (ElementJoueur instanceof Eau) {iconLabel.setIcon(eau_icon);}
        if (ElementJoueur instanceof Terre) {iconLabel.setIcon(terre_icon);}
		
        //Son metier
		metierLabel=new JLabel();
		metierLabel.setFont(normalFond);
		metierLabel.setForeground(Color.white);
		playerPanel.add(metierLabel);
		
		f.add(playerPanel);
		
		if (MetierJoueur!=null) {
        	metierLabel.setText(String.valueOf(MetierJoueur));
        }
		
		//Affichage des points de vie et des points d'experience du joueur
		
		//Panel pour petit tableau : "HP : Nombre HP"
		//Ce panel est appele par CombatNode pour colorie la bordure en rouge
        hpPanel=new JPanel();
		hpPanel.setBounds(750, 100, 100, 50);
		hpPanel.setBackground(Color.black);
		hpPanel.setLayout(new GridLayout(1,2));
		hpPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		hpPanel.setVisible(true);
		f.add(hpPanel);
		
		//Panel pour petit tableau : "Exp : Nombre d'Exp"
		JPanel expPanel=new JPanel();
		expPanel.setBounds(950, 100, 100, 50);
		expPanel.setBackground(Color.black);
		expPanel.setLayout(new GridLayout(1,2));
		expPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		expPanel.setVisible(true);
		f.add(expPanel);
		
		//Hp :
		JLabel hpLabel = new JLabel(" HP :");
		hpLabel.setFont(normalFond);
		hpLabel.setForeground(Color.white);
		hpPanel.add(hpLabel);
		
		//Nombre de point de vie
		hpLabelNb = new JLabel(String.valueOf(ElementJoueur.getHealth()));
		hpLabelNb.setFont(normalFond);
		hpLabelNb.setForeground(Color.white);
		hpPanel.add(hpLabelNb);
		
		// Exp :
		JLabel expLabel = new JLabel(" Exp :");
		expLabel.setFont(normalFond);
		expLabel.setForeground(Color.white);
		expPanel.add(expLabel);
		
		//Nombre de point d'experience
		expLabelNb = new JLabel(String.valueOf(ElementJoueur.getExp()));
		expLabelNb.setFont(normalFond);
		expLabelNb.setForeground(Color.white);
		expPanel.add(expLabelNb);
		
		
		
		//Affichage description du noeud
		descriptionArea.setText(currentNode.getDescription());
		f.add(descriptionArea);
		f.add(resultArea); 	//Affichage de la consequence du choix
			    	
		
		//Si besoin de remarques
		remarqueArea=new JTextArea("Les armes et les outils (autre que couteau, eau et votre arme de votre -futur- métier) ont un nombre d'utilisation limité et disparaitront de l'inventaire automatiquement"
				+ "\nMaintenez longuement votre curseur sur une arme ou un outil pour afficher leurs caractéristiques."
				+" \n2 pouvoirs sont choisis au hasard parmi vos pouvoirs disponibles pour chacun combat. Vous obtenez un nouveau pouvoir avec 20 degats lorsque vous avez 20 points d'exp."
				+ "et un pouvoir avec 30 dégâts à partir de 40 exp");
		remarqueArea.setBounds(750, 200, 400, 130);
		remarqueArea.setBackground(Color.gray);
		remarqueArea.setForeground(Color.white);
		remarqueArea.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		remarqueArea.setLineWrap(true); //renvoie a la ligne si dépasse la taille
		remarqueArea.setWrapStyleWord(true);
		remarqueArea.setVisible(false);
		f.add(remarqueArea);
		
		niveauArea=new JTextArea("");
		niveauArea.setBounds(750, 200, 400, 150);
		niveauArea.setBackground(Color.blue);
		niveauArea.setForeground(Color.white);
		niveauArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
		niveauArea.setLineWrap(true); //renvoie a la ligne si dépasse la taille
		niveauArea.setWrapStyleWord(true);
		niveauArea.setVisible(false);
		f.add(niveauArea);
		
		
		//Pour les EnigmesNode
		enigmeRep=new JTextField();
		enigmeRep.setBounds(250, 530, 200, 30);
		enigmeRep.setBackground(Color.black);
		enigmeRep.setForeground(Color.white);
		enigmeBouton=new JButton("Vérifier");
		enigmeBouton.setBackground(Color.black);//fond du bouton
		enigmeBouton.setForeground(Color.white);
		enigmeBouton.setBounds(300, 600, 100, 30);
		enigmeRep.setVisible(false); enigmeBouton.setVisible(false);
		f.add(enigmeRep); f.add(enigmeBouton);
		
		enigmeBouton.addActionListener(new ActionListener() { 
			@Override
			public void actionPerformed(ActionEvent e) {
				repUtilisateur=enigmeRep.getText(); 
				currentNode=currentNode.chooseNextIG(Game.this, 1);
				resultArea.setVisible(true);
//				titrePanel.setVisible(false);
				IP.setVisible(false);
				suiteBouton.setVisible(true);
				descriptionArea.setVisible(false);
				ChoixBoutonPanel.setVisible(false);
				enigmeRep.setVisible(false); enigmeBouton.setVisible(false);
			}
		});
		
		//Panel pour acceuillier les boutons choix
		ChoixBoutonPanel=new JPanel();
		ChoixBoutonPanel.setBounds(10, 540, 650, 100);
		ChoixBoutonPanel.setBackground(Color.black);
		ChoixBoutonPanel.setLayout(new GridLayout(2,2));
		f.add(ChoixBoutonPanel);
		//Boutons choix
		choix1 = new JButton("Choix 1");
		choix1.setBackground(Color.black); choix1.setForeground (Color.white);
		choix2 = new  JButton("Choix 2");
		choix2.setBackground(Color.black); choix2.setForeground (Color.white);
		choix3 = new JButton("Choix 3");
		choix3.setBackground(Color.black); choix3.setForeground (Color.white);
		choix4 = new JButton("Choix 4");
		choix4.setBackground(Color.black);choix4.setForeground (Color.white);
		
		ChoixBoutonPanel.remove(feu);ChoixBoutonPanel.remove(terre);ChoixBoutonPanel.remove(eau);ChoixBoutonPanel.remove(air);
		ChoixBoutonPanel.add(choix1);
		ChoixBoutonPanel.add(choix2);
		ChoixBoutonPanel.add(choix3);
		ChoixBoutonPanel.add(choix4);
		
		
		//Inventaire sous forme de tableau a une colonne
		inventairePanel=new JPanel();
		inventairePanel.setBounds(750, 360, 200, 200);
		inventairePanel.setBackground(Color.black);
		inventairePanel.setLayout(new GridLayout(6,1));
		inventairePanel.setVisible(false);
		f.add(inventairePanel);
				
		//Boutons lie a l'inventaire (qui permet de choisir une arme ou un outil)
		itemBouton1=new JButton();
		itemBouton1.setBackground(Color.black); itemBouton1.setForeground(Color.white);
		itemBouton1.setFont(normalFond); 		itemBouton1.setFocusPainted(false);
		itemBouton2=new JButton();
		itemBouton2.setBackground(Color.black); itemBouton2.setForeground(Color.white);
		itemBouton2.setFont(normalFond);		itemBouton2.setFocusPainted(false);
		itemBouton3=new JButton();
		itemBouton3.setBackground(Color.black); itemBouton3.setForeground(Color.white);
		itemBouton3.setFont(normalFond); 		itemBouton3.setFocusPainted(false);
		itemBouton4=new JButton();
		itemBouton4.setBackground(Color.black); itemBouton4.setForeground(Color.white);
		itemBouton4.setFont(normalFond);		itemBouton4.setFocusPainted(false);
		itemBouton5=new JButton();
		itemBouton5.setBackground(Color.black); itemBouton5.setForeground(Color.white);
		itemBouton5.setFont(normalFond);		itemBouton5.setFocusPainted(false);
		itemBouton6=new JButton();
		itemBouton6.setBackground(Color.black); itemBouton6.setForeground(Color.white);
		itemBouton6.setFont(normalFond); 		itemBouton6.setFocusPainted(false);
		inventairePanel.add(itemBouton1); inventairePanel.add(itemBouton2); inventairePanel.add(itemBouton3);
		inventairePanel.add(itemBouton4); inventairePanel.add(itemBouton5); inventairePanel.add(itemBouton6);

		
		
		//Affichage barre menu
				
		//JMenu menu; JMenuItem m1,m2,m3; 
		JMenuBar mb=new JMenuBar();
		menu=new JMenu("Menu");
		m1=new JMenuItem("Nouvelle partie");
		m2=new JMenuItem("Sauvegarder la partie");
		m3=new JMenuItem("Ouvrir");
		menu.add(m1);menu.add(m2);menu.add(m3);mb.add(menu);
				
		
		
		m1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				f.setVisible(false);
				try {
					new Game(false, false);
				} catch (ErrDurabilite e1) {
					e1.printErr();
				}
			}
		});
		m2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sauvegarderPartie();
			}
		});
		m3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			    try {
					reprendrePartie();
				} catch (ErrDurabilite e1) {
					e1.printErr();
				}
			}
		});
				
		//Affichage d'un bouton pour afficher le prochain noeud
		suiteBouton = new JButton(">");
		suiteBouton.setBackground(Color.black);//fond du bouton
		suiteBouton.setForeground(Color.white);
		suiteBouton.setBounds(250, 500, 200, 100);
		suiteBouton.setVisible(false);
			
		f.add(suiteBouton);	
		f.setJMenuBar(mb);
		f.setSize(1200,725);
		f.getContentPane().setBackground(Color.black);
		f.setLayout(null);

		f.setVisible(true);		
				
		
				
//		JOptionPane.showMessageDialog(f,"Bienvenue dans notre jeu");
//		String pseudo=JOptionPane.showInputDialog(f,"Choisissez un pseudo");
							
	}
	
	
	
	

	
	/**
	 * <b>Sauvegarde l'etat actuel de la partie dans un fichier.</b>
	 * <p>Affiche une fenêtre de dialogue pour choisir l'emplacement et le nom du fichier de sauvegarde.
	 * Une fois l'emplacement choisi, enregistre les donnees de l'etat actuel de la partie dans le fichier.
	 * Les donnees sauvegardees comprennent les informations sur le joueur, le metier, l'inventaire et le nœud actuel.
	 * Si une erreur survient pendant le processus de sauvegarde, affiche la trace de l'erreur.</p>
	 */
	
	private void sauvegarderPartie() {
		JFileChooser j = new JFileChooser();
		int result =j.showSaveDialog(f);	
		if (result == JFileChooser.APPROVE_OPTION) {
            try {
            	// Selection du fichier et sauvegarde des donnees actuelles
                File selectedFile = j.getSelectedFile();
                FileOutputStream fileOut = new FileOutputStream(selectedFile);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                
             // Enregistrement des donnees de la partie actuelle dans le fichier
                out.writeObject(ElementJoueur);
                out.writeObject(MetierJoueur);
                out.writeObject(Inventaire);
                out.writeObject(currentNode);
                out.writeInt(ElementJoueur.getHealth());
                out.writeInt(ElementJoueur.getExp());
                
             // Fermeture des flux et confirmation de sauvegarde
                out.close();
                fileOut.close();
                System.out.println("Partie sauvegardée avec succès.");
            }
        catch (IOException ex) {
        	// Affichage des erreurs en cas de problème lors de la sauvegarde
	        ex.printStackTrace();}
		}
	}
	
	/**
	 * Sauvegarde les donnees de la partie en cours dans un fichier depuis la console.
	 *
	 * Cette methode cree un fichier de sauvegarde contenant les donnees de la partie actuelle.
	 * Les donnees telles que les objets ElementJoueur, MetierJoueur, Inventaire, currentNode,
	 * la sante et l'experience du joueur sont serialisees et ecrites dans le fichier.
	 *
	 * @see ElementJoueur Classe representant un element du joueur
	 * @see MetierJoueur Classe representant le metier du joueur
	 * @see Inventaire Classe representant l'inventaire du joueur
	 * @see currentNode Noeud actuel dans le jeu
	 */
	public void sauvegarderPartieSurConsole() {
        try {
            // Creation d'un nom de fichier base sur la date et l'heure actuelles
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String nomFichier = "sauvegarde_" + dateFormat.format(new Date()) + ".dat";

            // Ecriture des donnees de la partie dans un fichier
            FileOutputStream fileOut = new FileOutputStream(nomFichier);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            // Enregistrement des donnees de la partie actuelle dans le fichier
            // (Ici, je suppose que ElementJoueur, MetierJoueur, Inventaire, currentNode sont des objets serialisables)
            out.writeObject(ElementJoueur);
            out.writeObject(MetierJoueur);
            out.writeObject(Inventaire);
            out.writeObject(currentNode);
            out.writeInt(ElementJoueur.getHealth());
            out.writeInt(ElementJoueur.getExp());

            // Fermeture des flux et confirmation de sauvegarde
            out.close();
            fileOut.close();
            System.out.println("Partie sauvegardée avec succès : " + nomFichier);
            new Main();
        } catch (IOException ex) {
            // Affichage des erreurs en cas de problème lors de la sauvegarde
            ex.printStackTrace();
        }
    }
	
	
	/**
	 * Reprend une partie sauvegardee en chargeant les donnees depuis un fichier.
	 * <p>
	 * Affiche une fenêtre de selection de fichier pour choisir la sauvegarde à charger.
	 * Une fois le fichier choisi, recupère les donnees de la partie sauvegardee pour reprendre la partie.
	 * Met à jour les elements du jeu tels que l'etat du joueur, l'inventaire et le nœud actuel.
	 * Affiche un message de confirmation une fois la partie reprise.
	 * Si une erreur survient lors du chargement ou de la lecture du fichier, affiche la trace de l'erreur.</p>
	 * @throws ErrDurabilite 
	 */
	private void reprendrePartie() throws ErrDurabilite {
	    JFileChooser j = new JFileChooser();
	    int result = j.showOpenDialog(f);
	    if (result == JFileChooser.APPROVE_OPTION) {
	        try {
	        	// Selection du fichier et chargement des donnees
	            File selectedFile = j.getSelectedFile();
	            FileInputStream fileIn = new FileInputStream(selectedFile);
	            ObjectInputStream in = new ObjectInputStream(fileIn);
	            
	            // Recuperation des donnees de la sauvegarde
	            ElementJoueur = (PersoDeBase) in.readObject();
	            MetierJoueur = (PersoDeBase) in.readObject();
	            Inventaire = (Inventaire) in.readObject();
	            currentNode=(Node) in.readObject();
	            ElementJoueur.setHealth(in.readInt());
	            ElementJoueur.setExp(in.readInt());
	            
	         // Fermeture des flux et confirmation de reprise de partie
	            in.close();
	            fileIn.close();
	            System.out.println("Partie reprise avec succès.");
	            
	         // Fermeture des fenêtres actuelles
	            f1.setVisible(false); 
	            if(f !=null) {f.setVisible(false);f=null;}
	            // Fermeture des fenêtres actuelles et demarrage du jeu
	    		creerJeu(false, false);
	        } catch (IOException  | ClassNotFoundException ex) {
	            ex.printStackTrace();
	        }
	    }
	}
	
	
	/**
	 * Gère les actions associees aux choix d'objets dans l'inventaire du joueur pendant un combat.
	 * <br>
	 * Si le joueur a selectionne une arme, appelle de la methode combatIG de la classe CombatNode, le joueur attaque l'ennemi. Si celui-ci a encore des points de vie après l'attaque, il riposte.
	 * Si le joueur a selectionne un outil, il augmente ses points de vie ou points d'experience
	 * Si l'ennemi est vaincu, la methode fait passer au prochain nœud.
	 * @throws ErrDurabilite Exception liee à la durabilite des armes et outils.
	 */
	
	private void actionBoutonsInventaire() throws ErrDurabilite {		
		//Si le joueur a choisi une arme
		if (armechoisie != null) {
			//On verifie si apres l'attaque, l'ennemi a encore des points de vie
			if (((CombatNode)currentNode).getEnnemi().getHealth()-armechoisie.getDegats()>0) { 
				try {
					((CombatNode)currentNode).combatIG(Game.this, 1); // ON ATTAQUE AVEC L'ARME, L'ENNEMI PERD DES POINTS DE VIE ET ATTAQUE
				} catch (ErrDurabilite e) {e.printErr();}
				
				inventairePanel.setVisible(false);
			}
			//Si l'ennemi n'a plus de points de points de vie, alors on passe au prochain noeud
			else{
				afficherFenetreResult();
				resultArea.setText("Vous avez vaincu les ennemis");
				inventairePanel.setVisible(false);
				currentNode=currentNode.chooseNextIG(Game.this, 1);}	
			}
		
		//Si le joueur a choisi un outil
		else if (outilchoisi!=null) {
			try {
				((CombatNode)currentNode).combatIG(Game.this, 2);
			} catch (ErrDurabilite e) {
				e.printErr();
			}
			inventairePanel.setVisible(false);
		}
	}
	
	/**
	 * Reprend une partie sauvegardee depuis la console.
	 *
	 * Cette methode permet de reprendre une partie sauvegardee en verifiant la presence
	 * de fichiers de sauvegarde dans le repertoire courant et les affiche de la plus recente à la plus ancienne.
	 *
	 * @throws ErrDurabilite Une exception levee en cas d'erreur de durabilite ou d'accès aux fichiers de sauvegarde.
	 */
	private void reprendrePartieSurConsole() throws ErrDurabilite {
        // Code pour reprendre une partie sauvegardee
    	File repertoire = new File("."); // Chemin vers le repertoire des sauvegardes

        if (repertoire.exists() && repertoire.isDirectory()) {
            File[] fichiers = repertoire.listFiles();

            if (fichiers != null && fichiers.length > 0) {
                Arrays.sort(fichiers, Comparator.comparingLong(File::lastModified).reversed());

                System.out.println("Liste des sauvegardes (de la plus récente à la plus ancienne) :");
                for (int i = 0; i < fichiers.length; i++) {
                    System.out.println((i + 1) + ". " + fichiers[i].getName() + " - Date : " + new Date(fichiers[i].lastModified()));
                }
                System.out.println("Entrez le numéro de la sauvegarde à reprendre : ");
                Scanner scanner = new Scanner(System.in);//manque scanner.close() mais c'est pour éviter de potientiels problèmes
                int numSauvegarde = scanner.nextInt();
                scanner.nextLine();

                // Code pour charger la sauvegarde choisie
                System.out.println("Chargement de la sauvegarde " + numSauvegarde + " en cours...");
                if (numSauvegarde > 0 && numSauvegarde <= fichiers.length) {
                    try {
                        FileInputStream fileInputStream = new FileInputStream(fichiers[numSauvegarde - 1]);
                        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                        
                     // Recuperation des donnees de la sauvegarde
                        ElementJoueur =(PersoDeBase) objectInputStream.readObject();
    		            MetierJoueur = (PersoDeBase) objectInputStream.readObject();
    		            Inventaire = (Inventaire) objectInputStream.readObject();
    		            currentNode = (Node) objectInputStream.readObject();
    		            ElementJoueur.setHealth(objectInputStream.readInt());
    		            ElementJoueur.setExp(objectInputStream.readInt()); 

                        objectInputStream.close();
                        fileInputStream.close();
                        System.out.println("Chargement de la sauvegarde " + numSauvegarde + " effectué avec succès.");
                        try {
							creerJeu(true, true);
						} catch (ErrDurabilite e) {
							e.printStackTrace();
						}
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Numéro de sauvegarde invalide.");
                }
            } else {
                System.out.println("Aucune sauvegarde disponible.");
            }
        } else {
            System.out.println("Répertoire de sauvegardes introuvable.");
        }
    }
	/**
	 * Joue un son au debut du programme.
	 *
	 * Cette methode charge et joue un fichier audio specifique au demarrage du programme.
	 * Le fichier audio doit être au format WAV et se trouve dans le repertoire "sons" du repertoire courant.
	 * En cas de problème lors du chargement ou de la lecture du son, une trace de l'erreur est affichee.
	 */
	private void sondebut() {
		try {
			File file = new File(".//sons//son_debut.wav");
			// AudioInputStream est utilise pour lire les donnees audio à partir du fichier.
			AudioInputStream sound = AudioSystem.getAudioInputStream(file);
			// Clip est un objet qui peut être utilise pour jouer des clips audio courts.
			clip = AudioSystem.getClip();
			clip.open(sound);
			clip.start();
		}
		catch (Exception e) { e.printStackTrace(); }
	}
	
	public JLabel getMetierLabel() {
		return metierLabel;
	}

	public JTextArea getResultArea() {
		return resultArea;
	}

	public JTextArea getDescriptionArea() {
		return descriptionArea;
	}

	public JPanel getChoixBoutonPanel() {
		return ChoixBoutonPanel;
	}

	public JButton getChoix1() {
		return choix1;
	}

	public JButton getChoix2() {
		return choix2;
	}

	public JButton getChoix3() {
		return choix3;
	}

	public JButton getChoix4() {
		return choix4;
	}
	
	public JButton getItemBouton1() {
		return itemBouton1;
	}

	public JButton getItemBouton2() {
		return itemBouton2;
	}

	public JButton getItemBouton3() {
		return itemBouton3;
	}

	public JButton getItemBouton4() {
		return itemBouton4;
	}

	public JButton getItemBouton5() {
		return itemBouton5;
	}

	public JButton getItemBouton6() {
		return itemBouton6;
	}

	public Outils getOutilchoisi() {
		return outilchoisi;
	}

	public Armes getArmeChoisie() {
		return armechoisie;
	}

	public JButton getSuiteBouton() {
		return suiteBouton;
	}

	public String getRepUtilisateur() {
		return repUtilisateur;
	}

	public PersoDeBase getMetierJoueur() {
		return MetierJoueur;
	}

	public Node getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(Node currentNode) {
		this.currentNode = currentNode;
	}

	public PersoDeBase getElementJoueur() {
		return ElementJoueur;
	}

	public JLabel getHpLabelNb() {
		return hpLabelNb;
	}

	public JLabel getExpLabelNb() {
		return expLabelNb;
	}

	public JLabel getImageLabel() {
		return ImageLabel;
	}

	public Inventaire getInventaire() {
		return Inventaire;
	}

	public JPanel getHpPanel() {
		return hpPanel;
	}

	public JFrame getF() {
		return f;
	}

	public JTextArea getNiveauArea() {
		return niveauArea;
	}

	public TerminalNode getDefaite() {
		return Defaite;
	}

}
