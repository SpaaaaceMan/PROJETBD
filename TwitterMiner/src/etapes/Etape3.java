package etapes;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Etape3 {
	
	private String file;
	
	public Etape3(String file) {
		this.file = file;
	}

	public ArrayList<String> Nettoyage () throws IOException{
		ArrayList<String> motsInutiles = new ArrayList<String>();
		String currentLine = new String();
		BufferedReader readFile = new BufferedReader (new FileReader(this.file));
		while ((currentLine = readFile.readLine()) != null){
			motsInutiles.add(currentLine);
		}
		return motsInutiles;
	}
	
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
	
	public static void main(String[] args){
		Etape3 lift = new Etape3("files/motsinutiles");
		ArrayList<AssociationRule> rules = new ArrayList<AssociationRule>();
		rules = Etape2.extractAllDf("Apriori/test.out");
		lift.bestLift(rules);
	}
	
}
