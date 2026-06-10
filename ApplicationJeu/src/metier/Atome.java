package src.metier;

import java.util.ArrayList;

import src.metier.enums.Couleur;
import src.metier.enums.TypeAtome;

public class Atome
{
	private Case position;
	private TypeAtome type;
	private ArrayList<Atome> voisins;
	private boolean estBase;
	private Couleur couleurBase;

	public Atome(Case position, TypeAtome type)
	{
		this.position = position;
		this.type = type;
		this.voisins = new ArrayList<>();
		this.estBase = false;
		this.couleurBase = null;
	}

	public Case getPosition() {return this.position;}
	public TypeAtome getType() {return this.type;}
	public ArrayList<Atome> getVoisins() {return this.voisins;}
	public boolean estBase() {return this.estBase;}
	public Couleur getCouleurBase() {return this.couleurBase;}

	public void definirBase(Couleur couleur)
	{
		this.estBase = true;
		this.couleurBase = couleur;
	}

	public void viderVoisins() {this.voisins.clear();}

	public void ajouterVoisin(Atome voisin)
	{
		if (voisin == null || voisin == this) {return;}
		if (!this.contientVoisin(voisin)) {this.voisins.add(voisin);}
	}

	public boolean contientVoisin(Atome voisin)
	{
		for (int i = 0; i < this.voisins.size(); i++)
		{
			if (this.voisins.get(i) == voisin) {return true;}
		}
		return false;
	}
}
