# Fonctionnement technique bref des methodes

Ce fichier resume comment les methodes marchent techniquement, classe par classe.
L'idee : expliquer les controles, les boucles, les listes et les appels importants sans trop detailler.

## ApplicationCreationPlateau

### `Controleur`

- `demarrer()` : instancie un `Plateau`, cree les zones avec `verifierNombreZones(4)`, puis instancie `Fenetre` pour afficher l'IHM.
- `main(...)` : point d'entree. Il cree le controleur et lance `demarrer()`.

### `metier.Case`

- Le constructeur stocke deux entiers : `colonne` et `ligne`.
- Les getters renvoient directement ces attributs.
- `memesCoordonnees(...)` fait une comparaison simple avec `==` sur la colonne et la ligne.

### `metier.Atome`

- Le constructeur stocke la position et le type, puis initialise `voisins` avec une `ArrayList`.
- `definirBase(...)` fait un controle : si l'atome est deja une base ou si la couleur est `null`, il refuse. Sinon il passe `estBase` a `true`.
- `viderVoisins()` vide la `ArrayList` avec `clear()`. C'est utilise avant de recalculer les liaisons.
- `ajouterVoisin(...)` evite les erreurs : pas de `null`, pas soi-meme, pas de doublon.
- `contientVoisin(...)` parcourt la liste des voisins et compare les objets avec `==`.

### `metier.Zone`

- La zone stocke un `id` et une `ArrayList<Case>`.
- `contientCase(...)` parcourt la liste des cases et compare les coordonnees.
- `ajouterCase(...)` ajoute une nouvelle `Case` seulement si elle n'existe pas deja.
- `peutAjouterCaseVoisine(...)` controle la continuite : une nouvelle case doit toucher une case existante par un cote.
- `estConnexe()` fait un parcours de cases : elle part d'une case, visite ses voisines, puis verifie si toutes les cases ont ete atteintes.
- `supprimerCase(...)` parcourt la liste avec un indice et retire les cases qui correspondent.
- `supprimerCasesHorsPlateau(...)` retire les cases dont les coordonnees sortent des dimensions du plateau.

### `metier.Plateau`

- Le constructeur initialise les dimensions, la pioche, les listes de zones/atomes, puis ajoute toutes les couleurs et tous les types disponibles.
- Les methodes `getZoneParId`, `getZoneCase`, `getAtomeCase` font toutes le meme principe : parcours d'une `ArrayList` puis retour de l'objet trouve, sinon `null`.
- `setDimensions(...)` change les dimensions, nettoie les elements hors plateau, puis recalcule les voisins.
- `verifierNombreZones(...)` ajoute les zones manquantes avec une boucle, puis supprime les zones en trop.
- `affecterCaseZone(...)` verifie la case, verifie la zone, verifie que la case est voisine, retire la case des autres zones, puis l'ajoute.
- `toutesCasesOntUneZone()` parcourt toutes les coordonnees du plateau avec deux boucles imbriquees.
- `getZoneSeparee(...)` appelle `estConnexe()` sur chaque zone pour trouver une zone coupee.
- `ajouterAtome(...)` controle la case, le type et l'absence d'atome, puis ajoute un nouvel `Atome`.
- `supprimerAtome(...)` cherche l'atome, le retire de la liste, puis recalcule les voisins.
- `definirBase(...)` verifie les doublons de couleur et de symbole, cree l'atome si besoin, puis appelle `Atome.definirBase(...)`.
- `calculerVoisinsAtomes()` vide tous les voisins, puis teste les 8 directions autour de chaque atome.
- `chercherPremierAtomeDirection(...)` avance case par case dans une direction jusqu'a trouver un atome ou sortir du plateau.

### `metier.LirePlateau`

- `lire(...)` lit le fichier avec `Files.readAllLines(...)`.
- Chaque ligne est decoupee avec `split(";")`.
- Si la ligne commence par `PARAMETRES`, la methode cree le `Plateau`.
- Si la ligne commence par `ZONE`, elle cree une `Zone`, lit les coordonnees deux par deux, puis ajoute les cases.
- Si la ligne commence par `ATOME`, elle lit colonne, ligne, type, puis ajoute un atome ou une base.
- Le `try/catch` transforme les erreurs de conversion ou de tableau en `IOException` avec le numero de ligne.
- `lireCouleurBase(...)` regarde la 5e valeur d'une ligne `ATOME`. Si elle correspond a une couleur de l'enum, elle la renvoie, sinon `null`.

### `metier.EnregistreurPlateau`

- `ecrire(...)` ouvre un `BufferedWriter` sur le fichier de sortie.
- Les donnees sont ecrites ligne par ligne au format texte separe par des `;`.
- `ecrireParametres(...)` ecrit dimensions et pioche.
- `ecrireTypes(...)` et `ecrireCouleurs(...)` parcourent les listes et ecrivent les valeurs enum.
- `ecrireZones(...)` parcourt les zones, ignore les zones vides, puis ecrit toutes les cases.
- `ecrireAtomes(...)` parcourt les atomes, ecrit position/type, puis ajoute la couleur si c'est une base.

