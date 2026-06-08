package metier;

import java.util.ArrayList;

import metier.enums.Couleur;

public class Zone
{
	/*-------------------- */
	/*      Attributs      */
	/*-------------------- */
	private int id;
	private Couleur couleur;
	private ArrayList<Case> cases;

	/*---------------------*/
	/*     Constructeur    */
	/*---------------------*/
	public Zone(int id)
	{
		this.id = id;
		this.couleur = null;
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

	public Couleur getCouleur()
	{
		return this.couleur;
	}

	public void setCouleur(Couleur couleur)
	{
		this.couleur = couleur;
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

	public boolean peutAjouterCaseVoisine(int colonne, int ligne)
	{
		if (this.cases.isEmpty() || this.contientCase(colonne, ligne))
		{
			return true;
		}
		for (int i = 0; i < this.cases.size(); i++)
		{
			Case caseZone = this.cases.get(i);
			int ecartColonne = Math.abs(caseZone.getColonne() - colonne);
			int ecartLigne = Math.abs(caseZone.getLigne() - ligne);
			if (ecartColonne + ecartLigne == 1)
			{
				return true;
			}
		}
		return false;
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
