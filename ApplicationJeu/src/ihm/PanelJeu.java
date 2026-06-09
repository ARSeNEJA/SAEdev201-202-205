package src.ihm;

import src.Controleur;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import java.awt.RenderingHints;
import java.awt.Graphics2D;
import java.awt.Graphics;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;

public class PanelJeu extends JPanel
{
	private JPanel panelPlateau;
	private JPanel panelInformation;

	public PanelJeu( Controleur ctrl )
	{
		this.setLayout(new BorderLayout(10, 10));

        // PANEL de Jeu ( panel central )
		panelPlateau = new JPanel();
        panelPlateau.setBackground(new Color(255, 255, 255));
                
        // PANEL INFORMATION ( panel gauche )
        panelInformation = new JPanel();
        panelInformation.setLayout       (new BoxLayout(panelInformation, BoxLayout.Y_AXIS));
        panelInformation.setPreferredSize(new Dimension(360, 0)                            );
        

        // Zone deck
        JPanel panelPioche    = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelPioche.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Pioche", TitledBorder.LEFT, TitledBorder.TOP));

        JLabel carteRetournee = creerVisuelCarte("Pioche",            new Color( 70, 130, 180), Color.WHITE);
        JLabel carteTournee   = creerVisuelCarte("Oxygène\n(Foncée)", new Color(240, 240, 240), Color.BLACK);

        JLabel lblMinuterie   = new JLabel("Cartes foncées restantes : 7", SwingConstants.CENTER);
        lblMinuterie.setFont      (new Font("Arial", Font.ITALIC, 13));
        lblMinuterie.setForeground(new Color(200, 0, 0)              );
        lblMinuterie.setAlignmentX(Component.CENTER_ALIGNMENT        );

        panelPioche.add(carteRetournee);
        panelPioche.add(carteTournee  );
        panelPioche.add(lblMinuterie  );


        // Zone carte defausse
        JPanel panelDefausse  = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelDefausse.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Défausse", TitledBorder.LEFT, TitledBorder.TOP));

        JLabel carteDefaussee = creerVisuelCarte("Carbone", new Color(200, 200, 200), Color.DARK_GRAY);
        panelDefausse.add(carteDefaussee);


        //Zone du score des manches
        JPanel panelScoreManches = new JPanel(new GridLayout(3, 1, 5, 5));
        panelScoreManches.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Progression", TitledBorder.LEFT, TitledBorder.TOP));
        
        JLabel   lblInfos  = new JLabel(String.format(%40s , "Informations : Zones  Atomes max  Résultat"), SwingConstants.LEFT);
        JLabel[] lblScoreManche = new JLabel[5];
        for (int i = 0; i < lblScoreManche.length; i++)
        {
            lblScoreManche[i] = new JLabel (String.format(%40s , "Manche " + (i + 1) + "    5  x  2  =  10"), SwingConstants.LEFT);
            lblScoreManche[i].setFont      (new Font("Arial", Font.PLAIN, 13));
            lblScoreManche[i].setForeground(new Color(0, 102, 204)           );
        }
        
        panelMancheScore.add(lblScoreManche);       
        
        panelInformation.add(panelPioche);
        panelInformation.add(Box.createVerticalStrut(5));
        //panelInformation.add(lblMinuterie);
        //panelInformation.add(Box.createVerticalStrut(15));
        panelInformation.add(panelDefausse);
        panelInformation.add(Box.createVerticalGlue()); 
        panelInformation.add(panelMancheScore);
        panelInformation.add(Box.createVerticalStrut(15));
        
        this.add(panelInformation, BorderLayout.WEST  );
        this.add(panelPlateau,     BorderLayout.CENTER);
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

    private JLabel creerVisuelCarte(String texte, Color fond, Color couleurTexte) 
    {
        JLabel lblCarte = new JLabel("<html><center>" + texte.replace("\n", "<br>") + "</center></html>", SwingConstants.CENTER);
        lblCarte.setPreferredSize(new Dimension(100, 140));
        lblCarte.setOpaque(true);
        lblCarte.setBackground(fond);
        lblCarte.setForeground(couleurTexte);
        lblCarte.setFont(new Font("Arial", Font.BOLD, 14));
        lblCarte.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2, true),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        return lblCarte;
    }
}