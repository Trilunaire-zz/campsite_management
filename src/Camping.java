
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
* La classe principale qui affiche la fenetre de camping
* @author Simon Brigant
* @see JFrame
*/
@SuppressWarnings("serial")
public class Camping extends JFrame{
	final static private int NB_EMP = 100;
	private boolean fileOpen = false;
	/**
	 * souris est une variable qui sauvegarde le mouseEvent pour s'en resservir en dehors
	 * @see MouseListener
	 * @see MouseEvent
	 */
	private MouseEvent souris = null;
	private boolean deplacement;
	/**
	 * Le panel qui contient tous les boutons
	 * @see JPanel
	 */
	private JPanel lesEmp;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fichier;
	private JMenu edition;
	private JMenu calendrier;
	/**
	 * Un tableau d'association emplacement - bouton
	 * @see Emplacement
	 * @see ButtonEmp
	 */
	private ButtonEmp[] collecEmp;
	private JButton clicked;
	private JButton clickedInter;
	/**
	 * assoTrouve est une instance de ButtonEmp qui prend la valeur de l'association correspondant au bouton clicke
	 * @see ButtonEmp
	 */
	private ButtonEmp assoTrouve;
	/**
	 * assoTrouveInter est une instance de ButtonEmp qui garde en memoire la valeur de l'association correspondant au bouton clicke et que l'on compte deplacer
	 * @see ButtonEmp
	 */
	private ButtonEmp assoTrouveInter;
	private boolean trouve;
	private int i;
	/**
	 * Un vecteur qui contient tous les numeros libres pour les emplacements
	 * @see Vector
	 */
	private Vector<Integer> num_libre;

	//les items du menu
	private JMenuItem modeEdit;
	private JMenuItem modeGes;
	private JMenuItem enregistrer;
	private JMenuItem ouvrir;

	//les items du "clic droit"
	private JPopupMenu popup;
	private JMenuItem ajouter;
	private JMenuItem supprimer;
	private JMenuItem deplacer;
	private JMenuItem dejaReserve;

	private JFileChooser chooser;

	/**
	 * Une variable qui permet de savoir si on est en mode gestion ou edition (0 : Gestion , 1 : Edition, 2 : Deplacement)
	 */
	private int mode;

