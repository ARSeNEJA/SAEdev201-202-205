package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import metier.GestionPlateau;
import metier.Plateau;

public class Fenetre extends JFrame
{
	/*--------------------*/
	/*      Attributs     */
	/*--------------------*/
	private Plateau plateau;
	private GestionPlateau gestionPlateau;
	private PanelParametres panelParametres;
	private PanelModificationPlateau panelModificationPlateau;
	private PanelAutres panelAutres;
	private PanelPlateau panelEditeur;

	/*--------------------*/
	/*    Constructeur    */
	/*--------------------*/
	// Construit la fenetre principale de l'editeur de plateau.
	public Fenetre(Plateau plateau)
	{
		this.plateau     = plateau;
		this.gestionPlateau = new GestionPlateau();

		this.setTitle("Creation du plateau");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(new JPanel(new BorderLayout(10, 10)));

		/*-------------------------*/
		/* Creation des composants */
		/*-------------------------*/
		this.panelModificationPlateau = new PanelModificationPlateau();
		this.panelAutres              = new PanelAutres(this);
		this.panelEditeur             = new PanelPlateau(plateau, this);
		this.panelParametres          = new PanelParametres(plateau, this);

		JPanel panelGauche = new JPanel();
		panelGauche.setLayout(new BoxLayout(panelGauche, BoxLayout.Y_AXIS));
		panelGauche.setOpaque(false);
		panelGauche.add(this.panelParametres);
		panelGauche.add(this.panelModificationPlateau);
		panelGauche.add(this.panelAutres);

		JScrollPane scrollEditeur = new JScrollPane(this.panelEditeur);
		scrollEditeur.setPreferredSize(new Dimension(620, 560));
		scrollEditeur.setOpaque(false);

		/*----------------------*/
		/* Ajout des composants */
		/*----------------------*/
		this.add(panelGauche, BorderLayout.WEST);
		this.add(scrollEditeur, BorderLayout.CENTER);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/*-----------*/
	/*  Getters  */
	/*-----------*/
	// Renvoie le panneau contenant les parametres du plateau.
	public PanelParametres getPanelParametres()
	{
		return this.panelParametres;
	}

	// Renvoie le panneau contenant les outils de modification.
	public PanelModificationPlateau getPanelModificationPlateau()
	{
		return this.panelModificationPlateau;
	}

	// Renvoie le plateau actuellement edite.
	public Plateau getPlateau()
	{
		return this.plateau;
	}

	/*---------------------*/
	/*    Méthodes         */
	/*---------------------*/
	// Ouvre un fichier plateau et recharge l'editeur avec son contenu.
	public void traiterOuverture()
	{
		File fichier = this.choisirFichierPlateau(this.gestionPlateau.getDossierPlateaux());
		if (fichier == null)
		{
			return;
		}
		try
		{
			this.plateau = this.gestionPlateau.lirePlateau(fichier);
			this.chargerPlateauOuvert(this.plateau, this.plateau.calculerNombreZones());
		}
		catch (IOException exception)
		{
			this.afficherMessage("Ouverture impossible : " + exception.getMessage());
		}
	}

	// Applique les parametres puis enregistre dans le fichier courant.
	public void traiterEnregistrement()
	{
		if (!this.appliquerParametresPlateau())
		{
			return;
		}

		this.enregistrerPlateauCourant();
	}

	// Applique les parametres puis demande un fichier pour enregistrer une copie.
	public void traiterEnregistrementCopie()
	{
		if (!this.appliquerParametresPlateau())
		{
			return;
		}

		File fichier = this.choisirFichierCopie(this.gestionPlateau.getDossierPlateaux());
		if (fichier == null)
		{
			return;
		}
		this.enregistrerCopie(fichier);
	}

	// Applique les valeurs des champs au plateau avant affichage ou sauvegarde.
	public boolean appliquerParametresPlateau()
	{
		int largeur;
		int hauteur;
		int tailleCase;
		int nombreZones;

		try
		{
			largeur     = this.panelParametres.getLargeurChoisie();
			hauteur     = this.panelParametres.getHauteurChoisie();
			tailleCase  = this.panelParametres.getTailleCaseChoisie();
			nombreZones = this.panelParametres.getNombreZonesChoisi();
		}
		catch (NumberFormatException exception)
		{
			this.afficherMessage("Les champs numeriques doivent contenir des nombres.");
			return false;
		}

		if (!this.plateau.parametresSontValides(largeur, hauteur, tailleCase,
				nombreZones, this.panelParametres.getTypePiocheChoisi()))
		{
			this.afficherMessage("Les champs numeriques doivent etre positifs.");
			return false;
		}

		this.plateau.actualiserParametres(largeur, hauteur, tailleCase,
				nombreZones, this.panelParametres.getTypePiocheChoisi());
		this.actualiserTailleEditeur();
		this.panelEditeur.repaint();
		return true;
	}

	// Enregistre dans le fichier courant apres validation metier.
	private void enregistrerPlateauCourant()
	{
		try
		{
			this.gestionPlateau.enregistrerPlateau(this.plateau, this.panelParametres.getNombreZonesChoisi());
			this.afficherMessage(
					"Le plateau a ete enregistre dans " +
					this.gestionPlateau.getFichierPlateauCourant().getName() + ".");
		}
		catch (IOException | IllegalArgumentException exception)
		{
			this.afficherMessage("Enregistrement impossible : " + exception.getMessage());
		}
	}

	// Enregistre le plateau dans un autre fichier choisi par l'utilisateur.
	private void enregistrerCopie(File fichier)
	{
		try
		{
			this.gestionPlateau.enregistrerCopiePlateau(this.plateau,
					this.panelParametres.getNombreZonesChoisi(), fichier);
			this.afficherMessage(
					"Le plateau a ete enregistre dans " +
					this.gestionPlateau.getFichierPlateauCourant().getName() + ".");
		}
		catch (IOException | IllegalArgumentException exception)
		{
			this.afficherMessage("Enregistrement impossible : " + exception.getMessage());
		}
	}

	// Ajuste la taille du panneau editeur selon le plateau.
	public void actualiserTailleEditeur()
	{
		this.panelEditeur.actualiserTaille();
	}

	// Remplace le plateau courant par celui qui vient d'etre ouvert.
	public void chargerPlateauOuvert(Plateau plateau, int nombreZones)
	{
		this.panelEditeur.setPlateau(plateau);
		this.panelParametres.chargerPlateau(plateau, nombreZones);
		this.panelEditeur.actualiserChoixEdition(this.panelModificationPlateau.getModeEditionChoisi(),
				this.panelParametres.getZoneActive(), this.panelModificationPlateau.getTypeAtomeChoisi(),
				this.panelModificationPlateau.getCouleurBaseChoisie());
		this.selectionnerModeEdition("Dessiner une zone");
		this.panelEditeur.repaint();
	}

	// Synchronise le mode d'edition avec le panneau de modification.
	public void selectionnerModeEdition(String mode)
	{
		if (!mode.equals(this.panelModificationPlateau.getModeEditionChoisi()))
		{
			this.panelModificationPlateau.selectionnerModeEdition(mode);
		}
	}

	// Affiche une boite de dialogue pour choisir un fichier plateau a ouvrir.
	public File choisirFichierPlateau(File dossier)
	{
		JFileChooser choixFichier = new JFileChooser(dossier);
		choixFichier.setDialogTitle("Ouvrir un plateau");
		choixFichier.setFileFilter(new FileNameExtensionFilter("Fichiers plateau (*.data)", "data"));
		choixFichier.setAcceptAllFileFilterUsed(false);

		if (choixFichier.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
		{
			return null;
		}
		File fichier = choixFichier.getSelectedFile();
		if (!this.gestionPlateau.estFichierData(fichier))
		{
			this.afficherMessage("Choisissez un fichier .data.");
			return null;
		}
		return fichier;
	}

	// Affiche une boite de dialogue pour choisir le fichier de copie.
	private File choisirFichierCopie(File dossier)
	{
		if (dossier != null && !dossier.exists())
		{
			dossier.mkdirs();
		}

		JFileChooser choixFichier = new JFileChooser(dossier);
		choixFichier.setDialogTitle("Creer une copie du plateau");
		choixFichier.setFileFilter(new FileNameExtensionFilter("Fichiers plateau (*.data)", "data"));
		choixFichier.setAcceptAllFileFilterUsed(false);
		choixFichier.setSelectedFile(this.gestionPlateau.getFichierCopiePropose());

		if (choixFichier.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
		{
			return null;
		}

		File fichier = this.gestionPlateau.ajouterExtensionData(choixFichier.getSelectedFile());
		if (fichier.exists())
		{
			int confirmation = JOptionPane.showConfirmDialog(this,
					"Le fichier existe deja. Voulez-vous le remplacer ?",
					"Remplacer le fichier",
					JOptionPane.YES_NO_OPTION);
			if (confirmation != JOptionPane.YES_OPTION)
			{
				return null;
			}
		}
		return fichier;
	}

	// Affiche un message d'information ou d'erreur a l'utilisateur.
	public void afficherMessage(String message)
	{
		JOptionPane.showMessageDialog(this, message);
	}
}
