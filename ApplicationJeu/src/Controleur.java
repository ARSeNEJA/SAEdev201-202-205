package src;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import src.ihm.FrameFermeture;
import src.ihm.FrameJeu;
import src.metier.Atome;
import src.metier.Jeu;
import src.metier.LirePlateau;
import src.metier.Plateau;

public class Controleur
{
	/*-------------------*/
	/*    Attributs      */
	/*-------------------*/
	private FrameJeu frameJeu;
	private FrameFermeture frameFermeture;
	private Jeu jeu;
	private Plateau plateauCourant;
	private boolean modeDemo;

	/*-------------------*/
	/*   Constructeur    */
	/*-------------------*/
	// Charge le plateau et lance le jeu.
	public Controleur()
	{
		this.modeDemo = false;
		try
		{
			this.chargerPlateau(new File("PlateauData/plateau.data"));
			this.frameJeu = new FrameJeu(this);
		}
		catch (IOException exception)
		{
			JOptionPane.showMessageDialog(null, "Chargement du plateau impossible : " + exception.getMessage());
		}
	}

	/*-------------------*/
	/*   Accesseurs      */
	/*-------------------*/
	// Renvoie le jeu courant.
	public Jeu getJeu()
	{
		return this.jeu;
	}

	// Indique le mode demo.
	public boolean estModeDemo()
	{
		return this.modeDemo;
	}

	/*-------------------*/
	/*    Methodes       */
	/*-------------------*/
	// Joue la case cliquee.
	public void clicPlateau(int colonne, int ligne)
	{
		if (this.jeu == null || this.jeu.estPartieTerminee()) {return;}
		Atome atome = this.jeu.getPlateau().getAtomeCase(colonne, ligne);
		this.jeu.jouerAtome(atome);
		this.frameJeu.actualiserAffichage();
	}

	// Passe la carte courante.
	public void passerTour()
	{
		if (this.jeu == null) {return;}
		this.jeu.passerTour();
		this.frameJeu.actualiserAffichage();
	}

	// Active ou desactive le mode demo.
	public void changerModeDemo()
	{
		this.modeDemo = !this.modeDemo;
		if (this.plateauCourant != null)
		{
			this.jeu = new Jeu(this.plateauCourant, this.modeDemo);
		}
		if (this.frameJeu != null) {this.frameJeu.actualiserAffichage();}
	}

	// Ouvre le choix de plateau.
	public void ouvrirChoixPlateau()
	{
		JFileChooser chooser = new JFileChooser(new File("PlateauData"));
		chooser.setDialogTitle("Choisir un plateau");
		chooser.setApproveButtonText("Ouvrir");
		chooser.setFileFilter(new FileNameExtensionFilter("Fichiers plateau (*.data)", "data"));
		int choix = chooser.showOpenDialog(this.frameJeu);
		if (choix == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				this.chargerPlateau(chooser.getSelectedFile());
				if (this.frameJeu != null) {this.frameJeu.actualiserAffichage();}
			}
			catch (IOException exception)
			{
				JOptionPane.showMessageDialog(this.frameJeu, "Chargement du plateau impossible : " + exception.getMessage());
			}
		}
	}

	// Charge un plateau donne.
	public void chargerPlateau(File fichier) throws IOException
	{
		Plateau plateau = new LirePlateau().lire(fichier);
		this.plateauCourant = plateau;
		this.jeu = new Jeu(plateau, this.modeDemo);
	}

	// Ouvre la confirmation.
	public void messageFermeture()
	{
		this.frameFermeture = new FrameFermeture(this);
	}

	// Verifie la confirmation.
	public boolean frameFermetureOuverte()
	{
		return this.frameFermeture != null && this.frameFermeture.isVisible();
	}

	// Ferme le jeu.
	public void fermerJeu()
	{
		if (this.frameJeu != null) {this.frameJeu.dispose();}
		if (this.frameFermeture != null) {this.frameFermeture.dispose();}
	}

	// Ferme la confirmation.
	public void fermerFermerFenetre()
	{
		if (this.frameFermeture != null) {this.frameFermeture.dispose();}
	}

	// Lance le programme.
	public static void main(String[] ar)
	{
		new Controleur();
	}
}