	/**
	* Constructeur de la fenetre
	*/
	public Camping(){
		this.setLayout(null);
		this.setResizable(false);
		this.setSize(600,600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Camping");

		//instanciation

		this.chooser = new JFileChooser();

		InfoCamping.init();

		lesEmp = new JPanel();

		fichier = new JMenu("Fichier");
		edition = new JMenu("Mode");
		calendrier = new JMenu(GestionTemp.get_dateToday());//on initialise le Calendrier qui sera a la date courante

		modeEdit = new JMenuItem("Edition");
		modeEdit.addActionListener(new ModeEdition());
		modeGes = new JMenuItem("Gestion");
		modeGes.addActionListener(new ModeGestion());
		enregistrer = new JMenuItem("Enregistrer");
		enregistrer.addActionListener(new BoutonMenu());
		ouvrir = new JMenuItem("Ouvrir");
		ouvrir.addActionListener(new BoutonMenu());

		collecEmp = new ButtonEmp[NB_EMP];
		clicked = new JButton();
		assoTrouve = new ButtonEmp(new Emplacement(0), new JButton());
		assoTrouveInter = new ButtonEmp(new Emplacement(0), new JButton());
		num_libre = new Vector<Integer>();

		popup = new JPopupMenu();
		ajouter = new JMenuItem("ajouter");
		ajouter.addActionListener(new AjoutEmp());
		supprimer = new JMenuItem("supprimer");
		supprimer.addActionListener(new SuppressionEmp());
		deplacer = new JMenuItem("deplacer");
		deplacer.addActionListener(new DeplacementEmp());
		dejaReserve=new JMenuItem("Suppression Impossible: Emplacement avec reservations");

		this.init();
	}
	//-------------------------------------------------

	/**
	* Accesseur retournant le nombre d'emplacement
	* @return int NB_EMP
	*/
	public static int get_NB_EMP(){
		return NB_EMP;
	}
	//-------------------------------------------------

	/**
	 * la methode qui initialise toutes les variables
	 */
	public void init(){
		fichier.add(ouvrir);
		fichier.add(enregistrer);

		edition.add(modeGes);
		edition.add(modeEdit);

		menuBar.add(fichier);
		menuBar.add(edition);
		menuBar.add(Box.createHorizontalGlue());//on sépare le menu du calndrier
		menuBar.add(calendrier);

		this.setJMenuBar(menuBar);

		this.mode = 0;

		//on commence en mode gestion donc l'option pour aller en mode gestion est grise
		modeGes.setEnabled(false);
	    getContentPane().setLayout(new BorderLayout());
		lesEmp.setLayout(new GridLayout(10,10)); //les emplacement forment un tableau de 10 sur 10

		//FIXME deux emplacements sont ajouté sans raison
		if(!fileOpen){//si on n'a pas ouvert de fichier (en gros InfoCamp contient les infos en RAM)
			for(int i=0 ; i<NB_EMP ; i++){
				num_libre.add(i+1); //on initialise le tableau des num libres
				collecEmp[i] = new ButtonEmp(new Emplacement(0), new JButton());
				//comme les emplacement sont vide on initialise tout en blanc et pas cliquable
				collecEmp[i].get_bouton().setBackground(Color.WHITE);
				collecEmp[i].get_bouton().setForeground(Color.WHITE);
				collecEmp[i].get_bouton().setBorder(BorderFactory.createLineBorder(Color.WHITE));
				collecEmp[i].get_bouton().setEnabled(false);
				//on ajoute un mouse listener sur chaque bouton
				collecEmp[i].get_bouton().addMouseListener(new Souris());
				collecEmp[i].get_emp().set_associationBouton(collecEmp[i]);
				lesEmp.add(collecEmp[i].get_bouton());
				//RESUME : créer un ButtonEmp contenant un emplacement vierge et un JButton blanc désactivé contenant un mouse listener
			}
		}else{
			for(int i=0; i<NB_EMP; i++){
				//TODO Comparer les information d'un emplacement déjà ajouté et celles d'un emplacement vide
				collecEmp[i] = InfoCamping.get_empCamp().get(i+2).get_associationBouton();
				collecEmp[i].get_bouton().addMouseListener(new Souris());
				lesEmp.add(collecEmp[i].get_bouton()); //FIXME les emplacements n'apparessent pas imédiatement lors de l'ouverture d'un fichier;
				System.out.println("Marche pour "+i);
			}
		}
		getContentPane().add(lesEmp, BorderLayout.CENTER);

		//ZONE DE TEST/////////////////////
		//collecEmp[2].get_emp().ajouter(num_libre, collecEmp[2]);
		//collecEmp[2].get_emp().set_libre(false);
		///////////////////////////////////
	}
	/**
	 * la fonction qui récupere le plus petit numéro libre et le supprime du vecteur
	 * @param v Vector<Integer>
	 * @return Le plus petit entier du vecteur
	 */
	public static int getNumMin(Vector<Integer> v){
		int min = NB_EMP;

		for(int i=0 ; i<v.size() ; i++){
			if((int)v.get(i) < min){
				min = (int)v.get(i);
			}
		}
		v.remove((Object)min);
		return min;
	}

	public class BoutonMenu implements ActionListener{
		int valueChooseUser;
		String nomFichier;
		FileNameExtensionFilter filtreFichier = new FileNameExtensionFilter("Fichiers Camping (.camp)","camp");
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==enregistrer){
				chooser.setFileFilter(filtreFichier);
				valueChooseUser=chooser.showSaveDialog(null);
				if(valueChooseUser==JFileChooser.APPROVE_OPTION){
					try {
						nomFichier=chooser.getSelectedFile().getName();
						if(nomFichier.contains(".camp")){
							InfoCamping.enregistrer(chooser.getSelectedFile().getAbsolutePath());
						}else{
							InfoCamping.enregistrer(chooser.getSelectedFile().getAbsolutePath()+".camp");
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}else if(e.getSource()==ouvrir){
				chooser.setFileFilter(filtreFichier);
				valueChooseUser=chooser.showOpenDialog(null);
				if(valueChooseUser==JFileChooser.APPROVE_OPTION){
					nomFichier=chooser.getSelectedFile().getName();
					if(nomFichier.contains(".camp")){
						try {
							InfoCamping.vider();
							InfoCamping.lire(chooser.getSelectedFile().getAbsolutePath());
							InfoCamping.affListeCli();
							InfoCamping.affListeEmp();
							menuBar.removeAll();
							lesEmp.removeAll();
							fileOpen=true;
							init();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else{
						JOptionPane.showMessageDialog(null, "Format de fichier non compatible", "Erreur", JOptionPane.ERROR_MESSAGE);					}
				}
				//meme chose
			}
		}
	}

	/**
	 * La classe qui gere le mode de gestion
	 * @author Simon Brigant
	 */
	private class ModeGestion implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			modeEdit.setEnabled(true);
			// Griser bouton
			modeGes.setEnabled(false);
			mode = 0;
			for(int i=0 ; i<NB_EMP ; i++){
				collecEmp[i].get_bouton().setBorder(BorderFactory.createLineBorder(Color.WHITE));
				collecEmp[i].get_bouton().setBackground(collecEmp[i].get_emp().getCouleur());
				collecEmp[i].get_bouton().setEnabled(!collecEmp[i].get_emp().get_vide());
			}
		}
	}

	/**
	 * La classe qui gère le mode édition
	 * @author Simon Brigant
	 * @see ActionListener
	 */
	private class ModeEdition implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			modeGes.setEnabled(true);
			// Griser bouton
			modeEdit.setEnabled(false);
			mode = 1;
			for(int i=0 ; i<NB_EMP ; i++){
				collecEmp[i].get_bouton().setBorder(BorderFactory.createLineBorder(Color.BLACK));
				collecEmp[i].get_bouton().setBackground(collecEmp[i].get_emp().getCouleur());
				collecEmp[i].get_bouton().setEnabled(true);
			}
		}
	}

