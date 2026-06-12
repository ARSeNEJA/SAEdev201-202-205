package src.metier;

import src.metier.enums.TypeAtome;
import src.metier.enums.Variante;

public class Carte
{
	/*-------------------*/
	/*    Attributs      */
	/*-------------------*/
	private TypeAtome typeAtome;
	private Variante variante;
	private boolean estJoker;

	/*-------------------*/
	/*   Constructeurs   */
	/*-------------------*/
	// Cree une carte normale.
	public Carte(TypeAtome typeAtome, Variante variante)
	{
		this.typeAtome = typeAtome;
		this.variante = variante;
		this.estJoker = false;
	}

	// Cree une carte joker.
	public Carte(Variante variante)
	{
		this.typeAtome = null;
		this.variante = variante;
		this.estJoker = true;
	}

	/*-------------------*/
	/*   Accesseurs      */
	/*-------------------*/
	// Renvoie le type.
	public TypeAtome getTypeAtome() {return this.typeAtome;}
	// Renvoie la variante.
	public Variante getVariante() {return this.variante;}
	// Indique si joker.
	public boolean estJoker() {return this.estJoker;}
	// Indique si foncee.
	public boolean estFoncee() {return this.variante == Variante.FONCEE;}

	/*-------------------*/
	/*    Methodes       */
	/*-------------------*/
	// Renvoie le nom.
	public String getNomCarte()
	{
		if (this.estJoker) {return "Joker";}
		return this.typeAtome.name();
	}

	// Renvoie le libelle.
	public String getLibelle()
	{
		return this.getNomCarte() + " " + this.variante.name().toLowerCase();
	}
}
