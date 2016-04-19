package ihm;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import etapes.AssociationRule;
import etapes.Etape2;


/**The step 4 of the project
 * @author Thomas
 *
 */
public class Etape4 {
	
	/**The main window
	 * 
	 */
	private JFrame fenetre;
	
	//private AssociationRule[] rules = AssociationRule.createRules();
	/**
	 * The AssociationRule to display
	 */
	private AssociationRule[] rules; 
	/**
	 * The main table
	 */
	private JTable liste;
	
	/**
	 * The constructor of the window
	 */
	public Etape4() {
		
		/*===ASSOCIATION RULES===*/
		ArrayList<AssociationRule> step2Res = Etape2.getExtractDF("files/AprioriRes.out", "files/IHMDFs.txt", 5, 5);
		rules =  new AssociationRule[step2Res.size()];
		rules = step2Res.toArray(rules);
		
		liste = new JTable(new MyTableModel(rules));
		
		fenetre = new JFrame("Règles d'associations");
		
		/*===BARRE DE RECHERCHE===*/
		JPanel panelSearch = new JPanel();
		panelSearch.setLayout(new BorderLayout());
		
		JTextField searchBar = new JTextField();
		searchBar.setPreferredSize(new Dimension(300, 30));
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (searchBar.getText().isEmpty()){
					//System.out.println("champ vide");
				}
				else {
					search(searchBar.getText());
				}
			}
		});
		
		searchBar.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					search(searchBar.getText());
			}
			public void keyReleased(KeyEvent e) {
			}
		});

		
		panelSearch.add(searchBar, BorderLayout.CENTER);
		panelSearch.add(searchButton, BorderLayout.EAST);
		
		panelSearch.setBorder(BorderFactory.createTitledBorder("Rechercher des motifs"));
		
		/*===LISTE DES RA===*/
		JPanel panelList = new JPanel();
		panelList.setLayout(new GridLayout());
		liste.setAutoCreateRowSorter(true);
	
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
	
	/**Function to search a word inside the results
	 * @param motifs The word to look for
	 */
	public void search (String motifs){
		liste.clearSelection();
		for(int i = 0; i < liste.getRowCount(); ++i){
			if (liste.getValueAt(i, 0).toString().contains(motifs)){
				//System.out.println("trouvé");
				liste.getSelectionModel().addSelectionInterval(i, i); 
			}
		}
	}

	/**The function executed from terminal execution
	 * @param args no arguments needed
	 */
	public static void main(String[] args) {
		 new Etape4();
	}
}
