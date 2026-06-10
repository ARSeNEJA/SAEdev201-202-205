package src.metier;

import src.metier.enums.TypeAtome;
import src.metier.enums.Variante;

public class Carte
{
	private TypeAtome typeAtome;
	private Variante variante;
	private boolean estJoker;

	public Carte(TypeAtome typeAtome, Variante variante)
	{
		this.typeAtome = typeAtome;
		this.variante = variante;
		this.estJoker = false;
	}

	public Carte(Variante variante)
	{
		this.typeAtome = null;
		this.variante = variante;
		this.estJoker = true;
	}

	public TypeAtome getTypeAtome() {return this.typeAtome;}
	public Variante getVariante() {return this.variante;}
	public boolean estJoker() {return this.estJoker;}
	public boolean estFoncee() {return this.variante == Variante.FONCEE;}

	public String getNomCarte()
	{
		if (this.estJoker) {return "Joker";}
		return this.typeAtome.name();
	}

	public String getLibelle()
	{
		return this.getNomCarte() + " " + this.variante.name().toLowerCase();
	}
}
