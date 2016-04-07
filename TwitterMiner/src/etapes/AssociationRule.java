package etapes;
import java.util.ArrayList;
import java.util.Collection;

public class AssociationRule {

	private Collection<String> leftValue;
	private Collection<String> rightValue;
	
	private double frequency;
	private double trust;
	
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
		testLeft.add("yolo");
		Collection<String> testRight = new ArrayList<String>();
		testRight.add("swagg");
		AssociationRule[] rules = new AssociationRule[]{
                new AssociationRule(testLeft, testRight, 0.8, 0.5),
                new AssociationRule(testLeft, testRight, 0.2, 0.3),
        };
		return rules;
	}
	
	public Collection<String> getLeftValue() {
		return leftValue;
	}

	public Collection<String> getRightValue() {
		return rightValue;
	}

	public double getFrequency() {
		return frequency;
	}

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
