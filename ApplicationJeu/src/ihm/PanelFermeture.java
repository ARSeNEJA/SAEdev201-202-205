package src.ihm;

import src.Controleur;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

public class PanelFermeture extends JPanel implements ActionListener
{
	/*-------------------*/
	/*    Attributs      */
	/*-------------------*/
	private Controleur     ctrl;
	private FrameFermeture frameFermeture;
	
	private JButton        btnNon, btnOui;
	
	private JPanel         panelChoix;
	
	private JLabel         lblMessage;
	
	/*-------------------*/
	/*   Constructeur    */
	/*-------------------*/
	// Cree le message de confirmation et les boutons Oui/Non.
	public PanelFermeture( Controleur ctrl )
	{
		this.ctrl = ctrl;
		
		this.setLayout( new GridLayout(2, 1) );
		
		this.panelChoix = new JPanel();
		
		this.lblMessage = new JLabel("<html>"                                                 +
		                             "Êtes-vous sûr de vouloir quitter ?<br>"                 +
		                             "<font size='2'>"                                        +
		                             "<center>Toute progression sera perdue.</font></center>" +
		                             "<html>"                                                  );
		this.lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.btnNon = new JButton("Non");
		this.btnOui = new JButton("Oui");
		
		this.btnNon.setFont(new Font("Arial", Font.BOLD, 15));
		this.btnOui.setFont(new Font("Arial", Font.BOLD, 15));
		
		this.btnNon.setPreferredSize(new Dimension(70, 25));
		this.btnOui.setPreferredSize(new Dimension(70, 25));
		
		this.panelChoix.add( btnNon );
		this.panelChoix.add( new JLabel("        ") );
		this.panelChoix.add( btnOui );
		
		this.add( this.lblMessage );
		this.add( this.panelChoix );
		
		this.btnNon.addActionListener(this);
		this.btnOui.addActionListener(this);
	}


	// Traite le choix de l'utilisateur lors de la fermeture.
	// Gere Oui ou Non.
	public void actionPerformed(ActionEvent e)
	{
		if ( e.getSource() == this.btnNon )
		{
			this.ctrl.fermerFermerFenetre();
		}
		else
		{
			this.ctrl.fermerJeu();
		}
	}

}


