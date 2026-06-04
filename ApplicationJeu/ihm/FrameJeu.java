package src.vue;

import src.Controleur;

import javax.swing.*;
import java.awt.Toolkit.*;
import java.awt.Dimension;

public class FrameJeu extends JFrame
{
	private Dimension tailleEcran;
	
	private int EcranX, EcranY;

	public FrameJeu ( Controleur ctrl )
	{
		tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		
		EcranX = (int) tailleEcran.getWidth();
		EcranY = (int) tailleEcran.getHeight();
		
		this.setTitle   ( "Scientifique Fou");
		this.setSize    ( EcranX, EcranY );
		this.setLocation( 0, 0 );
		
		// Création et ajout du Panel
		this.add ( new PanelJeu(ctrl) );
		
		// Gestion de la fermeture de la fenêtre
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Affichage
		this.setVisible(true);
	}


	public String fermetureDuJeu()
	{
		return "etes vous sur de vouloir quitter, toute progression dans cette partie sera perdu.";
	}

}
