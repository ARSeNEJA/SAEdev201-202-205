package src.ihm;

import src.Controleur;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

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
        JPanel panelPioche = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelPioche.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Pioche", TitledBorder.LEFT, TitledBorder.TOP));

        JLabel carteRetournee = creerVisuelCarte("? (Dos)", new Color(70, 130, 180), Color.WHITE);
        JLabel carteTournee   = creerVisuelCarte("Oxygène\n(Foncée)", new Color(240, 240, 240), Color.BLACK);

        panelPioche.add(carteRetournee);
        panelPioche.add(carteTournee);

        JLabel lblMinuterie = new JLabel("Cartes foncées restantes : 7", SwingConstants.CENTER);
        lblMinuterie.setFont(new Font("Arial", Font.ITALIC, 13));
        lblMinuterie.setForeground(new Color(200, 0, 0));
        lblMinuterie.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Zone carte defausse
        JPanel panelDefausse = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelDefausse.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Défausse", TitledBorder.LEFT, TitledBorder.TOP));

        JLabel carteDefaussee = creerVisuelCarte("Carbone", new Color(200, 200, 200), Color.DARK_GRAY);
        panelDefausse.add(carteDefaussee);

        //Zone des manches et scores 
        panelInformation = new JPanel();
        panelInformation.setLayout(new BoxLayout(panelInformation, BoxLayout.Y_AXIS));
        panelInformation.setPreferredSize(new Dimension(340, 0));

        // ---------------------------------------------------
        // SECTION A : Les Manches et Scores
        // ---------------------------------------------------
        JPanel panelMancheScore = new JPanel(new GridLayout(3, 1, 5, 5));
        panelMancheScore.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Progression", TitledBorder.LEFT, TitledBorder.TOP));
        
        JLabel lblManche      = new JLabel("Manche actuelle : 2 / 5", SwingConstants.CENTER);
        JLabel lblScoreManche = new JLabel("Score de la chaîne : 12 pts", SwingConstants.CENTER);
        JLabel lblScoreTotal  = new JLabel("Score total : 45 pts", SwingConstants.CENTER);
        
        lblManche.setFont(new Font("Arial", Font.BOLD, 15));
        lblManche.setForeground(new Color(0, 102, 204)); 
        lblScoreManche.setFont(new Font("Arial", Font.PLAIN, 14));
        lblScoreTotal.setFont(new Font("Arial", Font.BOLD, 14));
        
        panelMancheScore.add(lblManche);
        panelMancheScore.add(lblScoreManche);
        panelMancheScore.add(lblScoreTotal);
        

        //
        
        panelInformation.add(panelPioche);
        panelInformation.add(Box.createVerticalStrut(5));
        panelInformation.add(lblMinuterie);
        panelInformation.add(Box.createVerticalStrut(15));
        panelInformation.add(panelDefausse);
        panelInformation.add(Box.createVerticalGlue()); 
        panelInformation.add(panelMancheScore);
        panelInformation.add(Box.createVerticalStrut(15));
        

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