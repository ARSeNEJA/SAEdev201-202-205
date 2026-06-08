package metier.enums;

public enum TypeAtome
{
	H,O,C,S;

	// Renvoie le nom du fichier image associe au type d'atome.
	public String getNomImage()
	{
		return this.name().toLowerCase() + ".png";
	}
}
