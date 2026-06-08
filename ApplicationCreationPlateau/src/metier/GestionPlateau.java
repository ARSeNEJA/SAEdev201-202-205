package metier;

import java.io.File;
import java.io.IOException;

public class GestionPlateau
{
	private static final String CHEMIN_DOSSIER_PLATEAUX = "../PlateauData";
	private static final String NOM_PLATEAU_DEFAUT = "plateau.data";

	private File fichierPlateauCourant;

	public GestionPlateau()
	{
		this.fichierPlateauCourant = new File(CHEMIN_DOSSIER_PLATEAUX, NOM_PLATEAU_DEFAUT);
	}

	// Charge un plateau puis memorise son fichier comme fichier courant.
	public Plateau lirePlateau(File fichier) throws IOException
	{
		if (!this.estFichierData(fichier))
		{
			throw new IOException("Le fichier doit avoir l'extension .data.");
		}
		LirePlateau lecteur = new LirePlateau();
		Plateau plateauLu = lecteur.lire(fichier);
		this.fichierPlateauCourant = fichier;
		return plateauLu;
	}

	// Cree le dossier des plateaux si besoin avant de le fournir.
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

	// Ajoute ou remplace l'extension pour sauvegarder en .data.
	public File ajouterExtensionData(File fichier)
	{
		String nom = fichier.getName();
		String nomMinuscule = nom.toLowerCase();

		if (nomMinuscule.endsWith(".data"))
		{
			return fichier;
		}

		if (nomMinuscule.endsWith(".txt"))
		{
			nom = nom.substring(0, nom.length() - 4) + ".data";
		}
		else
		{
			nom = nom + ".data";
		}

		File dossier = fichier.getParentFile();
		if (dossier == null)
		{
			return new File(nom);
		}
		return new File(dossier, nom);
	}

	// Verifie que le fichier choisi utilise l'extension .data.
	public boolean estFichierData(File fichier)
	{
		return fichier != null && fichier.getName().toLowerCase().endsWith(".data");
	}

	public void enregistrerPlateau(Plateau plateau, int nombreZones) throws IOException
	{
		this.enregistrerPlateau(plateau, nombreZones, this.fichierPlateauCourant);
	}

	// Enregistre une copie dans le fichier choisi par l'utilisateur.
	public void enregistrerCopiePlateau(Plateau plateau, int nombreZones, File fichier) throws IOException
	{
		this.enregistrerPlateau(plateau, nombreZones, fichier);
	}

	// Valide puis ecrit le plateau dans le fichier cible.
	public void enregistrerPlateau(Plateau plateau, int nombreZones, File fichier) throws IOException
	{
		File fichierData = this.ajouterExtensionData(fichier);
		this.validerPlateauAvantEnregistrement(plateau, nombreZones);
		EnregistreurPlateau enregistreur = new EnregistreurPlateau();
		enregistreur.ecrire(plateau, fichierData);
		this.fichierPlateauCourant = fichierData;
	}

	// Controle les erreurs bloquantes avant une sauvegarde.
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
