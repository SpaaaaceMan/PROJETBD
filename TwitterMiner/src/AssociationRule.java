import java.util.Collection;

public class AssociationRule {

	private Collection<Integer> leftValue;
	private Collection<Integer> rightValue;
	
	public AssociationRule(Collection<Integer> leftValue, Collection<Integer> rightValue) {
		this.leftValue  = leftValue;
		this.rightValue = rightValue;
	}
	
	public Collection<Integer> getLeftValue() {
		return leftValue;
	}

	public Collection<Integer> getRightValue() {
		return rightValue;
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
