import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class Etape4 {
	
	private JFrame fenetre;
	private DefaultListModel<String> dlm = new DefaultListModel<String>();
	private JList<String> liste = new JList<String>(dlm);
	
	public Etape4() {
		fenetre = new JFrame("Règles d'associations");
		
		/*===BARRE DE RECHERCHE===*/
		JPanel panelSearch = new JPanel();
		panelSearch.setLayout(new BorderLayout());
		
		JTextField searchBar = new JTextField();
		searchBar.setPreferredSize(new Dimension(300, 30));
		
		JButton searchButton = new JButton("Search");
		
		panelSearch.add(searchBar, BorderLayout.CENTER);
		panelSearch.add(searchButton, BorderLayout.EAST);
		
		panelSearch.setBorder(BorderFactory.createTitledBorder("Rechercher des motifs"));
		
		/*===LISTE DES RA===*/
		JPanel panelList = new JPanel();
		panelList.setLayout(new GridLayout());
		
		for (int i = 0; i < 30; i++) {
			dlm.addElement("élément " + (i + 1));
		}
		
		DefaultListCellRenderer renderer =  (DefaultListCellRenderer)liste.getCellRenderer();  
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		panelList.add(new JScrollPane(liste));
		panelList.setBorder(BorderFactory.createTitledBorder("Règles d'associations trouvées"));
		
		/*===PARAMETRAGE DE LA FENETRE===*/
		fenetre.getContentPane().add(panelSearch, BorderLayout.NORTH);
		fenetre.getContentPane().add(panelList, BorderLayout.CENTER);		
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setMinimumSize(new Dimension(300, 200));
        fenetre.pack();
		fenetre.setLocationRelativeTo(null);
		fenetre.setVisible(true);
	}

	public static void main(String[] args) {
		Etape4 etape = new Etape4();
		//affiche les éléments de la liste
		for (int i = 0; i < etape.dlm.getSize(); i++) {
			System.out.println(etape.dlm.elementAt(i));
		}
	}
}
