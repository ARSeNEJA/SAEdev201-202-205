package src;

import src.ihm.FrameJeu;
import src.ihm.FrameFermeture;

public class ControleurJeu
{
	private FrameJeu frameJeu;
	private FrameFermeture frameFermeture;

	// Cree la fenetre principale du jeu.
	public Controleur()
	{
		this.frameJeu = new FrameJeu(this);
	}


	// Affiche la fenetre de confirmation de fermeture.
	public void messageFermeture()
	{
		this.frameFermeture = new FrameFermeture(this);
	}


	// Indique si la fenetre de confirmation est deja ouverte.
	public boolean frameFermetureOuverte()
	{
		return frameFermeture != null && frameFermeture.isVisible();
	}


	// Ferme la fenetre du jeu et la fenetre de confirmation.
	public void fermerJeu()
	{
		this.frameJeu.dispose();
		this.frameFermeture.dispose();
	}


	// Ferme uniquement la fenetre de confirmation.
	public void fermerFermerFenetre()
	{
		this.frameFermeture.dispose();
	}


	// Lance l'application de jeu.
	public static void main(String[] ar)
	{
		new ControleurJeu();
	}

}
