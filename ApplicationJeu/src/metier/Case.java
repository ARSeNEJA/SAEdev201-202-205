package src.metier;

public class Case
{
	private int colonne;
	private int ligne;

	public Case(int colonne, int ligne)
	{
		this.colonne = colonne;
		this.ligne = ligne;
	}

	public int getColonne() {return this.colonne;}
	public int getLigne()   {return this.ligne;}

	public boolean memesCoordonnees(int colonne, int ligne)
	{
		return this.colonne == colonne && this.ligne == ligne;
	}
}
