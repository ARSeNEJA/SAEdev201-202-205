package src.ihm;

import src.Controleur;
import src.metier.Atome;
import src.metier.Case;
import src.metier.Jeu;
import src.metier.Manche;
import src.metier.Plateau;
import src.metier.Zone;
import src.metier.enums.Couleur;
import src.metier.enums.TypeAtome;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlateauDessin extends JPanel
{
	/*-------------------*/
	/*    Attributs      */
	/*-------------------*/
	private static final String DOSSIER_IMAGES_JEU = "ApplicationJeu/images";
	private static final Color[] COULEURS_ZONES = {
		new Color(255, 160, 150, 115), new Color(125, 205, 155, 115),
		new Color(120, 165, 235, 115), new Color(190, 150, 110, 115),
		new Color(185, 145, 220, 115), new Color(100, 200, 205, 115)
	};

	private Controleur ctrl;
	private Image imageFond;

	// Constructeur optionnel si vous voulez définir la couleur de fond ici
	public PlateauDessin(Controleur ctrl)
	{
		this.ctrl = ctrl;
		this.setBackground(new Color(255, 255, 255));
		this.imageFond = this.chargerImageFond();
		this.addMouseListener(new MouseAdapter()
		{
			// Gere un clic plateau.
			public void mouseClicked(MouseEvent e)
			{
				Jeu jeu = PlateauDessin.this.ctrl.getJeu();
				if (jeu == null) {return;}
				int tailleCase = jeu.getPlateau().getTailleCase();
				int colonne = e.getX() / tailleCase;
				int ligne = e.getY() / tailleCase;
				if (!jeu.getPlateau().caseExiste(colonne, ligne)) {return;}
				PlateauDessin.this.ctrl.clicPlateau(colonne, ligne);
			}
		});
	}

	// Donne la taille reelle.
	public Dimension getPreferredSize()
	{
		Jeu jeu = this.ctrl.getJeu();
		if (jeu == null) {return new Dimension(600, 500);}
		Plateau plateau = jeu.getPlateau();
		return new Dimension(plateau.getLargeur() * plateau.getTailleCase(),
				plateau.getHauteur() * plateau.getTailleCase());
	}

	// Dessine le plateau.
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Jeu jeu = this.ctrl.getJeu();
		if (jeu == null) {return;}

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		Plateau plateau = jeu.getPlateau();
		int tailleCase = plateau.getTailleCase();
		this.dessinerFond(g2);
		this.dessinerZones(g2, plateau, tailleCase);
		this.dessinerLiaisonsPossibles(g2, plateau, tailleCase);
		this.dessinerLiaisonsManches(g2, jeu, tailleCase);
		this.dessinerBases(g2, plateau, tailleCase);
		this.dessinerAtomes(g2, plateau, tailleCase);
	}

	// Dessine le fond.
	private void dessinerFond(Graphics2D g2)
	{
		if (this.imageFond != null)
		{
			g2.drawImage(this.imageFond, 0, 0, this.getWidth(), this.getHeight(), null);
		}
	}

	// Dessine les zones.
	private void dessinerZones(Graphics2D g2, Plateau plateau, int tailleCase)
	{
		ArrayList<Zone> zones = plateau.getZones();
		for (int i = 0; i < zones.size(); i++)
		{
			Zone zone = zones.get(i);
			g2.setColor(COULEURS_ZONES[(zone.getId() - 1 + COULEURS_ZONES.length) % COULEURS_ZONES.length]);
			ArrayList<Case> cases = zone.getCases();
			for (int j = 0; j < cases.size(); j++)
			{
				Case c = cases.get(j);
				g2.fillRect(c.getColonne() * tailleCase, c.getLigne() * tailleCase, tailleCase, tailleCase);
			}
		}
	}

	// Dessine les voisins.
	private void dessinerLiaisonsPossibles(Graphics2D g2, Plateau plateau, int tailleCase)
	{
		g2.setStroke(new BasicStroke(2));
		g2.setColor(new Color(170, 170, 175));
		ArrayList<Atome> atomes = plateau.getAtomes();
		for (int i = 0; i < atomes.size(); i++)
		{
			Atome a = atomes.get(i);
			ArrayList<Atome> voisins = a.getVoisins();
			for (int j = 0; j < voisins.size(); j++)
			{
				Atome b = voisins.get(j);
				if (atomes.indexOf(b) > i) {this.tracerLigneAtomes(g2, a, b, tailleCase);}
			}
		}
	}

	// Dessine les chemins.
	private void dessinerLiaisonsManches(Graphics2D g2, Jeu jeu, int tailleCase)
	{
		ArrayList<Manche> manches = jeu.getManches();
		g2.setStroke(new BasicStroke(5));
		for (int m = 0; m < manches.size(); m++)
		{
			Manche manche = manches.get(m);
			g2.setColor(this.getCouleurAwt(manche.getCouleur()));
			ArrayList<Atome> chaine = manche.getChaine();
			for (int i = 0; i < chaine.size() - 1; i++)
			{
				this.tracerLigneAtomes(g2, chaine.get(i), chaine.get(i + 1), tailleCase);
			}
		}
	}

	// Dessine les bases.
	private void dessinerBases(Graphics2D g2, Plateau plateau, int tailleCase)
	{
		ArrayList<Atome> atomes = plateau.getAtomes();
		for (int i = 0; i < atomes.size(); i++)
		{
			Atome atome = atomes.get(i);
			if (atome.estBase())
			{
				Case c = atome.getPosition();
				Color couleur = this.getCouleurAwt(atome.getCouleurBase());
				g2.setColor(new Color(couleur.getRed(), couleur.getGreen(), couleur.getBlue(), 80));
				g2.fillRect(c.getColonne() * tailleCase + 3, c.getLigne() * tailleCase + 3, tailleCase - 6, tailleCase - 6);
				g2.setColor(couleur);
				g2.setStroke(new BasicStroke(4));
				g2.drawRect(c.getColonne() * tailleCase + 3, c.getLigne() * tailleCase + 3, tailleCase - 6, tailleCase - 6);
			}
		}
	}

	// Dessine les atomes.
	private void dessinerAtomes(Graphics2D g2, Plateau plateau, int tailleCase)
	{
		ArrayList<Atome> atomes = plateau.getAtomes();
		for (int i = 0; i < atomes.size(); i++)
		{
			Atome atome = atomes.get(i);
			Case c = atome.getPosition();
			int x = c.getColonne() * tailleCase;
			int y = c.getLigne() * tailleCase;
			int marge = Math.max(4, tailleCase / 12);
			Image image = this.chargerImageAtome(atome.getType());
			if (image != null)
			{
				g2.drawImage(image, x + marge, y + marge, tailleCase - 2 * marge, tailleCase - 2 * marge, null);
			}
			else
			{
				g2.setColor(Color.WHITE);
				g2.fillOval(x + marge, y + marge, tailleCase - 2 * marge, tailleCase - 2 * marge);
				g2.setColor(Color.BLACK);
				g2.drawOval(x + marge, y + marge, tailleCase - 2 * marge, tailleCase - 2 * marge);
				g2.setFont(new Font("Arial", Font.BOLD, Math.max(12, tailleCase / 3)));
				FontMetrics fm = g2.getFontMetrics();
				String texte = atome.getType().name();
				g2.drawString(texte, x + (tailleCase - fm.stringWidth(texte)) / 2,
						y + (tailleCase + fm.getAscent()) / 2 - 4);
			}
		}
	}

	// Trace une liaison.
	private void tracerLigneAtomes(Graphics2D g2, Atome a, Atome b, int tailleCase)
	{
		int x1 = a.getPosition().getColonne() * tailleCase + tailleCase / 2;
		int y1 = a.getPosition().getLigne() * tailleCase + tailleCase / 2;
		int x2 = b.getPosition().getColonne() * tailleCase + tailleCase / 2;
		int y2 = b.getPosition().getLigne() * tailleCase + tailleCase / 2;
		g2.drawLine(x1, y1, x2, y2);
	}

	// Charge un symbole.
	private Image chargerImageAtome(TypeAtome type)
	{
		File fichier = new File(DOSSIER_IMAGES_JEU + "/symbole", type.getNomImage());
		if (fichier.exists())
		{
			try {return ImageIO.read(fichier);}
			catch (IOException exception) {return null;}
		}
		return null;
	}

	// Charge le fond.
	private Image chargerImageFond()
	{
		File fichier = new File(DOSSIER_IMAGES_JEU, "fond_microscope.png");
		if (fichier.exists())
		{
			try {return ImageIO.read(fichier);}
			catch (IOException exception) {return null;}
		}
		return null;
	}

	// Convertit une couleur.
	private Color getCouleurAwt(Couleur couleur)
	{
		if (couleur == Couleur.ROUGE) {return new Color(205, 45, 45);}
		if (couleur == Couleur.VERT)  {return new Color(35, 145, 75);}
		if (couleur == Couleur.BLEU)  {return new Color(45, 95, 205);}
		return new Color(135, 82, 45);
	}
}
