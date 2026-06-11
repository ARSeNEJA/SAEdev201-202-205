package src.ihm;

import javax.swing.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;

public class PlateauDessin extends JPanel 
{
    // Constructeur optionnel si vous voulez définir la couleur de fond ici
    public PlateauDessin() 
    {
        this.setBackground(new Color(255, 255, 255));
    }

    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g); 
            
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int nbColonnes = 10; 
        int nbLignes   = 10; 

        int tailleCase = Math.min(this.getWidth() / nbColonnes, this.getHeight() / nbLignes);

        int margeX = (this.getWidth() - (tailleCase * nbColonnes)) / 2;
        int margeY = (this.getHeight() - (tailleCase * nbLignes)) / 2;

        g2.setColor(new Color(200, 200, 200));

        for (int i = 0; i <= nbColonnes; i++) {
            int x = margeX + (i * tailleCase);
            g2.drawLine(x, margeY, x, margeY + (nbLignes * tailleCase));
        }

        for (int j = 0; j <= nbLignes; j++) {
            int y = margeY + (j * tailleCase);
            g2.drawLine(margeX, y, margeX + (nbColonnes * tailleCase), y);
        }
    }
}