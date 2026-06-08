import ihm.Fenetre;
import metier.Plateau;

public class Controleur
{
	public void demarrer()
	{
		Plateau plateau = new Plateau(10, 8, 50);
		plateau.verifierNombreZones(4);
		new Fenetre(plateau);
	}

	/*---------*/
	/*  Main   */
	/*---------*/
	public static void main(String[] args)
	{
		new Controleur().demarrer();
	}
}
