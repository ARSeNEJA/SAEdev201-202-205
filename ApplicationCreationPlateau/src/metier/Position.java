package metier;

public class Position
{
	/*--------------------*/
	/*      Attributs     */
	/*--------------------*/
	private int colonne;
	private int ligne;

	/*---------------------*/
	/*     Constructeur    */
	/*---------------------*/
	public Position(int colonne, int ligne)
	{
		this.colonne = colonne;
		this.ligne = ligne;
	}

	/*---------------------*/
	/*        Getters      */
	/*---------------------*/
	public int getColonne() {return this.colonne;}
	public int getLigne()   {return this.ligne;}

	/*-------------------- */
	/*       Méthode       */
	/*-------------------- */
	public boolean memesCoordonnees(int colonne, int ligne)
	{
		return this.colonne == colonne && this.ligne == ligne;
	}
}
