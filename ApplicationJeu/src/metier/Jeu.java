package src.metier;

import java.util.ArrayList;
import java.util.Random;

public class Jeu
{
	private Plateau plateau;
	private ArrayList<Atome> basesDisponibles;
	private ArrayList<Manche> manches;
	private Manche mancheCourante;
	private Deck deck;
	private int indexManche;
	private boolean partieTerminee;
	private String message;
	private boolean modeDemo;

	// Cree un jeu normal.
	public Jeu(Plateau plateau)
	{
		this(plateau, false);
	}

	// Cree un jeu avec ou sans mode demo.
	public Jeu(Plateau plateau, boolean modeDemo)
	{
		this.plateau = plateau;
		this.modeDemo = modeDemo;
		this.basesDisponibles = plateau.getBases();
		this.melangerBases();
		this.manches = new ArrayList<Manche>();
		this.indexManche = 0;
		this.partieTerminee = false;
		this.message = "";
		this.demarrerMancheSuivante();
	}

	// Renvoie le plateau.
	public Plateau getPlateau() {return this.plateau;}
	// Renvoie les manches.
	public ArrayList<Manche> getManches() {return this.manches;}
	// Renvoie la manche en cours.
	public Manche getMancheCourante() {return this.mancheCourante;}
	// Renvoie le deck courant.
	public Deck getDeck() {return this.deck;}
	// Indique si la partie est terminee.
	public boolean estPartieTerminee() {return this.partieTerminee;}
	// Renvoie le message courant.
	public String getMessage() {return this.message;}
	// Renvoie le numero de manche.
	public int getNumeroManche() {return this.indexManche;}
	// Renvoie le nombre de manches.
	public int getNombreManches() {return this.basesDisponibles.size();}

	// Melange les bases de depart.
	private void melangerBases()
	{
		Random random = new Random();
		for (int i = this.basesDisponibles.size() - 1; i > 0; i--)
		{
			int j = random.nextInt(i + 1);
			Atome tmp = this.basesDisponibles.get(i);
			this.basesDisponibles.set(i, this.basesDisponibles.get(j));
			this.basesDisponibles.set(j, tmp);
		}
	}

	// Lance la manche suivante.
	private void demarrerMancheSuivante()
	{
		if (this.basesDisponibles.isEmpty())
		{
			this.partieTerminee = true;
			this.message = "Aucune base dans le plateau.";
			return;
		}
		if (this.indexManche >= this.basesDisponibles.size())
		{
			this.partieTerminee = true;
			this.mancheCourante = null;
			this.deck = null;
			this.message = "Partie terminee.";
			return;
		}
		Atome base = this.basesDisponibles.get(this.indexManche);
		this.mancheCourante = new Manche(base);
		this.manches.add(this.mancheCourante);
		this.deck = new Deck(this.plateau.getTypesSelectionnes(), this.modeDemo);
		this.indexManche++;
		this.actualiserScoreManche();
		this.message = "Base de depart : " + base.getType().name() + " " + base.getCouleurBase().getNom();
	}

	// Joue un atome clique.
	public boolean jouerAtome(Atome atome)
	{
		if (this.partieTerminee || this.mancheCourante == null || this.deck == null)
		{
			return false;
		}
		Carte carte = this.deck.getCarteCourante();
		if (carte == null)
		{
			this.finirManche();
			return false;
		}
		this.message = "";
		if (!this.deplacementAutorise(atome, carte))
		{
			if (this.message == null || this.message.length() == 0)
			{
				this.message = "Deplacement refuse.";
			}
			return false;
		}
		if (!this.ajouterAtomeSurChemin(atome))
		{
			if (this.message == null || this.message.length() == 0)
			{
				this.message = "Deplacement refuse.";
			}
			return false;
		}
		this.deck.consommerCarteCourante();
		this.actualiserScoreManche();
		this.avancerApresCarte();
		return true;
	}

	// Passe la carte courante.
	public void passerTour()
	{
		if (this.partieTerminee || this.deck == null) {return;}
		this.deck.consommerCarteCourante();
		this.avancerApresCarte();
	}

	// Avance apres une carte.
	private void avancerApresCarte()
	{
		if (this.deck.getCartesFonceesRestantes() <= 0)
		{
			this.finirManche();
		}
		else
		{
			this.deck.piocherSuivante();
			this.message = "Carte suivante.";
		}
	}

	// Valide le deplacement.
	private boolean deplacementAutorise(Atome atome, Carte carte)
	{
		if (atome == null || carte == null) {return false;}
		if (this.mancheCourante.contientAtome(atome)) {return false;}
		if (!carte.estJoker() && atome.getType() != carte.getTypeAtome()) {return false;}
		return this.peutRelierDepuisUneExtremite(atome);
	}

	// Teste les deux extremites.
	private boolean peutRelierDepuisUneExtremite(Atome atome)
	{
		Atome extremiteA = this.mancheCourante.getExtremiteA();
		Atome extremiteB = this.mancheCourante.getExtremiteB();
		if (this.liaisonPossibleDepuis(extremiteA, atome))
		{
			return true;
		}
		if (this.liaisonPossibleDepuis(extremiteB, atome))
		{
			return true;
		}
		return false;
	}

	// Ajoute au bon bout.
	private boolean ajouterAtomeSurChemin(Atome atome)
	{
		Atome extremiteA = this.mancheCourante.getExtremiteA();
		Atome extremiteB = this.mancheCourante.getExtremiteB();
		ArrayList<Atome> chaine = this.mancheCourante.getChaine();
		if (this.liaisonPossibleDepuis(extremiteA, atome))
		{
			chaine.add(0, atome);
			return true;
		}
		if (this.liaisonPossibleDepuis(extremiteB, atome))
		{
			chaine.add(atome);
			return true;
		}
		return false;
	}

