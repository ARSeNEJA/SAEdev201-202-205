package ihm;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import metier.enums.Couleur;
import metier.enums.TypeAtome;

public class PanelBarreEdition extends JPanel implements ActionListener
{
	/*------------------*/
	/*     Attributs    */
	/*------------------*/
	private FenetreParametres fenetreParametres;
	private JComboBox<String>    comboModeEdition;
	private JComboBox<TypeAtome> comboTypeAtome;
	private JComboBox<Couleur>   comboCouleurBase;
	private JButton boutonOuvrir;
	private JButton boutonEnregistrer;
	private JButton boutonCopie;

	/*------------------*/
	/*   Constructeur   */
	/*------------------*/
	public PanelBarreEdition(FenetreParametres fenetreParametres)
	{
		this.fenetreParametres = fenetreParametres;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		/*-------------------------*/
		/* Creation des composants */
		/*-------------------------*/
		this.comboModeEdition  = new JComboBox<>();
		this.comboTypeAtome    = new JComboBox<>();
		this.comboCouleurBase  = new JComboBox<>(Couleur.values());
		this.boutonOuvrir      = new JButton("Ouvrir");
		this.boutonEnregistrer = new JButton("Enregistrer le plateau");
		this.boutonCopie       = new JButton("Copie");

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
		this.add(new JLabel("Mode"));
		this.add(this.comboModeEdition);
		this.add(new JLabel("Types d'atomes"));
		this.add(this.comboTypeAtome);
		this.add(new JLabel("Bases"));
		this.add(this.comboCouleurBase);
		this.add(this.boutonOuvrir);
		this.add(this.boutonEnregistrer);
		this.add(this.boutonCopie);

		/*-------------------------*/
		/* Ajout des listeners     */
		/*-------------------------*/
		this.boutonOuvrir.addActionListener(this);
		this.boutonEnregistrer.addActionListener(this);
		this.boutonCopie.addActionListener(this);
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
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.boutonOuvrir)
		{
			this.fenetreParametres.traiterOuverture();
		}
		if (e.getSource() == this.boutonEnregistrer)
		{
			this.fenetreParametres.traiterEnregistrement();
		}
		if (e.getSource() == this.boutonCopie)
		{
			this.fenetreParametres.traiterEnregistrementCopie();
		}
		if (e.getSource() == this.comboTypeAtome)
		{
			this.selectionnerModeEdition("Placer un atome");
		}
		if (e.getSource() == this.comboCouleurBase)
		{
			this.selectionnerModeEdition("Placer une base");
		}
	}

	public void selectionnerModeEdition(String mode)
	{
		if (!mode.equals(this.getModeEditionChoisi()))
		{
			this.comboModeEdition.setSelectedItem(mode);
		}
	}
}
