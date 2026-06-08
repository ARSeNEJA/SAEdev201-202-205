package metier;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import metier.enums.Couleur;
import metier.enums.TypeAtome;

public class EnregistreurPlateau
{
	private static final String CHEMIN_FICHIER = "../PlateauData/plateau.data";

	public void ecrire(Plateau plateau) throws IOException
	{
		File fichier = new File(CHEMIN_FICHIER);
		this.ecrire(plateau, fichier);
	}

	public void ecrire(Plateau plateau, File fichier) throws IOException
	{
		File dossier = fichier.getParentFile();
		if (dossier != null && !dossier.exists())
		{
			dossier.mkdirs();
		}

		// Ferme toujours le fichier meme si l'ecriture echoue.
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier)))
		{
			this.ecrireParametres(writer, plateau);
			this.ecrireTypes(writer, plateau);
			this.ecrireCouleurs(writer, plateau);
			this.ecrireZones(writer, plateau);
			this.ecrireAtomes(writer, plateau);
		}
	}

	private void ecrireParametres(BufferedWriter writer, Plateau plateau) throws IOException
	{
		writer.write("PARAMETRES");
		writer.write(";");
		writer.write(String.valueOf(plateau.getLargeur()));
		writer.write(";");
		writer.write(String.valueOf(plateau.getHauteur()));
		writer.write(";");
		writer.write(String.valueOf(plateau.getTailleCase()));
		writer.write(";");
		writer.write(plateau.getTypePioche().name());
		writer.newLine();
	}

	private void ecrireTypes(BufferedWriter writer, Plateau plateau) throws IOException
	{
		writer.write("TYPES");
		ArrayList<TypeAtome> types = plateau.getTypesSelectionnes();
		for (int i = 0; i < types.size(); i++)
		{
			writer.write(";");
			writer.write(types.get(i).name());
		}
		writer.newLine();
	}

	private void ecrireCouleurs(BufferedWriter writer, Plateau plateau) throws IOException
	{
		writer.write("COULEURS");
		ArrayList<Couleur> couleurs = plateau.getCouleursSelectionnees();
		for (int i = 0; i < couleurs.size(); i++)
		{
			writer.write(";");
			writer.write(couleurs.get(i).name());
		}
		writer.newLine();
	}

	private void ecrireZones(BufferedWriter writer, Plateau plateau) throws IOException
	{
		ArrayList<Zone> zones = plateau.getZones();
		for (int i = 0; i < zones.size(); i++)
		{
			Zone zone = zones.get(i);
			if (!zone.getCases().isEmpty())
			{
				writer.write("ZONE");
				writer.write(";");
				writer.write(String.valueOf(zone.getId()));
				ArrayList<Case> cases = zone.getCases();
				for (int j = 0; j < cases.size(); j++)
				{
					writer.write(";");
					writer.write(String.valueOf(cases.get(j).getColonne()));
					writer.write(";");
					writer.write(String.valueOf(cases.get(j).getLigne()));
				}
				writer.newLine();
			}
		}
	}

	private void ecrireAtomes(BufferedWriter writer, Plateau plateau) throws IOException
	{
		ArrayList<Atome> atomes = plateau.getAtomes();
		for (int i = 0; i < atomes.size(); i++)
		{
			Atome atome = atomes.get(i);
			writer.write("ATOME");
			writer.write(";");
			writer.write(String.valueOf(atome.getPosition().getColonne()));
			writer.write(";");
			writer.write(String.valueOf(atome.getPosition().getLigne()));
			writer.write(";");
			writer.write(atome.getType().name());
			if (atome.estBase())
			{
				writer.write(";");
				writer.write(atome.getCouleurBase().name());
			}
			writer.newLine();
		}
	}
}