	// Verifie une liaison.
	private boolean liaisonPossibleDepuis(Atome depart, Atome arrivee)
	{
		if (depart == null || arrivee == null || !depart.contientVoisin(arrivee))
		{
			return false;
		}
		if (this.liaisonDejaUtilisee(depart, arrivee))
		{
			this.message = "Deplacement refuse : liaison deja utilisee.";
			return false;
		}
		if (this.segmentCroiseChemins(depart, arrivee))
		{
			this.message = "Deplacement refuse : liaison croisee.";
			return false;
		}
		return true;
	}

	// Refuse une liaison deja prise.
	private boolean liaisonDejaUtilisee(Atome depart, Atome arrivee)
	{
		for (int i = 0; i < this.manches.size(); i++)
		{
			ArrayList<Atome> chaine = this.manches.get(i).getChaine();
			for (int j = 0; j < chaine.size() - 1; j++)
			{
				Atome a = chaine.get(j);
				Atome b = chaine.get(j + 1);
				if ((a == depart && b == arrivee) || (a == arrivee && b == depart))
				{
					return true;
				}
			}
		}
		return false;
	}

	// Detecte les croisements.
	private boolean segmentCroiseChemins(Atome depart, Atome arrivee)
	{
		for (int m = 0; m < this.manches.size(); m++)
		{
			ArrayList<Atome> chaine = this.manches.get(m).getChaine();
			for (int i = 0; i < chaine.size() - 1; i++)
			{
				Atome a = chaine.get(i);
				Atome b = chaine.get(i + 1);
				if (!this.segmentsPartagentUneExtremite(depart, arrivee, a, b) && this.segmentsSeCoupent(depart, arrivee, a, b))
				{
					return true;
				}
			}
		}
		return false;
	}

	// Teste une extremite commune.
	private boolean segmentsPartagentUneExtremite(Atome a1, Atome a2, Atome b1, Atome b2)
	{
		return a1 == b1 || a1 == b2 || a2 == b1 || a2 == b2;
	}

	// Teste deux segments.
	private boolean segmentsSeCoupent(Atome a1, Atome a2, Atome b1, Atome b2)
	{
		int x1 = a1.getPosition().getColonne();
		int y1 = a1.getPosition().getLigne();
		int x2 = a2.getPosition().getColonne();
		int y2 = a2.getPosition().getLigne();
		int x3 = b1.getPosition().getColonne();
		int y3 = b1.getPosition().getLigne();
		int x4 = b2.getPosition().getColonne();
		int y4 = b2.getPosition().getLigne();

		int o1 = this.orientation(x1, y1, x2, y2, x3, y3);
		int o2 = this.orientation(x1, y1, x2, y2, x4, y4);
		int o3 = this.orientation(x3, y3, x4, y4, x1, y1);
		int o4 = this.orientation(x3, y3, x4, y4, x2, y2);

		if (o1 != o2 && o3 != o4) {return true;}
		if (o1 == 0 && this.pointSurSegment(x1, y1, x3, y3, x2, y2)) {return true;}
		if (o2 == 0 && this.pointSurSegment(x1, y1, x4, y4, x2, y2)) {return true;}
		if (o3 == 0 && this.pointSurSegment(x3, y3, x1, y1, x4, y4)) {return true;}
		return o4 == 0 && this.pointSurSegment(x3, y3, x2, y2, x4, y4);
	}

	// Calcule une orientation.
	private int orientation(int ax, int ay, int bx, int by, int cx, int cy)
	{
		int valeur = (by - ay) * (cx - bx) - (bx - ax) * (cy - by);
		if (valeur == 0) {return 0;}
		if (valeur > 0) {return 1;}
		return 2;
	}

	// Teste un point sur segment.
	private boolean pointSurSegment(int ax, int ay, int px, int py, int bx, int by)
	{
		return px >= Math.min(ax, bx) && px <= Math.max(ax, bx) &&
			   py >= Math.min(ay, by) && py <= Math.max(ay, by);
	}

	// Termine la manche.
	private void finirManche()
	{
		this.actualiserScoreManche();
		this.message = "Manche terminee : " + this.mancheCourante.getScore() + " points.";
		this.demarrerMancheSuivante();
	}

	// Calcule le score total.
	public int calculerScoreTotal()
	{
		int total = 0;
		for (int i = 0; i < this.manches.size(); i++)
		{
			total = total + this.manches.get(i).getScore();
		}
		return total;
	}

	// Met a jour le score.
	public void actualiserScoreManche()
	{
		if (this.mancheCourante == null) {return;}
		ArrayList<Zone> zonesVisitees = new ArrayList<>();
		ArrayList<Integer> compteurs = new ArrayList<>();
		ArrayList<Atome> chaine = this.mancheCourante.getChaine();
		for (int i = 0; i < chaine.size(); i++)
		{
			Zone zone = this.plateau.getZoneAtome(chaine.get(i));
			if (zone != null)
			{
				int indice = this.indexZone(zonesVisitees, zone);
				if (indice < 0)
				{
					zonesVisitees.add(zone);
					compteurs.add(1);
				}
				else
				{
					compteurs.set(indice, compteurs.get(indice) + 1);
				}
			}
		}
		int max = 0;
		for (int i = 0; i < compteurs.size(); i++)
		{
			if (compteurs.get(i) > max) {max = compteurs.get(i);}
		}
		this.mancheCourante.setScore(max, zonesVisitees.size());
	}

	// Cherche une zone.
	private int indexZone(ArrayList<Zone> zones, Zone zone)
	{
		for (int i = 0; i < zones.size(); i++)
		{
			if (zones.get(i) == zone) {return i;}
		}
		return -1;
	}
}
