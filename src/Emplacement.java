import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Vector;

/**
* @Description La classe qui definit les emplacements.
* @author Tristan Laurent and Simon Brigant
*/
@SuppressWarnings("serial")
public class Emplacement implements Serializable{
	private int num;
	private boolean libre;
	private boolean vide;
	private Vector<Reservation> lesReserv;
	private ButtonEmp associationBouton;

	/**
	* Constructeur d'un emplacement à partir d'un chiffre
	* @param int i
	*/
	public Emplacement(int i){
		this.lesReserv= new Vector<Reservation>();
		this.num = i;
		this.libre = true;
		this.vide = true;
		InfoCamping.ajoutEmpListe(this);
	}

	/**
	* Accesseur qui renvois la liste des réservation associées à cet emplacement
	* @return Vector<Reservation>
	*/
	public Vector<Reservation> getListeReserv(){
		return lesReserv;
	}

	/**
	 * Accesseur qui permet de changer la valeur du vecteur de réservation
	 * @param le nouveau vecteur de reservation
	 */
	public void setListeReserv(Vector<Reservation> new_v){
		this.lesReserv=new_v;
		InfoCamping.majEmpInfo(this);
	}

	/**
	* Une méthode qui ajoute une reservation dans la liste des réservations
	* @param Reservation new_reservation la réservation à ajouter
	*/
	public void addReserv(Reservation new_reservation){
		if(this.lesReserv.contains(new_reservation)){
			System.out.println("Cette reservation existe déjà!");//on affichera un message d'erreur avec une JPopup
		}else{
			this.lesReserv.add(new_reservation);
			System.out.println("Reservation ajoute"); //on affichera un message de confirmation
			InfoCamping.majEmpInfo(this);
		}
	}

	/**
	* Méthode d'instance qui supprime une réservation de la liste
	* @param Reservation La réservation à enlever
	*/
	public void removeReserv(Reservation old_Reservation){
		if(this.lesReserv.contains(old_Reservation)){
			this.lesReserv.remove(old_Reservation);
			System.out.println("La reservation a ete supprime");
			InfoCamping.majEmpInfo(this);
		}else{
			System.out.println("Reservation inexistante pour le moment");
		}
	}

	/**
	* Méthode d'instance qui renvois la réservation courante si elle existe et null dans le cas contraire, et met à jour l'état en fonction de la réservation
	* @return Reservation la réservation correspondant à la date courante
	*/
	public Reservation getCurrentReservation(){
		Reservation reservCourante=null;

		Calendar currentDate=GestionTemp.get_CalendarToday();
		this.libre=true;
		boolean reserve = false;
		int i= 0;
		//on met le calendrier à 23h59, car lors de la comparaison , même les minutes comptent
		currentDate.set(Calendar.HOUR, 23);
		currentDate.set(Calendar.MINUTE, 59);

		while(i<lesReserv.size() && !reserve){//pour toutes les Reservations on va voir si on en a une aujourd'hui
			if((lesReserv.get(i).get_CalendarArrive().compareTo(currentDate)<0) && (lesReserv.get(i).get_CalendarDepart().compareTo(currentDate)>0)){
				reserve=true;
				reservCourante=lesReserv.get(i);
				this.libre=false;
				System.out.println("Reservation courante!!");
			}
			i++;
		}

		return reservCourante;
	}

	/**
	* Accesseur pour obtenir le numero
	* @return int le numero de l'emplacement
	*/
	public int get_num(){
		return this.num;
	}

	/**
	* Accesseur pour régler le numéro de l'emplacement
	* @param int new_num
	*/
	public void set_num(int new_num){
		this.num = new_num;
		InfoCamping.majEmpInfo(this);
	}

	/**
	* Accesseur pour voir si l'emplacement est vide
	* @return boolean vide
	*/
	public boolean get_vide(){
		return this.vide;
	}