	/**
	 * Écouteur sur le menu contextuel "ajouter"
	 * @author Simon Brigant
	 * @see ActionListener
	 */
	private class AjoutEmp implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			assoTrouve.get_emp().ajouter(num_libre, assoTrouve);
		}
	}

	/**
	 * Écouteur sur le menu contextuel "supprimer"
	 * @author Simon Brigant
	 * @see ActionListener
	 */
	private class SuppressionEmp implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			if(JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cet emplacement?","Confirmation",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
				assoTrouve.get_emp().supprimer(num_libre, assoTrouve);
			}
		}
	}

	/**
	 * Écouteur sur le menu contextuel "déplacer"
	 * @author Simon Brigant
	 * @see ActionListener
	 */
	private class DeplacementEmp implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			deplacement = true;
			mode = 2;

			clickedInter = (JButton)souris.getComponent();
			trouve = false;
			i = 0;
			while(trouve == false || i<NB_EMP){
				if(clickedInter == collecEmp[i].get_bouton()){
					assoTrouveInter = collecEmp[i];
					trouve = true;
				}
				i++;
			}
		}
	}

	/**
	 * La classe qui gere lorsqu'on clique sur un emplacement
	 * @author Simon Brigant
	 * @see MouseListener
	 */
	private class Souris implements MouseListener {
		/**
		 * @param e
		 * Le nom de l'evenement qui se produit lorsqu'on clique sur un bouton
		 */
		public void mouseClicked(MouseEvent e) {
			popup.removeAll();
			souris = e;
			//clicked recupere le bouton sur lequel on clique
			clicked = (JButton)e.getComponent();
			trouve = false;
			i = 0;
			//on cherche ce bouton dans le tableau des asso
			while(trouve == false || i<NB_EMP){
				if(clicked == collecEmp[i].get_bouton()){
					assoTrouve = collecEmp[i];
					trouve = true;
				}
				i++;
			}

			//si on est mode edition et que l'on presse le bouton droit
			if(mode==1 && ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK)){
				//si l'emplacement est vide
				if( assoTrouve.get_emp().get_vide() ){
					popup.add(ajouter);
				}
				//si l'emplacement n'est pas vide et est libre
				else if( assoTrouve.get_emp().get_vide()==false && assoTrouve.get_emp().getListeReserv().isEmpty() ){
					popup.add(deplacer);
					popup.add(supprimer);
				}
				//si l'emplacement n'est pas libre
				else if( !assoTrouve.get_emp().getListeReserv().isEmpty() ){
					popup.add(deplacer);
					dejaReserve.setEnabled(false);
					popup.add(dejaReserve);
				}
				popup.show(e.getComponent(), e.getX(), e.getY()); //on affiche le JPopMenu
			}
			//si on est en mode gestion et que le clic gauche est presse
			else if(mode==0 && ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK)) {
				//si l'emplacement n'est pas vide
				if( assoTrouve.get_emp().get_vide()==false ){
					FenetreEmplacement maFenetreTest = new FenetreEmplacement(assoTrouve.get_emp());//alors on creer une fenetre en fonction de l'emplacment
					maFenetreTest.setVisible(true);
				}
			}
			//Si on est mode deplacement ( on attend un deplacement )
			else if(mode==2) {
				//si l'emplacement est vide et qu'on appuie sur le clic gauche
				if( assoTrouve.get_emp().get_vide()==true && ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) ){
					deplacement = assoTrouve.get_emp().deplacer(num_libre, assoTrouveInter, assoTrouve);
				}
				//si l'emplacement n'est pas vide et qu'on appuie sur le clic gauche
				else if( assoTrouve.get_emp().get_vide()==false && ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) ){
					JOptionPane.showMessageDialog(null, "Emplacement deja occupe", "Erreur", JOptionPane.ERROR_MESSAGE);
					mode = 1;
				}
				if(deplacement == false){
					mode = 1;
				}
			}
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
}
