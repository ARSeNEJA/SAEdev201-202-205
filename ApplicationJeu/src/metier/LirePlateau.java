package src.metier;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import src.metier.enums.Couleur;
import src.metier.enums.TypeAtome;
import src.metier.enums.TypePioche;

public class LirePlateau
{
	public Plateau lire(File fichier) throws IOException
	{
		Plateau plateau = null;
		BufferedReader reader = new BufferedReader(new FileReader(fichier));
		String ligne = reader.readLine();
		int numeroLigne = 1;
		while (ligne != null)
		{
			String[] valeurs = ligne.trim().split(";");
			try
			{
				if (valeurs.length > 0 && "PARAMETRES".equals(valeurs[0]))
				{
					plateau = new Plateau(Integer.parseInt(valeurs[1]), Integer.parseInt(valeurs[2]), Integer.parseInt(valeurs[3]));
					plateau.setTypePioche(TypePioche.valueOf(valeurs[4]));
				}
				else if (valeurs.length > 0 && "TYPES".equals(valeurs[0]) && plateau != null)
				{
					for (int i = 1; i < valeurs.length; i++)
					{
						plateau.getTypesSelectionnes().add(TypeAtome.valueOf(valeurs[i].trim()));
					}
				}
				else if (valeurs.length > 0 && "COULEURS".equals(valeurs[0]) && plateau != null)
				{
					for (int i = 1; i < valeurs.length; i++)
					{
						plateau.getCouleursSelectionnees().add(Couleur.valueOf(valeurs[i].trim()));
					}
				}
				else if (valeurs.length > 0 && "ZONE".equals(valeurs[0]) && plateau != null)
				{
					Zone zone = new Zone(Integer.parseInt(valeurs[1].trim()));
					for (int i = 2; i + 1 < valeurs.length; i = i + 2)
					{
						zone.ajouterCase(Integer.parseInt(valeurs[i].trim()), Integer.parseInt(valeurs[i + 1].trim()));
					}
					plateau.getZones().add(zone);
				}
				else if (valeurs.length > 0 && "ATOME".equals(valeurs[0]) && plateau != null)
				{
					Atome atome = new Atome(new Case(Integer.parseInt(valeurs[1].trim()), Integer.parseInt(valeurs[2].trim())),
							TypeAtome.valueOf(valeurs[3].trim()));
					if (valeurs.length >= 5)
					{
						atome.definirBase(Couleur.valueOf(valeurs[4].trim()));
					}
					plateau.ajouterAtome(atome);
				}
			}
			catch (RuntimeException exception)
			{
				reader.close();
				throw new IOException("ligne " + numeroLigne + " invalide", exception);
			}
			ligne = reader.readLine();
			numeroLigne++;
		}
		reader.close();
		if (plateau == null) {throw new IOException("PARAMETRES manquant");}
		if (plateau.getTypesSelectionnes().isEmpty())
		{
			plateau.getTypesSelectionnes().add(TypeAtome.H);
			plateau.getTypesSelectionnes().add(TypeAtome.O);
			plateau.getTypesSelectionnes().add(TypeAtome.C);
			plateau.getTypesSelectionnes().add(TypeAtome.S);
		}
		plateau.calculerVoisinsAtomes();
		return plateau;
	}
}
