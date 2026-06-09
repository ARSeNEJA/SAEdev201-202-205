package metier;

import java.util.ArrayList;

public class Zone
{
	/*-------------------- */
	/*      Attributs      */
	/*-------------------- */
	private int id;
	private ArrayList<Case> cases;

	/*---------------------*/
	/*     Constructeur    */
	/*---------------------*/
	// Cree une zone vide avec son identifiant.
	public Zone(int id)
	{
		this.id = id;
		this.cases = new ArrayList<Case>();
	}

	/*---------------------*/
	/*        Getters      */
	/*---------------------*/
	// Renvoie l'identifiant de la zone.
	public int getId()
	{
		return this.id;
	}

	// Renvoie les cases appartenant a la zone.
	public ArrayList<Case> getCases()
	{
		return this.cases;
	}

	/*-------------------- */
	/*       Méthodes      */
	/*-------------------- */
	// Verifie si une case precise appartient deja a la zone.
	public boolean contientCase(int colonne, int ligne)
	{
		for (int i = 0; i < this.cases.size(); i++)
		{
			if (this.cases.get(i).memesCoordonnees(colonne, ligne))
			{
				return true;
			}
		}
		return false;
	}

	// Ajoute une case si elle n'est pas encore presente.
	public void ajouterCase(int colonne, int ligne)
	{
		if (!this.contientCase(colonne, ligne))
		{
			this.cases.add(new Case(colonne, ligne));
		}
	}

	// Autorise l'ajout seulement si la case touche une case existante.
	public boolean peutAjouterCaseVoisine(int colonne, int ligne)
	{
		if (this.cases.isEmpty() || this.contientCase(colonne, ligne))
		{
			return true;
		}
		for (int i = 0; i < this.cases.size(); i++)
		{
			Case caseZone = this.cases.get(i);
			int ecartColonne = Math.abs(caseZone.getColonne() - colonne);
			int ecartLigne = Math.abs(caseZone.getLigne() - ligne);
			if (ecartColonne + ecartLigne == 1)
			{
				return true;
			}
		}
		return false;
	}

	// Controle que toutes les cases de la zone restent connectees.
	public boolean estConnexe()
	{
		if (this.cases.size() <= 1)
		{
			return true;
		}

		ArrayList<Case> casesVisitees = new ArrayList<Case>();
		ArrayList<Case> casesAVisiter = new ArrayList<Case>();
		casesAVisiter.add(this.cases.get(0));

		while (!casesAVisiter.isEmpty())
		{
			Case caseCourante = casesAVisiter.remove(0);
			if (!this.contientCaseDansListe(casesVisitees, caseCourante))
			{
				casesVisitees.add(caseCourante);
				this.ajouterVoisinesNonVisitees(caseCourante, casesVisitees, casesAVisiter);
			}
		}

		return casesVisitees.size() == this.cases.size();
	}

	// Ajoute a la recherche les voisines directes encore non visitees.
	private void ajouterVoisinesNonVisitees(Case caseCourante, ArrayList<Case> casesVisitees,
			ArrayList<Case> casesAVisiter)
	{
		for (int i = 0; i < this.cases.size(); i++)
		{
			Case caseZone = this.cases.get(i);
			int ecartColonne = Math.abs(caseCourante.getColonne() - caseZone.getColonne());
			int ecartLigne = Math.abs(caseCourante.getLigne() - caseZone.getLigne());
			if (ecartColonne + ecartLigne == 1 &&
					!this.contientCaseDansListe(casesVisitees, caseZone) &&
					!this.contientCaseDansListe(casesAVisiter, caseZone))
			{
				casesAVisiter.add(caseZone);
			}
		}
	}

	// Cherche une case equivalente dans une liste de cases.
	private boolean contientCaseDansListe(ArrayList<Case> liste, Case caseCherchee)
	{
		for (int i = 0; i < liste.size(); i++)
		{
			Case caseListe = liste.get(i);
			if (caseListe.memesCoordonnees(caseCherchee.getColonne(), caseCherchee.getLigne()))
			{
				return true;
			}
		}
		return false;
	}

	// Supprime une case de la zone a partir de ses coordonnees.
	public void supprimerCase(int colonne, int ligne)
	{
		int i = 0;
		while (i < this.cases.size())
		{
			if (this.cases.get(i).memesCoordonnees(colonne, ligne))
			{
				this.cases.remove(i);
			}
			else
			{
				i++;
			}
		}
	}

	// Retire les cases qui depassent les dimensions actuelles du plateau.
	public void supprimerCasesHorsPlateau(int largeur, int hauteur)
	{
		int i = 0;
		while (i < this.cases.size())
		{
			Case position = this.cases.get(i);
			if (position.getColonne() < 0 || position.getLigne() < 0 ||
				position.getColonne() >= largeur || position.getLigne() >= hauteur)
			{
				this.cases.remove(i);
			}
			else
			{
				i++;
			}
		}
	}
}
