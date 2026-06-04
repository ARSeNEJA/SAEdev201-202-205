package src.controleur;

import src.vue.FrameJeu;

public class Controleur
{
	private FrameJeu frameJeu;

	public Controleur()
	{
		this.frameJeu = new FrameJeu(this);
	}


		public static void main(String[] ar)
	{
		new Controleur();
	}
}
