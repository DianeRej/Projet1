package fr.isika.cda23.projet1.front;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class FirstPanFormulaireLogin extends GridPane{

public FirstPanFormulaireLogin() {
		
		//Création des objets qui vont constituer le formulaire
		Label lblAuth = new Label("Authentifiez-vous :");
		
		HBox hbLogo = new HBox();
		try {
			Image image = new Image(new FileInputStream("src/main/java/fichiers/logo.png"));
			ImageView imageView = new ImageView(image);
			System.out.println(image.getUrl());
			System.out.println(imageView.getId());
			imageView.setX(30);
			imageView.setY(30);
			imageView.setFitHeight(150);
			imageView.setFitWidth(150);
			Group grpTest = new Group(imageView);
			hbLogo.getChildren().add(grpTest);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}

		Label lblLogin = new Label("Login : ");
		TextField fldLogin = new TextField();

		Label lblPassword = new Label("Mot de passe");
		PasswordField fldPassword = new PasswordField();
		
		HBox hbRadioButtons = new HBox();
		RadioButton Admin = new RadioButton("Admin");
		RadioButton Utilisateur = new RadioButton("Utilisateur");
		ToggleGroup tgAdminUtilisateur = new ToggleGroup();
		tgAdminUtilisateur.getToggles().addAll(Admin, Utilisateur);
		hbRadioButtons.getChildren().addAll(Admin, Utilisateur);

		//il faut ajouter la fonction de vérification du login et mdp et si c'est correct changer de page pour aller sur la page du tableau sinon afficher le pop-up
		Button btnValidez = new Button("Connexion");

		//Ajout de tous les objets à la gridPane
		this.addRow(0, lblAuth, hbLogo);
		this.addRow(1, lblLogin, fldLogin);
		this.addRow(2, lblPassword, fldPassword);
		this.addRow(3, hbRadioButtons);
		this.addRow(4, btnValidez);

		this.setVgap(15);
		this.setHgap(15);
		this.setPadding(new Insets(30, 30, 20, 20));
		
//A mettre dans l'App
		// Alerte Pop up
//		Alert a = new Alert(Alert.AlertType.CONFIRMATION);
//		a.initOwner(scene);
//		a.setTitle("Fenetre Pop-up");
//		a.setHeaderText("Attention Vous n'etes pas Admin !");
//		a.setContentText("veuillez cocher la bonne case");
//		a.show();

	}


}


