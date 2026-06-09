package src.ihm;

import src.ControleurJeu;

import javax.swing.*;
import java.awt.Toolkit.*;
import java.awt.Dimension;

public class FrameFermeture extends JFrame
{
	private Dimension tailleEcran;
	private int       ecranX, ecranY;
	
	private int       tailleFrameX, tailleFrameY;
	
<<<<<<< HEAD
	public FrameFermeture( ControleurJeu ctrl )
=======
	// Cree la fenetre de confirmation centree a l'ecran.
	public FrameFermeture( Controleur ctrl )
>>>>>>> origin/main
	{
		tailleEcran  = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		
		ecranX       = (int) tailleEcran.getWidth();
		ecranY       = (int) tailleEcran.getHeight();
		
		tailleFrameX = 400;
		tailleFrameY = 200;
		
		this.setTitle   ( "Quitter Le Jeu");
		this.setSize    ( tailleFrameX, tailleFrameY );
		this.setLocation( ecranX/2-tailleFrameX/2, ecranY/2-tailleFrameY/2 );
		
		// Création et ajout du Panel
		this.add ( new PanelFermeture(ctrl) );
		
		// Gestion de la fermeture de la fenêtre
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//Affichage
		this.setVisible(true);
	}


	// Ferme la fenetre de confirmation.
	public void fermerFenetre()
	{
		this.dispose();
	}

}
