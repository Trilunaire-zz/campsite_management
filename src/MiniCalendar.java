import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicArrowButton;


@SuppressWarnings("serial")
public class MiniCalendar extends JFrame{
	@SuppressWarnings("unused")
	private final String[] libelleJour={"L","M","M","J","V","S","D"};
	private final String[] mois={"Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre", "Octobre", "Novembre", "Decembre"};
	private Calendar monCalendrier;
	/**
	 * @param args
	 */
	public MiniCalendar(Calendar today){
		this.setSize(200,200);
		this.setMinimumSize(this.getSize());
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.monCalendrier=today;
		this.setContentPane(this.createGUI());
	}
	
	public JPanel createGUI(){
		JPanel main = new JPanel();
		JPanel haut = this.monthSelection();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));//on alignera le tout verticalement
		main.add(haut);
		return main;
	}
	
	public JPanel monthSelection(){
		JPanel mois = new JPanel();
		mois.setLayout(new BoxLayout(mois, BoxLayout.X_AXIS)); //agencement horizontal
		mois.setBorder(BorderFactory.createEmptyBorder( 5, 5, 5, 5));
		mois.add(new BasicArrowButton(BasicArrowButton.WEST));//fléche ver la gauche
		//on placera un écouteur qui permettra de diminuer le mois
		mois.add(Box.createHorizontalGlue());
		mois.add(this.mois(monCalendrier.get(Calendar.MONTH)));//on initialise le mois en fonction de ce qu'on aura réglé dans notre calendar
		mois.add(Box.createHorizontalGlue());
		mois.add(new BasicArrowButton(BasicArrowButton.EAST));//fleche vers la droite
		//on placera un écouteur pour diminuer le mois
		return mois;	
	}
	
	public JLabel mois(int m){
		JLabel moisAffiche = new JLabel(mois[m]);
		return moisAffiche;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MiniCalendar test = new MiniCalendar(GestionTemp.get_CalendarToday());
		test.setVisible(true);
	}

}
