package ihm;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import metier.Atome;
import metier.Case;
import metier.Plateau;
import metier.Zone;
import metier.enums.Couleur;
import metier.enums.TypeAtome;

public class PanelPlateau extends JPanel implements ActionListener
{
	private static final String MODE_ZONE       = "Dessiner une zone";
	private static final String MODE_PLACEMENT  = "Placer un atome";
	private static final String MODE_BASE       = "Placer une base";
	private static final String MODE_SUPPRESSION = "Supprimer";

	private static final Color COULEUR_GRILLE = new Color(80, 80, 85, 110);
	private static final Color[] COULEURS_ZONES = {
		new Color(255, 160, 150, 115),
		new Color(125, 205, 155, 115),
		new Color(120, 165, 235, 115),
		new Color(190, 150, 110, 115),
		new Color(185, 145, 220, 115),
		new Color(100, 200, 205, 115),
		new Color(235, 205, 105, 115),
		new Color(230, 135, 190, 115),
		new Color(125, 175, 105, 115),
		new Color(245, 170, 95, 115),
		new Color(145, 165, 225, 115),
		new Color(165, 165, 95, 115)
	};

	/*------------------*/
	/*     Attributs    */
	/*------------------*/
	private Plateau plateau;
	private Fenetre fenetreParametres;
	private int zoneActive;
	private String modeEdition;
	private TypeAtome typeAtome;
	private Couleur couleurBase;
	private Image imageFond;

	/*------------------*/
	/*   Constructeur   */
	/*------------------*/
	public PanelPlateau(Plateau plateau, Fenetre fenetreParametres)
	{
		this.plateau           = plateau;
		this.fenetreParametres = fenetreParametres;
		this.zoneActive        = 1;
		this.modeEdition       = MODE_ZONE;
		this.typeAtome         = TypeAtome.H;
		this.couleurBase       = Couleur.ROUGE;
		this.setOpaque(false);
		this.imageFond = this.chargerImageFond();

		GererSouris gererSouris = new GererSouris();
		this.addMouseListener(gererSouris);
		this.addMouseMotionListener(gererSouris);
		this.actualiserTaille();
	}

	public void setPlateau(Plateau plateau)
	{
		this.plateau = plateau;
		this.actualiserTaille();
	}

	/*-----------------*/
	/*    Méthodes     */
	/*-----------------*/
	public void actionPerformed(ActionEvent e)
	{
		this.zoneActive  = this.fenetreParametres.getPanelParametres().getZoneActive();
		this.modeEdition = MODE_ZONE;
		this.fenetreParametres.selectionnerModeEdition(MODE_ZONE);
	}

	private void actualiserChoixDepuisFenetre()
	{
		this.actualiserChoixEdition(
				this.fenetreParametres.getPanelModificationPlateau().getModeEditionChoisi(),
				this.fenetreParametres.getPanelParametres().getZoneActive(),
				this.fenetreParametres.getPanelModificationPlateau().getTypeAtomeChoisi(),
				this.fenetreParametres.getPanelModificationPlateau().getCouleurBaseChoisie());
	}

