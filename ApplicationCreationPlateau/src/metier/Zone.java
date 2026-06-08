package metier;

import java.util.ArrayList;

import metier.enums.Couleur;

public class Zone
{
	/*-------------------- */
	/*      Attributs      */
	/*-------------------- */
	private int id;
	private Couleur couleur;
	private ArrayList<Case> cases;

	/*---------------------*/
	/*     Constructeur    */
	/*---------------------*/
	public Zone(int id)
	{
		this.id = id;
		this.couleur = null;
		this.cases = new ArrayList<Case>();
	}

	/*---------------------*/
	/*        Getters      */
	/*---------------------*/
	public int getId()
	{
		return this.id;
	}

	public ArrayList<Case> getCases()
	{
		return this.cases;
	}

	public Couleur getCouleur()
	{
		return this.couleur;
	}

	public void setCouleur(Couleur couleur)
	{
		this.couleur = couleur;
	}

	/*-------------------- */
	/*       Méthodes      */
	/*-------------------- */
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

	public void ajouterCase(int colonne, int ligne)
	{
		if (!this.contientCase(colonne, ligne))
		{
			this.cases.add(new Case(colonne, ligne));
		}
	}

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
