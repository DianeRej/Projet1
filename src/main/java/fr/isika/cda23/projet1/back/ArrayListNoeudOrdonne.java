package fr.isika.cda23.projet1.back;

import java.util.ArrayList;
import java.util.List;

//classe de l'arrayList permettant de créer une arrayList qui servira de base pour le tableau du fron
public class ArrayListNoeudOrdonne {
	
	// Initialisation de la liste qui recevra les noeuds de l'arbre dans l'ordre
	// alphabétique
	List<Stagiaire> listeOrdonnee = new ArrayList<>();

	// méthode d'ajout de noeud à cette liste
	public void addStagiaire(Stagiaire stagiaire) {
		listeOrdonnee.add(stagiaire);
	}

	public void effacerListe() {
		listeOrdonnee.clear();
	}

	
	// méthode permettant de récupérer la liste
	public List<Stagiaire> getStagiaire() {
		return listeOrdonnee;
	}
	


	@Override
	public String toString() {
		return listeOrdonnee + "\n";
	}

}
