package src.ihm;

import src.ControleurJeu;

import javax.swing.*;
import java.awt.Toolkit.*;
import java.awt.Dimension;
import java.awt.event.*;

public class FrameJeu extends JFrame
{
	private Dimension tailleEcran;
	private int ecranX, ecranY;

	public FrameJeu ( ControleurJeu ctrl )
	{
		tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		
		ecranX = (int) tailleEcran.getWidth();
		ecranY = (int) tailleEcran.getHeight();
		
		this.setTitle   ( "Scientifique Fou");
		this.setSize    ( ecranX, ecranY );
		this.setLocation( 0, 0 );
		
		// Création et ajout du Panel
		this.add ( new PanelJeu(ctrl) );
		
		// Gestion de la fermeture de la fenêtre
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new FermetureFenetre(ctrl));
		
		//Affichage
		this.setVisible(true);
	}


	//
	private class FermetureFenetre extends WindowAdapter
	{
		private ControleurJeu ctrl;
		
		public FermetureFenetre(ControleurJeu ctrl)
		{
			this.ctrl = ctrl;
		}
		
		public void windowClosing(WindowEvent e)
		{
			if ( !this.ctrl.frameFermetureOuverte() )
				this.ctrl.messageFermeture();
		}
	}

}
