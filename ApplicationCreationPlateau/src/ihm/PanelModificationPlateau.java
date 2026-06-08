package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.enums.Couleur;
import metier.enums.TypeAtome;

public class PanelModificationPlateau extends JPanel implements ActionListener
{
	/*------------------*/
	/*     Attributs    */
	/*------------------*/
	private JComboBox<String>    comboModeEdition;
	private JComboBox<TypeAtome> comboTypeAtome;
	private JComboBox<Couleur>   comboCouleurBase;

	/*------------------*/
	/*   Constructeur   */
	/*------------------*/
	public PanelModificationPlateau()
	{
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Modification plateau"));

		/*-------------------------*/
		/* Creation des composants */
		/*-------------------------*/
		this.comboModeEdition = new JComboBox<>();
		this.comboTypeAtome   = new JComboBox<>();
		this.comboCouleurBase = new JComboBox<>(Couleur.values());

		this.comboModeEdition.addItem("Dessiner une zone");
		this.comboModeEdition.addItem("Placer un atome");
		this.comboModeEdition.addItem("Placer une base");
		this.comboModeEdition.addItem("Supprimer");

		TypeAtome[] types = TypeAtome.values();
		for (int i = 0; i < types.length; i++)
		{
			this.comboTypeAtome.addItem(types[i]);
		}

		/*----------------------*/
		/* Ajout des composants */
		/*----------------------*/
		this.ajouterLigne("Mode", this.comboModeEdition);
		this.ajouterLigne("Types d'atomes", this.comboTypeAtome);
		this.ajouterLigne("Bases", this.comboCouleurBase);

		/*-------------------------*/
		/* Ajout des listeners     */
		/*-------------------------*/
		this.comboModeEdition.addActionListener(this);
		this.comboTypeAtome.addActionListener(this);
		this.comboCouleurBase.addActionListener(this);
	}

	/*------------------*/
	/*     Getters      */
	/*------------------*/
	public TypeAtome getTypeAtomeChoisi()
	{
		TypeAtome type = (TypeAtome) this.comboTypeAtome.getSelectedItem();
		if (type == null) {return TypeAtome.H;}
		return type;
	}

	public Couleur getCouleurBaseChoisie()
	{
		Couleur couleur = (Couleur) this.comboCouleurBase.getSelectedItem();
		if (couleur == null) {return Couleur.ROUGE;}
		return couleur;
	}

	public String getModeEditionChoisi()
	{
		return (String) this.comboModeEdition.getSelectedItem();
	}

	/*----------*/
	/* Methodes */
	/*----------*/
	// Oriente le mode selon le choix d'atome ou de base.
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.comboTypeAtome)
		{
			this.selectionnerModeEdition("Placer un atome");
		}
		if (e.getSource() == this.comboCouleurBase)
		{
			this.selectionnerModeEdition("Placer une base");
		}
	}

	// Force le mode associe au choix de l'utilisateur.
	public void selectionnerModeEdition(String mode)
	{
		if (!mode.equals(this.getModeEditionChoisi()))
		{
			this.comboModeEdition.setSelectedItem(mode);
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
}
