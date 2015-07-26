/**
 * Classe permettant d'interagir et de transmettre les données au niveau des différentes fenetres
 * @author trilunaire
 */
public class Interaction{
	static private FListeReservation fenListe;
	static private FAjoutReserv fenAjout;
	static private FenetreEmplacement femplacement;
	
	/**
	 * Permet de régler la fenetre qui résume l'emplacement
	 * @param fe
	 */
	static public void set_femplacement(FenetreEmplacement fe){
		femplacement=fe;
	}
	
	/**
	 * Méthode qui sera appellé dans le constructeur de la fenetre de la liste des réservations
	 * @param fl la fenetre de la liste des réservations qui sera choisi
	 */
	static public void set_fenList(FListeReservation fl){//méthode qui sera appellé lors de la création d'une fenetre Emplacement
		fenListe=fl;
	}
	
	/**
	 * Lance une fenetre permettant de saisir des informations pour réserver (appelé lors d'un appuis sur le bouton 'ajouter' dans la fenetre de la liste des reservations)
	 * @param e l'emplacement qui sera transmit dans la fenetre d'ajout
	 */
	static public void demandeAjouter(Emplacement e){ //quand on appuiera sur le boutons ajouter
		fenAjout=new FAjoutReserv(e);
		fenAjout.setVisible(true);
	}

	/**
	 * Permet d'ajouter une reservation dans la fenetre de la liste (appelé lors de la validation d'une nouvelle réservation
	 * @param new_Reserv la réservation à ajouter
	 */
	static public void ajouterReservation(Reservation new_Reserv){
		fenListe.ajouterReservation(new_Reserv);
		Emplacement emp = fenListe.get_monEmplacement();
		if(emp.getCurrentReservation()!=null){//dans le cas ou l'on à ajouté une reservation pour la date courante
			emp.updateButtonColor();
			femplacement.majInfo();
		}
	}
	
	static public void reservSupp(){
		femplacement.majInfo();
	}
}