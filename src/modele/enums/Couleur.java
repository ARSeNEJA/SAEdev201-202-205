package modele.enums;

public enum Couleur
{
	ROUGE, VERT, BLEU, MARRON, JAUNE, VIOLET;

	public String getNom()
	{
		if (this == ROUGE)  { return "Rouge"; }
		if (this == VERT)   { return "Vert"; }
		if (this == BLEU)   { return "Bleu"; }
		if (this == MARRON) { return "Marron"; }
		if (this == JAUNE)  { return "Jaune"; }
		return "Violet";
	}
}
