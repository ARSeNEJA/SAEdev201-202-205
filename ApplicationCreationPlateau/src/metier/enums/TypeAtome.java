package metier.enums;

public enum TypeAtome
{
	H,O,C,S;

	public String getNomImage()
	{
		return this.name().toLowerCase() + ".png";
	}
}
