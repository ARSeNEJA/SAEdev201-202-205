package src.metier;

import java.util.ArrayList;
import java.util.Random;

import src.metier.enums.TypeAtome;
import src.metier.enums.Variante;

public class Deck
{
	/*-------------------*/
	/*    Attributs      */
	/*-------------------*/
	private ArrayList<Carte> pioche;
	private ArrayList<Carte> defausse;
	private Carte carteCourante;
	private int cartesFonceesRestantes;

	/*-------------------*/
	/*   Constructeur    */
	/*-------------------*/
	// Cree une pioche melangee.
	public Deck(ArrayList<TypeAtome> types)
	{
		this(types, false);
	}

	// Cree la pioche selon le mode choisi.
	public Deck(ArrayList<TypeAtome> types, boolean modeDemo)
	{
		this.pioche = new ArrayList<Carte>();
		this.defausse = new ArrayList<Carte>();
		this.cartesFonceesRestantes = 0;
		this.generer(types);
		if (!modeDemo)
		{
			this.melanger();
		}
		this.piocherSuivante();
	}

	/*-------------------*/
	/*   Accesseurs      */
	/*-------------------*/
	// Renvoie la carte a jouer.
	public Carte getCarteCourante() {return this.carteCourante;}
	// Renvoie les cartes defaussees.
	public ArrayList<Carte> getDefausse() {return this.defausse;}
	// Renvoie les cartes foncees restantes.
	public int getCartesFonceesRestantes() {return this.cartesFonceesRestantes;}
	// Renvoie la taille de la pioche.
	public int getTaillePioche() {return this.pioche.size();}

	/*-------------------*/
	/*    Methodes       */
	/*-------------------*/
	// Genere les cartes du deck.
	private void generer(ArrayList<TypeAtome> types)
	{
		for (int i = 0; i < types.size(); i++)
		{
			this.ajouterPaire(types.get(i));
		}
		this.ajouterPaireJoker();
	}

	// Ajoute claire et foncee.
	private void ajouterPaire(TypeAtome type)
	{
		this.pioche.add(new Carte(type, Variante.CLAIRE));
		this.pioche.add(new Carte(type, Variante.FONCEE));
		this.cartesFonceesRestantes++;
	}

	// Ajoute les deux jokers.
	private void ajouterPaireJoker()
	{
		this.pioche.add(new Carte(Variante.CLAIRE));
		this.pioche.add(new Carte(Variante.FONCEE));
		this.cartesFonceesRestantes++;
	}

	// Melange les cartes.
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

	// Defausse la carte courante.
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

	// Pioche la carte suivante.
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
