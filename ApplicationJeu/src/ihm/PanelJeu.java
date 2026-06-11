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
    // Composants du panel de jeu
	private PlateauDessin panelPlateau;
	private JPanel        panelInformation;
    private int           lInformation;

    // Composants du panel d'information(piohe)
    private JPanel        panelPioche;
    private JPanel        panelCartes;

    // Composants du panel d'information(defausse)
    private JPanel        panelDefausse;
    private JPanel        panelGridDefausse;

    // Composants du panel d'information(manches)
    private JPanel        panelManches;
    private JPanel        panelGridManche;

    // Composants du panel pioche
    private JLabel        lblMinuterie;
    //private JButton       btnPiocher;
    //private JLabel        lblCartePiochee;

    // Composants du panel defausse
    private JScrollPane   spDefausse;
    private JLabel[]      lblCartesDefaussee;

    // Composants du panel manches
    private JLabel        lblInfos;
    private JLabel[]      lblScoreManche;
    private JScrollPane   spManches;


	public PanelJeu( Controleur ctrl, int lEcran, int hEcran )
	{        
		this.setLayout(new BorderLayout(10, 10));

        // ================
        //  Plateau de jeu
        // ================
		panelPlateau = new PlateauDessin();
        
        
        // ====================
        //  Zone d'information
        // ====================
        panelInformation = new JPanel();

        panelInformation.setLayout       (new BoxLayout(panelInformation, BoxLayout.Y_AXIS)   );
        panelInformation.setPreferredSize(new Dimension((int)(lEcran * 0.18), hEcran)         );

        lInformation = lEcran - 20;


        //  PIOCHE
        // ============
        panelPioche = new JPanel(new BorderLayout()               );
        panelCartes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        panelPioche.setBorder       (BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Pioche"));
        panelPioche.setPreferredSize(new Dimension((int)(lInformation * 0.2), (int)(hEcran * 0.3)));
        panelPioche.setMinimumSize  (new Dimension(                      250, (int)(hEcran * 0.2)));

        JLabel carteRetournee = creerVisuelCarte("Pioche",            new Color( 70, 130, 180), Color.WHITE);   // A remplacer par un bouton avec image de carte
        JLabel carteTournee   = creerVisuelCarte("Oxygène\n(Foncée)", new Color(240, 240, 240), Color.BLACK);   // A remplacer par une image de carte

        lblMinuterie = new JLabel ("Cartes foncées restantes : 7", SwingConstants.CENTER);
        lblMinuterie.setFont      (new Font("Arial", Font.ITALIC | Font.BOLD, 13)       );
        lblMinuterie.setForeground(new Color(220, 0, 0)                                 );


        //  Defausse
        // ==============
        panelDefausse     = new JPanel(new BorderLayout()          );
        panelGridDefausse = new JPanel(new GridLayout(0, 3, 10, 10));
        spDefausse        = new JScrollPane(panelGridDefausse, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panelDefausse.setBorder       (BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Défausse"));
        panelDefausse.setPreferredSize(new Dimension((int)(lInformation * 0.2), (int)(hEcran * 0.3)));
        panelDefausse.setMinimumSize  (new Dimension(                      250, (int)(hEcran * 0.2)));

        lblCartesDefaussee    = new JLabel[12];
        for (int i = 0; i < lblCartesDefaussee.length; i++)
        {
            lblCartesDefaussee[i] = creerVisuelCarte("Carbone", new Color(200, 200, 200), Color.DARK_GRAY); // A remplacer par les images de cartes
            lblCartesDefaussee[i].setPreferredSize(new Dimension(70, 145));                                 //
        } 


        //  Manches
        // =============
        panelManches    = new JPanel(new BorderLayout()         );
        panelGridManche = new JPanel(new GridLayout(0, 1, 0, 10));
        spManches       = new JScrollPane(panelGridManche, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        panelManches.setBorder       (BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Progression"));
        panelManches.setPreferredSize(new Dimension((int)(lInformation * 0.2), (int)(hEcran * 0.40)));
        panelManches.setMinimumSize  (new Dimension(                      250, (int)(hEcran * 0.15)));
        
        lblInfos  = new JLabel(String.format("%40s" , "Informations  Zones | Atomes | Résultat"));
        panelGridManche.add(lblInfos);

        lblScoreManche = new JLabel[10];
        for (int i = 0; i < lblScoreManche.length; i++)
        {
            lblScoreManche[i] = new JLabel (String.format("%40s" , "Manche " + (i + 1) + "         5   x   2      =    10")); // A changer pour etre automatique
            lblScoreManche[i].setFont      (new Font("Arial", Font.PLAIN, 15)                                              ); //
            lblScoreManche[i].setForeground(Color.BLACK                                                                    ); //
            lblScoreManche[i].setBorder    (BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY)                        ); //
            lblScoreManche[i].setPreferredSize(new Dimension(300, 50)                                                  ); //
        }

        // ======================
        //  Ajout des composants
        // ======================
        // Ajout des composants du panel Pioche
        panelCartes.add(carteRetournee);
        panelCartes.add(carteTournee  );

        panelPioche.add(panelCartes,  BorderLayout.CENTER);
        panelPioche.add(lblMinuterie, BorderLayout.SOUTH );

        // Ajout des composants du panel Défausse
        for (JLabel lbl : lblCartesDefaussee)
            panelGridDefausse.add(lbl);

        panelDefausse.add(spDefausse, BorderLayout.CENTER);

        // Ajout des composants du panel Information
        for (JLabel lbl : lblScoreManche)
            panelGridManche.add(lbl);
        
        panelManches.add(spManches, BorderLayout.CENTER);

        // Ajout des panels de la zone d'information
        panelInformation.add(panelPioche  );
        panelInformation.add(panelDefausse);
        panelInformation.add(panelManches );

        // Ajout des panels du jeu a la fenêtre
        this.add(panelInformation, BorderLayout.WEST  );
        this.add(panelPlateau,     BorderLayout.CENTER);
	}


    private JLabel creerVisuelCarte(String texte, Color fond, Color couleurTexte) 
    {
        JLabel lblCarte = new JLabel("<html><center>" + texte.replace("\n", "<br>") + "</center></html>", SwingConstants.CENTER);
        lblCarte.setPreferredSize(new Dimension(140, 210));
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