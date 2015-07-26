import java.util.Vector;

/**
 * Classe qui servira à stoquer toutes les donné concernant le camping, c'àd les clients et les Emplacements
 * @author trilunaire
 */
public class InfoCamping {
	private static Vector<Client> clientsCamping;
	
	public InfoCamping(){
		clientsCamping=new Vector<Client>();
	}
	/**
	* Retourne la valeur de clientsCamping
	* @return clientsCamping Vector<Client>
	*/
	@SuppressWarnings("static-access")
	public Vector<Client> get_clientsCamping(){
		return this.clientsCamping;
	}
	
	/**
	* Modifie la valeur de clientsCamping
	* @param new_clientsCamping Vector<Client>
	*/
	@SuppressWarnings("static-access")
	public void set_clientsCamping(Vector<Client> new_clientsCamping){
		this.clientsCamping = new_clientsCamping;
	}
	
	static public void ajoutClientListe(Client cli){
		if(!clientsCamping.contains(cli)){
			clientsCamping.add(cli);
		}else{
			//faire une verif, et mettre un message d'erreur si le client existe déjà
		}
	}
	
	static public void supprimerClientListe(Client cli){
		if(clientsCamping.contains(cli)){
			clientsCamping.remove(cli);
		}else{
			//rien pour l'instant
		}
	}
	
	static public void majClientInfo(Client cli){
		clientsCamping.remove(cli);
		clientsCamping.add(cli);
	}
	
	static public void affListeCli(){
		for(int i=0; i<clientsCamping.size(); i++){
			System.out.println(clientsCamping.get(i).toString());
		}
	}
}