	/**
	* Accesseur pour remplir ou vider l'emplacement
	* @param boolean new_vide
	*/
	public void set_vide(boolean new_vide){
		this.vide = new_vide;
		InfoCamping.majEmpInfo(this);
	}

	/**
	* Accesseur pour voir si l'emplacement est libre
	* @return boolean libre
	*/
	public boolean get_libre(){
		return this.libre;
	}

	/**
	* Accesseur pour libérer ou occuper l'emplacement
	* @param boolean new_libre
	*/
	public void set_libre(boolean new_libre){
		this.libre = new_libre;
		InfoCamping.majEmpInfo(this);
	}

	/**
	* Retourne la valeur de associationBouton
	* @return ButtonEmp le ButtonEmp associant un JButton à un Emplacement
	*/
	public ButtonEmp get_associationBouton(){
		return this.associationBouton;
	}

	/**
	* Modifie la valeur de associationBouton
	* @param ButtonEmp la nouvelle association
	*/
	public void set_associationBouton(ButtonEmp new_associationBouton){
		this.associationBouton = new_associationBouton;
		InfoCamping.majEmpInfo(this);
	}

	/**
	* Renvois un vecteur contenant tous les jours ou l'emplacement est reservé pour le Calendar que l'on lui donne
	* @param cal: le calendrier à partir duquel on souhaite obtenir les jours réservés
	*/
	public Vector<String> reservDaysInMonth(Calendar cal){//ne revois pas encore les jours dans une reservation débutant dans un mois précédent
		int nbreJourEntre=0;
		Vector<String> joursReserves = new Vector<String>();
		Reservation resTmp; //va nous éviter de ré-appeler la méthode get de Vector
		Calendar calTmp;


		for (int i=0; i<lesReserv.size(); i++) {
			resTmp=lesReserv.get(i);

			//maintenant qu'on a une reservation, on regarde si la date de début ou de fin de reservation sont incluses dans le mois donné en param
			if((resTmp.get_CalendarArrive().get(Calendar.YEAR)==cal.get(Calendar.YEAR) && resTmp.get_CalendarArrive().get(Calendar.MONTH)==cal.get(Calendar.MONTH)) || (resTmp.get_CalendarDepart().get(Calendar.YEAR)==cal.get(Calendar.YEAR) && resTmp.get_CalendarDepart().get(Calendar.MONTH)==cal.get(Calendar.MONTH))){

				nbreJourEntre=GestionTemp.nbJ_betweenTwoDates(resTmp.get_CalendarArrive(), resTmp.get_CalendarDepart());
				calTmp=(Calendar) resTmp.get_CalendarArrive().clone(); //on prends la date d'arrivé du client

				for(int j=0; j<=nbreJourEntre; j++){
					if(calTmp.get(Calendar.YEAR)==cal.get(Calendar.YEAR)){//si le calendrier temporaire (initialisé à la valeur d'arrivé du client) correspond au mois de la reservation, on ajout le premier jour, et on l'incrémente
						if(calTmp.get(Calendar.MONTH)==cal.get(Calendar.MONTH)){
							joursReserves.add(calTmp.get(Calendar.DAY_OF_MONTH)+"");
							calTmp.set(Calendar.DAY_OF_MONTH, calTmp.get(Calendar.DAY_OF_MONTH)+1);
						}else{//sinon on incrémente juste le jour
							calTmp.set(Calendar.DAY_OF_MONTH, calTmp.get(Calendar.DAY_OF_MONTH)+1);
						}
					}else{
						calTmp.set(Calendar.DAY_OF_MONTH, calTmp.get(Calendar.DAY_OF_MONTH)+1);
					}
				}
			}
		}
		/*
		 * Permet de tester si les valeurs son bonnes
		for(int i=0; i<joursReserves.size();i++){
			System.out.println(joursReserves.get(i));
		}*/

		return joursReserves;
	}

	/**
	* Méthode qui met à jour la couleur de l'emplacement
	*/
	public void updateButtonColor(){
		ButtonEmp btmp=this.get_associationBouton();
		btmp.get_bouton().setBackground(this.getCouleur());
		this.set_associationBouton(btmp);
	}

