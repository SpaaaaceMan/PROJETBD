package ihm;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import etapes.AssociationRule;
import etapes.Etape2;


public class Etape4 {
	
	private JFrame fenetre;
	@SuppressWarnings("unused")
	private TableModel dlm = new DefaultTableModel();	
	//private AssociationRule[] rules = AssociationRule.createRules();
	private AssociationRule[] rules; 
	private JTable liste;
	
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
				if (searchBar.getText().isEmpty())
					System.out.println("champ vide");
				else
					search(searchBar.getText());
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
	
	public void search (String motifs){
		liste.clearSelection();
		for(int i = 0; i < liste.getRowCount(); ++i){
			if (liste.getValueAt(i, 0).toString().contains(motifs)){
				System.out.println("trouvé");
				liste.getSelectionModel().addSelectionInterval(i, i); 
			}
		}
	}

	public static void main(String[] args) {
		 new Etape4();
	}
}
