package src.metier;

import java.util.ArrayList;

import src.metier.enums.Couleur;
import src.metier.enums.TypeAtome;

public class Atome
{
	/*-------------------*/
	/*    Attributs      */
	/*-------------------*/
	private Case position;
	private TypeAtome type;
	private ArrayList<Atome> voisins;
	private boolean estBase;
	private Couleur couleurBase;

	/*-------------------*/
	/*   Constructeur    */
	/*-------------------*/
	// Cree un atome.
	public Atome(Case position, TypeAtome type)
	{
		this.position = position;
		this.type = type;
		this.voisins = new ArrayList<Atome>();
		this.estBase = false;
		this.couleurBase = null;
	}

	/*-------------------*/
	/*   Accesseurs      */
	/*-------------------*/
	// Renvoie la position.
	public Case getPosition() {return this.position;}
	// Renvoie le type.
	public TypeAtome getType() {return this.type;}
	// Renvoie les voisins.
	public ArrayList<Atome> getVoisins() {return this.voisins;}
	// Indique si base.
	public boolean estBase() {return this.estBase;}
	// Renvoie la couleur.
	public Couleur getCouleurBase() {return this.couleurBase;}

	/*-------------------*/
	/*    Methodes       */
	/*-------------------*/
	// Definit une base.
	public void definirBase(Couleur couleur)
	{
		this.estBase = true;
		this.couleurBase = couleur;
	}

	// Vide les voisins.
	public void viderVoisins() {this.voisins.clear();}

	// Ajoute un voisin.
	public void ajouterVoisin(Atome voisin)
	{
		if (voisin == null || voisin == this) {return;}
		if (!this.contientVoisin(voisin)) {this.voisins.add(voisin);}
	}

	// Teste un voisin.
	public boolean contientVoisin(Atome voisin)
	{
		for (int i = 0; i < this.voisins.size(); i++)
		{
			if (this.voisins.get(i) == voisin) {return true;}
		}
		return false;
	}
}
