package src.ihm;

import src.Controleur;

import java.awt.BorderLayout;

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
		
		@Override
		protected void paintComponent(Graphics g) 
		{
			super.paintComponent(g); 
			
			Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Dessin d'une grille témoin
            g2.setColor(new Color(220, 220, 220));
            int tailleCase = 60;
            for (int i = 0; i < this.getWidth(); i += tailleCase) {
                g2.drawLine(i, 0, i, this.getHeight());
            }
            for (int j = 0; j < this.getHeight(); j += tailleCase) {
                g2.drawLine(0, j, this.getWidth(), j);
            }

            // Texte de substitution au centre
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            String message = "[ Emplacement du Plateau Moléculaire ]";
            int strWidth = g2.getFontMetrics().stringWidth(message);
            g2.drawString(message, (this.getWidth() - strWidth) / 2, this.getHeight() / 2);
        }
        


        panelPlateau.setBackground(new Color(250, 250, 250));
        panelPlateau.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

	}
}