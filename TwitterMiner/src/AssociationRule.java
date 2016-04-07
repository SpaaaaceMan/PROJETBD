import java.util.Collection;

public class AssociationRule {

	private Collection<String> leftValue;
	private Collection<String> rightValue;
	
	private int frequency;
	private int trust;
	
	public AssociationRule(Collection<String> leftValue, Collection<String> rightValue, 
			int frequency, int trust) {
		
		this.leftValue  = leftValue;
		this.rightValue = rightValue;
		
		this.frequency = frequency;
		this.trust     = trust;
	}
	
	public Collection<String> getLeftValue() {
		return leftValue;
	}

	public Collection<String> getRightValue() {
		return rightValue;
	}

	public int getFrequency() {
		return frequency;
	}

	public int getTrust() {
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
