package ihm;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelAutres extends JPanel implements ActionListener
{
	/*------------------*/
	/*     Attributs    */
	/*------------------*/
	private Fenetre fenetreParametres;
	private JButton boutonOuvrir;
	private JButton boutonEnregistrer;
	private JButton boutonCopie;

	/*------------------*/
	/*   Constructeur   */
	/*------------------*/
	public PanelAutres(Fenetre fenetreParametres)
	{
		this.fenetreParametres = fenetreParametres;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createTitledBorder("Autres"));

		/*-------------------------*/
		/* Creation des composants */
		/*-------------------------*/
		this.boutonOuvrir      = new JButton("Ouvrir");
		this.boutonEnregistrer = new JButton("Enregistrer le plateau");
		this.boutonCopie       = new JButton("Copie");

		/*----------------------*/
		/* Ajout des composants */
		/*----------------------*/
		this.ajouterBouton(this.boutonOuvrir);
		this.ajouterBouton(this.boutonEnregistrer);
		this.ajouterBouton(this.boutonCopie);

		/*-------------------------*/
		/* Ajout des listeners     */
		/*-------------------------*/
		this.boutonOuvrir.addActionListener(this);
		this.boutonEnregistrer.addActionListener(this);
		this.boutonCopie.addActionListener(this);
	}

	/*----------*/
	/* Methodes */
	/*----------*/
	// Redirige les boutons fichier vers la fenetre principale.
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
	}

	// Aligne les boutons dans la colonne du panneau.
	private void ajouterBouton(JButton bouton)
	{
		JPanel enveloppe = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
		enveloppe.setOpaque(false);
		enveloppe.add(bouton);
		this.add(enveloppe);
	}
}
