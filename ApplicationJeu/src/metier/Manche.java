package src.metier;

import java.util.ArrayList;

import src.metier.enums.Couleur;

public class Manche
{
	private Couleur couleur;
	private Atome base;
	private ArrayList<Atome> chaine;
	private int score;
	private int maxAtomesZone;
	private int nbZonesVisitees;

	// Cree une manche.
	public Manche(Atome base)
	{
		this.base = base;
		this.couleur = base.getCouleurBase();
		this.chaine = new ArrayList<Atome>();
		this.chaine.add(base);
		this.score = 0;
		this.maxAtomesZone = 0;
		this.nbZonesVisitees = 0;
	}

	// Renvoie la couleur.
	public Couleur getCouleur() {return this.couleur;}
	// Renvoie la base.
	public Atome getBase() {return this.base;}
	// Renvoie la chaine.
	public ArrayList<Atome> getChaine() {return this.chaine;}
	// Renvoie le score.
	public int getScore() {return this.score;}
	// Renvoie le maximum.
	public int getMaxAtomesZone() {return this.maxAtomesZone;}
	// Renvoie les zones visitees.
	public int getNbZonesVisitees() {return this.nbZonesVisitees;}

	// Calcule le score.
	public void setScore(int maxAtomesZone, int nbZonesVisitees)
	{
		this.maxAtomesZone = maxAtomesZone;
		this.nbZonesVisitees = nbZonesVisitees;
		this.score = maxAtomesZone * nbZonesVisitees;
	}

	// Renvoie le debut.
	public Atome getExtremiteA()
	{
		if (this.chaine.isEmpty()) {return null;}
		return this.chaine.get(0);
	}

	// Renvoie la fin.
	public Atome getExtremiteB()
	{
		if (this.chaine.isEmpty()) {return null;}
		return this.chaine.get(this.chaine.size() - 1);
	}

	// Teste un atome.
	public boolean contientAtome(Atome atome)
	{
		for (int i = 0; i < this.chaine.size(); i++)
		{
			if (this.chaine.get(i) == atome) {return true;}
		}
		return false;
	}

	// Ajoute depuis un bout.
	public boolean ajouterDepuisExtremite(Atome atome)
	{
		Atome a = this.getExtremiteA();
		Atome b = this.getExtremiteB();
		if (a != null && a.contientVoisin(atome))
		{
			this.chaine.add(0, atome);
			return true;
		}
		if (b != null && b.contientVoisin(atome))
		{
			this.chaine.add(atome);
			return true;
		}
		return false;
	}
}
