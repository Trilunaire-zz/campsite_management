import java.io.Serializable;
import java.util.Calendar;

/**
 * Classe définissant une réservation, qui comporte un client, une date d'arrivé et une date de départ
 * @author Tristan Laurent
 */
@SuppressWarnings("serial")
public class Reservation implements Serializable{
	private Client client;
	private Calendar arrive;
	private Calendar depart;
	
	/**
	 * Constructeur d'une reservation avec un client, un calendrier d'arrivé et de départ
	 * @param client
	 * @param arrive
	 * @param depart
	 */
	public Reservation(Client client, Calendar arrive, Calendar depart){
		this.client=client;
		this.arrive=arrive;
		this.depart=depart;
	}
	
	/**
	 * Constructeur d'une reservation initialisé avec aucun Client et les dates courantes
	 */
	public Reservation(){
		this.client=null;
		this.arrive=GestionTemp.get_CalendarToday();
		this.depart=GestionTemp.get_CalendarToday();
	}

	/**
	* Accesseur permettant de récupérer le calendrier correspondant à la date de départ du client
	* @return Calendar this.depart
	*/
	public Calendar get_CalendarDepart(){
		return this.depart;
	}
	
	/**
	* Accesseur permettant de gérer le calendrier correspondant à la date de départ
	* @param Calendar new_depart
	*/
	public void set_CalendarDepart(Calendar new_depart){
		this.depart = new_depart;
	}
	

	/**
	* Accesseur permettant de récupérer le calendrier correspondant à la date de d'arrivé du client
	* @return Calendar this.arrive
	*/
	public Calendar get_CalendarArrive(){
		return this.arrive;
	}
	
	/**
	* Accesseur permettant de gérer le calendrier correspondant à la date d'arrivé
	* @param Calendar new_arrive
	*/
	public void set_CalendarArrive(Calendar new_arrive){
		this.arrive = new_arrive;
	}
	

	/**
	* Accesseur permettant de récupérer le client associé à cette réservation
	* @return Client this.client
	*/
	public Client get_client(){
		return this.client;
	}
	
	/**
	* Accesseur permettant de gérer un client associé à cette réservation
	* @param Client new_client
	*/
	public void set_client(Client new_client){
		this.client = new_client;
	}
	

	/**
	* Retoune la date d'arrive du client sous la forme JJ/MM/AAAA
	* @return String
	*/
	public String getDateArrive(){
		return this.arrive.get(Calendar.DAY_OF_MONTH)+"/"+(this.arrive.get(Calendar.MONTH)+1)+"/"+this.arrive.get(Calendar.YEAR);
	}

	/**
	* Permet de gérer la date d'arrivé du client, utile dans le cas d'une modification de la reservation.
	* @param int jour
	* @param int mois
	* @param int annee
	*/
	public void setDateArrive(int jour, int mois, int annee){
		this.arrive.set(annee,mois,jour);
	}

	/**
	* Retourne la date de départ du client sous la forme JJ/MM/AAAA
	* @return String
	*/
	public String getDateDepart(){
		return this.depart.get(Calendar.DAY_OF_MONTH)+"/"+(this.depart.get(Calendar.MONTH)+1)+"/"+this.depart.get(Calendar.YEAR);
	}

	/**
	* Permet de regler la date de départ du client, utile dans le cas d'une modification
	* @param int jour
	* @param int mois
	* @param int annee
	*/
	public void setDateDepart(int jour, int mois, int annee){
		this.depart.set(annee, mois, jour);

	}
	
	/**
	 * Donne les informations d'une reservation
	 * @return Représentation d'une reservation sous forme d'un client, une date d'arrivé et une date de départ
	 */
	public String toString(){
		return this.client.toString()+" "+this.getDateArrive()+" "+this.getDateDepart();
	}
}