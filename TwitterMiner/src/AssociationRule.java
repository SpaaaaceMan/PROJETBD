import java.util.Collection;

public class AssociationRule {

	private Collection<Character> leftValue;
	private Collection<Character> rightValue;
	
	public AssociationRule(Collection<Character> leftValue, Collection<Character> rightValue) {
		this.leftValue  = leftValue;
		this.rightValue = rightValue;
	}
	
	@Override
	public String toString() {
		String str = "";
		
		for (Character lChar : leftValue) {
			str += lChar + " ";
		}
		str += "→ ";
		for (Character rChar : rightValue) {
			str += rChar + " ";
		}
		
		return str;
	}
}