### `metier.GestionPlateau`

- Cette classe sert d'intermediaire entre l'IHM et les classes de lecture/ecriture.
- `lirePlateau(...)` controle l'extension `.data`, appelle `LirePlateau`, puis memorise le fichier courant.
- `ajouterExtensionData(...)` manipule le nom du fichier pour garantir l'extension `.data`.
- `enregistrerPlateau(...)` valide d'abord le plateau, puis appelle `EnregistreurPlateau`.
- `validerPlateauAvantEnregistrement(...)` bloque la sauvegarde avec des `IllegalArgumentException` si le plateau est incomplet ou incoherent.

### `metier.enums`

- `Couleur.getNom()` convertit une valeur enum en texte lisible avec des `if`.
- `TypeAtome.getNomImage()` convertit le nom de l'enum en minuscule et ajoute `.png`.
- `TypePioche` ne contient que deux constantes : `OUVERTE` et `FERMEE`.

## Interface de creation

### `ihm.Fenetre`

- Le constructeur cree les panels Swing, les place avec un `BorderLayout`, puis affiche la fenetre.
- `traiterOuverture()` ouvre un `JFileChooser`, lit le fichier choisi, puis recharge les panels.
- `traiterEnregistrement()` applique les parametres puis sauvegarde dans le fichier courant.
- `traiterEnregistrementCopie()` applique les parametres puis demande un fichier de destination.
- `appliquerParametresPlateau()` lit les champs, convertit les textes en nombres, valide, met a jour le plateau, puis repeint.
- `selectionnerModeEdition(...)` synchronise le mode entre la fenetre et le panel de modification.
- `afficherMessage(...)` affiche une boite de dialogue avec `JOptionPane`.

### `ihm.PanelParametres`

- Le constructeur cree les champs texte, les listes deroulantes et le bouton appliquer.
- Les getters lisent les valeurs des composants Swing.
- `actionPerformed(...)` reagit aux actions : changer le nombre de zones ou appliquer les parametres.
- `remplirZonesActives()` reconstruit la liste des zones actives selon le nombre choisi.

### `ihm.PanelModificationPlateau`

- Le constructeur cree les choix du mode, du type d'atome et de la couleur.
- `actionPerformed(...)` change automatiquement le mode : choisir un type passe en mode atome, choisir une couleur passe en mode base.
- `selectionnerModeEdition(...)` change la selection dans la combo si besoin.

### `ihm.PanelAutres`

- Le constructeur cree les boutons fichier.
- `actionPerformed(...)` teste la source de l'evenement et appelle la methode correspondante de `Fenetre`.

### `ihm.PanelPlateau`

- Le constructeur garde le plateau, charge le fond, ajoute les listeners souris et calcule la taille.
- `actualiserChoixDepuisFenetre()` recupere les choix des autres panels avant chaque action souris.
- `mouseClicked(...)` place un atome ou une base selon le mode courant.
- `mousePressed(...)` commence le dessin de zone, la suppression d'atome ou la suppression de zone.
- `mouseDragged(...)` repete l'action pendant le glissement.
- `obtenirPositionGrille(...)` convertit les pixels souris en colonne/ligne avec une division par `tailleCase`.
- `affecterCaseZone(...)`, `supprimerZone(...)`, `supprimerAtome(...)` font les controles puis appellent les methodes du `Plateau`.
- `paintComponent(...)` redessine l'interface : fond, zones, grille, numeros, liaisons, bases et images.
- Les methodes `dessiner...` parcourent les listes du plateau et utilisent l'objet `Graphics`.
- `chargerImageAtome(...)` et `chargerImageFond()` lisent les images avec `ImageIO`.

## ApplicationJeu

### `ControleurJeu`

- Le controleur garde les references vers la fenetre de jeu et la fenetre de fermeture.
- `messageFermeture()` cree la fenetre de confirmation.
- `frameFermetureOuverte()` evite d'ouvrir plusieurs confirmations.
- `fermerJeu()` ferme les fenetres avec `dispose()`.

### `FrameJeu`

- Le constructeur recupere la taille de l'ecran, configure la fenetre, ajoute `PanelJeu`, puis ajoute un `WindowListener`.
- `windowClosing(...)` intercepte la croix de fermeture et demande confirmation au lieu de fermer directement.

### `FrameFermeture`

- Le constructeur calcule une position centree, ajoute `PanelFermeture`, puis affiche la fenetre.
- `fermerFenetre()` ferme seulement cette fenetre.

### `PanelJeu`

- Le constructeur ajoute simplement un label pour l'instant.

### `PanelFermeture`

- Le constructeur cree le message, les boutons et les listeners.
- `actionPerformed(...)` regarde quel bouton est clique : `Non` ferme seulement la confirmation, `Oui` ferme tout le jeu.
