package src.ihm;

import src.Controleur;

import javax.swing.*;
import java.awt.Toolkit.*;
import java.awt.Dimension;
import java.awt.event.*;

public class FrameJeu extends JFrame
{
	private Dimension tailleEcran;
	private int ecranX, ecranY;

	// Cree la fenetre principale en plein ecran.
	public FrameJeu ( Controleur ctrl )
	{
		tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		
		ecranX = (int) tailleEcran.getWidth();
		ecranY = (int) tailleEcran.getHeight();
		
		this.setTitle   ( "Scientifique Fou");
		this.setSize    ( ecranX, ecranY );
		this.setLocation( 0, 0 );
		
		// Création et ajout du Panel
		this.add ( new PanelJeu(ctrl, ecranX, ecranY) );
		
		// Gestion de la fermeture de la fenêtre
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new FermetureFenetre(ctrl));
		
		//Affichage
		this.setVisible(true);
	}


	// Intercepte la fermeture pour demander confirmation.
	private class FermetureFenetre extends WindowAdapter
	{
		private Controleur ctrl;
		
		// Memorise le controleur appele lors de la fermeture.
		public FermetureFenetre(Controleur ctrl)
		{
			this.ctrl = ctrl;
		}
		
		// Ouvre la confirmation si elle n'est pas deja visible.
		public void windowClosing(WindowEvent e)
		{
			if ( !this.ctrl.frameFermetureOuverte() )
				this.ctrl.messageFermeture();
		}
	}

}
