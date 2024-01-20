# Structure du code et justification de la hiérarchie entre les classes
Notre projet “Projet dont vous êtes le Héros “ est constitué de 4 packages : représentation, univers, jeu et representation.decorators.
## 1. Package représentation et representation.decorarors
### 1.1. Structure du package représentation et de representation.decorators
-	**Event** : Interface définissant les méthodes display et chooseNext pour l'avancement du jeu.
-	**Node** : Classe abstraite représentant les nœuds génériques. Implémente l'interface Event et définit les méthodes de base (comme la redéfinition de la méthode equal). Implémente également Serializable pour permettre de sauvegarder un Node lors de la sauvegarde de la partie.
- **InnerNode** : Classe abstraite représentant les nœuds internes au jeu (autres que TerminalNode). Elle hérite de la classe Node (car un InnerNode est un type particulier de Node). Un InnerNode peut être un DecisionNode, un ChanceNode, un EnigmeNode ou un CombatNode. On a pour cette raison mis cette classe en abstrait pour pouvoir déclarer des méthodes sans fournir l’implémentation et “forcer” les classes filles (DecisionNode, ChanceNode, EnigmeNode, CombatNode) à les implémenter.
-	**DecisionNode** : Sous-classe d’InnerNode représentant les nœuds où le joueur doit prendre une décision parmi différents choix qui sont stockés dans une ArrayList decisionChoices.
-	**ChanceNode** : Sous-classe d'InnerNode, représentant un nœud de chance où le nœud suivant est choisi au hasard (parmi une collection de ChancePossibilités) Un ChanceNode peut se présenter de deux manières :
    -	Il peut être un nœud aléatoire qui fait suite à un autre InnerNode (sélectionné aléatoirement parmi un élément de l’ArrayList ChancecCsq) où
    -	Il peut avoir des choix dans une ArrayList decisionChoices (comme DecisionNode) et le résultat de ce choix est aléatoire (résultat sélectionné aléatoirement parmi un élément de l’ArrayList ChancecCsq). Si ce n’est pas clair, vous pouvez consulter des exemples dans le scénario (document pdf dans le dossier) : pour le premier cas voire le nœud “Retrouver la créature légendaire” choix 3 et pour le deuxième cas se référer à “Rencontre avec La Gardienne de la Forêt” et au nœud “L’impressionner”
-	**EnigmeNode** : Sous-classe d’InnerNode représentant un nœud où une énigme est posée et le joueur doit rentrer une réponse (un mot).
-	**CombatNode** : Sous-classe d’InnerNode représentant un nœud ou le joueur combat contre un ennemi. Le joueur peut attaquer avec une arme, obtenir des points de vie avec des outils, ou attaquer avec un pouvoir.
-	**TerminalNode** : Sous classe de Node représentant un nœud terminal, final qui n’a pas de nœud suivant. Ce nœud représente la fin du jeu.
-	**DecisionChoix** : Classe représentant un choix d’un nœud (DecisionNode ou ChanceNode). Elle est utilisée par la classe DecisionNode pour stocker les différentes options de choix disponibles pour le joueur. Chaque choix est associé à un résultat et éventuellement à des récompenses (comme de nouvelles armes, outils ou points d’expérience). C’est ce choix qui détermine le nœud suivant.
-	**ChancePossibilite** : Classe représentant une possibilité associée à un ChanceNode.
-	**DecisionNodeTest** : classe de test JUnit pour réaliser des tests unitaires sur les méthodes de la classe DecisionNode.

Dans le package representation.decorators :
-	**NodeDecorator** : décorateur abstrait implémentant Event.
-	**ImageNode et SoundNode** : deux décorateurs concrets héritant de NodeDecorator et créés selon le design pattern Decorator. Elles permettent respectivement de montrer une image et de jouer un son.

### 1.2 Justification de la hiérarchie des classes pour le package représentation
-	**Interfaces** : L'utilisation de l'interface Event permet de définir un contrat pour les actions nécessaires à la progression du jeu. Cela rend le code plus modulaire et permet l'implémentation du design pattern Decorator.
-	**Utilisation de l'héritage** :
      -	L'utilisation de l'héritage permet de créer une hiérarchie logique entre différents types de nœuds du jeu. En effet, les nœuds de décision (DecisionNode), les nœuds de chance (ChanceNode), les nœuds d’énigme (EnigmeNode) et les nœuds terminaux (TerminalNode) héritent de la classe Node. Cette dernière permet de fournir une structure claire pour toutes les classes filles qui en hériteront. En effet, comme vu en cours, la classe abstraite Node permet de définir un standard ou un contrat que les classes filles suivront. Cela permet notamment de définir une seule fois les méthodes display, getid, getDescription, setDescription, et de redéfinir la méthode equals pour toutes les classes héritant de Node.
      - Les nœuds de décision (DecisionNode), les nœuds de chance (ChanceNode), les nœuds d’énigme (EnigmeNode) et les nœuds de Combat (CombatNode) héritent de la classe InnerNode car ce sont des nœuds interne au jeu, il ne marque pas la fin du jeu et sont appelés à l’aide d’une boucle (boucle while si le jeu est joué dans la console et boucle entre les ActionListener liés aux boutons si le jeu est joué dans l’interface graphique).
      - La classe Combat hérite de DecisionNode car le joueur doit prendre les bonnes décisions pour gagner le combat le plus vite possible tout en perdant le moins de points de vie.

