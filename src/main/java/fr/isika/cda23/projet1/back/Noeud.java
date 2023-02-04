package fr.isika.cda23.projet1.back;

public class Noeud {

	// Attributs
	public Stagiaire stagiaire;
	public int fg;
	public int fd;
	public int suivant;
	

	// Constantes de taille pour le fichier binaire
	public final static int TAILLE_NOM_MAX = 21;
	public final static int TAILLE_PRENOM_MAX = 20;
	public final static int TAILLE_DEPT_MAX = 3;
	public final static int TAILLE_PROMO_MAX = 11;
	// Taille de l'objet noeud en octet = 126 octets
	public final static int TAILLE_OBJET_OCTET = (TAILLE_NOM_MAX + TAILLE_PRENOM_MAX + TAILLE_DEPT_MAX
			+ TAILLE_PROMO_MAX) * 2 + 4 * 4;
	// valeur par défaut en l'absence de fils ou de suivant pour le noeud
	public static final int FILS_VIDE = -1;
	public static final int SUIVANT_VIDE = -1;

	// Constructeur
	public Noeud(Stagiaire stagiaire, int fG, int fD, int suivant) {
		this.stagiaire = stagiaire;
		fg = fG;
		fd = fD;
		this.suivant = suivant;
	}

	@Override
	public String toString() {
		return stagiaire + ", fg : " + fg + ", fd : " + fd + ", suivant : " + suivant + "\n";
	}

	// Création de la racine
	public static void ajouterRacine(ArrayListStagiaire liste) {
		Noeud noeudRacine = new Noeud(liste.stagiaires.get(0), -1, -1, -1);
		GestionFichiers.EcritureBinaire(noeudRacine, 0);
	}

	// Ajout d'un noeud
	// avec index = 0 au début puisqu'on commence toujours par la racine
	public static void ajouterNoeud(Noeud newNoeud, int index) {
		// comparaison entre le nom du noeud existant et le nom du noeud à ajouter
		// les deux noms sont identiques
		if (GestionFichiers.LectureNom(index).compareTo(newNoeud.stagiaire.nomLong()) == 0) {
			// on va donc regarder si le noeud a un suivant
			// si le noeud a déjà un suivant
			if (GestionFichiers.LectureSuivant(index) != SUIVANT_VIDE) {
				ajouterNoeud(newNoeud, GestionFichiers.LectureSuivant(index));
				// si le noeud n'a pas encore de suivant
			} else {
				GestionFichiers.EcritureSuivant(index);
				GestionFichiers.EcritureBinaire(newNoeud, GestionFichiers.PositionDernierIndex());
			}

			// Si le nouveau nom est avant dans l'ordre alphabétique on va à gauche de
			// l'arbre
		} else if (GestionFichiers.LectureNom(index).compareTo(newNoeud.stagiaire.nomLong()) > 0) {
			// S'il y a un FG donc !=-1 alors appel récursif en prenant comme nouvel index
			// la valeur contenu dans fg
			if (GestionFichiers.LectureFG(index) != FILS_VIDE) {
				ajouterNoeud(newNoeud, GestionFichiers.LectureFG(index));
			} else { // sinon modif de la valeur fg du noeud existant
				GestionFichiers.EcritureFG(index);
				// et écriture du nouveau noeud à la fin du fichier .bin
				GestionFichiers.EcritureBinaire(newNoeud, GestionFichiers.PositionDernierIndex());
			} // idem mais dans le cas où l'on se déplace à droite

		} else if (GestionFichiers.LectureNom(index).compareTo(newNoeud.stagiaire.nomLong()) < 0) {
			if (GestionFichiers.LectureFD(index) != FILS_VIDE) {
				ajouterNoeud(newNoeud, GestionFichiers.LectureFD(index));
			} else {
				GestionFichiers.EcritureFD(index);
				// et écriture du nouveau noeud à la fin
				GestionFichiers.EcritureBinaire(newNoeud, GestionFichiers.PositionDernierIndex());
			}
		}
	}

	// permet de générer une arraylist des noeuds triés dans l'ordre alphabétique
	public static ArrayListNoeudOrdonne parcoursInfixe(int index, ArrayListNoeudOrdonne listeStagiaire) {
		// on parcours l'arbre dans l'ordre gauche-noeud-droit
		if (GestionFichiers.LectureFG(index) != FILS_VIDE) {
			parcoursInfixe(GestionFichiers.LectureFG(index), listeStagiaire);
		}

		listeStagiaire.addStagiaire(GestionFichiers.LectureNoeud(index).stagiaire);

		if (GestionFichiers.LectureSuivant(index) != SUIVANT_VIDE) {
			listeStagiaire.addStagiaire(GestionFichiers.LectureNoeud(GestionFichiers.LectureSuivant(index)).stagiaire);
		}

		if (GestionFichiers.LectureFD(index) != FILS_VIDE) {
			parcoursInfixe(GestionFichiers.LectureFD(index), listeStagiaire);
		}
		return listeStagiaire;
	}

