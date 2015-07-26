import java.util.Calendar;
import java.util.TimeZone;

/**
 * Classe qui permet de de gérer tous ce qui est en rapport avec le temps
 * @author Tristan Laurent
 */
public class GestionTemp {
	@SuppressWarnings("unused")
	private final String[] jourSemaine = {"Lundi","Mardi","Mercredi","Jeudi","Vendredi","Samedi","Dimanche"};
	
	/**
	 * Permet de donner la date courante
	 * @return String la date du jour sous forme JJ/MM/AAAA
	 */
	public static String get_dateToday(){
		Calendar date = Calendar.getInstance(TimeZone.getDefault());//on initialise un Calendar aux paramètres system
		int numeroJour=date.get(Calendar.DAY_OF_MONTH);//retourne le numero du mois courant
		int numeroMois=date.get(Calendar.MONTH)+1;//retourne le numero du mois, sachant que Janvier est le mois numero 0 (Java logic motherfucker \o/)
		int annee=date.get(Calendar.YEAR);//retourne l'année courante
		return numeroJour+"/"+numeroMois+"/"+annee;
	}
	
	/**
	* Méthode de classe qui retourne un Calendar initialisé à la date du jour
	* @return Calendar un calendar à la date courante
	*/
	public static Calendar get_CalendarToday(){
		return Calendar.getInstance(TimeZone.getDefault());//on initialise un Calendar aux paramètres system
	}
	
	/**
	 * Retourne le nombre de jours entre dateSup et dateInf
	 * @param Calendar la date de fin 
	 * @param Calendar la date de début
	 * @return int le nombre de jour entre ces deux dates
	 */
	public static int nbJ_betweenTwoDates(Calendar dateSup, Calendar dateInf){
		int nbreJour=0;
		int nbreAnnee=dateSup.get(Calendar.YEAR)-dateInf.get(Calendar.YEAR);
		int nbreAnneeBisextile=0;

		for (int i=0; i<nbreAnnee; i++) { //tant qu'on est pas allé de dateInf à dateSup
			if((dateInf.get(Calendar.YEAR)+i)%4==0){//si on a une année bisextile (les années bisextiles sont divisibles par 4)
				nbreAnneeBisextile++;
			}
		}

		nbreJour=(nbreAnnee-nbreAnneeBisextile)*365+nbreAnneeBisextile*366+(dateSup.get(Calendar.DAY_OF_YEAR)-dateInf.get(Calendar.DAY_OF_YEAR));
		
		if(nbreJour<0){
			nbreJour=-nbreJour;
		}

		return nbreJour;
	}
	/*private static String get_jourSemaine(){
		//pour l'instant je sais pas à partir de quoi on va renvoyer ça
	}*/

	/**
	* Méthode qui renvois le nombre de jours dans un mois
	* @param Calendar le calendar qui contiens le mois dont on veut savoir le nombre de jours
	* @return int le nombre de jours dans le mois
	*/
	public static int nbreJourDansMois(Calendar c){
		//on obtient le nombre de jours dans un mois en prenant le jour avant le mois suivant, en gros le dernier jour du mois:
		Calendar tmp=(Calendar) c.clone();
		tmp.set(tmp.get(Calendar.YEAR),tmp.get(Calendar.MONTH)+1,1);//on l'initialise au premier jour du mois suivant
		tmp.set(tmp.get(Calendar.YEAR),tmp.get(Calendar.MONTH),tmp.get(Calendar.DAY_OF_MONTH)-1);
		return tmp.get(Calendar.DAY_OF_MONTH);	
	}
	
	/**
	* Méthode qui renvois le numero du jour de la semaine d'un Calendar (parce que le système est basé sur les Americains, qui commence le dimanche)
	* @return int le numero du jour de la semaine
	*/
	public static int numeroJourSemaineEurope(Calendar c){//parce qu'on remercie les americains de pas faire comme tout le monde et de commencer la semaine le dimanche
		int jourSemaine=c.get(Calendar.DAY_OF_WEEK);
		if(jourSemaine==1){
			jourSemaine=7;
		}else{
			jourSemaine--;
		}
		return jourSemaine;
	}
	
	public static String calendarToString(Calendar cal){
		return cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR);
	}
	// public static void main(String[] args){
	// 	Calendar test=GestionTemp.get_CalendarToday();
	// 	test.set(2015,0,2);
	// 	test.set(test.get(Calendar.YEAR),test.get(Calendar.MONTH)-1,test.get(Calendar.DAY_OF_MONTH)-1);
	// 	System.out.println("Voici la date test: "+test.get(Calendar.DAY_OF_MONTH)+"/"+test.get(Calendar.MONTH)+"/"+test.get(Calendar.YEAR));
	// }
}
