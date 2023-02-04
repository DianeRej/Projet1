package fr.isika.cda23.projet1.back;

import java.util.Objects;

//Classe objet du stagiaire
public class Stagiaire {

	// Attributs
	public String nom;
	public String prenom;
	public String dept;
	public String promo;
	public int annee;

	// Constante de taille en char de chaque attribut pour le fichier binaire
	public final static int TAILLE_NOM_MAX = 21; // 42 octets
	public final static int TAILLE_PRENOM_MAX = 20; // 40 octets
	public final static int TAILLE_DEPT_MAX = 3; // 6 octets
	public final static int TAILLE_PROMO_MAX = 11; // 22 octets

	// Constructeur
	public Stagiaire(String nom, String prenom, String dept, String promo, int annee) {
		this.nom = nom;
		this.prenom = prenom;
		this.dept = dept;
		this.promo = promo;
		this.annee = annee;
	}

	// méthodes pour l'écriture dans le fichier binaire
	// sert à allonger les noms de chaque String pour qu'ils fassent toujours la
	// même taille dans le fichier binaire
	
	// on rajoute des espaces après les caractères pour atteindre le nombre de
	// caractères défini plus haut (lignes 15 à 19)
	public String nomLong() {
		String nomLong = "";
		if (nom.length() < TAILLE_NOM_MAX) {
			nomLong = nom;
			for (int i = nom.length(); i < TAILLE_NOM_MAX; i++) {
				nomLong += " ";
			}
		} else {
			nomLong = nom.substring(0, TAILLE_NOM_MAX);
		}
		return nomLong;
	}

	//les vides servent à la méthode de suppression pour effacer les informations du stagiaire
	public String nomLongVide() {
		String nomLongVide = "";
		for (int i = 0; i < TAILLE_NOM_MAX; i++) {
			nomLongVide += " ";
		}
		return nomLongVide;
	}

	public String prenomLong() {
		String prenomLong = "";
		if (prenom.length() < TAILLE_PRENOM_MAX) {
			prenomLong = prenom;
			for (int i = prenom.length(); i < TAILLE_PRENOM_MAX; i++) {
				prenomLong += " ";
			}
		} else {
			prenomLong = prenom.substring(0, TAILLE_PRENOM_MAX);
		}
		return prenomLong;
	}

	public String prenomLongVide() {
		String prenomLongVide = "";
		for (int i = 0; i < TAILLE_PRENOM_MAX; i++) {
			prenomLongVide += " ";
		}
		return prenomLongVide;
	}

	public String deptLong() {
		String deptLong = "";
		if (dept.length() < TAILLE_DEPT_MAX) {
			deptLong = dept;
			for (int i = dept.length(); i < TAILLE_DEPT_MAX; i++) {
				deptLong += " ";
			}
		} else {
			deptLong = dept.substring(0, TAILLE_DEPT_MAX);
		}
		return deptLong;
	}

	public String deptLongVide() {
		String deptLongVide = "";
		for (int i = 0; i < TAILLE_DEPT_MAX; i++) {
			deptLongVide += " ";
		}
		return deptLongVide;
	}

	public String promoLong() {
		String promoLong = "";
		if (promo.length() < TAILLE_PROMO_MAX) {
			promoLong = promo;
			for (int i = promo.length(); i < TAILLE_PROMO_MAX; i++) {
				promoLong += " ";
			}
		} else {
			promoLong = promo.substring(0, TAILLE_PROMO_MAX);
		}
		return promoLong;
	}

	public String promoLongVide() {
		String promoLongVide = "";
		for (int i = 0; i < TAILLE_PROMO_MAX; i++) {
			promoLongVide += " ";
		}
		return promoLongVide;
	}

	// Getters et Setters
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getPromo() {
		return promo;
	}

	public void setPromo(String promo) {
		this.promo = promo;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	// Méthode toString()
	public String toString() {
		return "nom : " + nom + ", prénom : " + prenom + ", département : " + dept + ", promotion : " + promo
				+ ", année : " + annee + "\n";
	}

	@Override
	public int hashCode() {
		return Objects.hash(annee, dept, nom, prenom, promo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stagiaire other = (Stagiaire) obj;
		return annee == other.annee && Objects.equals(dept, other.dept) && Objects.equals(nom, other.nom)
				&& Objects.equals(prenom, other.prenom) && Objects.equals(promo, other.promo);
	}

}

