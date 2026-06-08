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
	public PanelParametres getPanelParametres()
	{
		return this.panelParametres;
	}

	public PanelModificationPlateau getPanelModificationPlateau()
	{
		return this.panelModificationPlateau;
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

	public void traiterEnregistrement()
	{
		if (!this.appliquerParametresPlateau())
		{
			return;
		}

		this.enregistrerPlateauCourant();
	}

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

	public void actualiserTailleEditeur()
	{
		this.panelEditeur.actualiserTaille();
	}

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

	public void selectionnerModeEdition(String mode)
	{
		if (!mode.equals(this.panelModificationPlateau.getModeEditionChoisi()))
		{
			this.panelModificationPlateau.selectionnerModeEdition(mode);
		}
	}

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

	public void afficherMessage(String message)
	{
		JOptionPane.showMessageDialog(this, message);
	}
}
