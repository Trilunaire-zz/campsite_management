import java.io.Serializable;
import java.util.Vector;


/**
* Classe définissant un client
* @author Tristan Laurent
* @version 1
*/
@SuppressWarnings("serial")
public class Client implements Serializable{
	private String nom; //a rassembler en HashMap<numero,nom>
	private String genre; //choix entre homme et femme(pour afficher Mr ou Mme)
	private Vector<Reservation> lesReservations; //un client peut avoir plusieurs reservations à son nom
	private static int numero;//le numero sera déduit automatiquement
	
	/**
	* Constructeur prenant en paramètre le numero et le nom d'un client
	* @param String le nom et le numero du client
	*/
	@SuppressWarnings("static-access")
	public Client(String nom){
		this.numero++;
		this.nom=nom;
		this.genre="";
		InfoCamping.ajoutClientListe(this);
		InfoCamping.affListeCli();
		//on fera appel à la méthode pour choisir le genre dans l'interface graphique
	}

	
	/**
	* Retourne le genre d'un Client
	* @return String genre
	*/
	public String get_genre(){
		return this.genre;
	}
	
	/**
	* Regle le genre d'une instance de client à homme
	*/
	public void set_genre_homme(){
		this.genre = "Mr";
		InfoCamping.majClientInfo(this);
	}

	/**
	* Regle le genre d'une instance de client à femme
	*/
	public void set_genre_femme(){
		this.genre = "Mme";
		InfoCamping.majClientInfo(this);
	}
	
	/**
	* Retourne le numero d'une instance de Client
	* @return int numero
	*/
	@SuppressWarnings("static-access")
	public int get_numero(){
		return this.numero;
	}
	
	/**
	* Modifie le numero d'une instance de Client à partir d'un numero en parametre
	* @param int new_numero
	*/
	@SuppressWarnings("static-access")
	public void set_numero(int new_numero){
		this.numero = new_numero;
		InfoCamping.majClientInfo(this);
	}
	
	/**
	* Accesseur permettant de récupérer le nom d'une instance de Client
	* @return String nom
	*/ 
	public String get_nom(){
		return this.nom;
	}
	
	/**
	* Accesseur utilisé pour modifier le nom d'un client
	* @param String le nouveau nom du client
	*/
	public void set_nom(String new_nom){
		this.nom = new_nom;
		InfoCamping.majClientInfo(this);
	}

	/**
	* Méthode pour afficher le genre et le nom d'un client
	* @return String
	*/
	public String toString(){
		return this.get_genre()+" "+this.get_nom();
	}

	/**
	* Méthode permettant d'ajouter une réservation pour un Client
	* @param Reservation la nouvelle réservation qui sera ajoutée pour le Client
	*/
	public void addReservation(Reservation newReservation){//créer une exception pour renvoyer un message en cas d'erreur
		if(!lesReservations.contains(newReservation)){//on verifie si ça contient déjà 
			lesReservations.add(newReservation);
			newReservation.set_client(this);//on supprime le client de la reservation
			InfoCamping.majClientInfo(this);
		}else{
			//on propage une exception
		}
	}

	/**
	* Méthode qui permet d'enlever une réservation pour un client
	* @param Reservation la réservation à enlever
	*/
	public void removeReservation(Reservation oldReservation){
		lesReservations.remove(oldReservation);
		oldReservation.set_client(null);
		InfoCamping.majClientInfo(this);
	}
	
	/**
	* Méthode qui retourne la liste de toutes les réservation du client sous forme de caractère
	* @return String la liste des réservations
	*/
	public String listReservation(){
		String allReserv="";
		for(int i=0; i<lesReservations.size(); i++){
			allReserv=allReserv+lesReservations.get(i)+"\n";//on retourne les reservations sous forme de chaines de caractère
		}
		return allReserv;
	}
}