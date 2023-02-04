package fr.isika.cda23;

import fr.isika.cda23.projet1.back.ArrayListNoeudOrdonne;
import fr.isika.cda23.projet1.back.ArrayListStagiaire;
import fr.isika.cda23.projet1.back.GestionFichiers;
import fr.isika.cda23.projet1.back.Noeud;
import fr.isika.cda23.projet1.front.FirstPanFormulaireLogin;
import fr.isika.cda23.projet1.front.SecondPanTableView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	public static void main(String[] args) { 
		// Lecture du fichier STAGIAIRES.DON et stockage des informations dans une
		// arraylist
		ArrayListStagiaire liste = new ArrayListStagiaire();
		GestionFichiers.Lecture(liste);

		// Construction de l'arbre et écriture dans le fichier stagiaires.bin
		// Ajout de la racine
		Noeud.ajouterRacine(liste); 

		// Ajout de tous les stagiaires à l'arbre (il y en a en tout 1365 mais pour les
		// tests en en prend 20)
		for (int i = 1; i < 20; i++) {
			Noeud noeud = new Noeud(liste.stagiaires.get(i), -1, -1, -1);
			Noeud.ajouterNoeud(noeud, 0);
		}

		// Affichage de la liste des stagiaires inscrits dans l'arbre dans l'ordre du
		// fichier.don
		System.out.println("Liste initiale");
		for (int i = 0; i < 20; i++) {
			GestionFichiers.LectureBinaire(i);
		}

		// Affichage de la liste rangée dans l'ordre alphabétique dans la console
		// cette liste est stockée dans une arrayList listeOrdonnée qui va servir à la
		// création du tableau en front
//		System.out.println("Liste rangée dans l'ordre alphabétique");
//		ArrayListNoeudOrdonne listeOrdonnee = new ArrayListNoeudOrdonne();
//		Noeud.parcoursInfixe(0, listeOrdonnee);
//		System.out.println(listeOrdonnee);

		// Tests de méthodes (à commenter ou effacer plus tard)

//				System.out.println("test successeur de la racine");
//				System.out.println(Noeud.trouverSuccesseur(4));
//				
//				System.out.println("test de la méthode trouverNoeudASup et trouverParent");
//				System.out.println(Noeud.trouverNoeudASupEtParent(listeOrdonnee.noeudsOrdonnes.get(0), 0, 0));
//				System.out.println("On va supprimer un noeud ayant deux fils " + listeOrdonnee.noeudsOrdonnes.get(17));
		//
//				Noeud.supprimerNoeud(listeOrdonnee.noeudsOrdonnes.get(17), 6);
//				System.out.println("Liste après suppression");
//				for (int i = 0; i < 20; i++) {
//					GestionFichiers.LectureBinaire(i);
//				}
		
		launch();
		
	}

	@Override
	public void start(Stage stage) {
		//On instancie la liste qui va servir à générer le tableau
		ArrayListNoeudOrdonne listeOrdonnee = new ArrayListNoeudOrdonne();
		Noeud.parcoursInfixe(0, listeOrdonnee);

		// On instancie la première fenêtre grâce à la classe FirstPanFormulaireLogin
		SecondPanTableView root = new SecondPanTableView(listeOrdonnee);

		Scene scene = new Scene(root);

		stage.setTitle("Login");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}
}