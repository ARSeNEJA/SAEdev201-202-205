package ihm;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import metier.Plateau;
import metier.enums.TypePioche;

public class PanelParametres extends JPanel
{
	private JTextField champLargeur;
	private JTextField champHauteur;
	private JTextField champTailleCase;
	private JComboBox<Integer> comboNombreZones;
	private JComboBox<Integer> comboZoneActive;
	private JComboBox<TypePioche> comboPioche;

	public PanelParametres(Plateau plateau)
	{
		super(new GridLayout(0, 1, 6, 6));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 4));

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
		this.comboNombreZones.addActionListener(e -> this.actualiserZonesActives());

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

		this.actualiserZonesActives();
	}

	public void setActionCommandParametres(String actionCommand)
	{
		this.champLargeur.setActionCommand(actionCommand);
		this.champHauteur.setActionCommand(actionCommand);
		this.champTailleCase.setActionCommand(actionCommand);
		this.comboNombreZones.setActionCommand(actionCommand);
		this.comboPioche.setActionCommand(actionCommand);
	}

	public void setActionCommandZoneActive(String actionCommand)
	{
		this.comboZoneActive.setActionCommand(actionCommand);
	}

	public void ajouterListeners(ActionListener actionListener)
	{
		this.champLargeur.addActionListener(actionListener);
		this.champHauteur.addActionListener(actionListener);
		this.champTailleCase.addActionListener(actionListener);
		this.comboNombreZones.addActionListener(actionListener);
		this.comboZoneActive.addActionListener(actionListener);
		this.comboPioche.addActionListener(actionListener);
	}

	private void actualiserZonesActives()
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

	public ParametresPlateau getParametresPlateau()
	{
		return new ParametresPlateau(
				this.lireEntier(this.champLargeur.getText()),
				this.lireEntier(this.champHauteur.getText()),
				this.lireEntier(this.champTailleCase.getText()),
				this.getNombreZonesChoisi(),
				(TypePioche) this.comboPioche.getSelectedItem());
	}

	private int getNombreZonesChoisi()
	{
		Integer valeur = (Integer) this.comboNombreZones.getSelectedItem();
		if (valeur == null)
		{
			return 1;
		}
		return valeur.intValue();
	}

	public int getZoneActive()
	{
		Integer valeur = (Integer) this.comboZoneActive.getSelectedItem();
		if (valeur == null)
		{
			return 1;
		}
		return valeur.intValue();
	}

	private int lireEntier(String texte)
	{
		try
		{
			return Integer.parseInt(texte.trim());
		}
		catch (NumberFormatException exception)
		{
			return -1;
		}
	}
}
