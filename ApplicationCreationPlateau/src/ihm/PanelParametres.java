package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metier.Plateau;
import metier.enums.TypePioche;


public class PanelParametres extends JPanel implements ActionListener
{
	private Fenetre fenetreParametres;
	private JTextField champLargeur;
	private JTextField champHauteur;
	private JTextField champTailleCase;
	private JComboBox<Integer> comboNombreZones;
	private JComboBox<Integer> comboZoneActive;
	private JComboBox<TypePioche> comboPioche;
	private JButton boutonAppliquerParametres;

	public PanelParametres(Plateau plateau, Fenetre fenetreParametres)
	{
		this.fenetreParametres = fenetreParametres;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Parametres plateau"));

		/*-------------------------*/
		/* Creation des composants */
		/*-------------------------*/
		this.champLargeur = new JTextField(String.valueOf(plateau.getLargeur()), 3);
		this.champHauteur = new JTextField(String.valueOf(plateau.getHauteur()), 3);
		this.champTailleCase = new JTextField(String.valueOf(plateau.getTailleCase()), 3);

		this.comboNombreZones = new JComboBox<>();
		for (int i = 1; i <= 20; i++)
		{
			this.comboNombreZones.addItem(i);
		}
		this.comboNombreZones.setSelectedItem(4);

		this.comboZoneActive = new JComboBox<>();
		this.comboPioche = new JComboBox<>(TypePioche.values());
		this.boutonAppliquerParametres = new JButton("Appliquer les parametres");

		this.comboNombreZones.addActionListener(this);
		this.champLargeur.addActionListener(this);
		this.champHauteur.addActionListener(this);
		this.champTailleCase.addActionListener(this);
		this.comboZoneActive.addActionListener(this);
		this.comboPioche.addActionListener(this);
		this.boutonAppliquerParametres.addActionListener(this);

		/*----------------------*/
		/* Ajout des composants */
		/*----------------------*/
		this.ajouterLigne("Largeur", this.champLargeur);
		this.ajouterLigne("Hauteur", this.champHauteur);
		this.ajouterLigne("Taille d'une case", this.champTailleCase);
		this.ajouterLigne("Nombre de zones", this.comboNombreZones);
		this.ajouterLigne("Zone coloree", this.comboZoneActive);
		this.ajouterLigne("Type de pioche", this.comboPioche);
		this.ajouterBouton(this.boutonAppliquerParametres);

		this.remplirZonesActives();
	}

	/*------------------*/
	/*     Getters      */
	/*------------------*/
	public int getLargeurChoisie() {return Integer.parseInt(this.champLargeur.getText().trim());}
	public int getHauteurChoisie() {return Integer.parseInt(this.champHauteur.getText().trim());}
	public int getTailleCaseChoisie() {return Integer.parseInt(this.champTailleCase.getText().trim());}
	public TypePioche getTypePiocheChoisi() {return (TypePioche) this.comboPioche.getSelectedItem();}

	public int getNombreZonesChoisi()
	{
		Integer valeur = (Integer) this.comboNombreZones.getSelectedItem();
		if (valeur == null)
		{
			return 1;
		}
		return valeur;
	}

	public int getZoneActive()
	{
		Integer valeur = (Integer) this.comboZoneActive.getSelectedItem();
		if (valeur == null)
		{
			return 1;
		}
		return valeur;
	}

	/*------------------*/
	/*   Methode        */
	/*------------------*/
	
	// Applique les actions utilisateur depuis les champs de parametres.
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.comboNombreZones)
		{
			this.remplirZonesActives();
		}
		if (e.getSource() == this.boutonAppliquerParametres)
		{
			this.fenetreParametres.appliquerParametresPlateau();
		}
	}

	// Recharge les champs quand un plateau est ouvert depuis un fichier.
	public void chargerPlateau(Plateau plateau, int nombreZones)
	{
		this.champLargeur.setText(String.valueOf(plateau.getLargeur()));
		this.champHauteur.setText(String.valueOf(plateau.getHauteur()));
		this.champTailleCase.setText(String.valueOf(plateau.getTailleCase()));
		this.comboNombreZones.setSelectedItem(nombreZones);
		this.comboPioche.setSelectedItem(plateau.getTypePioche());
		this.remplirZonesActives();
	}

	// Reconstruit la liste des zones disponibles selon le nombre choisi.
	private void remplirZonesActives()
	{
		int nombreZones = this.getNombreZonesChoisi();
		int zoneActive = this.getZoneActive();

		this.comboZoneActive.removeAllItems();
		for (int i = 1; i <= nombreZones; i++)
		{
			this.comboZoneActive.addItem(i);
		}
		if (zoneActive >= 1 && zoneActive <= nombreZones)
		{
			this.comboZoneActive.setSelectedItem(zoneActive);
		}
		else if (this.comboZoneActive.getItemCount() > 0)
		{
			this.comboZoneActive.setSelectedIndex(0);
		}
	}

	// Ajoute une ligne compacte avec le libelle a gauche et le champ a droite.
	private void ajouterLigne(String libelle, java.awt.Component champ)
	{
		JPanel ligne = new JPanel(new BorderLayout(6, 0));
		ligne.setOpaque(false);

		JLabel label = new JLabel(libelle);
		label.setPreferredSize(new Dimension(110, 24));
		ligne.add(label, BorderLayout.WEST);
		ligne.add(champ, BorderLayout.CENTER);

		JPanel enveloppe = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
		enveloppe.setOpaque(false);
		enveloppe.add(ligne);
		this.add(enveloppe);
	}

	// Aligne le bouton des parametres avec les autres lignes.
	private void ajouterBouton(JButton bouton)
	{
		JPanel enveloppe = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
		enveloppe.setOpaque(false);
		enveloppe.add(bouton);
		this.add(enveloppe);
	}


}
