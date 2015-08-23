import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Vector;

/**
 * Classe qui servira à stocker toutes les données concernant le camping, c'àd les clients et les Emplacements (les reservations étant contenues dans les emplacements)
 * @author trilunaire
 * @see Serializable
 */
@SuppressWarnings("serial")
public class InfoCamping implements Serializable{
	static private Vector<Client> clientsCamping;
	static private Vector<Emplacement> empCamp;

	static public void init(){
		clientsCamping=new Vector<Client>();
		empCamp=new Vector<Emplacement>();
	}

	/**
	* Retourne la valeur de clientsCamping
	* @return clientsCamping Vector<Client>
	*/
	static public Vector<Client> get_clientsCamping(){
		return clientsCamping;
	}

	/**
	* Modifie la valeur de clientsCamping
	* @param new_clientsCamping Vector<Client>
	*/
	@SuppressWarnings("static-access")
	public void set_clientsCamping(Vector<Client> new_clientsCamping){
		this.clientsCamping = new_clientsCamping;
	}

	/**
	* Retourne la valeur de empCamp
	* @return empCamp Vector<Emplacement>
	*/
	static public Vector<Emplacement> get_empCamp(){
		return empCamp;
	}

	/**
	* Modifie la valeur de empCamp
	* @param new_empCamp Vector<Emplacement>
	*/
	static public void set_empCamp(Vector<Emplacement> new_empCamp){
		empCamp = new_empCamp;
	}

	/**
	* Permet d'ajouter un client à la liste globale de clients (méthode appelée lors de la création d'un client)
	* @param cli le client qui sera ajouté à la liste
	*/
	static public void ajoutClientListe(Client cli){
		if(!clientsCamping.contains(cli)){
			clientsCamping.add(cli);
		}else{
			//faire une verif, et mettre un message d'erreur si le client existe déjà
		}
	}

	/**
	* Permet de supprimer un client à la liste globale de clients (méthode appelée lors de la suppression on d'un client)
	* @param cli le client qui sera supprimé à la liste
	*/
	static public void supprimerClientListe(Client cli){
		if(clientsCamping.contains(cli)){
			clientsCamping.remove(cli);
		}else{
			//rien pour l'instant
		}
	}

	/**
	* méthode appelée lors de la modification d'un client
	* @param cli le client qui doit être mis à jour
	*/
	static public void majClientInfo(Client cli){
		clientsCamping.set(clientsCamping.indexOf((Client)cli),cli);
	}


	/**
	* Permet d'afficher la liste de tous les clients
	*/
	static public void affListeCli(){
		for(int i=0; i<clientsCamping.size(); i++){
			System.out.println(clientsCamping.get(i).toString());
		}
	}

	/**
	* Permet d'ajouter un emplacement à la liste globale des emplacements (méthode appelée lors de la création d'un emplacement)
	* @param emp l'emplacement qui sera ajouté à la liste
	*/
	static public void ajoutEmpListe(Emplacement emp){
		if(!empCamp.contains(emp)){
			empCamp.add(emp);
		}
	}

	/**
	* Permet de supprimer un emplacement à la liste globale des emplacements (méthode appelée lors de la suppression d'un emplacement)
	* @param emp l'emplacement qui sera supprimé à la liste
	*/
	static public void suppEmpListe(Emplacement emp){
		if(empCamp.contains(emp)){
			empCamp.remove(emp);
		}
	}

	/**
	* méthode appelée lors de la modification des informations d'un emplacements
	* @param emp: l'emplacement à mettre à jour
	*/
	static public void majEmpInfo(Emplacement emp){
		empCamp.set(empCamp.indexOf((Emplacement)emp),emp);
	}

	/**
	* permet d'afficher la liste de tous les emplacements (avec le numero)
	*/
	static public void affListeEmp(){
		for(int i=0; i<empCamp.size(); i++){
			System.out.println("Emplacement n°"+empCamp.get(i).get_num()+" enregistré");
		}
	}

	/**
	* Permet d'enregistrer l'objet InfoCamping sous la forme de deux vecteurs dans un fichier dont le chemin absolu est donné en paramètre
	* @param fileName le chemin absolu du fichier qui sera enregistré
	* @throws IOException
	*/
	static public void enregistrer(String fileName) throws IOException{
		FileOutputStream fos=new FileOutputStream(fileName);
		ObjectOutputStream oos=new ObjectOutputStream(fos);

		oos.writeObject(get_clientsCamping());
		oos.writeObject(get_empCamp());

		oos.close();
	}

	/**
	* Méthode qui permet de lire un fichier afin d'en enregistrer les données
	* @param fileName le nom du fichier à lire
	* @throws ClassNotFoundException, IOException
	*/
	@SuppressWarnings("unchecked")
	static public void lire(String fileName) throws IOException, ClassNotFoundException{
		FileInputStream fis=new FileInputStream(fileName);
		ObjectInputStream ois=new ObjectInputStream(fis);

		clientsCamping= (Vector<Client>)ois.readObject();
		empCamp=(Vector<Emplacement>)ois.readObject();

		ois.close();
	}

	static public void vider(){
		clientsCamping.clear();
		empCamp.clear();
	}
}
