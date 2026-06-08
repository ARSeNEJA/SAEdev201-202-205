package ihm;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metier.Plateau;
import metier.enums.TypePioche;


public class PanelParametres extends JPanel implements ActionListener
{
	private FenetreParametres fenetreParametres;
	private Plateau plateau;
	private PanelPlateau panelEditeur;
	private JTextField champLargeur;
	private JTextField champHauteur;
	private JTextField champTailleCase;
	private JComboBox<Integer> comboNombreZones;
	private JComboBox<Integer> comboZoneActive;
	private JComboBox<TypePioche> comboPioche;
	private JButton boutonAppliquerParametres;

	public PanelParametres(Plateau plateau, PanelPlateau panelEditeur, FenetreParametres fenetreParametres)
	{
		this.fenetreParametres = fenetreParametres;
		this.plateau = plateau;
		this.panelEditeur = panelEditeur;
		this.setLayout(new GridLayout(0, 1, 6, 6));

		/*-------------------------*/
		/* Creation des composants */
		/*-------------------------*/
		this.champLargeur = new JTextField(String.valueOf(plateau.getLargeur()), 6);
		this.champHauteur = new JTextField(String.valueOf(plateau.getHauteur()), 6);
		this.champTailleCase = new JTextField(String.valueOf(plateau.getTailleCase()), 6);

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
		this.add(new JLabel("Largeur"));
		this.add(this.champLargeur);
		this.add(new JLabel("Hauteur"));
		this.add(this.champHauteur);
		this.add(new JLabel("Taille d'une case"));
		this.add(this.champTailleCase);
		this.add(new JLabel("Nombre de zones"));
		this.add(this.comboNombreZones);
		this.add(new JLabel("Zone coloree"));
		this.add(this.comboZoneActive);
		this.add(new JLabel("Type de pioche"));
		this.add(this.comboPioche);
		this.add(this.boutonAppliquerParametres);

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

	public void chargerPlateau(Plateau plateau, int nombreZones)
	{
		this.plateau = plateau;
		this.champLargeur.setText(String.valueOf(plateau.getLargeur()));
		this.champHauteur.setText(String.valueOf(plateau.getHauteur()));
		this.champTailleCase.setText(String.valueOf(plateau.getTailleCase()));
		this.comboNombreZones.setSelectedItem(nombreZones);
		this.comboPioche.setSelectedItem(plateau.getTypePioche());
		this.remplirZonesActives();
	}

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


}