	// méthode pour trouver le noeud successeur dans le cas de la suppression d'une
	// racine ayant deux fils
	// on commence avec l'index du noeud à supprimer
	public static NoeudAvecIndex trouverSuccesseur(int indexNoeudASup) {

		// On se place sur le FD de la racine
		Noeud noeudSuc = GestionFichiers.LectureNoeud(GestionFichiers.LectureFD(indexNoeudASup));
		int indexSuccesseur = GestionFichiers.LectureFD(indexNoeudASup);
		int indexParentDuSuccesseur = indexNoeudASup;
		// Tant que le noeud que l'on regarde a un fils gauche on descend dans l'arbre
		// vers la gauche
		while (GestionFichiers.LectureFG(indexSuccesseur) != FILS_VIDE) {
			noeudSuc = GestionFichiers.LectureNoeud(GestionFichiers.LectureFG(indexSuccesseur));
			indexParentDuSuccesseur = indexSuccesseur;
			indexSuccesseur = GestionFichiers.LectureFG(indexSuccesseur);
		}
		// On retourne le noeud le plus à gauche du côté droit de l'arbre
		NoeudAvecIndex noeudSuccesseur = new NoeudAvecIndex(noeudSuc, indexSuccesseur, indexParentDuSuccesseur);
		return noeudSuccesseur;

	}

	// sert à trouver la position du noeud à supprimer dans l'arbre ainsi que
	// l'index de son parent
	// on commence à la racine donc avec index=0 et indexParent=0
	public static NoeudAvecIndex trouverNoeudASupEtParent(Stagiaire stagiaireASup, int index, int indexParent) {
		// on compare d'abord les noms
		// S'ils sont identiques alors on regarde si le stagiaire est aussi le même
		if (GestionFichiers.LectureNom(index).compareTo(stagiaireASup.nomLong()) == 0) {
			// si le stagiaire du noeud à supprimer est égal au stagiaire du noeud auquel on
			// le compare
			if (GestionFichiers.LectureNoeud(index).stagiaire.equals(stagiaireASup) == true) {
				// on retourne l'index de ce noeud
				NoeudAvecIndex noeudASupprimer = new NoeudAvecIndex(GestionFichiers.LectureNoeud(index), index, indexParent);
				return noeudASupprimer;
			} else { // sinon cela signifie que le noeud à supprimer fait partie des suivants et donc
						// on fait un appel récursif
				return trouverNoeudASupEtParent(stagiaireASup, GestionFichiers.LectureSuivant(index), index);
			}
			// sinon si le nom du noeud est avant dans l'alphabet on va à gauche dans
			// l'arbre
		} else if (GestionFichiers.LectureNom(index).compareTo(stagiaireASup.nomLong()) > 0) {
			// s'il y a un fils gauche
			if (GestionFichiers.LectureFG(index) != FILS_VIDE) {
				// on fait un appel récursif
				return trouverNoeudASupEtParent(stagiaireASup, GestionFichiers.LectureFG(index), index);
			} // sinon le noeud ne se trouve pas dans cette partie de l'arbre
		} else { // sinon si le nom du noeud est après dans l'aphabet on va à droite dans l'arbre
			// s'il y a un fils droit
			if (GestionFichiers.LectureFD(index) != FILS_VIDE) {
				// on fait un appel récursif
				return trouverNoeudASupEtParent(stagiaireASup, GestionFichiers.LectureFD(index), index);
			} // sinon le noeud ne se trouve pas dans cette partie de l'arbre
		}
		// Si tout l'arbre est parcouru sans un retour, cela signifie que le noeud que
		// l'on cherche à sup ne se trouve pas dans l'arbre donc on retourne -1
		Noeud noeudVide = new Noeud(stagiaireASup, -1, -1, -1);
		NoeudAvecIndex noeudASupprimer = new NoeudAvecIndex(noeudVide, -1, -1);
		return noeudASupprimer;
	}

