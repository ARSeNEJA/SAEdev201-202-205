package src.metier;

import java.util.ArrayList;
import java.util.Random;

import src.metier.enums.TypeAtome;
import src.metier.enums.Variante;

public class Deck
{
	private ArrayList<Carte> pioche;
	private ArrayList<Carte> defausse;
	private Carte carteCourante;
	private int cartesFonceesRestantes;

	public Deck(ArrayList<TypeAtome> types)
	{
		this.pioche = new ArrayList<>();
		this.defausse = new ArrayList<>();
		this.cartesFonceesRestantes = 0;
		this.generer(types);
		this.melanger();
		this.piocherSuivante();
	}

	public Carte getCarteCourante() {return this.carteCourante;}
	public ArrayList<Carte> getDefausse() {return this.defausse;}
	public int getCartesFonceesRestantes() {return this.cartesFonceesRestantes;}
	public int getTaillePioche() {return this.pioche.size();}

	private void generer(ArrayList<TypeAtome> types)
	{
		for (int i = 0; i < types.size(); i++)
		{
			this.ajouterPaire(types.get(i));
		}
		this.ajouterPaireJoker();
	}

	private void ajouterPaire(TypeAtome type)
	{
		this.pioche.add(new Carte(type, Variante.CLAIRE));
		this.pioche.add(new Carte(type, Variante.FONCEE));
		this.cartesFonceesRestantes++;
	}

	private void ajouterPaireJoker()
	{
		this.pioche.add(new Carte(Variante.CLAIRE));
		this.pioche.add(new Carte(Variante.FONCEE));
		this.cartesFonceesRestantes++;
	}

	private void melanger()
	{
		Random random = new Random();
		for (int i = this.pioche.size() - 1; i > 0; i--)
		{
			int j = random.nextInt(i + 1);
			Carte tmp = this.pioche.get(i);
			this.pioche.set(i, this.pioche.get(j));
			this.pioche.set(j, tmp);
		}
	}

	public Carte consommerCarteCourante()
	{
		Carte carte = this.carteCourante;
		if (carte != null)
		{
			this.defausse.add(carte);
			if (carte.estFoncee()) {this.cartesFonceesRestantes--;}
		}
		this.carteCourante = null;
		return carte;
	}

	public void piocherSuivante()
	{
		if (this.pioche.isEmpty())
		{
			this.carteCourante = null;
		}
		else
		{
			this.carteCourante = this.pioche.remove(0);
		}
	}
}
