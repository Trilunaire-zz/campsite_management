import javax.swing.JButton;

/**
 * La classe qui permet de creer des instances d'association entre bouton et emplacement
 * @author Simon Brigant
 */
class ButtonEmp {
	private Emplacement emp;
	private JButton bouton;
	
	/**
	* Constructeur associant un JButton à un emplacement
	* @param Emplacement l'emplacement associé au JButton
	* @param JButton le JButton associé à l'emplacement
	*/
	public ButtonEmp(Emplacement e, JButton j){
		this.emp = e;
		this.bouton = j;
	}
	
	/**
	* L'accesseur pour récupérer l'emplacement
	* @return Emplacement l'emplacement associé au JButton
	*/
	public Emplacement get_emp(){
		return this.emp;
	}

	/**
	* L'accesseur qui permet de changer l'emplacement
	* @param Emplacement l'emplacement à changer
	*/
	public void set_emp(Emplacement e){
		this.emp = e;
	}

	/**
	* Accesseur qui permet de récupérer le JButton
	* @return JButton le JButton à récupérer
	*/
	public JButton get_bouton(){
		return this.bouton;
	}

	/**
	* Accesseur qui permet de changer le Jbutton
	* @param JButton le nouveau JButton
	*/
	public void set_bouton(JButton j){
		this.bouton = j;
	}
}
