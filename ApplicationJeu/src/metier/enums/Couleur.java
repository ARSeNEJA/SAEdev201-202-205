package src.metier.enums;

public enum Couleur
{
	ROUGE,VERT,BLEU,MARRON;

	/*-------------------*/
	/*    Methodes       */
	/*-------------------*/
	// Renvoie le nom.
	public String getNom()
	{
		if (this == ROUGE) {return "Rouge";}
		if (this == VERT)  {return "Vert";}
		if (this == BLEU)  {return "Bleu";}
		return "Marron";
	}
}
