package src.ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import src.Controleur;
import src.metier.Carte;
import src.metier.Deck;
import src.metier.Jeu;
import src.metier.Manche;

public class PanelJeu extends JPanel implements ActionListener
{
	/*-------------------*/
	/*    Attributs      */
	/*-------------------*/
	private static final String DOSSIER_IMAGES_JEU = "ApplicationJeu/images";

	private Controleur ctrl;
	private int        lInformation;

	private PlateauDessin panelPlateau;

	private JPanel panelCentre;
	private JPanel panelMessage;
	private JPanel panelInformation;
	private JPanel panelPioche;
	private JPanel panelCartes;
	private JPanel panelDefausse;
	private JPanel panelGridDefausse;
	private JPanel panelManches;
	private JPanel panelGridManche;
	private JPanel conteneurPlateau;

	private JScrollPane scrollPlateau;
	private JScrollPane spDefausse;
	private JScrollPane spManches;

	private JLabel  lblMessage;
	private JLabel  lblMinuterie;
	private JLabel  lblCartePiochee;
	private JLabel  lblInfos;

	private JButton btnPiocher;
	private JButton btnModeDemo;
	private JButton btnOuvrirPlateau;


	/*-------------------*/
	/*   Constructeur    */
	/*-------------------*/
	public PanelJeu( Controleur ctrl, int lEcran, int hEcran )
	{
		this.ctrl = ctrl;
		this.lInformation = lEcran - 20;

		/*---------------------------*/
		/* Creation des composants   */
		/*---------------------------*/
		this.panelPlateau     = new PlateauDessin(ctrl);
		this.panelCentre      = new JPanel(new BorderLayout(0, 6));
		this.panelMessage     = new JPanel(new BorderLayout(10, 0));
		this.conteneurPlateau = new JPanel(new GridLayout(1, 1));
		this.panelInformation = new JPanel();
		this.panelPioche      = new JPanel(new BorderLayout());
		this.panelCartes      = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		this.panelDefausse    = new JPanel(new BorderLayout());
		this.panelGridDefausse = new JPanel(new GridLayout(0, 3, 10, 10));
		this.panelManches     = new JPanel(new BorderLayout());
		this.panelGridManche  = new JPanel(new GridLayout(0, 1, 0, 10));

		this.scrollPlateau = new JScrollPane(this.conteneurPlateau,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.spDefausse = new JScrollPane(this.panelGridDefausse,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.spManches = new JScrollPane(this.panelGridManche,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		this.lblMessage      = new JLabel(" ", SwingConstants.CENTER);
		this.lblMinuterie    = new JLabel("Cartes foncées restantes : 0", SwingConstants.CENTER);
		this.lblCartePiochee = this.creerVisuelCarte("Carte Piochée", new Color(70, 130, 180), Color.WHITE, 140, 210);
		this.lblInfos        = new JLabel(String.format("%40s" , "Informations  Zones | Atomes | Résultat"));

		this.btnPiocher       = this.creerVisuelPioche();
		this.btnModeDemo      = new JButton("Mode démo : OFF");
		this.btnOuvrirPlateau = new JButton("Ouvrir plateau");

		/*---------------------------*/
		/* Reglage des composants    */
		/*---------------------------*/
		this.setLayout(new BorderLayout(10, 10));
		this.panelInformation.setLayout(new BoxLayout(this.panelInformation, BoxLayout.Y_AXIS));

		this.panelInformation.setPreferredSize(new Dimension((int)(lEcran * 0.18), hEcran));

		this.panelPioche.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Pioche"));
		this.panelPioche.setPreferredSize(new Dimension((int)(this.lInformation * 0.2), (int)(hEcran * 0.3)));
		this.panelPioche.setMinimumSize(new Dimension(250, (int)(hEcran * 0.2)));

		this.panelDefausse.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Défausse"));
		this.panelDefausse.setPreferredSize(new Dimension((int)(this.lInformation * 0.2), (int)(hEcran * 0.3)));
		this.panelDefausse.setMinimumSize(new Dimension(250, (int)(hEcran * 0.2)));

		this.panelManches.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Progression"));
		this.panelManches.setPreferredSize(new Dimension((int)(this.lInformation * 0.2), (int)(hEcran * 0.40)));
		this.panelManches.setMinimumSize(new Dimension(250, (int)(hEcran * 0.15)));

		this.lblMessage.setFont(new Font("Arial", Font.BOLD, 14));
		this.lblMessage.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

		this.lblMinuterie.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 13));
		this.lblMinuterie.setForeground(new Color(220, 0, 0));

		this.lblInfos.setFont(new Font("Arial", Font.BOLD, 13));

		/*---------------------------*/
		/* Positionnement            */
		/*---------------------------*/
		this.panelMessage.add(this.btnModeDemo,      BorderLayout.WEST  );
		this.panelMessage.add(this.lblMessage,       BorderLayout.CENTER);
		this.panelMessage.add(this.btnOuvrirPlateau, BorderLayout.EAST  );

		this.conteneurPlateau.add(this.panelPlateau);
		this.panelCentre.add(this.panelMessage,  BorderLayout.NORTH );
		this.panelCentre.add(this.scrollPlateau, BorderLayout.CENTER);

		this.panelCartes.add(this.btnPiocher);
		this.panelCartes.add(this.lblCartePiochee);
		this.panelPioche.add(this.panelCartes,  BorderLayout.CENTER);
		this.panelPioche.add(this.lblMinuterie, BorderLayout.SOUTH );

		this.panelDefausse.add(this.spDefausse, BorderLayout.CENTER);

		this.panelGridManche.add(this.lblInfos);
		this.panelManches.add(this.spManches, BorderLayout.CENTER);

		this.panelInformation.add(this.panelPioche  );
		this.panelInformation.add(this.panelDefausse);
		this.panelInformation.add(this.panelManches );

		this.add(this.panelInformation, BorderLayout.WEST  );
		this.add(this.panelCentre,      BorderLayout.CENTER);

		/*---------------------------*/
		/* Activation                */
		/*---------------------------*/
		this.btnPiocher.addActionListener(this);
		this.btnModeDemo.addActionListener(this);
		this.btnOuvrirPlateau.addActionListener(this);
		this.actualiserAffichage();
	}

	/*-------------------*/
	/*     Methodes      */
	/*-------------------*/

	// Gere les boutons.
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.btnPiocher)
		{
			this.ctrl.passerTour();
		}
		else if (e.getSource() == this.btnModeDemo)
		{
			this.ctrl.changerModeDemo();
		}
		else if (e.getSource() == this.btnOuvrirPlateau)
		{
			this.ctrl.ouvrirChoixPlateau();
		}
	}

	// Met a jour l'IHM.
	public void actualiserAffichage()
	{
		Jeu jeu = this.ctrl.getJeu();
		if (jeu == null) {return;}

		Deck deck = jeu.getDeck();
		Carte carte = null;
		if (deck != null) {carte = deck.getCarteCourante();}

		this.mettreAJourCarte(this.lblCartePiochee, carte, "Fin", 140, 210);
		int foncees = 0;
		if (deck != null) {foncees = deck.getCartesFonceesRestantes();}
		this.lblMinuterie.setText("Cartes foncées restantes : " + foncees);

		this.actualiserDefausse(deck);
		this.actualiserProgression(jeu);

		this.lblMessage.setText(jeu.getMessage());
		if (jeu.getMessage() != null && jeu.getMessage().startsWith("Deplacement refuse"))
		{
			this.lblMessage.setForeground(new Color(190, 35, 35));
		}
		else
		{
			this.lblMessage.setForeground(Color.BLACK);
		}

		if (this.ctrl.estModeDemo())
		{
			this.btnModeDemo.setText("Mode démo : ON");
		}
		else
		{
			this.btnModeDemo.setText("Mode démo : OFF");
		}

		this.btnPiocher.setEnabled(!jeu.estPartieTerminee());
		this.panelPlateau.revalidate();
		this.panelPlateau.repaint();
	}

	// Met a jour la defausse.
	private void actualiserDefausse(Deck deck)
	{
		this.panelGridDefausse.removeAll();
		if (deck != null)
		{
			ArrayList<Carte> defausse = deck.getDefausse();
			for (int i = 0; i < defausse.size(); i++)
			{
				JLabel label = this.creerVisuelCarte("", Color.WHITE, Color.BLACK, 70, 105);
				this.mettreAJourCarte(label, defausse.get(i), "", 70, 105);
				this.panelGridDefausse.add(label);
			}
		}

		int nbCartes = 0;
		if (deck != null) {nbCartes = deck.getDefausse().size();}
		int nbLignes = (nbCartes + 2) / 3;
		if (nbLignes < 1) {nbLignes = 1;}

		int largeur = 3 * 70 + 2 * 10;
		int hauteur = nbLignes * 105 + (nbLignes - 1) * 10;
		Dimension taille = new Dimension(largeur, hauteur);
		this.panelGridDefausse.setPreferredSize(taille);
		this.panelGridDefausse.setMinimumSize(taille);

		this.panelGridDefausse.revalidate();
		this.panelGridDefausse.repaint();
	}

	// Met a jour les scores.
	private void actualiserProgression(Jeu jeu)
	{
		this.panelGridManche.removeAll();
		this.lblInfos = new JLabel(String.format("%40s" , "Informations  Zones | Atomes | Résultat"));
		this.lblInfos.setFont(new Font("Arial", Font.BOLD, 13));
		this.panelGridManche.add(this.lblInfos);

		ArrayList<Manche> manches = jeu.getManches();
		for (int i = 0; i < jeu.getNombreManches(); i++)
		{
			String texte = String.format("%40s" , "Manche " + (i + 1) + "         0   x   0      =    0");
			if (i < manches.size())
			{
				Manche manche = manches.get(i);
				texte = String.format("%40s" , "Manche " + (i + 1) + "         " + manche.getMaxAtomesZone() +
						"   x   " + manche.getNbZonesVisitees() + "      =    " + manche.getScore());
			}
			JLabel label = new JLabel(texte);
			label.setFont(new Font("Arial", Font.PLAIN, 15));
			label.setForeground(Color.BLACK);
			label.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
			label.setPreferredSize(new Dimension(300, 50));
			this.panelGridManche.add(label);
		}

		JLabel total = new JLabel(String.format("%40s", "Score total : " + jeu.calculerScoreTotal()));
		total.setFont(new Font("Arial", Font.BOLD, 15));
		this.panelGridManche.add(total);
		this.panelGridManche.revalidate();
		this.panelGridManche.repaint();
	}

	// Cree le bouton pioche.
	private JButton creerVisuelPioche()
	{
		JButton btn = new JButton("Piocher");
		btn.setPreferredSize(new Dimension(140, 210));
		btn.setOpaque(true);
		btn.setBackground(new Color(70, 130, 180));
		btn.setForeground(Color.WHITE);
		btn.setFont(new Font("Arial", Font.BOLD, 14));
		btn.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 2, true),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
		));

		File fichier = new File(DOSSIER_IMAGES_JEU + "/carte", "dos.png");
		if (fichier.exists())
		{
			try
			{
				Image image = ImageIO.read(fichier);
				btn.setText("");
				btn.setIcon(new ImageIcon(image.getScaledInstance(140, 210, Image.SCALE_SMOOTH)));
			}
			catch (IOException exception)
			{
				btn.setText("Piocher");
			}
		}
		return btn;
	}

	// Cree un visuel de carte.
	private JLabel creerVisuelCarte(String nom, Color bgColor, Color fgColor, int largeur, int hauteur)
	{
		JLabel lbl = new JLabel(nom, SwingConstants.CENTER);
		Dimension taille = new Dimension(largeur, hauteur);
		lbl.setPreferredSize(taille);
		lbl.setMinimumSize(taille);
		lbl.setMaximumSize(taille);
		lbl.setOpaque(true);
		lbl.setBackground(bgColor);
		lbl.setForeground(fgColor);
		lbl.setFont(new Font("Arial", Font.BOLD, 14));
		lbl.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(Color.BLACK, 2, true),
				BorderFactory.createEmptyBorder(5, 5, 5, 5)
		));
		return lbl;
	}

	// Affiche une carte.
	private void mettreAJourCarte(JLabel label, Carte carte, String texteVide, int largeur, int hauteur)
	{
		if (carte == null)
		{
			label.setIcon(null);
			label.setText(texteVide);
			return;
		}

		Image imageCarte = this.chargerImageCarte(carte);
		if (imageCarte != null)
		{
			label.setText("");
			label.setIcon(new ImageIcon(imageCarte.getScaledInstance(largeur, hauteur, Image.SCALE_SMOOTH)));
			return;
		}
		label.setIcon(null);
		label.setText(carte.getNomCarte());
	}

	// Charge une image de carte.
	private Image chargerImageCarte(Carte carte)
	{
		String nomType = carte.getNomCarte().toLowerCase();
		String nomFichier = nomType + "_" + carte.getVariante().name().toLowerCase() + ".png";
		File fichier = new File(DOSSIER_IMAGES_JEU + "/carte", nomFichier);
		if (fichier.exists())
		{
			try {return ImageIO.read(fichier);}
			catch (IOException exception) {return null;}
		}
		return null;
	}
}
