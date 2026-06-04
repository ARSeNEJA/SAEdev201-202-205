package metier.enums;

public enum Couleur
{
	ROUGE,VERT,BLEU,MARRON;

	public String getNom()
	{
		if (this == ROUGE){return "Rouge";}
		if (this == VERT){return "Vert";}
		if (this == BLEU){return "Bleu";}
		return "Marron";
	}
}
