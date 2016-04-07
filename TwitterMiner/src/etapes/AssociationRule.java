package etapes;
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
		Collection<String> testLeft = null;
		Collection<String> testRight = null;
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
		String str = "";
		
		for (String lValue : leftValue) {
			str += lValue + ", ";
		}
		str += "→ ";
		for (String rValue : rightValue) {
			str += rValue + ", ";
		}
		
		return str;
	}
}
