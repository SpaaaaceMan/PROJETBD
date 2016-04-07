package etapes;
import java.util.ArrayList;
import java.util.Collection;

/**This is an association rule. this represents stuff like X→Y
 * 
 * @author Thomas MEDARD, Kurt SAVIO, Julien TEULLE
 *
 */
public class AssociationRule {

	/**
	 * The left item in the association rule (X)
	 */
	private Collection<String> leftValue;
	/**
	 * The right item in the association rule (Y) 
	 */
	private Collection<String> rightValue;
	
	/**
	 * Its frequency
	 */
	private double frequency;
	/**
	 * Its trus
	 */
	private double trust;
	
	/**
	 * The constructor
	 * @param leftValue the left items (X).
	 * @param rightValue the right items (Y)
	 * @param frequency The frequency of this AssociationRule
	 * @param trust The trust of this AssociationRule
	 */
	public AssociationRule(Collection<String> leftValue, Collection<String> rightValue, 
			double frequency, double trust) {
		
		this.leftValue  = leftValue;
		this.rightValue = rightValue;
		
		this.frequency = frequency;
		this.trust     = trust;
	}
	
	public static AssociationRule[] createRules(){
		Collection<String> testLeft = new ArrayList<String>();
		testLeft.add("yolo");
		Collection<String> testRight = new ArrayList<String>();
		testRight.add("swagg");
		
		Collection<String> testLeft2 = new ArrayList<String>();
		testLeft2.add("zut");
		Collection<String> testRight2 = new ArrayList<String>();
		testRight2.add("hello");
		AssociationRule[] rules = new AssociationRule[]{
                new AssociationRule(testLeft, testRight, 0.8, 0.5),
                new AssociationRule(testLeft, testRight, 0.2, 0.3),
                new AssociationRule(testLeft2, testRight2, 0.1, 0.9),
        };
		return rules;
	}
	/**
	 * The getter for the left item of this AssociationRule (X)
	 * @return the left item of this Association Rule 
	 */
	public Collection<String> getLeftValue() {
		return leftValue;
	}

	/**
	 * The getter for the right item of this Association Rule (Y)
	 * @return the getter for the right item
	 */
	public Collection<String> getRightValue() {
		return rightValue;
	}

	/**
	 * Get the frequency of this AssociationRule
	 * @return the frequency of this AssociationRule
	 */
	public double getFrequency() {
		return frequency;
	}

	/**
	 * 
	 * @return
	 */
	public double getTrust() {
		return trust;
	}

	@Override
	public String toString() {
		//Final String to return
		String str = "";
		
		//Counter
		int counter = 0;
		
		//Left value
		for (String lValue : leftValue) {
			if (counter != leftValue.size() - 1) {
				str += lValue + ", ";
			}
			else {
				str += lValue + " ";
			}
			
			++counter;
		}
		
		counter = 0;
		
		//Asscotiation rule character
		str += "→ ";
		
		//Right value
		for (String rValue : rightValue) {
			if (counter != rightValue.size() - 1) {
				str += rValue + ", ";
			}
			else {
				str += rValue;
			}
			
			++counter;
		}
		
		return str;
	}
}
