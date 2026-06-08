package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import metier.GestionPlateau;
import metier.Plateau;

public class FenetreParametres extends JFrame
{
	/*--------------------*/
	/*      Attributs     */
	/*--------------------*/
	private Plateau plateau;
	private GestionPlateau gestionPlateau;
	private PanelParametres panelParametres;
	private PanelBarreEdition panelBarreEdition;
	private PanelPlateau panelEditeur;
	private File fichierPlateauCourant;

	/*--------------------*/
	/*    Constructeur    */
	/*--------------------*/
	public FenetreParametres(Plateau plateau)
	{
		this.plateau     = plateau;
		this.gestionPlateau = new GestionPlateau();
		this.fichierPlateauCourant = new File("../PlateauData/plateau.txt");

		this.setTitle("Creation du plateau");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel fond = new JLabel(new ImageIcon("images/fond_microscope.png"));
		fond.setLayout(new BorderLayout(10, 10));
		this.setContentPane(fond);

		/*-------------------------*/
		/* Creation des composants */
		/*-------------------------*/
		this.panelBarreEdition = new PanelBarreEdition(this);
		this.panelEditeur      = new PanelPlateau(plateau, this);
		this.panelParametres   = new PanelParametres(plateau, this.panelEditeur, this);
		this.panelBarreEdition.setOpaque(false);
		this.panelParametres.setOpaque(false);

		JPanel panelCentre = new JPanel(new BorderLayout());
		JScrollPane scrollEditeur = new JScrollPane(this.panelEditeur);
		scrollEditeur.setPreferredSize(new Dimension(620, 560));
		panelCentre.setOpaque(false);
		scrollEditeur.setOpaque(false);
		panelCentre.add(this.panelBarreEdition, BorderLayout.NORTH);
		panelCentre.add(scrollEditeur, BorderLayout.CENTER);

		/*----------------------*/
		/* Ajout des composants */
		/*----------------------*/
		this.add(this.panelParametres, BorderLayout.WEST);
		this.add(panelCentre, BorderLayout.CENTER);

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/*-----------*/
	/*  Getters  */
	/*-----------*/
	public PanelParametres getPanelParametres()
	{
		return this.panelParametres;
	}

	public PanelBarreEdition getPanelBarreEdition()
	{
		return this.panelBarreEdition;
	}

	public Plateau getPlateau()
	{
		return this.plateau;
	}

	/*---------------------*/
	/*    Méthodes         */
	/*---------------------*/
	public void traiterOuverture()
	{
		File fichier = this.choisirFichierPlateau(new File("../PlateauData"));
		if (fichier == null)
		{
			return;
		}
		try
		{
			this.plateau = this.gestionPlateau.lirePlateau(fichier);
			this.fichierPlateauCourant = fichier;
			this.chargerPlateauOuvert(this.plateau, this.plateau.calculerNombreZones());
		}
		catch (IOException exception)
		{
			this.afficherMessage("Ouverture impossible : " + exception.getMessage());
		}
	}

	public void traiterEnregistrement()
	{
		if (!this.appliquerParametresPlateau(false))
		{
			return;
		}

		int nombreZones = this.panelParametres.getNombreZonesChoisi();
		File fichier = this.choisirDestinationEnregistrement();
		if (fichier == null)
		{
			return;
		}

		try
		{
			this.gestionPlateau.enregistrerPlateau(this.plateau, nombreZones, fichier);
			this.afficherMessage(
					"Le plateau a ete enregistre dans " + fichier.getName() + ".");
		}
		catch (IOException exception)
		{
			this.afficherMessage("Enregistrement impossible : " + exception.getMessage());
		}
	}

	public boolean appliquerParametresPlateau()
	{
		return this.appliquerParametresPlateau(true);
	}

	private boolean appliquerParametresPlateau(boolean afficherConfirmation)
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
		if (afficherConfirmation)
		{
			this.afficherMessage("Les parametres du plateau ont ete appliques.");
		}
		return true;
	}

	public void actualiserTailleEditeur()
	{
		this.panelEditeur.actualiserTaille();
	}

	public void chargerPlateauOuvert(Plateau plateau, int nombreZones)
	{
		this.panelEditeur.setPlateau(plateau);
		this.panelParametres.chargerPlateau(plateau, nombreZones);
		this.panelEditeur.actualiserChoixEdition(this.panelBarreEdition.getModeEditionChoisi(),
				this.panelParametres.getZoneActive(), this.panelBarreEdition.getTypeAtomeChoisi(),
				this.panelBarreEdition.getCouleurBaseChoisie());
		this.selectionnerModeEdition("Dessiner une zone");
		this.panelEditeur.repaint();
	}

	public void selectionnerModeEdition(String mode)
	{
		if (!mode.equals(this.panelBarreEdition.getModeEditionChoisi()))
		{
			this.panelBarreEdition.selectionnerModeEdition(mode);
		}
	}

	public File choisirFichierPlateau(File dossier)
	{
		JFileChooser choixFichier = new JFileChooser(dossier);
		choixFichier.setDialogTitle("Ouvrir un plateau");
		choixFichier.setFileFilter(new FileNameExtensionFilter("Fichiers texte (*.txt)", "txt"));

		if (choixFichier.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
		{
			return null;
		}
		return choixFichier.getSelectedFile();
	}

	private File choisirDestinationEnregistrement()
	{
		Object[] choix = {"Enregistrer le fichier actuel", "Creer une copie", "Annuler"};
		int reponse = JOptionPane.showOptionDialog(this,
				"Voulez-vous enregistrer le fichier actuel ou creer une copie ?",
				"Enregistrer le plateau",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				choix,
				choix[0]);

		if (reponse == 0)
		{
			return this.fichierPlateauCourant;
		}
		if (reponse == 1)
		{
			return this.choisirFichierCopie(new File("../PlateauData"));
		}
		return null;
	}

	private File choisirFichierCopie(File dossier)
	{
		if (dossier != null && !dossier.exists())
		{
			dossier.mkdirs();
		}

		JFileChooser choixFichier = new JFileChooser(dossier);
		choixFichier.setDialogTitle("Creer une copie du plateau");
		choixFichier.setFileFilter(new FileNameExtensionFilter("Fichiers texte (*.txt)", "txt"));
		choixFichier.setSelectedFile(new File(dossier, this.fichierPlateauCourant.getName()));

		if (choixFichier.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
		{
			return null;
		}

		File fichier = this.ajouterExtensionTxt(choixFichier.getSelectedFile());
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

	private File ajouterExtensionTxt(File fichier)
	{
		if (fichier.getName().toLowerCase().endsWith(".txt"))
		{
			return fichier;
		}
		File dossier = fichier.getParentFile();
		if (dossier == null)
		{
			return new File(fichier.getName() + ".txt");
		}
		return new File(dossier, fichier.getName() + ".txt");
	}

	public void afficherMessage(String message)
	{
		JOptionPane.showMessageDialog(this, message);
	}
}
