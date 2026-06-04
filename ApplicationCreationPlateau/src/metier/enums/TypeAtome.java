package metier.enums;

public enum TypeAtome
{
	H,O,C,N,JOKER;

	public String getNomImage()
	{
		if (this == H) {return "hydrogene.png";}
		if (this == O) {return "oxygene.png";}
		if (this == C) {return "carbone.png";}
		if (this == N) {return "azote.png";}
		return "joker.png";
	}
}
