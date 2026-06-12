package src.metier;

public class Case
{
	/*-------------------*/
	/*    Attributs      */
	/*-------------------*/
	private int colonne;
	private int ligne;

	/*-------------------*/
	/*   Constructeur    */
	/*-------------------*/
	// Cree une case.
	public Case(int colonne, int ligne)
	{
		this.colonne = colonne;
		this.ligne = ligne;
	}

	/*-------------------*/
	/*   Accesseurs      */
	/*-------------------*/
	// Renvoie la colonne.
	public int getColonne() {return this.colonne;}
	// Renvoie la ligne.
	public int getLigne()   {return this.ligne;}

	/*-------------------*/
	/*    Methodes       */
	/*-------------------*/
	// Compare les coordonnees.
	public boolean memesCoordonnees(int colonne, int ligne)
	{
		return this.colonne == colonne && this.ligne == ligne;
	}
}
