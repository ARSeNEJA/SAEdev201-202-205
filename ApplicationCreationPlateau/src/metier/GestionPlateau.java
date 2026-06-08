package metier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import metier.enums.Couleur;
import metier.enums.TypeAtome;
import metier.enums.TypePioche;

public class GestionPlateau
{
	private static final String CHEMIN_DOSSIER_PLATEAUX = "../PlateauData";
	private static final String NOM_PLATEAU_DEFAUT = "plateau.txt";

	private File fichierPlateauCourant;

	public GestionPlateau()
	{
		this.fichierPlateauCourant = new File(CHEMIN_DOSSIER_PLATEAUX, NOM_PLATEAU_DEFAUT);
	}

	public Plateau lirePlateau(File fichier) throws IOException
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
						throw new IOException("PARAMETRES manquant.");
					Zone zone = new Zone(Integer.parseInt(valeurs[1].trim()));
					for (int j = 2; j + 1 < valeurs.length; j += 2)
					{
						int colonne = Integer.parseInt(valeurs[j].trim());
						int ligneZone = Integer.parseInt(valeurs[j + 1].trim());
						if (!plateauLu.caseExiste(colonne, ligneZone))
							throw new IllegalArgumentException();
						zone.ajouterCase(colonne, ligneZone);
					}
					plateauLu.getZones().add(zone);
				}
				else if ("ATOME".equals(valeurs[0]))
				{
					if (plateauLu == null)
						throw new IOException("PARAMETRES manquant.");
					int colonne = Integer.parseInt(valeurs[1].trim());
					int ligneAtome = Integer.parseInt(valeurs[2].trim());
					TypeAtome type = TypeAtome.valueOf(valeurs[3].trim());
					Couleur couleurBase = this.lireCouleurBase(valeurs);

					if (couleurBase != null)
					{
						if (!plateauLu.definirBase(colonne, ligneAtome, type, couleurBase))
							throw new IllegalArgumentException();
					}
					else if (!plateauLu.ajouterAtome(colonne, ligneAtome, type))
					{
						throw new IllegalArgumentException();
					}
				}
			}
			catch (IllegalArgumentException | IndexOutOfBoundsException exception)
			{
				throw new IOException("ligne " + (i + 1) + " invalide.");
			}
		}
		if (plateauLu == null)
			throw new IOException("PARAMETRES manquant.");
		plateauLu.calculerVoisinsAtomes();
		this.fichierPlateauCourant = fichier;
		return plateauLu;
	}

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

	public File getDossierPlateaux()
	{
		File dossier = new File(CHEMIN_DOSSIER_PLATEAUX);
		if (!dossier.exists())
		{
			dossier.mkdirs();
		}
		return dossier;
	}

	public File getFichierPlateauCourant()
	{
		return this.fichierPlateauCourant;
	}

	public File getFichierCopiePropose()
	{
		return new File(this.getDossierPlateaux(), this.fichierPlateauCourant.getName());
	}

	public File ajouterExtensionTxt(File fichier)
	{
		if (fichier.getName().toLowerCase().endsWith(".txt"))
		{
			return fichier;
		}
		File dossier = fichier.getParentFile();
		if (dossier == null)
		{
			return new File(fichier.getName() + ".txt");
		}
		return new File(dossier, fichier.getName() + ".txt");
	}

	public void enregistrerPlateau(Plateau plateau, int nombreZones) throws IOException
	{
		this.enregistrerPlateau(plateau, nombreZones, this.fichierPlateauCourant);
	}

	public void enregistrerCopiePlateau(Plateau plateau, int nombreZones, File fichier) throws IOException
	{
		File fichierCopie = this.ajouterExtensionTxt(fichier);
		this.enregistrerPlateau(plateau, nombreZones, fichierCopie);
	}

	public void enregistrerPlateau(Plateau plateau, int nombreZones, File fichier) throws IOException
	{
		this.validerPlateauAvantEnregistrement(plateau, nombreZones);
		EnregistreurPlateau enregistreur = new EnregistreurPlateau();
		enregistreur.ecrire(plateau, fichier);
		this.fichierPlateauCourant = fichier;
	}

	public void validerPlateauAvantEnregistrement(Plateau plateau, int nombreZones)
	{
		if (plateau.getAtomes().isEmpty())
		{
			throw new IllegalArgumentException("Placez au moins un atome avant d'enregistrer.");
		}
		if (!plateau.toutesCasesOntUneZone())
		{
			throw new IllegalArgumentException("Toutes les cases du plateau doivent appartenir a une zone.");
		}
		if (!plateau.toutesZonesOntDesCases(nombreZones))
		{
			throw new IllegalArgumentException("Chaque zone doit contenir au moins une case.");
		}
		Zone zoneSeparee = plateau.getZoneSeparee(nombreZones);
		if (zoneSeparee != null)
		{
			throw new IllegalArgumentException("La zone " + zoneSeparee.getId() +
					" est separee en plusieurs morceaux.");
		}

		Atome atomeIsole = plateau.getAtomeIsole();
		if (atomeIsole != null)
		{
			throw new IllegalArgumentException("L'atome en colonne " + atomeIsole.getPosition().getColonne() +
					", ligne " + atomeIsole.getPosition().getLigne() + " est isole.");
		}
	}
}
