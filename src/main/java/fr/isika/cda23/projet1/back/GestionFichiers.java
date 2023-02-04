package fr.isika.cda23.projet1.back;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;

public class GestionFichiers {

	// méthode pour l'extraction des données du fichier .don
		// chaque stagiaire est lu et ses informations sont stockées dans un objet
		// stagiaire qui est ajouté à une arraylist
		public static void Lecture(ArrayListStagiaire liste) {
			try {
				// objet FR pour lire le fichier (path relatif à modifier chez vous pour faire
				// correspondre à votre emplacement)
				FileReader fr = new FileReader("src/main/java/fichiers/STAGIAIRES.DON");

				// objet BR pour lire plusieurs caractères à la suite
				BufferedReader br = new BufferedReader(fr);

				String separationStagiaire;
				String nom = "";
				String prenom = "";
				String dept = "";
				String promo = "";
				String Sannee = "";
				int annee = 0;

				while (br.ready()) {
					// stockage du contenu lu dans les 5 variables
					nom = br.readLine();
					prenom = br.readLine();
					dept = br.readLine();
					promo = br.readLine();
					Sannee = br.readLine();
					annee = Integer.valueOf(Sannee);
					separationStagiaire = br.readLine(); // stocke juste la * qui sépare les infos

					// Attribution de ces variables à un objet Stagiaire
					Stagiaire stagiaire = new Stagiaire(nom, prenom, dept, promo, annee);
					liste.addStagiaire(stagiaire);
				}

				// Fermeture du flux
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Le fichier doit être réécrit à chaque fois qu'on utilise l'arbre binaire
		// (donc on l'efface à chaque fois qu'on veut lancer le main pour repartir d'un
		// fichier vide
		public static void EcritureBinaire(Noeud noeud, int index) {

			// permet d'écrire les informations contenues dans l'arbre binaire dans un
			// fichier binaire
			// il contient des objets de type Noeud (qui contient les informations
			// Stagiaire, fils gauche, fils droit et suivant)

			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile("src/main/java/fichiers/stagiaires.bin", "rw");
				// écriture du nom (méthode dans classe Stagiaire)
				raf.seek(index);
				raf.writeChars(noeud.stagiaire.nomLong());// 42 octets
				// idem pour les autres String
				raf.writeChars(noeud.stagiaire.prenomLong()); // 40 octets
				raf.writeChars(noeud.stagiaire.deptLong()); // 6 octets
				raf.writeChars(noeud.stagiaire.promoLong()); // 22 octets
				// écriture de l'année et des index des fils gauche fils droit
				raf.writeInt(noeud.stagiaire.getAnnee()); // 4 octets
				raf.writeInt(noeud.fg); // 4 octets
				raf.writeInt(noeud.fd); // 4 octets
				raf.writeInt(noeud.suivant); // 4 octets
				// soit une taille totale de 126 octets pour chaque noeud

				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public static void LectureBinaire(int index) {
			// permet d'aller lire les informations d'un noeud dans le fichier binaire à
			// partir de son index (sa position dans le fichier)
			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile("src/main/java/fichiers/stagiaires.bin", "rw");
				raf.seek(index * Noeud.TAILLE_OBJET_OCTET);
				String nomLu = "";
				for (int i = 0; i < Stagiaire.TAILLE_NOM_MAX; i++) {
					nomLu += raf.readChar();
				}
				String prenomLu = "";
				for (int i = 0; i < Stagiaire.TAILLE_PRENOM_MAX; i++) {
					prenomLu += raf.readChar();
				}
				String deptLu = "";
				for (int i = 0; i < Stagiaire.TAILLE_DEPT_MAX; i++) {
					deptLu += raf.readChar();
				}
				String promoLue = "";
				for (int i = 0; i < Stagiaire.TAILLE_PROMO_MAX; i++) {
					promoLue += raf.readChar();
				}
				int anneeLue;
				anneeLue = raf.readInt();
				int fgLu;
				fgLu = raf.readInt();
				int fdLu;
				fdLu = raf.readInt();
				int suivantLu;
				suivantLu = raf.readInt();

				System.out.println("Le stagiaire numéro " + index + " est nom : " + nomLu + ", prénom : " + prenomLu
						+ ", département(ou pays) : " + deptLu + ", promo : " + promoLue + ", année : " + anneeLue
						+ ". L'index fg :" + fgLu + ", l'index fd : " + fdLu + " et le suivant : " + suivantLu);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public static Noeud LectureNoeud(int index) {
			// permet d'aller rechercher un noeud et de le renvoyer grâce à son index (sa
			// position dans le fichier)
			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile("src/main/java/fichiers/stagiaires.bin", "rw");
				raf.seek(index * Noeud.TAILLE_OBJET_OCTET);
				String nomLu = "";
				for (int i = 0; i < Stagiaire.TAILLE_NOM_MAX; i++) {
					nomLu += raf.readChar();
				}
				String prenomLu = "";
				for (int i = 0; i < Stagiaire.TAILLE_PRENOM_MAX; i++) {
					prenomLu += raf.readChar();
				}
				String deptLu = "";
				for (int i = 0; i < Stagiaire.TAILLE_DEPT_MAX; i++) {
					deptLu += raf.readChar();
				}
				String promoLue = "";
				for (int i = 0; i < Stagiaire.TAILLE_PROMO_MAX; i++) {
					promoLue += raf.readChar();
				}
				int anneeLue;
				anneeLue = raf.readInt();
				int fgLu;
				fgLu = raf.readInt();
				int fdLu;
				fdLu = raf.readInt();
				int suivantLu;
				suivantLu = raf.readInt();
				Stagiaire stagiaire = new Stagiaire(nomLu, prenomLu, deptLu, promoLue, anneeLue);
				Noeud noeud = new Noeud(stagiaire, fgLu, fdLu, suivantLu);
				raf.close();
				return noeud;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		// méthode permettant de lire le nom d'un noeud
		public static String LectureNom(int index) {
			String nomLu = "";
			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile("src/main/java/fichiers/stagiaires.bin", "rw");
				raf.seek((index) * Noeud.TAILLE_OBJET_OCTET);
				for (int i = 0; i < Stagiaire.TAILLE_NOM_MAX; i++) {
					nomLu += raf.readChar();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return nomLu;
		}

		// méthode permettant de lire l'index (la position dans le fichier) du fils
		// gauche d'un noeud (index -1 si pas de fils gauche)
		public static int LectureFG(int index) {
			int fg = 0;
			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile("src/main/java/fichiers/stagiaires.bin", "rw");
				raf.seek(index * Noeud.TAILLE_OBJET_OCTET + 114); // 114 position de l'int fg
				fg = raf.readInt();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return fg;
		}

		// méthode permettant de lire l'index (la position dans le fichier) du fils
		// droit d'un noeud (index -1 si pas de fils droit)
		public static int LectureFD(int index) {
			int fd = 0;
			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile("src/main/java/fichiers/stagiaires.bin", "rw");
				raf.seek(index * Noeud.TAILLE_OBJET_OCTET + 118); // 118 position de l'int fd
				fd = raf.readInt();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return fd;
		}

		// méthode permettant de lire l'index (la position dans le fichier) du suivant
		// (liste chainée en cas de doublon de nom de stagiaire) d'un noeud (index -1 si
		// pas de suivant)
		public static int LectureSuivant(int index) {
			int suivant = 0;
			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile("src/main/java/fichiers/stagiaires.bin", "rw");
				raf.seek(index * Noeud.TAILLE_OBJET_OCTET + 122); // 122 position de l'int suivant
				suivant = raf.readInt();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return suivant;
		}

		// méthode permettant d'écrire l'index du fils gauche d'un noeud à l'ajout
		public static void EcritureFG(int index) {
			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile("src/main/java/fichiers/stagiaires.bin", "rw");
				// On se place à la position du fils gauche du noeud à l'index entré en argument
				raf.seek(index * Noeud.TAILLE_OBJET_OCTET + 114);
				int dernierIndex = (int) (raf.length() / Noeud.TAILLE_OBJET_OCTET);
				raf.writeInt(dernierIndex); // index du nouveau noeud

				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// méthode permettant de modifier l'index du fils gauche d'un noeud en cas de
		// suppression
		public static void ModifierFG(int index, int nouvelIndex) {
			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile("src/main/java/fichiers/stagiaires.bin", "rw");
				// On se place à la position du fils gauche du noeud à l'index entré en argument
				raf.seek(index * Noeud.TAILLE_OBJET_OCTET + 114);
				raf.writeInt(nouvelIndex); // index du nouveau noeud
				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// méthode permettant d'écrire l'index du fils droit d'un noeud à l'ajout
		public static void EcritureFD(int index) {
			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile("src/main/java/fichiers/stagiaires.bin", "rw");
				raf.seek(index * Noeud.TAILLE_OBJET_OCTET + 118);
				int dernierIndex = 0;
				dernierIndex = (int) (raf.length() / Noeud.TAILLE_OBJET_OCTET);
				raf.writeInt(dernierIndex); // index du nouveau noeud
				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// méthode permettant de modifier l'index du fils droit d'un noeud en cas de
		// suppression
		public static void ModifierFD(int index, int nouvelIndex) {
			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile("src/main/java/fichiers/stagiaires.bin", "rw");
				// On se place à la position du fils gauche du noeud à l'index entré en argument
				raf.seek(index * Noeud.TAILLE_OBJET_OCTET + 118);
				raf.writeInt(nouvelIndex); // index du nouveau noeud
				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// méthode permettant d'écrire l'index du suivant d'un noeud à l'ajout
		public static void EcritureSuivant(int index) {
			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile("src/main/java/fichiers/stagiaires.bin", "rw");
				raf.seek(index * Noeud.TAILLE_OBJET_OCTET + 122);
				int dernierIndex = 0;
				dernierIndex = (int) (raf.length() / Noeud.TAILLE_OBJET_OCTET);
				raf.writeInt(dernierIndex); // index du nouveau noeud
				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// méthode permettant de modifier l'index du suivant d'un noeud en cas de
		// suppression
		public static void ModifierSuivant(int index, int nouvelIndex) {
			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile("src/main/java/fichiers/stagiaires.bin", "rw");
				// On se place à la position du fils gauche du noeud à l'index entré en argument
				raf.seek(index * Noeud.TAILLE_OBJET_OCTET + 122);
				raf.writeInt(nouvelIndex); // index du nouveau noeud
				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// méthode renvoyant la position du dernier index dans le fichier binaire
		public static int PositionDernierIndex() {
			RandomAccessFile raf;
			int posDernierIndex = 0;
			try {
				raf = new RandomAccessFile("src/main/java/fichiers/stagiaires.bin", "rw");
				posDernierIndex = (int) (raf.length());

				raf.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return posDernierIndex;
		}

		// méthode permettant d'effacer les informations d'un noeud (remplace les
		// informations par une suite d'espaces " "
		public static void supprimerNoeud(Noeud noeudASup, int index) {
			RandomAccessFile raf;
			try {
				raf = new RandomAccessFile("src/main/java/fichiers/stagiaires.bin", "rw");
				raf.seek(index * Noeud.TAILLE_OBJET_OCTET);

				raf.writeChars(noeudASup.stagiaire.nomLongVide());
				raf.writeChars(noeudASup.stagiaire.prenomLongVide());
				raf.writeChars(noeudASup.stagiaire.deptLongVide());
				raf.writeChars(noeudASup.stagiaire.promoLongVide());
				raf.writeInt(0);
				raf.writeInt(-1);
				raf.writeInt(-1);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

