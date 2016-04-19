package ihm;
import javax.swing.table.AbstractTableModel;

import etapes.AssociationRule;


/**
 * The table inside the main window
 * @author Thomas
 */
public class MyTableModel extends AbstractTableModel {
	
	/**
	 * Some AbstractTableModel stuff
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The AssociationRule to display
	 */
	private final AssociationRule[] rules;
 
    /**
     * The titles
     */
    private final String[] entetes = {"Règles d'association", "Fréquence", "Confiance"};
 
    /**The constructor
     * @param rules the AssociationRule
     */
    public MyTableModel(AssociationRule[] rules) {
        super();
        this.rules = rules;
    }
 
    /** (non-Javadoc)
     * @see javax.swing.table.TableModel#getRowCount()
     */
    public int getRowCount() {
        return rules.length;
    }
 
    /** (non-Javadoc)
     * @see javax.swing.table.TableModel#getColumnCount()
     */
    public int getColumnCount() {
        return entetes.length;
    }
 
    /** (non-Javadoc)
     * @see javax.swing.table.AbstractTableModel#getColumnName(int)
     */
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }
 
    /** (non-Javadoc)
     * @see javax.swing.table.TableModel#getValueAt(int, int)
     */
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