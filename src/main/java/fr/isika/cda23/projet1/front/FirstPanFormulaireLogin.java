package fr.isika.cda23.projet1.front;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fr.isika.cda23.projet1.back.ArrayListNoeudOrdonne;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class FirstPanFormulaireLogin extends GridPane {

	public FirstPanFormulaireLogin(ArrayListNoeudOrdonne liste) {

		// Création des objets qui vont constituer le formulaire
		Label lblAuth = new Label("Authentifiez-vous :");

		HBox hbLogo = new HBox();
		try {
			Image image = new Image(new FileInputStream("src/main/java/fichiers/logo2.png"));
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
		lblLogin.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 15));

		Label lblPassword = new Label("Mot de passe");
		PasswordField fldPassword = new PasswordField();
		lblPassword.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
		lblAuth.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 20));

		HBox hbRadioButtons = new HBox();
		hbRadioButtons.setAlignment(Pos.BASELINE_RIGHT);
		RadioButton Admin = new RadioButton("Admin");
		RadioButton Utilisateur = new RadioButton("Utilisateur");
		Admin.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
		Utilisateur.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
		ToggleGroup tgStatut = new ToggleGroup();
		tgStatut.getToggles().addAll(Admin, Utilisateur);
		Label lblStatut = new Label();
		tgStatut.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
				Labeled itemSelected = (Labeled) newValue;
				lblStatut.setText("" + itemSelected.getText());
			}
		});
		hbRadioButtons.getChildren().addAll(Admin, Utilisateur);

		// permet de vérifier le statut de l'utilisateur et de passer à la deuxième
		// fenêtre
		Button btnValidez = new Button("Connexion");
		btnValidez.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 15));
		btnValidez.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (fldLogin.getText().equals("isika") && fldPassword.getText().equals("123456")
						&& lblStatut.getText().equals("Admin")) {
					// On instancie notre second panneau:
					SecondPanTableView secondPan = new SecondPanTableView(liste, 1);
					// On instancie une nouvelle scène en lui donnant notre nouveau panneau:
					Scene sceneVersTableau = new Scene(secondPan);
					// On récupère notre stage en remontant l'arborescence de composant à partir du
					// panneau courant:
					Stage stage = (Stage) FirstPanFormulaireLogin.this.getScene().getWindow();
					// On donne à notre stage la nouvelle scene:
					stage.setScene(sceneVersTableau);
				} else if (lblStatut.getText().equals("Utilisateur")) {
					// On instancie notre second panneau:
					SecondPanTableView secondPan = new SecondPanTableView(liste, 0);
					// On instancie une nouvelle scène en lui donnant notre nouveau panneau:
					Scene sceneVersTableau = new Scene(secondPan);
					// On récupère notre stage en remontant l'arborescence de composant à partir du
					// panneau courant:
					Stage stage = (Stage) FirstPanFormulaireLogin.this.getScene().getWindow();
					// On donne à notre stage la nouvelle scene:
					stage.setScene(sceneVersTableau);
				} else if (!fldLogin.getText().equals("isika") || !fldPassword.getText().equals("123456")) {
					// Alerte Pop up
					Alert a = new Alert(Alert.AlertType.ERROR);
					a.initOwner(null);
					a.setTitle("Erreur");
					a.setHeaderText("Mot de passe ou Login incorrect ");
					a.setContentText("Veuillez ressaisir vos informations ");
					a.show();
				} else if (lblStatut.getText().equals("")) {
					Alert a = new Alert(Alert.AlertType.ERROR);
					a.initOwner(null);
					a.setTitle("Erreur");
					a.setContentText("Veuillez choisir votre statut svp ");
					a.show();
				}
			}
		});

		// Ajout de tous les objets à la gridPane
		this.add(lblAuth, 0, 0);
		this.add(hbLogo, 4, 0);
		this.addRow(1, lblLogin, fldLogin);
		this.addRow(2, lblPassword, fldPassword);
		this.add(Admin, 1, 5);
		this.add(Utilisateur, 2, 5);
		this.add(btnValidez, 1, 8);

		this.setVgap(10);
		this.setHgap(10);
		this.setPadding(new Insets(1, 50, 10, 10));

	}

}
