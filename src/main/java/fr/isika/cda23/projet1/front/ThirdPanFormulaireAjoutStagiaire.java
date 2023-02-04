package fr.isika.cda23.projet1.front;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fr.isika.cda23.projet1.back.ArrayListNoeudOrdonne;
import fr.isika.cda23.projet1.back.Stagiaire;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ThirdPanFormulaireAjoutStagiaire extends BorderPane {

	// Attributs ?

	// Constructeur
	public ThirdPanFormulaireAjoutStagiaire(ArrayListNoeudOrdonne liste) {
		super();

		// GridPane central
		GridPane gpFormulaire = new GridPane();

		// Création des éléments contenus dans la gridPane
		Label lblNom = new Label("Nom :");
		TextField fldNom = new TextField();

		Label lblPrenom = new Label("Prénom :");
		TextField fldPrenom = new TextField();

		Label lblAnneeEntree = new Label("Année d'entrée :");
		ChoiceBox<String> cbAnneeEntree = new ChoiceBox<>();
		// On ajoute dans la ChoiceBox les valeurs disponibles pour l'utilisateur:
		cbAnneeEntree.getItems().addAll("Année d'entrée", "2002", "2003", "2004", "2005", "2006", "2007", "2009",
				"2008", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021",
				"2022", "2023");
		cbAnneeEntree.getSelectionModel().select(0);

		// On ajoute un listener qui va retenir la valeur sélectionnée par l'utilisateur
		Label AnneeChoisie = new Label();
		cbAnneeEntree.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				AnneeChoisie.setText(newValue);
			}
		});

		Label lblCodePromo = new Label("Code Promotion :");
		TextField fldCodePromo = new TextField();

		Label lblDepartement = new Label("Département :");
		TextField fldDepartement = new TextField();

		// ajout dans la gridPane
		gpFormulaire.add(lblNom, 0, 0);
		gpFormulaire.add(lblPrenom, 0, 1);
		gpFormulaire.add(lblCodePromo, 0, 2);
		gpFormulaire.add(lblAnneeEntree, 0, 3);
		gpFormulaire.add(lblDepartement, 0, 4);

		gpFormulaire.add(fldNom, 1, 0);
		gpFormulaire.add(fldPrenom, 1, 1);
		gpFormulaire.add(fldCodePromo, 1, 2);
		gpFormulaire.add(cbAnneeEntree, 1, 3);
		gpFormulaire.add(fldDepartement, 1, 4);

		// style de la gridPane
		gpFormulaire.setPadding(new Insets(50, 50, 80, 30));
		gpFormulaire.setHgap(25);
		gpFormulaire.setVgap(25);

		// En-tête de la fenêtre avec le logo
		HBox hbLogo = new HBox();
		try {
			Image image = new Image(new FileInputStream("src/main/java/fichiers/logo.png"));
			ImageView imageView = new ImageView(image);
			System.out.println(image.getUrl());
			System.out.println(imageView.getId());
			imageView.setX(30);
			imageView.setY(30);

			// setting the fit height and width of the image view
			imageView.setFitHeight(150);
			imageView.setFitWidth(150);

			Group grpTest = new Group(imageView);
			hbLogo.getChildren().add(grpTest);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Pied de la fenêtre
		VBox vbButtons = new VBox();
		
		//Bouton Validez qui sotcke les informations entrées dans les textField et la ChoiceBox 
		//dans une variable stagiaire pour pouvoir ensuite l'ajouter à la liste
		Button Valider = new Button("Validez");
		Valider.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Je Valide");
				Stagiaire nouveauStagiaire = new Stagiaire(fldNom.getText(), fldPrenom.getText(),
						fldDepartement.getText(), fldCodePromo.getText(), Integer.parseInt(AnneeChoisie.getText()));
				liste.addStagiaire(nouveauStagiaire);
				System.out.println(nouveauStagiaire);
			}
		});
		//Bouton retour qui devra permettre de revenir à la page précédente (le tableView) sans avoir à ajouter un stagiaire
		Button Retour = new Button("Retour");
		Retour.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Je retourne à la page précédente");
			}
		});
		
		//Ajout des objets dans la VBox
		vbButtons.getChildren().addAll(Valider, Retour);
		
		//Style
		vbButtons.setAlignment(Pos.CENTER_RIGHT);
		vbButtons.setPadding(new Insets(50, 30, 30, 30));
		vbButtons.setSpacing(30);
		
		//Ajout des trois conteneurs
		this.setTop(hbLogo);
		this.setCenter(gpFormulaire);
		this.setBottom(vbButtons);
		
		//Style de la fenêtre
		this.setStyle("-fx-background-color:beige ");
	}

}
