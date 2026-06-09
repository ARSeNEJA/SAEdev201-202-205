import ihm.Fenetre;
import metier.Plateau;

public class Controleur
{
	// Prepare le plateau par defaut et ouvre la fenetre de creation.
	public void demarrer()
	{
		Plateau plateau = new Plateau(10, 8, 50);
		plateau.verifierNombreZones(4);
		new Fenetre(plateau);
	}

	/*---------*/
	/*  Main   */
	/*---------*/
	// Lance l'application de creation du plateau.
	public static void main(String[] args)
	{
		new Controleur().demarrer();
	}
}
