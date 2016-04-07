import java.util.Collection;

public class AssociationRule {

	private Collection<Integer> leftValue;
	private Collection<Integer> rightValue;
	
	private int frequency;
	private int trust;
	
	public AssociationRule(Collection<Integer> leftValue, Collection<Integer> rightValue, 
			int frequency, int trust) {
		
		this.leftValue  = leftValue;
		this.rightValue = rightValue;
		
		this.frequency = frequency;
		this.trust     = trust;
	}
	
	public Collection<Integer> getLeftValue() {
		return leftValue;
	}

	public Collection<Integer> getRightValue() {
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
		
		for (Integer lValue : leftValue) {
			str += lValue + ", ";
		}
		str += "→ ";
		for (Integer rValue : rightValue) {
			str += rValue + ", ";
		}
		
		return str;
	}
}
