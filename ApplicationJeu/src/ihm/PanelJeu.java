package src.ihm;

import src.Controleur;

import java.awt.BorderLayout;
import java.awt.*;
import javax.swing.*;

public class PanelJeu extends JPanel
{
	private JPanel panelPlateau;
	private JPanel panelInformation;

	public PanelJeu( Controleur ctrl )
	{
		//this.add(new JLabel("Test 1"));

		this.setLayout(new BorderLayout(10, 10));
		
		panelPlateau = new JPanel();
		
		// PANNEL de JEu ( panneau central )
        panelPlateau.setBackground(new Color(250, 250, 250));
        panelPlateau.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

                
        // PANNEL INFORMATION - HUD ( Zone gauche )
        panelInformation = new JPanel();
        panelInformation.setLayout(new BoxLayout(panelInformation, BoxLayout.Y_AXIS));
        panelInformation.setSize(320, 0);

        // Zone deck


        // Zone carte defausse


        //Zone des manche 

        this.add(panelPlateau, BorderLayout.CENTER);
        this.add(panelInformation, BorderLayout.WEST);
	}

    protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g); 
			
		Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dessin d'une grille témoin
        g2.setColor(new Color(220, 220, 220));
        int tailleCase = 60;  //a modifier en fonction de la taille du plateau
        for (int i = 0; i < this.getWidth(); i += tailleCase) {
            g2.drawLine(i, 0, i, this.getHeight());
        }
        for (int j = 0; j < this.getHeight(); j += tailleCase) {
            g2.drawLine(0, j, this.getWidth(), j);
        }

            
    }
}