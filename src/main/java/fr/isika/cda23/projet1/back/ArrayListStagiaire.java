package fr.isika.cda23.projet1.back;

import java.util.ArrayList;
import java.util.List;

//classe de l'arrayList permettant de stocker les informations extraites du fichier
public class ArrayListStagiaire {

	// instanciation de la liste
		public List<Stagiaire> stagiaires = new ArrayList<>();

		// méthode pour ajouter des stagiaires à la liste
		public void addStagiaire(Stagiaire stagiaire) {
			stagiaires.add(stagiaire);
		}

		// méthode qui renvoie la liste de stagiaires
		public List<Stagiaire> getStagiaire() {
			return stagiaires;
		}

		@Override
		public String toString() {
			return "ArrayListStagiaire [stagiaires=" + stagiaires + "]";
		}
}
