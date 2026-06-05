package src;

import src.ihm.FrameJeu;
import src.ihm.FrameFermeture;

public class Controleur
{
	private FrameJeu frameJeu;
	private FrameFermeture frameFermeture;

	public Controleur()
	{
		this.frameJeu = new FrameJeu(this);
	}


	public void messageFermeture()
	{
		this.frameFermeture = new FrameFermeture(this);
	}


	public boolean frameFermetureOuverte()
	{
		return frameFermeture != null && frameFermeture.isVisible();
	}


	public void fermerJeu()
	{
		this.frameJeu.dispose();
		this.frameFermeture.dispose();
	}


	public void fermerFermerFenetre()
	{
		this.frameFermeture.dispose();
	}


	public static void main(String[] ar)
	{
		new Controleur();
	}

}
