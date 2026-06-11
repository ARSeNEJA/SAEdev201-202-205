package src.metier;

import java.util.ArrayList;

public class Zone
{
	private int id;
	private ArrayList<Case> cases;

	public Zone(int id)
	{
		this.id = id;
		this.cases = new ArrayList<>();
	}

	public int getId() {return this.id;}
	public ArrayList<Case> getCases() {return this.cases;}

	public boolean contientCase(int colonne, int ligne)
	{
		for (int i = 0; i < this.cases.size(); i++)
		{
			if (this.cases.get(i).memesCoordonnees(colonne, ligne)) {return true;}
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
}
