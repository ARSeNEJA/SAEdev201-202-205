package metier;

public class Case
{
	/*--------------------*/
	/*      Attributs     */
	/*--------------------*/
	private int colonne;
	private int ligne;

	/*---------------------*/
	/*     Constructeur    */
	/*---------------------*/
	// Memorise les coordonnees d'une case du plateau.
	public Case(int colonne, int ligne)
	{
		this.colonne = colonne;
		this.ligne = ligne;
	}

	/*---------------------*/
	/*        Getters      */
	/*---------------------*/
	// Renvoie la colonne de la case.
	public int getColonne() {return this.colonne;}
	// Renvoie la ligne de la case.
	public int getLigne()   {return this.ligne;}

	/*-------------------- */
	/*       Méthode       */
	/*-------------------- */
	// Compare les coordonnees de cette case avec celles donnees.
	public boolean memesCoordonnees(int colonne, int ligne)
	{
		return this.colonne == colonne && this.ligne == ligne;
	}
}
