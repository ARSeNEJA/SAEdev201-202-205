package ihm;

import metier.enums.TypePioche;

public class ParametresPlateau
{
	public final int largeur;
	public final int hauteur;
	public final int tailleCase;
	public final int nombreZones;
	public final TypePioche typePioche;

	public ParametresPlateau(int largeur, int hauteur, int tailleCase, int nombreZones, TypePioche typePioche)
	{
		this.largeur = largeur;
		this.hauteur = hauteur;
		this.tailleCase = tailleCase;
		this.nombreZones = nombreZones;
		this.typePioche = typePioche;
	}

	public boolean estValide()
	{
		return this.largeur > 0 && this.hauteur > 0 && this.tailleCase > 0 &&
			   this.nombreZones > 0 && this.typePioche != null;
	}
}
