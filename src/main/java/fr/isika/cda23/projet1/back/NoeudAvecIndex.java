package fr.isika.cda23.projet1.back;

//classe objet qui permet de stocker les informations d'un noeud, de son index et de l'index de son parent
//sert pour la méthode de suppression
public class NoeudAvecIndex {

	// Attributs
	public Noeud noeud;
	public int index;
	public int indexParent;

	//cosntructeurs
	public NoeudAvecIndex(Noeud noeud, int index, int indexParent) {
		this.noeud = noeud;
		this.index = index;
		this.indexParent = indexParent;
	}

	public NoeudAvecIndex() {
		this.noeud = null;
		this.index = -1;
		this.indexParent = -1;
	}

	
	@Override
	public String toString() {
		return "NoeudAvecIndex [index=" + index + ", indexParent=" + indexParent + "]";
	}

}

