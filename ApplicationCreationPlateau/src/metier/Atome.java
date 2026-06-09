package metier;

import java.util.ArrayList;

import metier.enums.Couleur;
import metier.enums.TypeAtome;

public class Atome
{
	/*-------------------- */
	/*      Attributs      */
	/*-------------------- */
	private Case position;
	private TypeAtome type;
	private ArrayList<Atome> voisins;
	private boolean estBase;
	private Couleur couleurBase;

	/*-------------------- */
	/*    Constructeur     */
	/*-------------------- */
	// Cree un atome avec sa position, son type et aucune liaison.
	public Atome(Case position, TypeAtome type)
	{
		this.position = position;
		this.type = type;
		this.voisins = new ArrayList<>();
		this.estBase = false;
		this.couleurBase = null;
	}

	/*---------------------*/
	/*       Methodes      */
	/*---------------------*/
	// Renvoie la position de l'atome sur le plateau.
	public Case getPosition()           {return this.position;}
	// Renvoie le type chimique de l'atome.
	public TypeAtome getType()          {return this.type;}
	// Renvoie la liste des atomes relies a cet atome.
	public ArrayList<Atome> getVoisins(){return this.voisins;}
	// Indique si cet atome sert de base.
	public boolean estBase()            {return this.estBase;}
	// Renvoie la couleur de base associee a l'atome.
	public Couleur getCouleurBase()     {return this.couleurBase;}

	// Transforme l'atome en base si aucune base n'est deja definie.
	public boolean definirBase(Couleur couleur)
	{
		if (this.estBase || couleur == null)
		{
			return false;
		}
		this.estBase = true;
		this.couleurBase = couleur;
		return true;
	}

	// Retire toutes les liaisons avant un nouveau calcul des voisins.
	public void viderVoisins(){this.voisins.clear();}

	// Ajoute un voisin valide sans creer de doublon.
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

	// Verifie si l'atome donne est deja dans la liste des voisins.
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
