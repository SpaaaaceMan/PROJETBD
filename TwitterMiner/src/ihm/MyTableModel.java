package ihm;
import javax.swing.table.AbstractTableModel;

import etapes.AssociationRule;


@SuppressWarnings("serial")
public class MyTableModel extends AbstractTableModel {
	private final AssociationRule[] rules;
 
    private final String[] entetes = {"Règles d'association", "Fréquence", "Confiance"};
 
    public MyTableModel(AssociationRule[] rules) {
        super();
        this.rules = rules;
    }
 
    public int getRowCount() {
        return rules.length;
    }
 
    public int getColumnCount() {
        return entetes.length;
    }
 
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }
 
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0:
                return rules[rowIndex].toString();
            case 1:
                return rules[rowIndex].getFrequency();
            case 2:
                return rules[rowIndex].getTrust();
            default:
                return null; //Ne devrait jamais arriver
        }
    }
}