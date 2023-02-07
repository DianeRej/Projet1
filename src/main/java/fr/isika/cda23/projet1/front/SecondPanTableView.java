package fr.isika.cda23.projet1.front;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fr.isika.cda23.projet1.back.ArrayListNoeudOrdonne;
import fr.isika.cda23.projet1.back.GestionFichiers;
import fr.isika.cda23.projet1.back.Noeud;
import fr.isika.cda23.projet1.back.NoeudAvecIndex;
import fr.isika.cda23.projet1.back.Stagiaire;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SecondPanTableView extends VBox {

	// Attributs de la VBox ?
	Stagiaire stagiaireSelectionne = new Stagiaire("", "", "", "", 0);
	// test
	Label lblTest = new Label();

	// Constructeur (admin prend la valeur 0 si utilisateur et 1 si admin)
	public SecondPanTableView(ArrayListNoeudOrdonne liste, int admin) {
		super();

		// création des objets qui vont constituer le Pan
		// Première partie : le haut de la fenêtre :
		HBox hb = new HBox();

		Label lblTitre = new Label("Liste des Stagiaires");
		lblTitre.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
		hb.getChildren().add(lblTitre);
		hb.setAlignment(Pos.BOTTOM_CENTER);

		try {
			// import de l'image
			Image logo = new Image(new FileInputStream("src/main/java/fichiers/logo2.png"));
			ImageView logoView = new ImageView(logo);
			System.out.println(logo.getUrl());
			System.out.println(logoView.getId());
			// coordonnées de l'image
			logoView.setX(30);
			logoView.setY(30);
			logoView.setFitHeight(150);
			logoView.setFitWidth(150);
			// ajout de l'image à un groupe puis ajout de ce groupe à la HBox
			Group grpTest = new Group(logoView);
			hb.getChildren().add(grpTest);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Deuxième partie : tableau
		AnchorPane ap = new AnchorPane();
		// Instanciation du tableView
		TableView<Stagiaire> tableView = new TableView<Stagiaire>();
		tableView.setEditable(true);

		// Instanciation des colonnes + association de la colonne et de l'attribut
		// correspondant
		TableColumn<Stagiaire, String> colNom = new TableColumn<>("Nom");
		colNom.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("nom"));

		TableColumn<Stagiaire, String> colPrenom = new TableColumn<>("Prénom");
		colPrenom.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("prenom"));

		TableColumn<Stagiaire, String> colDept = new TableColumn<>("Département");
		colDept.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("dept"));

		TableColumn<Stagiaire, String> colPromo = new TableColumn<>("Promo");
		colPromo.setCellValueFactory(new PropertyValueFactory<Stagiaire, String>("promo"));

		TableColumn<Stagiaire, Integer> colAnnee = new TableColumn<>("Année");
		colAnnee.setCellValueFactory(new PropertyValueFactory<Stagiaire, Integer>("annee"));

		// ajout des colonnes créées au tableview
		tableView.getColumns().addAll(colNom, colPrenom, colDept, colPromo, colAnnee);
		// police qui s'ajuste
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// ajout du lien entre tableview et la liste du parcours infixe
		tableView.setItems(FXCollections.observableArrayList(liste.getStagiaire()));

		// ajout du tableau à Pane
		ap.getChildren().add(tableView);
		ap.setPrefSize(400, 300);
		AnchorPane.setTopAnchor(tableView, 5.);
		AnchorPane.setLeftAnchor(tableView, 5.);
		AnchorPane.setRightAnchor(tableView, 5.);
		AnchorPane.setBottomAnchor(tableView, 5.);

		// listener pour sélectionner la valeur du tableau sur laquelle on clique
		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Stagiaire>() {
			@Override
			public void changed(ObservableValue<? extends Stagiaire> observable, Stagiaire oldValue,
					Stagiaire newValue) {
				// TODO Auto-generated method stub
				stagiaireSelectionne = newValue;
			}
		});

		// TilePane pour la partie sous le tableau
		TilePane tp = new TilePane();

		// Création de tous les boutons de la TilePane
		Button btnAjouterStagiaire = new Button("Ajouter Stagiaire");
		btnAjouterStagiaire.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
		btnAjouterStagiaire.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				liste.effacerListe();

				ThirdPanFormulaireAjoutStagiaire formulaireAjout = new ThirdPanFormulaireAjoutStagiaire(liste, admin);
				Scene sceneTransition = new Scene(formulaireAjout);
				Stage stage = (Stage) SecondPanTableView.this.getScene().getWindow();
				stage.setScene(sceneTransition);

			}
		});

		Button btnModifierStagiaire = new Button("Modifier Stagiaire");
		btnModifierStagiaire.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
		btnModifierStagiaire.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// permet de modifier la ligne sélectionnée dans le tableau
				// on supprime la première version puis on ajoute la seconde après l'apparition
				// du pop-up et la redirection vers le formulaire
				NoeudAvecIndex noeudSelectionne = Noeud.trouverNoeudASupEtParent(stagiaireSelectionne, 0, 0);
				Noeud.supprimerNoeud(noeudSelectionne.noeud, noeudSelectionne.index);
				tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
				stagiaireSelectionne = new Stagiaire("", "", "", "", 0);
				liste.effacerListe();
				// Alerte Pop up
				Alert PopUpModif = new Alert(Alert.AlertType.CONFIRMATION);
				PopUpModif.initOwner(null);
				PopUpModif.setTitle("Modification");
				PopUpModif.setHeaderText("Redirection vers le formulaire");
				PopUpModif.setContentText("Veuillez compléter le formulaire suivant avec les nouvelles informations");
				PopUpModif.show();

				ThirdPanFormulaireAjoutStagiaire formulaireAjout = new ThirdPanFormulaireAjoutStagiaire(liste, admin);
				Scene sceneTransition = new Scene(formulaireAjout);
				Stage stage = (Stage) SecondPanTableView.this.getScene().getWindow();
				stage.setScene(sceneTransition);
			}
		});

		Button btnSupprimerStagiaire = new Button("Supprimer Stagiaire");
		btnSupprimerStagiaire.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
		// permet de supprimer la ligne sélectionnée
		btnSupprimerStagiaire.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				NoeudAvecIndex noeudSelectionne = Noeud.trouverNoeudASupEtParent(stagiaireSelectionne, 0, 0);
				Noeud.supprimerNoeud(noeudSelectionne.noeud, noeudSelectionne.index);
				for (int i = 0; i < 20; i++) {
					GestionFichiers.LectureBinaire(i);
				}
				tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
				stagiaireSelectionne = new Stagiaire("", "", "", "", 0);
				liste.effacerListe();
			}
		});

		Button btnRechercher = new Button("Rechercher");
		btnRechercher.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
		// A voir si on a le temps
		Button btnImprimer = new Button("Imprimer");
		btnImprimer.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
		// idem

		tp.getChildren().addAll(btnAjouterStagiaire, btnRechercher, btnImprimer);
		if (admin == 1) {
			tp.getChildren().addAll(btnModifierStagiaire, btnSupprimerStagiaire);
		}
		tp.setAlignment(Pos.TOP_RIGHT);

		// ajout des trois conteneurs dans la VBox
		this.getChildren().addAll(hb, ap, tp);
		this.setPadding(new Insets(20, 20, 20, 20));
		this.setStyle("-fx-background-color:CYAN");
	}

}
