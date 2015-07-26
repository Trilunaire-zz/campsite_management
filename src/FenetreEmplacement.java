import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
* Fenetre affichant un résumé de l'état d'un emplacement à la date courante
* @author Tristan Laurent
* @version 2
* @see JDialog
*/
@SuppressWarnings("serial")
public class FenetreEmplacement extends JFrame{
	private final int ESPACE_BTS = 10;
	private Emplacement emp;
	private Reservation reservCourante;
	
	private JPanel main;
	private JPanel panelEtat;
	private JPanel panelClient;
	private JPanel panelDArrive;
	private JPanel panelDDepart;
	
	private JButton calendrier;
	private JButton listeReserv;
	
	private JLabel nom;
	private JLabel etat;
	private JLabel depart;
	private JLabel arrive;
	
	/**
	* Constructeur de la fenetre des détails de l'emplacement
	* @param Emplacement l'emplacement dont informations seront affichés
	*/
	public FenetreEmplacement(Emplacement e){//on en déduira le titre de la fenetre
		Interaction.set_femplacement(this);
		
		this.emp=e;
		this.reservCourante=emp.getCurrentReservation();
		
		this.setSize(200,300);
		this.setMinimumSize(this.getSize());
		this.setResizable(false);
		this.setTitle("n°"+emp.get_num());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.main = new JPanel();
		
		this.createGUI();
	}

	/**
	* Méthode qui crée l'interface graphique
	*/
	public void createGUI(){		
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		main.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10));//bordure invisible qui permet de laisser une marge interne de 10 pixels
		
		panelEtat=new JPanel();
		panelClient=new JPanel();
		panelDArrive=new JPanel();
		panelDDepart=new JPanel();


		panelEtat.setLayout(new BorderLayout());
		panelClient.setLayout(new BorderLayout());
		panelDArrive.setLayout(new BorderLayout());
		panelDDepart.setLayout(new BorderLayout());


		//on créer nos 4 champs
		if(reservCourante!=null){
			etat=new JLabel("occupé");
			nom=new JLabel(reservCourante.get_client().toString());
			arrive=new JLabel(reservCourante.getDateArrive());
			depart=new JLabel(reservCourante.getDateDepart());
		}else{
			etat=new JLabel("libre");
			nom=new JLabel("");
			depart=new JLabel("");
			arrive=new JLabel("");
		}
		
		//on les ajoute aux différents panels
		panelEtat.add(new JLabel("État"), BorderLayout.WEST);
		panelEtat.add(etat, BorderLayout.EAST);
		panelClient.add(new JLabel("Nom"), BorderLayout.WEST);
		panelClient.add(nom, BorderLayout.EAST);
		panelDArrive.add(new JLabel("Départ"), BorderLayout.WEST);
		panelDArrive.add(arrive, BorderLayout.EAST);
		panelDDepart.add(new JLabel("Arrivé"), BorderLayout.WEST);
		panelDDepart.add(depart, BorderLayout.EAST);
		

		//et nos deux boutons
		calendrier=new JButton("Calendrier...");
		listeReserv=new JButton("Reservation(s)...");
		
		
		listeReserv.setMaximumSize(new Dimension(200, 40));
		listeReserv.setAlignmentX(Component.CENTER_ALIGNMENT);
		listeReserv.addActionListener(new Boutons());
		
		calendrier.setMaximumSize(new Dimension(200, 40));
		calendrier.setAlignmentX(Component.CENTER_ALIGNMENT);
		calendrier.addActionListener(new Boutons());
		

		main.add(panelEtat);
		main.add(panelClient);
		main.add(panelDArrive);
		main.add(panelDDepart);
		main.add(Box.createVerticalGlue());
		
		main.add(calendrier);
		main.add(Box.createRigidArea(new Dimension(1,ESPACE_BTS)));//on espacie les deux boutons de 10px
		main.add(listeReserv);
		
		this.setContentPane(main);
	}

	/**
	* Méthode permettant de créer un Panel qui contient du texte à droite et à gauche (utiliser pour les champs Client   nom)
	* @param String Le texte qui sera affiché à gauche
	* @param String Le texte qui sera affiché à droite
	* @return JPanel Un JPanel contenant du texte agencé à droite et à gauche
	*/
	
	public void majInfo(){
		reservCourante=emp.getCurrentReservation();
		if(reservCourante!=null){
			etat.setText("occupé");
			nom.setText(reservCourante.get_client().toString());
			arrive.setText(reservCourante.getDateArrive());
			depart.setText(reservCourante.getDateDepart());
		}else{
			etat.setText("libre");
			nom.setText("");
			depart.setText("");
			arrive.setText("");
		}
	}
	
	/**
	* Écouteur pour ouvrir la liste de reservation ou le calendrier
	* @author Tristan Laurent
	* @see ActionListener
	*/
	public class Boutons implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==calendrier){
				Calendrier cal=new Calendrier(emp);
				cal.setVisible(true);
			}else if(e.getSource()==listeReserv){
				FListeReservation fList=new FListeReservation(emp);
				fList.setVisible(true);
			}
		}
	}
	

	/*public static void main(String args[]){
		FenetreEmplacement maFenetreTest = new FenetreEmplacement(42);
		maFenetreTest.setVisible(true);
	}*/
}