package src.metier;

import java.util.ArrayList;

import src.metier.enums.Couleur;
import src.metier.enums.TypeAtome;
import src.metier.enums.TypePioche;

public class Plateau
{
	private int largeur;
	private int hauteur;
	private int tailleCase;
	private TypePioche typePioche;
	private ArrayList<TypeAtome> typesSelectionnes;
	private ArrayList<Couleur> couleursSelectionnees;
	private ArrayList<Zone> zones;
	private ArrayList<Atome> atomes;

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

	public int getLargeur() {return this.largeur;}
	public int getHauteur() {return this.hauteur;}
	public int getTailleCase() {return this.tailleCase;}
	public TypePioche getTypePioche() {return this.typePioche;}
	public ArrayList<TypeAtome> getTypesSelectionnes() {return this.typesSelectionnes;}
	public ArrayList<Couleur> getCouleursSelectionnees() {return this.couleursSelectionnees;}
	public ArrayList<Zone> getZones() {return this.zones;}
	public ArrayList<Atome> getAtomes() {return this.atomes;}

	public void setTypePioche(TypePioche typePioche)
	{
		this.typePioche = typePioche;
	}

	public boolean caseExiste(int colonne, int ligne)
	{
		return colonne >= 0 && ligne >= 0 && colonne < this.largeur && ligne < this.hauteur;
	}

	public Atome getAtomeCase(int colonne, int ligne)
	{
		for (int i = 0; i < this.atomes.size(); i++)
		{
			Atome atome = this.atomes.get(i);
			if (atome.getPosition().memesCoordonnees(colonne, ligne)) {return atome;}
		}
		return null;
	}

	public Zone getZoneCase(int colonne, int ligne)
	{
		for (int i = 0; i < this.zones.size(); i++)
		{
			if (this.zones.get(i).contientCase(colonne, ligne)) {return this.zones.get(i);}
		}
		return null;
	}

	public Zone getZoneAtome(Atome atome)
	{
		if (atome == null) {return null;}
		return this.getZoneCase(atome.getPosition().getColonne(), atome.getPosition().getLigne());
	}

	public void ajouterAtome(Atome atome)
	{
		if (atome != null && this.getAtomeCase(atome.getPosition().getColonne(), atome.getPosition().getLigne()) == null)
		{
			this.atomes.add(atome);
		}
	}

	public ArrayList<Atome> getBases()
	{
		ArrayList<Atome> bases = new ArrayList<>();
		for (int i = 0; i < this.atomes.size(); i++)
		{
			if (this.atomes.get(i).estBase()) {bases.add(this.atomes.get(i));}
		}
		return bases;
	}

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
