import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
* Fenetre affichant la liste de toutes les réservations pour l'emplacement
* @author Tristan Laurent
* @see JFrame
*/
@SuppressWarnings("serial")
public class FListeReservation extends JFrame{
	final private int ESPACE_ENTRE_BOUTTONS = 25;
	final private Object[] NOM_COLONES = {"Nom","Arrivée","Départ"};
	
	private Emplacement monEmplacement;
	
	private JTable tableau;
	private DefaultTableModel modele;
	private ListSelectionModel selection;
	
	private JPanel panelBoutton;
	private JPanel main;
	private JScrollPane scrollPane;
	
	private JButton ajouter;
	private JButton supprimer;
	private JButton modifier;
	private JButton retour;

	
	/**
	* Conctructeur de la fenetre à partir des réservations d'un emplacement
	* @param Emplacement l'emplacement qui contiendra les réservations
	*/
	public FListeReservation(Emplacement e){
		Interaction.set_fenList(this);
		this.monEmplacement=e;
		this.setResizable(false);
		this.setTitle("Reservations - Emplacement n°"+e.get_num());
		this.setSize(500, 300);
		this.setMinimumSize(getSize());
		this.setLocationRelativeTo(null);
		
		this.main=new JPanel();
		this.panelBoutton=new JPanel();
		
		this.createGUI();
	}
	
	
	/**
	* Méthode qui renvois l'emplacement dont est affiché la liste des reservations
	* @return Emplacement l'emplacement qui est associé à la fenetre
	*/
	public Emplacement get_monEmplacement(){
		return this.monEmplacement;
	}

	/**
	* Permet de créer l'interface graphique, avec le tableau à gauche et les boutons à droite
	*/
	public void createGUI(){
		main.setLayout(new BoxLayout(main, BoxLayout.X_AXIS));
		main.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		main.add(tableauReserv());
		main.add(boutons());
		this.setContentPane(main);
	}

	/**
	* Méthode qui permet d'ajouter une réservation dans le TableModel
	* @param Reservation la réservation à ajouter dans le tableau
	*/
	public void ajouterReservation(Reservation new_Reserv){
		Object[] nouvelleLigne={new_Reserv.get_client().toString(),new_Reserv.getDateArrive(),new_Reserv.getDateDepart()};
		modele.addRow(nouvelleLigne);
	}
	
