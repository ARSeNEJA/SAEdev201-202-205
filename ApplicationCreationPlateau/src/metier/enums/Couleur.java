package metier.enums;

public enum Couleur
{
	ROUGE,VERT,BLEU,MARRON;

	// Renvoie le nom lisible de la couleur.
	public String getNom()
	{
		if (this == ROUGE){return "Rouge";}
		if (this == VERT) {return "Vert";}
		if (this == BLEU) {return "Bleu";}
		return "Marron";
	}
}
