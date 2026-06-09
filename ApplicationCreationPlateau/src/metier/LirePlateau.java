package metier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import metier.enums.Couleur;
import metier.enums.TypeAtome;
import metier.enums.TypePioche;

public class LirePlateau
{
	// Lit le fichier texte et reconstruit les objets du plateau.
	public Plateau lire(File fichier) throws IOException
	{
		Plateau plateauLu = null;
		List<String> lignes = Files.readAllLines(fichier.toPath());

		for (int i = 0; i < lignes.size(); i++)
		{
			String ligne = lignes.get(i).trim();
			String[] valeurs = ligne.split(";");
			try
			{
				if ("PARAMETRES".equals(valeurs[0]))
				{
					plateauLu = new Plateau(Integer.parseInt(valeurs[1].trim()), Integer.parseInt(valeurs[2].trim()),
							Integer.parseInt(valeurs[3].trim()));
					plateauLu.setTypePioche(TypePioche.valueOf(valeurs[4].trim()));
				}
				else if ("ZONE".equals(valeurs[0]))
				{
					if (plateauLu == null)
					{
						throw new IOException("PARAMETRES manquant.");
					}
					Zone zone = new Zone(Integer.parseInt(valeurs[1].trim()));
					for (int j = 2; j + 1 < valeurs.length; j += 2)
					{
						int colonne = Integer.parseInt(valeurs[j].trim());
						int ligneZone = Integer.parseInt(valeurs[j + 1].trim());
						if (!plateauLu.caseExiste(colonne, ligneZone))
						{
							throw new IllegalArgumentException();
						}
						zone.ajouterCase(colonne, ligneZone);
					}
					plateauLu.getZones().add(zone);
				}
				else if ("ATOME".equals(valeurs[0]))
				{
					if (plateauLu == null)
					{
						throw new IOException("PARAMETRES manquant.");
					}
					int colonne = Integer.parseInt(valeurs[1].trim());
					int ligneAtome = Integer.parseInt(valeurs[2].trim());
					TypeAtome type = TypeAtome.valueOf(valeurs[3].trim());
					Couleur couleurBase = this.lireCouleurBase(valeurs);

					if (couleurBase != null)
					{
						if (!plateauLu.definirBase(colonne, ligneAtome, type, couleurBase))
						{
							throw new IllegalArgumentException();
						}
					}
					else if (!plateauLu.ajouterAtome(colonne, ligneAtome, type))
					{
						throw new IllegalArgumentException();
					}
				}
			}
			catch (IllegalArgumentException | IndexOutOfBoundsException exception)
			{
				throw new IOException("ligne " + (i + 1) + " invalide.", exception);
			}
		}

		if (plateauLu == null)
		{
			throw new IOException("PARAMETRES manquant.");
		}
		plateauLu.calculerVoisinsAtomes();
		return plateauLu;
	}

	// Lit la couleur de base optionnelle presente sur une ligne ATOME.
	private Couleur lireCouleurBase(String[] valeurs)
	{
		if (valeurs.length < 5)
		{
			return null;
		}
		Couleur[] couleurs = Couleur.values();
		for (int i = 0; i < couleurs.length; i++)
		{
			if (couleurs[i].name().equals(valeurs[4].trim()))
			{
				return couleurs[i];
			}
		}
		return null;
	}
}
