import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
* Boite de dialogue qui permet d'ajouter une réservation
* @author Tristan Laurent
* @see JDialog
*/
@SuppressWarnings("serial")
public class FAjoutReserv extends JDialog{
	final private String[] GENRES={"Mr","Mme"};
	final private int ESPACE_BOUTON = 10;
	final private int H_TEXTFIELD=25;
	final private int W_TEXTFIELD=100;
	final private int W_DATEFIELD=56;
	private Calendar date_arrive;
	private Calendar date_depart;
	private Reservation nouvReserv;
	private Client nouvClient;
	private Emplacement emp;
	
	private JPanel panelP;

	private JTextField jourDepart;
	private JTextField moisDepart;
	private JTextField anneeDepart;
	private JTextField jourArrive;
	private JTextField moisArrive;
	private JTextField anneeArrive;
	private JTextField nomCli;
	
	private JLabel jlClient;
	private JLabel jlArrive;
	private JLabel jlDepart;
	
	private JButton ajouter;
	private JButton annuler;
	
	private JComboBox<String> choixGenre;
	
	/**
	* Constructeur de la boite de dialogue pour un emplacement et à l'année courante
	* @param Emplacement l'emplacement ou sera ajouté la réservation
	*/
	public FAjoutReserv(Emplacement e){
		this.emp=e;
		this.setTitle("Ajouter reservation");
		this.setSize(280,200);
		this.setMinimumSize(this.getSize());
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		this.jourArrive = new JTextField();
		this.moisArrive = new JTextField();
		this.anneeArrive = new JTextField(GestionTemp.get_CalendarToday().get(Calendar.YEAR)+"");
		this.anneeDepart = new JTextField(GestionTemp.get_CalendarToday().get(Calendar.YEAR)+"");
		
		this.date_arrive=GestionTemp.get_CalendarToday();
		this.date_depart=GestionTemp.get_CalendarToday();
		
		this.createGUI();
	}
	
	/**
	 * Constructeur appelé lorsque l'on selectionne une date d'arrivé
	 * @param e
	 * @param arrive
	 */
	public FAjoutReserv(Emplacement e, Calendar arrive){
		this.emp=e;
		this.setTitle("Ajouter reservation");
		this.setSize(280,200);
		this.setMinimumSize(this.getSize());
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		this.jourArrive = new JTextField(arrive.get(Calendar.DAY_OF_MONTH)+"");
		this.jourArrive.setEditable(false);
		this.moisArrive = new JTextField((arrive.get(Calendar.MONTH)+1)+"");
		this.moisArrive.setEditable(false);
		this.anneeArrive = new JTextField(arrive.get(Calendar.YEAR)+"");
		this.anneeArrive.setEditable(false);
		this.anneeDepart = new JTextField(arrive.get(Calendar.YEAR)+"");
		
		this.date_arrive=arrive;
		this.date_depart=GestionTemp.get_CalendarToday();
		
		this.createGUI();
	}
	
	/**
	* Méthode qui crée l'interface graphique
	*/
	public void createGUI(){
		panelP=new JPanel();
		
		panelP.setLayout(new BoxLayout(panelP, BoxLayout.Y_AXIS));
		panelP.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		panelP.add(this.client()); //on ajoute le client
		panelP.add(Box.createRigidArea(new Dimension(1,10)));//et on les sépare de 10px
		panelP.add(this.arrive()); //la date d'arrive
		panelP.add(Box.createRigidArea(new Dimension(1,10)));
		panelP.add(this.depart()); //et celle de départ
		panelP.add(Box.createVerticalGlue());
		panelP.add(this.bouttons());
		
		this.setContentPane(panelP);
	}
	