	// Suppression d'un noeud
	public static void supprimerNoeud(Noeud noeudASup, int indexNoeudASup) {
		// initialisation
		NoeudAvecIndex infosSuppression = new NoeudAvecIndex();
		infosSuppression = trouverNoeudASupEtParent(noeudASup.stagiaire, 0, 0);
		int indexParent = infosSuppression.indexParent;
		Noeud noeudParent = GestionFichiers.LectureNoeud(indexParent);

		// Cas 0 : supprimer un noeud qui a des doublons : on le remplace juste par son
		// suivant et on supprime son suivant
		if (GestionFichiers.LectureNoeud(infosSuppression.index).suivant != SUIVANT_VIDE) {
			// on remplace le noeud à supprimer par son suivant
			int indexSuivant = GestionFichiers.LectureSuivant(indexNoeudASup);
			Noeud noeudSuivant = GestionFichiers.LectureNoeud(GestionFichiers.LectureSuivant(indexNoeudASup));
			GestionFichiers.supprimerNoeud(noeudASup, GestionFichiers.LectureSuivant(indexNoeudASup));
			GestionFichiers.EcritureBinaire(noeudSuivant, indexNoeudASup * Noeud.TAILLE_OBJET_OCTET);
			// et on supprime le suivant

		}
		// CAS 1 : supprimerFeuille
		else if (GestionFichiers.LectureFG(indexNoeudASup) == -1 && GestionFichiers.LectureFD(indexNoeudASup) == -1) {
			// on supprime le noeud
			GestionFichiers.supprimerNoeud(noeudASup, indexParent);
			// on supprime la réf à ce noeud chez son parent
			// si le noeud à supprimer est le FG chez son parent
			if (indexNoeudASup == noeudParent.fg) {
				GestionFichiers.ModifierFG(indexParent, FILS_VIDE);

			} else if (indexNoeudASup == noeudParent.fd) { // sinon si le noeud à supprimer est le FD chez son
															// parent
				GestionFichiers.ModifierFD(indexParent, FILS_VIDE);

			} else if (indexNoeudASup == noeudParent.suivant) {
				GestionFichiers.ModifierSuivant(indexParent, SUIVANT_VIDE);
			}
			// si le noeud n'a pas de parent => racine donc pas de parent à modifier

			// CAS 2 : supprimer une racine ayant un seul fils
		} else if ((GestionFichiers.LectureFG(indexNoeudASup) != -1 && GestionFichiers.LectureFD(indexNoeudASup) == -1)
				|| (GestionFichiers.LectureFG(indexNoeudASup) == -1
						&& GestionFichiers.LectureFD(indexNoeudASup) != -1)) {
			if (GestionFichiers.LectureFG(indexNoeudASup) != -1) {
				// attribuer l'index de son FG au parent du noeud à sup
				if (indexNoeudASup == noeudParent.fg) { // si le noeud à sup est le FG de son parent
					GestionFichiers.ModifierFG(indexParent, infosSuppression.noeud.fg); // on remplace chez le parent, l'index du
																			// noeudASup par l'index du FG du NoeudASup
				} else if (indexNoeudASup == noeudParent.fd) { // sinon si le noeud à supprimer est le FD chez son
																// parent
					GestionFichiers.ModifierFD(indexParent, infosSuppression.noeud.fg);
				} // si le noeud n'a pas de parent => racine donc pas de parent à modifier
			} else {
				// deuxième cas : le noeudASup a un FD alors on veut attribuer l'index de son FD
				// au parent du noeudASup
				if (indexNoeudASup == noeudParent.fg) {
					GestionFichiers.ModifierFG(indexParent, infosSuppression.noeud.fd);
				} else if (indexNoeudASup == noeudParent.fd) {
					GestionFichiers.ModifierFD(indexParent, infosSuppression.noeud.fd);
				} // si le noeud n'a pas de parent => racine donc pas de parent à modifier
			}
			// puis supprimer le noeud
			GestionFichiers.supprimerNoeud(noeudASup, indexNoeudASup);

			// CAS 3 : supprimer une racine ayant deux fils
		} else {
			// on cherche son successeur
			NoeudAvecIndex noeudSuccesseurAvecIndex = trouverSuccesseur(indexNoeudASup);
			int indexSuccesseur = noeudSuccesseurAvecIndex.index;
			Noeud noeudSuccesseur = noeudSuccesseurAvecIndex.noeud;
			//on sauvegarde l'information FG et FD du noeudASup pour les redonner au successeur ensuite
			int indexFG = noeudASup.fg;
			int indexFD = noeudASup.fd;
			// on remplace le noeud à sup par le successeur
			GestionFichiers.EcritureBinaire(noeudSuccesseur, indexNoeudASup * Noeud.TAILLE_OBJET_OCTET);
			GestionFichiers.ModifierFG(indexNoeudASup, indexFG);
			GestionFichiers.ModifierFD(indexNoeudASup, indexFD);			
			// on supprime l'ancien noeud successeur
			GestionFichiers.supprimerNoeud(noeudSuccesseur, indexSuccesseur);
			// et donc la réf du successeur chez son ancien parent
			int indexParentDuSuccesseur = noeudSuccesseurAvecIndex.indexParent;
			Noeud noeudParentDuSuccesseur = GestionFichiers.LectureNoeud(indexParentDuSuccesseur);
			if (indexSuccesseur == noeudParentDuSuccesseur.fg) {
				GestionFichiers.ModifierFG(indexParentDuSuccesseur, -1);

			} else if (indexNoeudASup == noeudParentDuSuccesseur.fd) {
				GestionFichiers.ModifierFD(indexParentDuSuccesseur, -1);

			} else if (indexNoeudASup == noeudParentDuSuccesseur.suivant) {
				GestionFichiers.ModifierSuivant(indexParentDuSuccesseur, -1);
			} // si le noeud n'a pas de parent => racine donc pas de parent à modifie
		}
	}

}