	/**
	 * La méthode qui est appelé lorsqu'on veut ajouter un emplacement
	 * @param Vector<Integer> v : C'est le vecteur de numéro libre duquel on extrait un numéro qu'on assigne à l'emplacement.
	 * @param ButtonEmp bEmp
	 * @see Camping
	 */
	public void ajouter(Vector<Integer> v, ButtonEmp bEmp){
		int num;

		this.set_vide(false);
		this.set_libre(true);
		this.set_associationBouton(bEmp);
		num = Camping.getNumMin(v);
		bEmp.get_bouton().setText(""+num);
		this.set_num(num);
		bEmp.get_bouton().setBackground(Color.GREEN);
		bEmp.get_bouton().setForeground(Color.BLACK);

		InfoCamping.majEmpInfo(this);
	}

	/**
	 * La methode appelee lorsqu'on supprime un emplacement
	 * @param Vector<Integer> v Le vecteur de numero libre dans lequel on rajoute le numero de l'emplacement supprime.
	 * @param ButtonEmp bEmp Le bouton à modifier correspondant à l'emplacement séléctionné
	 * @see Camping
	 */
	public void supprimer(Vector<Integer> v, ButtonEmp bEmp){
		int n;

		this.set_vide(true);
		n = this.get_num();
		bEmp.get_bouton().setText("");
		this.set_num(0);
		this.set_associationBouton(bEmp);
		bEmp.get_bouton().setBackground(Color.WHITE);
		bEmp.get_bouton().setForeground(Color.WHITE);
		v.add(n); // on rajoute son numero dans le tableau de num libre
		InfoCamping.majEmpInfo(this);
	}

	/**
	 * La methode appelee lorsqu'on deplace un emplacement.
	 * @param Vector<Integer> num_libre: Le vecteur de numero libre.
	 * @param ButtonEmp selectionne: L'association qui correspond à l'emplacement à deplacer
	 * @param ButtonEmp destination: L'association qui correspond à l'emplacement de destination
	 * @return boolean Un booleen qui renvoie false pour mettre fin au mode deplacement.
	 * @see MouseEvent
	 */
	public boolean deplacer(Vector<Integer> num_libre, ButtonEmp selectionne, ButtonEmp destination){
		boolean d = true;
		int num;

		if( destination.get_emp().get_vide()==true ){
			num = selectionne.get_emp().get_num();
			/* ici c'est basiquement la methode "ajoute" sauf qu'on la modifie de telle facon qu'elle ne prenne pas le minimum
			mais le numero de l'emplacement deplace et on garde l'etat de l'emplacement deplace */
			destination.get_emp().setListeReserv(selectionne.get_emp().getListeReserv());
			destination.get_emp().set_vide(false);
			destination.get_emp().set_libre(selectionne.get_emp().get_libre());
			destination.get_bouton().setText(""+num);
			destination.get_emp().set_num(num);
			destination.get_bouton().setBackground(selectionne.get_emp().getCouleur());
			destination.get_bouton().setForeground(Color.BLACK);
			this.set_associationBouton(destination);

			//puis on nettois l'ancien emplacement
			selectionne.get_emp().supprimer(num_libre,selectionne);
			InfoCamping.majEmpInfo(selectionne.get_emp());
			num_libre.remove((Object)num);
			d = false;
			InfoCamping.majEmpInfo(this);
		}

		return d;
	}

	/**
	 * La methode qui renvoie la couleur d'arriere plan necessaire en fonction de l'etat de l'emplacement
	 * @return Color La couleur qu'on appliquera à l'emplacement.
	 */
	public Color getCouleur(){
		Color retour = Color.WHITE;

		//libre mais pas vide
		if(this.libre==true && this.vide==false){
			retour = Color.GREEN;
		}
		//pas libre et pas vide
		else if(this.libre==false && this.vide==false){
			retour = Color.RED;
		}
		return retour;
	}
}