	/**
	* Méthode qui renvoie un JPanel permettant de saisir la date de départ du Client
	* @return JPanel le panel permettant de saisir la date de départ
	*/
	public JPanel depart(){
		JPanel pTmp = new JPanel();
		pTmp.setLayout(new BoxLayout(pTmp,BoxLayout.X_AXIS));
		
		jourDepart = new JTextField();
		moisDepart = new JTextField();
		

		jourDepart.setPreferredSize(new Dimension(W_DATEFIELD,H_TEXTFIELD));
		jourDepart.setMaximumSize(new Dimension(W_DATEFIELD,H_TEXTFIELD));
		moisDepart.setPreferredSize(new Dimension(W_DATEFIELD,H_TEXTFIELD));
		moisDepart.setMaximumSize(new Dimension(W_DATEFIELD,H_TEXTFIELD));
		anneeDepart.setPreferredSize(new Dimension(W_DATEFIELD,H_TEXTFIELD));
		anneeDepart.setMaximumSize(new Dimension(W_DATEFIELD,H_TEXTFIELD));

		jlDepart=new JLabel("Départ:");
		
		pTmp.add(jlDepart);
		pTmp.add(Box.createHorizontalGlue());
		pTmp.add(jourDepart);
		pTmp.add(Box.createRigidArea(new Dimension(2,1)));
		pTmp.add(moisDepart);
		pTmp.add(Box.createRigidArea(new Dimension(2,1)));
		pTmp.add(anneeDepart);
		return pTmp;
	}
	

	/**
	* Une méthode qui créer le panel pour saisir la date d'arrivé du client
	* @return JPanel le panel qui permet de saisir la date d'arrivé
	*/
	public JPanel arrive(){
		JPanel pTmp = new JPanel();
		pTmp.setLayout(new BoxLayout(pTmp,BoxLayout.X_AXIS));

		jourArrive.setPreferredSize(new Dimension(W_DATEFIELD,H_TEXTFIELD));
		jourArrive.setMaximumSize(new Dimension(W_DATEFIELD,H_TEXTFIELD));
		moisArrive.setPreferredSize(new Dimension(W_DATEFIELD,H_TEXTFIELD));
		moisArrive.setMaximumSize(new Dimension(W_DATEFIELD,H_TEXTFIELD));
		anneeArrive.setPreferredSize(new Dimension(W_DATEFIELD,H_TEXTFIELD));
		anneeArrive.setMaximumSize(new Dimension(W_DATEFIELD,H_TEXTFIELD));
		
		jlArrive=new JLabel("Arrivé:");
		
		pTmp.add(jlArrive);
		pTmp.add(Box.createHorizontalGlue());
		pTmp.add(jourArrive);
		pTmp.add(Box.createRigidArea(new Dimension(2,1)));
		pTmp.add(moisArrive);
		pTmp.add(Box.createRigidArea(new Dimension(2,1)));
		pTmp.add(anneeArrive);
		return pTmp;
	}
	
	/**
	* Méthode qui permet de saisir les informations d'un client
	* @return JPanel le panel qui permet de saisir le nom et le genre d'un client
	*/
	public JPanel client(){
		JPanel pTmp= new JPanel();
		pTmp.setLayout(new BoxLayout(pTmp,BoxLayout.X_AXIS));
		
		jlClient=new JLabel("Client:");
		choixGenre=new JComboBox<String>(GENRES);
		nomCli=new JTextField();

		nomCli.setPreferredSize(new Dimension(W_TEXTFIELD,H_TEXTFIELD)); //on utilise setPreferredSize sinon on galère
		nomCli.setMaximumSize(new Dimension(W_TEXTFIELD,H_TEXTFIELD));
		
		choixGenre.setPreferredSize(new Dimension(60,H_TEXTFIELD));
		choixGenre.setMaximumSize(new Dimension(60,H_TEXTFIELD));	
		
		pTmp.add(jlClient);
		pTmp.add(Box.createHorizontalGlue());
		pTmp.add(choixGenre);
		pTmp.add(Box.createRigidArea(new Dimension(10,1)));
		pTmp.add(nomCli);
		
		return pTmp;
	}
	
