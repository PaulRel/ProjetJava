# Projet jeu "vous êtes le héros"
Ce projet est un jeu intéractif de type 'vous êtes le héros', où l'histoire évolue en fonction des choix du joueur et d'éventuels événements aléatoires. Les décisions du joueur influent sur le dénouement de l'histoire, menant à une fin heureuse ou au contraire tragique. L'histoire est divisée en 'nœuds', comprenant des nœuds de décision, où le joueur prend des choix, des nœuds 'chance' avec des événements aléatoires, des nœuds de combat, des nœuds 'énigmes', et des nœuds terminaux pour la fin du jeu.

## Structure du repository
Le fichier '_Presentation des classes.md_' décrit les fonctions et responsabilités des différentes classes du projet.

Le répertoire '_Captures_ecran_' contient des captures d'écran de l'interface graphique du jeu créée avec la bibliothèque graphique Java Swing.

Le fichier '_Scenario_' expose les différents scénarios possibles en fonction des choix du joueur, des noeuds de chance (où une part d'aléatoire intervient) ainsi que l'issue des combats (récompenses en cas de victoire). Chaque _noeud_ de l'histoire est détaillé avec sa description, les divers choix ou possibilités et le noeud suivant. 

Le dossier 'ProjetJavaJeu' englobe les éléments suivants:
-	_images_ : Inclut les images utilisées dans l'interface graphique (principalement générées par IA ou créees par moi-même)
-	_sons_ : Contient les fichiers audio employés dans l'interface graphique
-	_src_ : Comprend le code source en langage Java du projet

## Description et Objectifs du jeu
Le joueur évolue dans un monde où la plupart des humains sont sans pouvoir mais certains, dont lui, peuvent contrôler l'un des quatre éléments: l'air, la terre, l'eau ou le feu. Cependant, un maître des éléments a décidé d'utiliser son pouvoir pour faire le mal. Il a incendié les villages, asservi les habitants et défié les autres maîtres des éléments. Votre but est de l'arrêter et de rétablir la paix. Le joueur a également un métier à choisir entre Guerrier, Arbalétrier, Druide, Chasseur et Traqueur qui sera déterminé au cours des premiers nœuds. Chaque métier a une arme associée. Le but du jeu est de vaincre Le Maître des Ténèbres au cours du combat final.

## Instructions pour Lancer le Jeu
Pour lancer le jeu, ouvrir le dossier projet-java dans un IDE (de préférence Eclipse) prenant en charge Java. Votre IDE doit pouvoir exécuter Java Swing sinon le jeu est jouable sur console, mais l’expérience est moins intéressante, se rendre dans le dossier ProjetJeuJava/src/jeu, ouvrir la fichier Main.java et exécuter le programme. 

## Contrôles et Interactions
Pour interagir avec le jeu dans l’interface graphique, il faut appuyer sur les boutons situés en bas de la fenêtre en général. Pour utiliser une arme ou un outil, l’inventaire apparaît sur le côté avec toutes les armes et outils obtenus lors des nœuds précédents. Laissez votre pointeur longuement sur une arme ou un outil pour obtenir leurs caractéristiques (nom, dégâts, points bonus , durabilité).

## Guide du Joueur
Le joueur doit donc essayer d’obtenir le maximum de points d’expérience et de points de vie avant le combat final pour obtenir les pouvoirs les plus puissants. Il peut en obtenir en effectuant des missions, des combats, en résolvant des énigmes et en utilisant ses outils. Le nombre de missions effectuées et le type de nœuds (Énigme, Décision, Combat, Chance) sont déterminés par les choix du joueur mais également par la chance. Plus le joueur passe par des nœuds, c’est-à-dire accepte des offres de mission, effectue des combats, ou autres, plus il a de chance de vaincre le méchant. Si vous voulez en savoir plus sur le passage d’un nœud à un autre, se référer au document pdf du Scénario dans le dossier.

## Stratégie
Le joueur doit obtenir les pouvoirs qui provoquent le plus de dégâts pour avoir le maximum de chances de vaincre le Maître des Ténèbres lors du combat final. Retenez bien le nom du pouvoir et le nombre de dégâts associés sinon pour les retrouver, vous attaquez et le nombre de dégâts infligés à l’ennemi s’affichera. Un pouvoir entraînant 20 dégâts est obtenu lorsque le joueur obtient 20 points d’expérience et un autre pouvoir de 30 dégâts est obtenu à partir de 40 points d’expérience. Attention, essayez d’obtenir ces pouvoirs avant le Combat Final car les pouvoirs sont déterminés au hasard au début du combat parmi la liste des pouvoirs disponibles et ne changent pas durant toute la durée du combat. Vous avez également la possibilité de sauvegarder la partie avant ce combat si vous pensez avoir le nombre de points d’expérience nécessaire pour gagner. Faites également attention à vos points de vie lors du combat, le Maître des Ténèbres peut faire perdre entre 0 ou 40 points de vie !



## Annexes
### Sauvegarde de la Partie :
Le joueur peut sauvegarder sa progression pour lui permettre de revenir à un état antérieur s'il le souhaite. Cela peut être particulièrement utile avant le combat final. Pour sauvegarder la partie à partir de l'interface graphique, cliquez sur Menu en haut à gauche de la fenêtre et Sauvegarder la partie et choisissez un emplacement sur votre disque dur. Pour reprendre une partie débutez dans l’interface, vous exécutez le code, vous tapez 1 (pour jouer sur l’interface graphique),et cliquez sur le bouton Parties Sauvegardées. Si vous êtes sur une partie en cours, allez dans le Menu en haut à gauche et cliquez sur Ouvrir. Une fenêtre s’ouvre, retrouvez la sauvegarde là où vous l'avez enregistrée, sélectionnez la sauvegarde et cliquez sur ouvrir. Pour sauvegarder une partie dans la console, à chaque nœud de décision vous sera proposé de sauvegarder la partie en tapant 0. La sauvegarde est directement enregistrée dans le dossier actuel avec comme nom “sauvegarde _ date et heure de l’enregistrement”. Pour reprendre une partie, il faut relancer le jeu, tapez 2 pour jouer sur console et dans le menu principal tapez 2 (reprendre une sauvegarde). Les sauvegardes seront affichées de la plus récente à la plus ancienne.

