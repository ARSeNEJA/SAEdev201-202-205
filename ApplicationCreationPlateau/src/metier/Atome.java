package metier;

import java.util.ArrayList;

import metier.enums.TypeAtome;

public class Atome
{
	/*-------------------- */
	/*      Attributs      */
	/*-------------------- */
	private Position position;
	private TypeAtome type;
	private ArrayList<Atome> voisins;

	/*-------------------- */
	/*    Constructeur     */
	/*-------------------- */
	public Atome(Position position, TypeAtome type)
	{
		this.position = position;
		this.type = type;
		this.voisins = new ArrayList<>();
	}

	/*---------------------*/
	/*       Methodes      */
	/*---------------------*/
	public Position         getPosition()      {return this.position;}
	public TypeAtome        getType()          {return this.type;}
	public ArrayList<Atome> getVoisins()       {return this.voisins;}

	public void viderVoisins(){this.voisins.clear();}

	public void ajouterVoisin(Atome voisin)
	{
		if (voisin == null || voisin == this)
		{
			return;
		}
		if (!this.contientVoisin(voisin))
		{
			this.voisins.add(voisin);
		}
	}

	public boolean contientVoisin(Atome voisin)
	{
		for (int i = 0; i < this.voisins.size(); i++)
		{
			if (this.voisins.get(i) == voisin)
			{
				return true;
			}
		}
		return false;
	}
}
