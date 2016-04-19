package etapes;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**This is the step 3 of the project
 * @author Thomas
 *
 */
public class Etape3 {
	
	/**the file
	 * 
	 */
	private String file;
	
	/**The constructor
	 * @param file The file path
	 */
	public Etape3(String file) {
		this.file = file;
	}

	/**Get all useless words from the file
	 * @return The useless words found
	 * @throws IOException some File exceptions
	 */
	public ArrayList<String> Nettoyage () throws IOException{
		
		ArrayList<String> motsInutiles = new ArrayList<String>();
		String currentLine = new String();
		BufferedReader readFile = null;
		try {
			readFile = new BufferedReader (new FileReader(this.file));
			while ((currentLine = readFile.readLine()) != null){
				motsInutiles.add(currentLine);
			}		
		}
		catch (IOException e) {
    		e.printStackTrace();
    	} 
    	finally {
    		try {
	    		if (readFile != null) {
	    			readFile.close();		
	    		}
    		}
    		catch (IOException e) {
				e.printStackTrace();
			}
    	}
		
		return motsInutiles;
	}
	
	/**Main function for this step
	 * @param rules the AssociationRule
	 */
	public void bestLift(ArrayList<AssociationRule> rules){
		String[] rule = new String[rules.size()];
		Double[] bests = new Double[rules.size()];
		for (int i = 0; i < rules.size(); ++i){
			double trust = rules.get(i).getTrust();
			double frequency = rules.get(i).getFrequency();
			bests[i] = trust/frequency;
			rule[i] = rules.get(i).toString();
		}
		
		for (int i = 0; i < bests.length; ++i){
			
			for (int j = i; j < bests.length; ++j){
				
				if (bests[i] > bests[j]){
					Double temp = bests[i];
					bests[i] = bests[j];
					bests[j] = temp;
					
					String temp2 = rule[i];
					rule[i] = rule[j];
					rule[j] = temp2;
				}
			}
		}
		System.out.println("Voici les 10 meilleurs règles vis à vis du Lift : ");
		for (int i = 0; i < 10; ++i){
			System.out.print(rule[(rule.length-1) - i] + " : ");
			System.out.println(bests[(bests.length-1) - i]);
			System.out.println();
		}
	}
	
	/**The function executed from terminal execution
	 * @param args no arguments needed
	 */
	public static void main(String[] args){
		
		Etape3 lift = new Etape3("files/motsinutiles");
		ArrayList<AssociationRule> rules = new ArrayList<AssociationRule>();
		rules = Etape2.extractAllDf("Apriori/test.out");
		lift.bestLift(rules);
	}
	
}