## 2. Package univers
### 2.1. Structure du package univers
Dans le package univers nous avons créé 15 classes :
-	**PersoDeBase** : classe abstraite représentant une classe de base générique pour tous les personnages du jeu. On y retrouve ses points d’expérience et de vie, son niveau stocké dans une maps, et son élément.
-	**Eau, Terre, Air, Feu** : 4 sous-classes de PersoDeBase représentant nos 4 éléments qui feront partie du choix du joueur au tout début du jeu. Ces classes comprennent aussi les pouvoirs disponibles pour le joueur en fonction du choix qu’il fera.
-	**Armes** : classe représentant les armes que le joueur pourra obtenir avec son nom, ses dégâts et sa durabilité.
-	**Outils** : classe représentant les outils et objets que le joueur obtiendra au cours de jeu et qu’il pourra utiliser dans différentes missions.
-	**Inventaire** : classe qui regroupe les armes et outils que le joueur obtiendra au cours de jeu et qu’il pourra utiliser dans différentes missions. Les Armes et les Outils sont stockés dans des ArrayList.
-	**Arbalétrier, Chasseur, Guerrier, Druide et Traqueur** : 5 sous-classes de PersoDeBase représentant les métiers que le joueur peut acquérir. Chaque métier lui offrira une arme que le joueur gardera tout au long du jeu.
-	**Ennemis** : classe représentant les ennemis que le joueur pourra rencontrer durant son aventure. Ils sont classés en 3 catégories par une énumération de classe.
-	**InventaireTest** : classe de test JUnit pour réaliser des tests unitaires sur les méthodes de la classe Inventaire.

### 2.2. Justification de la hiérarchie des classes pour le package univers
Ce projet Java vise à créer un univers de jeu avec différents types de personnages associés à des éléments spécifiques, ainsi que des spécialisations de personnages représentées par des métiers.
- **La classe abstraite PersoDeBase** agit comme une classe de base générique pour tous les personnages du jeu. Elle contient des attributs communs tels que la santé (health) et l'expérience (exp) et le niveau. Cette approche permet une extension facile pour les types de personnages spécifiques.
- **Eau, Terre, Air, Feu** : Chaque classe représente un type spécifique de personnage associé à un élément. Elles héritent de la classe PersoDeBase pour réutiliser les caractéristiques communes. Chaque classe élémentaire a des pouvoirs spécifiques liés à son élément.
- **Arbalétrier, Chasseur, Guerrier, Druide, Traqueur** : Ces classes représentent des spécialisations de personnages, correspondant à des métiers possibles dans le jeu. Elles héritent également de PersoDeBase pour bénéficier des fonctionnalités communes. Chaque classe de métier a des compétences spécifiques et peut être associée à des armes particulières. Par exemple, la classe Traqueur utilise une dague empoisonnée, ajoutant ainsi une dimension stratégique au jeu.

La hiérarchie des classes est soigneusement conçue pour refléter les relations générales et spécifiques entre les possibilités de personnage dans le jeu. Les différentes classes offrent une modularité qui facilite l'extension du jeu. Les choix de conception permettent une gestion efficace des caractéristiques communes et spécifiques, ainsi que l'introduction de mécanismes de jeu tels que les pouvoirs élémentaires et les armes spécialisées.

## 3. Package jeu
### 3.1. Structure du package jeu
-	**Main** : Classe permettant de lancer le jeu en créant une instance de la classe Game.
-	**Game** : Class représentant une partie jouée. Elle crée l’interface graphique, initialise tous les nœuds et définit les caractéristiques du joueur.
-	**ChoixElement** : Sous-Classe de DecisionNode permettant au joueur de choisir son élément parmi feu, terre, eau et air et créer une instance des classes respectives (dans le cas d’une partie jouée dans la console)
-	**ChoixMetier** : Sous-classe de DecisionNode permettant au joueur de sélectionner son métier parmi plusieurs choix possibles (Arbalétrier, Chasseur, Traqueur, Guerrier ou Druide). Cette classe crée une instance d’une de cette classe de “métiers” et une instance de la classe Arme liée au métier choisi. Cette arme est automatiquement ajoutée à l'inventaire du joueur via la méthode ajouterArme de la classe Inventaire.
-	**Début** : classe retournant simplement le contexte du jeu de type String pour éviter de surcharger la classe Game.

### 3.2. Justification de la hiérarchie des classes pour le package jeu
Les classes ChoixMétier et ChoixElement hérite de DecisionNode car le joueur doit prendre une décision parmi plusieurs choix