	/**
	* Méthode qui permet de convertir un String en type int (utilisé pour les dates saisies par l'utilisateur)
	* @param String la chaîne de caractère à convertir
	* @return int la chaîne convertie
	*/
	@SuppressWarnings("finally")
	public int convertir(String s){
		Integer i;
		int entier=-1;
		try{
			i=Integer.valueOf(s);
			entier = i.intValue();
		}catch(NumberFormatException n){
			System.out.println("Nombre non valide");
			entier=-1;
		}finally{
			return entier;
		}
	}
	
	/**
	 * Méthode qui renvois les boutons permettant d'annuler ou de réserver
	 * @return JPanel le panel permettant de valider ou annuler
 	 */
	public JPanel bouttons(){
		JPanel pTmp = new JPanel();
		
		pTmp.setLayout(new BoxLayout(pTmp, BoxLayout.X_AXIS));
		
		annuler=new JButton("annuler");
		ajouter=new JButton("ajouter");
		
		annuler.addActionListener(new Boutton(this));
		ajouter.addActionListener(new Boutton(this));
		
		pTmp.add(Box.createHorizontalGlue());
		pTmp.add(annuler);
		pTmp.add(Box.createRigidArea(new Dimension(ESPACE_BOUTON,1)));
		pTmp.add(ajouter);
		
		return pTmp;
	}
	
	/**
	* Les écouteurs pour valider ou annuler une réservation
	* @author Tristan Laurent
	* @see ActionListener
	*/
	public class Boutton implements ActionListener{
		FAjoutReserv fenetre;
		public Boutton(FAjoutReserv f){
			this.fenetre=f;
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==ajouter){//pour finaliser l'ajout, ils va falloir verifier deux trois choses
				int jArrive=convertir(jourArrive.getText());
				int mArrive=convertir(moisArrive.getText())-1; //les mois commencent à 0 mais l'utilisateur n'est pas cencé le savoir
				int aArrive=convertir(anneeArrive.getText());
				date_arrive.set(aArrive,mArrive,jArrive);

				int jDepart=convertir(jourDepart.getText());
				int mDepart=convertir(moisDepart.getText())-1;
				int aDepart=convertir(anneeDepart.getText());
				date_depart.set(aDepart,mDepart,jDepart);
				
				if(jArrive==-1||mArrive==-1||jDepart==-1||mDepart==-1||aArrive==-1||aDepart==-1){//si on a mal tapé les chiffres
					JOptionPane.showMessageDialog(null, "Remplissez les dates correctement pour reserver!","Erreur",JOptionPane.OK_OPTION);
				}else{
					if(date_depart.compareTo(date_arrive)>0){ //déjà le gus peut pas partir avant d'être venu
						nouvClient=new Client(nomCli.getText());//Si les dates sont bonnes on peut créer le client
						if(choixGenre.getSelectedIndex()==0){//0 est l'index de Mr
							nouvClient.set_genre_homme();
						}else{ //sinon c'est une femme (inserer argument macho -->ici<-- )
							nouvClient.set_genre_femme();
						}
						//et quand notre client est crée, on peut créer la réservation
						nouvReserv=new Reservation(nouvClient,date_arrive,date_depart);
						System.out.println(nouvReserv.toString()); //test
						emp.addReserv(nouvReserv);
						Interaction.ajouterReservation(nouvReserv);
						fenetre.dispose();
					}else{//si les dates ne correspondent pas on dit
						JOptionPane.showMessageDialog(null, "La date de départ est antérieure à celle d'arrivé","Erreur",JOptionPane.OK_OPTION);
					}
				}
			}else if(e.getSource()==annuler){
				fenetre.dispose();
			}
		}
		
	}
	
	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		FAjoutReserv fenetre = new FAjoutReserv();
		fenetre.setVisible(true);
	}*/

}
