package metier;

import java.util.ArrayList;

import metier.enums.Couleur;
import metier.enums.TypeAtome;
import metier.enums.TypePioche;

public class Plateau
{

	/*-------------------- */
	/*      Attributs      */
	/*-------------------- */
	private int largeur;
	private int hauteur;
	private int tailleCase;
	private TypePioche typePioche;
	private ArrayList<TypeAtome> typesSelectionnes;
	private ArrayList<Couleur> couleursSelectionnees;
	private ArrayList<Zone> zones;
	private ArrayList<Atome> atomes;

	/*-------------------- */
	/*    Constructeur     */
	/*-------------------- */
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
		TypeAtome[] types = TypeAtome.values();
		for (int i = 0; i < types.length; i++)
		{
			this.typesSelectionnes.add(types[i]);
		}
		Couleur[] couleurs = Couleur.values();
		for (int i = 0; i < couleurs.length; i++)
		{
			this.couleursSelectionnees.add(couleurs[i]);
		}
	}

	/*------------------------*/
	/*    Getters  et Setters */
	/*------------------------*/

	public int                  getLargeur()               {return this.largeur;}
	public int                  getHauteur()               {return this.hauteur;}
	public int                  getTailleCase()            {return this.tailleCase;}
	public TypePioche           getTypePioche()            {return this.typePioche;}
	public ArrayList<TypeAtome> getTypesSelectionnes()     {return this.typesSelectionnes;}
	public ArrayList<Couleur>   getCouleursSelectionnees() {return this.couleursSelectionnees;}
	public ArrayList<Zone>      getZones()                 {return this.zones;}
	public ArrayList<Atome>     getAtomes()                {return this.atomes;}

	public Zone getZoneParId(int id)
	{
		for (int i = 0; i < this.zones.size(); i++)
		{
			if (this.zones.get(i).getId() == id)
			{
				return this.zones.get(i);
			}
		}
		return null;
	}

	public Zone getZoneCase(int colonne, int ligne)
	{
		for (int i = 0; i < this.zones.size(); i++)
		{
			if (this.zones.get(i).contientCase(colonne, ligne))
			{
				return this.zones.get(i);
			}
		}
		return null;
	}

	public Zone getZoneAtome(Atome atome)
	{
		if (atome == null)
		{
			return null;
		}
		return this.getZoneCase(atome.getPosition().getColonne(), atome.getPosition().getLigne());
	}

	public Atome getAtomeCase(int colonne, int ligne)
	{
		for (int i = 0; i < this.atomes.size(); i++)
		{
			Atome atome = this.atomes.get(i);
			if (atome.getPosition().memesCoordonnees(colonne, ligne))
			{
				return atome;
			}
		}
		return null;
	}

	public void setDimensions(int largeur, int hauteur, int tailleCase)
	{
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.tailleCase = tailleCase;
		for (int i = 0; i < this.zones.size(); i++)
		{
			this.zones.get(i).supprimerCasesHorsPlateau(largeur, hauteur);
		}
		this.supprimerAtomesHorsPlateau();
		this.calculerVoisinsAtomes();
	}

	public void setTypePioche(TypePioche typePioche)
	{
		this.typePioche = typePioche;
	}

	/*--------------------*/
	/*    Méthodes        */
	/*--------------------*/
	public int calculerNombreZones()
	{
		int maximum = 1;
		for (int i = 0; i < this.zones.size(); i++)
		{
			if (this.zones.get(i).getId() > maximum)
			{
				maximum = this.zones.get(i).getId();
			}
		}
		return maximum;
	}

	public void actualiserParametres(int largeur, int hauteur, int tailleCase, int nombreZones,
			TypePioche typePioche)
	{
		this.setDimensions(largeur, hauteur, tailleCase);
		this.verifierNombreZones(nombreZones);
		this.setTypePioche(typePioche);
	}

	public boolean parametresSontValides(int largeur, int hauteur, int tailleCase, int nombreZones,
			TypePioche typePioche)
	{
		return largeur > 0 &&
			   hauteur > 0 &&
			   tailleCase > 0 &&
			   nombreZones > 0 &&
			   typePioche != null;
	}

	public boolean caseExiste(int colonne, int ligne)
	{
		return colonne >= 0 && ligne >= 0 && colonne < this.largeur && ligne < this.hauteur;
	}

	public void verifierNombreZones(int nombreZones)
	{
		int id = 1;
		while (id <= nombreZones)
		{
			if (this.getZoneParId(id) == null)
			{
				this.zones.add(new Zone(id));
			}
			id++;
		}
		int i = 0;
		while (i < this.zones.size())
		{
			if (this.zones.get(i).getId() > nombreZones)
			{
				this.zones.remove(i);
			}
			else
			{
				i++;
			}
		}
	}

	public void retirerCaseDesZones(int colonne, int ligne)
	{
		for (int i = 0; i < this.zones.size(); i++)
		{
			this.zones.get(i).supprimerCase(colonne, ligne);
		}
	}

	public boolean toutesZonesOntDesCases(int nombreZones)
	{
		int id = 1;
		while (id <= nombreZones)
		{
			Zone zone = this.getZoneParId(id);
			if (zone == null || zone.getCases().isEmpty())
			{
				return false;
			}
			id++;
		}
		return true;
	}

	public boolean toutesCasesOntUneZone()
	{
		for (int colonne = 0; colonne < this.largeur; colonne++)
		{
			for (int ligne = 0; ligne < this.hauteur; ligne++)
			{
				if (this.getZoneCase(colonne, ligne) == null)
				{
					return false;
				}
			}
		}
		return true;
	}

	public boolean ajouterAtome(int colonne, int ligne, TypeAtome type)
	{
		if (!this.caseExiste(colonne, ligne) || type == null)
		{
			return false;
		}
		if (this.getAtomeCase(colonne, ligne) != null)
		{
			return false;
		}
		this.atomes.add(new Atome(new Case(colonne, ligne), type));
		this.calculerVoisinsAtomes();
		return true;
	}

	public Atome supprimerAtome(int colonne, int ligne)
	{
		Atome atome = this.getAtomeCase(colonne, ligne);
		if (atome == null)
		{
			return null;
		}
		if (atome.estBase())
		{
			Zone zone = this.getZoneAtome(atome);
			if (zone != null)
			{
				zone.setCouleur(null);
			}
		}
		this.atomes.remove(atome);
		this.calculerVoisinsAtomes();
		return atome;
	}

	public boolean definirBase(int colonne, int ligne, TypeAtome type, Couleur couleur)
	{
		if (!this.caseExiste(colonne, ligne) || couleur == null)
		{
			return false;
		}

		Atome atome = this.getAtomeCase(colonne, ligne);
		TypeAtome typeBase = type;
		if (atome != null)
		{
			typeBase = atome.getType();
		}
		if (typeBase == null)
		{
			return false;
		}
		if (this.baseExisteCouleur(couleur) || this.baseExisteSymbole(typeBase))
		{
			return false;
		}
		if (atome == null)
		{
			if (!this.ajouterAtome(colonne, ligne, typeBase))
			{
				return false;
			}
			atome = this.getAtomeCase(colonne, ligne);
		}
		if (atome == null || !atome.definirBase(couleur))
		{
			return false;
		}

		Zone zone = this.getZoneAtome(atome);
		if (zone != null)
		{
			zone.setCouleur(couleur);
		}
		return true;
	}

	public boolean baseExisteCouleur(Couleur couleur)
	{
		for (int i = 0; i < this.atomes.size(); i++)
		{
			Atome atome = this.atomes.get(i);
			if (atome.estBase() && atome.getCouleurBase() == couleur)
			{
				return true;
			}
		}
		return false;
	}

	public boolean baseExisteSymbole(TypeAtome type)
	{
		for (int i = 0; i < this.atomes.size(); i++)
		{
			Atome atome = this.atomes.get(i);
			if (atome.estBase() && atome.getType() == type)
			{
				return true;
			}
		}
		return false;
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
			for (int directionColonne = -1; directionColonne <= 1; directionColonne++)
			{
				for (int directionLigne = -1; directionLigne <= 1; directionLigne++)
				{
					if (directionColonne != 0 || directionLigne != 0)
					{
						Atome voisin = this.chercherPremierAtomeDirection(atome, directionColonne, directionLigne);
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

	private Atome chercherPremierAtomeDirection(Atome depart, int directionColonne, int directionLigne)
	{
		int colonne = depart.getPosition().getColonne() + directionColonne;
		int ligne = depart.getPosition().getLigne() + directionLigne;
		while (this.caseExiste(colonne, ligne))
		{
			Atome atome = this.getAtomeCase(colonne, ligne);
			if (atome != null)
			{
				return atome;
			}
			colonne = colonne + directionColonne;
			ligne = ligne + directionLigne;
		}
		return null;
	}

	private void supprimerAtomesHorsPlateau()
	{
		int i = 0;
		while (i < this.atomes.size())
		{
			Atome atome = this.atomes.get(i);
			if (!this.caseExiste(atome.getPosition().getColonne(), atome.getPosition().getLigne()))
			{
				this.atomes.remove(i);
			}
			else
			{
				i++;
			}
		}
	}
}
