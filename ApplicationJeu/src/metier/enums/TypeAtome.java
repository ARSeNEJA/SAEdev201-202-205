package src.metier.enums;

public enum TypeAtome
{
	H,O,C,S;

	/*-------------------*/
	/*    Methodes       */
	/*-------------------*/
	// Renvoie l image.
	public String getNomImage()
	{
		return this.name().toLowerCase() + ".png";
	}
}