	private class GererSouris extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			PanelPlateau.this.actualiserChoixDepuisFenetre();
			if (e.getButton() != MouseEvent.BUTTON1 ||
					MODE_ZONE.equals(PanelPlateau.this.modeEdition) ||
					MODE_SUPPRESSION.equals(PanelPlateau.this.modeEdition))
			{
				return;
			}
			Case caseCliquee = PanelPlateau.this.obtenirPositionGrille(e);
			PanelPlateau.this.placerAtomeOuBase(caseCliquee.getColonne(), caseCliquee.getLigne());
		}

		public void mousePressed(MouseEvent e)
		{
			PanelPlateau.this.actualiserChoixDepuisFenetre();
			if (e.getButton() == MouseEvent.BUTTON1)
			{
				if (MODE_SUPPRESSION.equals(PanelPlateau.this.modeEdition))
				{
					PanelPlateau.this.supprimerAtome(e);
				}
				else if (MODE_ZONE.equals(PanelPlateau.this.modeEdition))
				{
					PanelPlateau.this.affecterCaseZone(e);
				}
			}
			if (e.getButton() == MouseEvent.BUTTON3)
			{
				PanelPlateau.this.supprimerZone(e);
			}
		}

		public void mouseDragged(MouseEvent e)
		{
			PanelPlateau.this.actualiserChoixDepuisFenetre();
			if (SwingUtilities.isRightMouseButton(e))
			{
				PanelPlateau.this.supprimerZone(e);
			}
			else if (MODE_SUPPRESSION.equals(PanelPlateau.this.modeEdition))
			{
				PanelPlateau.this.supprimerAtome(e);
			}
			else if (MODE_ZONE.equals(PanelPlateau.this.modeEdition))
			{
				PanelPlateau.this.affecterCaseZone(e);
			}
		}
	}

	public void actualiserTaille()
	{
		this.setPreferredSize(new Dimension(
				this.plateau.getLargeur() * this.plateau.getTailleCase(),
				this.plateau.getHauteur() * this.plateau.getTailleCase()));
		this.revalidate();
		this.repaint();
	}

	public void actualiserChoixEdition(String modeEdition, int zoneActive, TypeAtome typeAtome, Couleur couleurBase)
	{
		this.modeEdition  = modeEdition;
		this.zoneActive   = zoneActive;
		this.typeAtome    = typeAtome;
		this.couleurBase  = couleurBase;
		if (this.modeEdition == null) {this.modeEdition = MODE_ZONE;}
		if (this.zoneActive < 1)      {this.zoneActive = 1;}
		if (this.typeAtome == null)   {this.typeAtome = TypeAtome.H;}
		if (this.couleurBase == null) {this.couleurBase = Couleur.ROUGE;}
	}

	private Case obtenirPositionGrille(MouseEvent e)
	{
		int colonne = e.getX() / this.plateau.getTailleCase();
		int ligne   = e.getY() / this.plateau.getTailleCase();
		return new Case(colonne, ligne);
	}

	private void affecterCaseZone(MouseEvent evenement)
	{
		Case caseCliquee = this.obtenirPositionGrille(evenement);
		int colonne = caseCliquee.getColonne();
		int ligne   = caseCliquee.getLigne();

		if (!this.plateau.caseExiste(colonne, ligne)) {return;}
		if (!this.plateau.affecterCaseZone(colonne, ligne, this.zoneActive)) {return;}
		this.repaint();
	}

	private void supprimerZone(MouseEvent evenement)
	{
		Case caseCliquee = this.obtenirPositionGrille(evenement);
		int colonne = caseCliquee.getColonne();
		int ligne   = caseCliquee.getLigne();

		if (!this.plateau.caseExiste(colonne, ligne)) {return;}
		this.plateau.retirerCaseDesZones(colonne, ligne);
		this.repaint();
	}

	private void supprimerAtome(MouseEvent evenement)
	{
		Case caseCliquee = this.obtenirPositionGrille(evenement);
		int colonne = caseCliquee.getColonne();
		int ligne   = caseCliquee.getLigne();

		if (!this.plateau.caseExiste(colonne, ligne)) {return;}
		this.plateau.supprimerAtome(colonne, ligne);
		this.repaint();
	}

	private void placerAtomeOuBase(int colonne, int ligne)
	{
		if (MODE_BASE.equals(this.modeEdition))
		{
			if (!this.plateau.definirBase(colonne, ligne, this.typeAtome, this.couleurBase))
			{
				this.fenetreParametres.afficherMessage(
						"Une base existe deja avec cette couleur ou ce symbole.");
			}
			this.repaint();
			return;
		}
		this.plateau.ajouterAtome(colonne, ligne, this.typeAtome);
		this.repaint();
	}

	//Dessin
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		int tailleCase = this.plateau.getTailleCase();

		if (this.imageFond != null)
		{
			g.drawImage(this.imageFond, 0, 0, this.getWidth(), this.getHeight(), null);
		}
		this.dessinerZones(g, tailleCase);
		this.dessinerGrille(g, tailleCase);
		this.dessinerNumerosZones(g, tailleCase);
		this.dessinerLiaisons(g, tailleCase);
		this.dessinerRectangleEpais(g, tailleCase);

		ArrayList<Atome> atomes = this.plateau.getAtomes();
		for (int i = 0; i < atomes.size(); i++)
		{
			Atome atome    = atomes.get(i);
			Case  position = atome.getPosition();
			int x = position.getColonne() * tailleCase;
			int y = position.getLigne()   * tailleCase;
			Image image = this.chargerImageAtome(atome.getType());
			if (image != null)
			{
				int marge = Math.max(4, tailleCase / 12);
				g.drawImage(image, x + marge, y + marge, tailleCase - 2 * marge, tailleCase - 2 * marge, null);
			}
		}
	}

	private void dessinerZones(Graphics g, int tailleCase)
	{
		ArrayList<Zone> zones = this.plateau.getZones();
		for (int i = 0; i < zones.size(); i++)
		{
			Zone zone = zones.get(i);
			int indexCouleur = zone.getId() - 1;
			if (indexCouleur < 0) {indexCouleur = 0;}
			g.setColor(COULEURS_ZONES[indexCouleur % COULEURS_ZONES.length]);
			ArrayList<Case> cases = zone.getCases();
			for (int j = 0; j < cases.size(); j++)
			{
				Case caseZone = cases.get(j);
				g.fillRect(caseZone.getColonne() * tailleCase, caseZone.getLigne() * tailleCase,
						   tailleCase, tailleCase);
			}
		}
	}

	private void dessinerGrille(Graphics g, int tailleCase)
	{
		int largeurPixels = this.plateau.getLargeur() * tailleCase;
		int hauteurPixels = this.plateau.getHauteur() * tailleCase;
		g.setColor(COULEUR_GRILLE);
		for (int x = 0; x <= largeurPixels; x = x + tailleCase)
		{
			g.drawLine(x, 0, x, hauteurPixels);
		}
		for (int y = 0; y <= hauteurPixels; y = y + tailleCase)
		{
			g.drawLine(0, y, largeurPixels, y);
		}
	}

	private void dessinerNumerosZones(Graphics g, int tailleCase)
	{
		ArrayList<Zone> zones = this.plateau.getZones();
		g.setFont(new Font("Arial", Font.BOLD, Math.max(12, tailleCase / 4)));
		FontMetrics metrics = g.getFontMetrics();
		g.setColor(new Color(35, 35, 35));
		for (int i = 0; i < zones.size(); i++)
		{
			Zone zone = zones.get(i);
			ArrayList<Case> cases = zone.getCases();
			for (int j = 0; j < cases.size(); j++)
			{
				Case caseZone = cases.get(j);
				String texte = String.valueOf(zone.getId());
				int x = caseZone.getColonne() * tailleCase + (tailleCase - metrics.stringWidth(texte)) / 2;
				int y = caseZone.getLigne()   * tailleCase + (tailleCase + metrics.getAscent()) / 2 - 3;
				g.drawString(texte, x, y);
			}
		}
	}

	private void dessinerLiaisons(Graphics g, int tailleCase)
	{
		g.setColor(new Color(145, 145, 150));
		ArrayList<Atome> atomes = this.plateau.getAtomes();
		for (int i = 0; i < atomes.size(); i++)
		{
			Atome atome = atomes.get(i);
			ArrayList<Atome> voisins = atome.getVoisins();
			for (int j = 0; j < voisins.size(); j++)
			{
				Atome voisin = voisins.get(j);
				if (atomes.indexOf(voisin) > i)
				{
					int x1 = atome.getPosition().getColonne() * tailleCase + tailleCase / 2;
					int y1 = atome.getPosition().getLigne()   * tailleCase + tailleCase / 2;
					int x2 = voisin.getPosition().getColonne() * tailleCase + tailleCase / 2;
					int y2 = voisin.getPosition().getLigne()   * tailleCase + tailleCase / 2;
					g.drawLine(x1, y1, x2, y2);
				}
			}
		}
	}

	private void dessinerRectangleEpais(Graphics g, int tailleCase)
	{
		ArrayList<Atome> atomes = this.plateau.getAtomes();
		for (int i = 0; i < atomes.size(); i++)
		{
			Atome atome = atomes.get(i);
			if (atome.estBase())
			{
				Case caseBase = atome.getPosition();
				int x        = caseBase.getColonne() * tailleCase + 2;
				int y        = caseBase.getLigne()   * tailleCase + 2;
				int largeur  = tailleCase - 4;
				int hauteur  = tailleCase - 4;
				int epaisseur = 5;
				Color couleurBase = this.getCouleur(atome.getCouleurBase());
				g.setColor(new Color(couleurBase.getRed(), couleurBase.getGreen(), couleurBase.getBlue(), 110));
				g.fillRect(x, y, largeur, hauteur);
				g.setColor(couleurBase);
				for (int k = 0; k < epaisseur; k++)
				{
					g.drawRect(x + k, y + k, largeur - 2 * k, hauteur - 2 * k);
				}
			}
		}
	}

	private Image chargerImageAtome(TypeAtome type)
	{
		File fichier = new File("images/symbole", type.getNomImage());
		if (fichier.exists())
		{
			try {return ImageIO.read(fichier);}
			catch (IOException exception) {return null;}
		}
		return null;
	}

	private Image chargerImageFond()
	{
		File fichier = new File("images/fond_microscope.png");
		if (fichier.exists())
		{
			try {return ImageIO.read(fichier);}
			catch (IOException exception) {return null;}
		}
		return null;
	}

	private Color getCouleur(Couleur couleur)
	{
		if (couleur == Couleur.ROUGE) {return new Color(205, 45,  45);}
		if (couleur == Couleur.VERT)  {return new Color(35,  145, 75);}
		if (couleur == Couleur.BLEU)  {return new Color(45,  95,  205);}
		return new Color(135, 82, 45);
	}
}
