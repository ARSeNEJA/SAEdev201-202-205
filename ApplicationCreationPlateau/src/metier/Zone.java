package metier;

import java.util.ArrayList;

public class Zone
{
	/*-------------------- */
	/*      Attributs      */
	/*-------------------- */
	private int id;
	private ArrayList<Case> cases;

	/*---------------------*/
	/*     Constructeur    */
	/*---------------------*/
	public Zone(int id)
	{
		this.id = id;
		this.cases = new ArrayList<Case>();
	}

	/*---------------------*/
	/*        Getters      */
	/*---------------------*/
	public int getId()
	{
		return this.id;
	}

	public ArrayList<Case> getCases()
	{
		return this.cases;
	}

	/*-------------------- */
	/*       Méthodes      */
	/*-------------------- */
	public boolean contientCase(int colonne, int ligne)
	{
		for (int i = 0; i < this.cases.size(); i++)
		{
			if (this.cases.get(i).memesCoordonnees(colonne, ligne))
			{
				return true;
			}
		}
		return false;
	}

	public void ajouterCase(int colonne, int ligne)
	{
		if (!this.contientCase(colonne, ligne))
		{
			this.cases.add(new Case(colonne, ligne));
		}
	}

	public void supprimerCase(int colonne, int ligne)
	{
		int i = 0;
		while (i < this.cases.size())
		{
			if (this.cases.get(i).memesCoordonnees(colonne, ligne))
			{
				this.cases.remove(i);
			}
			else
			{
				i++;
			}
		}
	}

	public void supprimerCasesHorsPlateau(int largeur, int hauteur)
	{
		int i = 0;
		while (i < this.cases.size())
		{
			Case position = this.cases.get(i);
			if (position.getColonne() < 0 || position.getLigne() < 0 ||
				position.getColonne() >= largeur || position.getLigne() >= hauteur)
			{
				this.cases.remove(i);
			}
			else
			{
				i++;
			}
		}
	}
}
