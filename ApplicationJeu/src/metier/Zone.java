package src.metier;

import java.util.ArrayList;

public class Zone
{
	/*-------------------*/
	/*    Attributs      */
	/*-------------------*/
	private int id;
	private ArrayList<Case> cases;

	/*-------------------*/
	/*   Constructeur    */
	/*-------------------*/
	// Cree une zone.
	public Zone(int id)
	{
		this.id = id;
		this.cases = new ArrayList<Case>();
	}

	/*-------------------*/
	/*   Accesseurs      */
	/*-------------------*/
	// Renvoie l'id.
	public int getId() {return this.id;}
	// Renvoie les cases.
	public ArrayList<Case> getCases() {return this.cases;}

	/*-------------------*/
	/*    Methodes       */
	/*-------------------*/
	// Teste une case.
	public boolean contientCase(int colonne, int ligne)
	{
		for (int i = 0; i < this.cases.size(); i++)
		{
			if (this.cases.get(i).memesCoordonnees(colonne, ligne)) {return true;}
		}
		return false;
	}

	// Ajoute une case.
	public void ajouterCase(int colonne, int ligne)
	{
		if (!this.contientCase(colonne, ligne))
		{
			this.cases.add(new Case(colonne, ligne));
		}
	}
}
