package src.metier;

import java.util.ArrayList;

import src.metier.enums.Couleur;
import src.metier.enums.TypeAtome;
import src.metier.enums.TypePioche;

public class Plateau
{
	/*-------------------*/
	/*    Attributs      */
	/*-------------------*/
	private int largeur;
	private int hauteur;
	private int tailleCase;
	private TypePioche typePioche;
	private ArrayList<TypeAtome> typesSelectionnes;
	private ArrayList<Couleur> couleursSelectionnees;
	private ArrayList<Zone> zones;
	private ArrayList<Atome> atomes;

	/*-------------------*/
	/*   Constructeur    */
	/*-------------------*/
	// Cree le plateau.
	public Plateau(int largeur, int hauteur, int tailleCase)
	{
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.tailleCase = tailleCase;
		this.typePioche = TypePioche.OUVERTE;
		this.typesSelectionnes = new ArrayList<>();
		this.couleursSelectionnees = new ArrayList<>();
		this.zones = new ArrayList<>();
		this.atomes = new ArrayList<>();
	}

	/*-------------------*/
	/*   Accesseurs      */
	/*-------------------*/
	// Renvoie la largeur.
	public int getLargeur() {return this.largeur;}
	// Renvoie la hauteur.
	public int getHauteur() {return this.hauteur;}
	// Renvoie la taille case.
	public int getTailleCase() {return this.tailleCase;}
	// Renvoie le type pioche.
	public TypePioche getTypePioche() {return this.typePioche;}
	// Renvoie les types.
	public ArrayList<TypeAtome> getTypesSelectionnes() {return this.typesSelectionnes;}
	// Renvoie les couleurs.
	public ArrayList<Couleur> getCouleursSelectionnees() {return this.couleursSelectionnees;}
	// Renvoie les zones.
	public ArrayList<Zone> getZones() {return this.zones;}
	// Renvoie les atomes.
	public ArrayList<Atome> getAtomes() {return this.atomes;}

	/*-------------------*/
	/*    Methodes       */
	/*-------------------*/
	// Definit le type pioche.
	public void setTypePioche(TypePioche typePioche)
	{
		this.typePioche = typePioche;
	}

	// Teste les limites.
	public boolean caseExiste(int colonne, int ligne)
	{
		return colonne >= 0 && ligne >= 0 && colonne < this.largeur && ligne < this.hauteur;
	}

	// Cherche un atome.
	public Atome getAtomeCase(int colonne, int ligne)
	{
		for (int i = 0; i < this.atomes.size(); i++)
		{
			Atome atome = this.atomes.get(i);
			if (atome.getPosition().memesCoordonnees(colonne, ligne)) {return atome;}
		}
		return null;
	}

	// Cherche une zone.
	public Zone getZoneCase(int colonne, int ligne)
	{
		for (int i = 0; i < this.zones.size(); i++)
		{
			if (this.zones.get(i).contientCase(colonne, ligne)) {return this.zones.get(i);}
		}
		return null;
	}

	// Cherche la zone atome.
	public Zone getZoneAtome(Atome atome)
	{
		if (atome == null) {return null;}
		return this.getZoneCase(atome.getPosition().getColonne(), atome.getPosition().getLigne());
	}

	// Ajoute un atome.
	public void ajouterAtome(Atome atome)
	{
		if (atome != null && this.getAtomeCase(atome.getPosition().getColonne(), atome.getPosition().getLigne()) == null)
		{
			this.atomes.add(atome);
		}
	}

	// Renvoie les bases.
	public ArrayList<Atome> getBases()
	{
		ArrayList<Atome> bases = new ArrayList<>();
		for (int i = 0; i < this.atomes.size(); i++)
		{
			if (this.atomes.get(i).estBase()) {bases.add(this.atomes.get(i));}
		}
		return bases;
	}

	// Calcule les voisins.
	public void calculerVoisinsAtomes()
	{
		for (int i = 0; i < this.atomes.size(); i++)
		{
			this.atomes.get(i).viderVoisins();
		}
		for (int i = 0; i < this.atomes.size(); i++)
		{
			Atome atome = this.atomes.get(i);
			for (int dc = -1; dc <= 1; dc++)
			{
				for (int dl = -1; dl <= 1; dl++)
				{
					if (dc != 0 || dl != 0)
					{
						Atome voisin = this.chercherPremierAtomeDirection(atome, dc, dl);
						if (voisin != null)
						{
							atome.ajouterVoisin(voisin);
							voisin.ajouterVoisin(atome);
						}
					}
				}
			}
		}
	}

	// Cherche dans une direction.
	private Atome chercherPremierAtomeDirection(Atome depart, int dc, int dl)
	{
		int colonne = depart.getPosition().getColonne() + dc;
		int ligne = depart.getPosition().getLigne() + dl;
		while (this.caseExiste(colonne, ligne))
		{
			Atome atome = this.getAtomeCase(colonne, ligne);
			if (atome != null) {return atome;}
			colonne = colonne + dc;
			ligne = ligne + dl;
		}
		return null;
	}
}