	/**
	* Méthode qui créer le tableau dans un JScrollPane
	* @return JScrollPane le tableau
	*/
	public JScrollPane tableauReserv(){
		modele=new DefaultTableModel(this.listeReserv(),NOM_COLONES);
		tableau = new JTable(modele); //on construit notre tableau
		selection=tableau.getSelectionModel();//on est obligé d'utiliser un listSelection modele pour pouvoir utiliser le listener
		selection.addListSelectionListener(new BoutonGrise());
		
		scrollPane = new JScrollPane(tableau); //on va créer un scrollPane a partir du tableau
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0,0,0,10));
		tableau.setFillsViewportHeight(true);//on dit au tableau de prendre toute la place
		return scrollPane;
	}
	
	/**
	* Méthode qui renvoie un tableau d'objet contenant la liste des reservation de l'emplacement
	* @return Object[][] la liste des réservation
	*/
	public Object[][] listeReserv(){
		Object[][] liste=new Object[monEmplacement.getListeReserv().size()][3];;
		for(int i=0; i<monEmplacement.getListeReserv().size();i++){
			liste[i][0]=monEmplacement.getListeReserv().get(i).get_client().toString();
			liste[i][1]=monEmplacement.getListeReserv().get(i).getDateArrive();
			liste[i][2]=monEmplacement.getListeReserv().get(i).getDateDepart();
		}
		return liste;
	}
	

	/**
	* Méthode permettant de créer le panel de gauche qui contient les boutons
	* @return JPanel le panel qui contient les boutons
	*/
	public JPanel boutons(){
		panelBoutton.setLayout(new BoxLayout(panelBoutton,BoxLayout.Y_AXIS));
		
		ajouter=new JButton("Ajouter...");
		ajouter.setMaximumSize(new Dimension(120, 40));
		ajouter.addActionListener(new Boutons(this));
		
		supprimer=new JButton("Supprimer");
		supprimer.setMaximumSize(new Dimension(120, 40));
		supprimer.setEnabled(false);
		supprimer.addActionListener(new Boutons(this));
		
		modifier=new JButton("Modifier...");
		modifier.setMaximumSize(new Dimension(120, 40));
		modifier.setEnabled(false);
		
		retour=new JButton("Retour");
		retour.setMaximumSize(new Dimension(120, 40));
		retour.addActionListener(new Boutons(this));//on ajoute le listener qui va permettre de fermer la fenetre
		
		panelBoutton.add(ajouter);
		panelBoutton.add(Box.createRigidArea(new Dimension(1,ESPACE_ENTRE_BOUTTONS)));
		panelBoutton.add(supprimer);
		panelBoutton.add(Box.createRigidArea(new Dimension(1,ESPACE_ENTRE_BOUTTONS)));
		panelBoutton.add(modifier);
		panelBoutton.add(Box.createVerticalGlue());
		panelBoutton.add(retour);
		
		return panelBoutton;
	}
	
	/**
	 * Écouteurs sur les boutons ajouter et supprimer
	 * @author Tristan Laurent
	 * @see ActionListener
	 */
	public class Boutons implements ActionListener{
		FListeReservation fenetre;
		public Boutons(FListeReservation f){
			fenetre=f;
		}
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==retour){
				fenetre.dispose();
			}else if(e.getSource()==supprimer){//si on veut supprimer, on affiche la popup
				int reponse=(int)JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cette réservation?","Confirmation",JOptionPane.YES_NO_OPTION);
				
				if(reponse==JOptionPane.YES_OPTION){ //trouver la valeur, parce que ce truc ne marche pas!
					System.out.println("Oui séléctionné");
					Vector<Reservation> lesReserv=monEmplacement.getListeReserv();
					boolean trouve = false;
					int i = 0;
					int ligneSelectionne;
					String restmp, resSelectionne;
					
					while(!trouve && i<lesReserv.size()){//on va chercher la réservation correspondant à la chaîne de caractère
						
						restmp=lesReserv.get(i).toString();
						ligneSelectionne=tableau.getSelectedRow();
						//on convertit la ligne séléctionné en chaîne de caractère pour pouvoir la comparer
						resSelectionne=modele.getValueAt(ligneSelectionne, 0)+" "+modele.getValueAt(ligneSelectionne, 1)+" "+modele.getValueAt(ligneSelectionne, 2);
						
						System.out.println(resSelectionne);
						System.out.println(restmp);
						if(resSelectionne.compareTo(restmp)==0){//pour ensuite la supprimer
							monEmplacement.removeReserv(lesReserv.get(i));
							modele.removeRow(tableau.getSelectedRow());
							System.out.println("Reservation Supprimé");
							if(monEmplacement.getCurrentReservation()==null){
								System.out.println("Couleur Mise à jour");
								monEmplacement.updateButtonColor();
							}
							Interaction.reservSupp();
							trouve=true;
						}
						i++;
					}
				}
				/*si la réponse est oui:
				 *  récupérer l'index du JTable et le supprimer
				 *  supprimer la réservation de la liste dans l'emplacement
				 *  Mettre à jour la couleur du bouton  
				 */
			}else if(e.getSource()==ajouter){//dans le cas d'un ajout
				Interaction.demandeAjouter(monEmplacement); //on ouvre une fenetre
			}
		}
	}
	
	/**
	 * Écouteur pour griser les boutons 'Modifier' et 'Supprimer' quand on a rien séléctionné
	 * @author Tristan Laurent
	 * @see ListSelectionListener
	 */
	public class BoutonGrise implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e) {
			if(tableau.getSelectedRow()==-1){//si il n'y a rien de selectionné (index=-1)
				supprimer.setEnabled(false);//on active pas les boutons!!!
				modifier.setEnabled(false);
			}else{
				supprimer.setEnabled(true);
				modifier.setEnabled(true);
			}
		}
	}
	
	/*
	public static void main(String[] args) {
		Emplacement e0=new Emplacement("1",1);
		
		Reservation r1=new Reservation();
		Reservation r2=new Reservation();
		
		Client monCli1 = new Client(1,"Croupton");
		Client monCli2 = new Client(2,"Jedusor");
		monCli1.set_genre_homme();
		monCli2.set_genre_femme();
		r1.set_client(monCli1);
		r2.set_client(monCli2);
		
		e0.addReserv(r1);
		e0.addReserv(r2);
		
		FListeReservation test = new FListeReservation(e0);
		test.setVisible(true);
	}*/


}
