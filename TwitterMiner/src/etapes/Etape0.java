package etapes;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * This will extract 10 000 Twitts
 * @author Thomas MEDARD, Kurt SAVIO, Julien TEULLE
 *
 */
public class Etape0 {
	
	/**This will extract nbTwitt Status from Twitter containing the word word
	 * 
	 */
	public static void etape0 (String word, int nbTwitt) {
		String FILE_NAME = "files/TwittsResults.csv";
		File file = CSVFile.getResource(FILE_NAME);
		CSVFile csvFile = new CSVFile(file, ';');
		
		TwitterHandler result = new TwitterHandler();
		
		List<Map<String, String>> data = null;
		try {
			data = result.searchBy(word, nbTwitt);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		int max = 0;
		
		for (int i = 0; i < data.size(); ++i){
			if (Integer.parseInt(data.get(i).get("Limite")) > max){
				max = Integer.parseInt(data.get(i).get("Limite"));
			}
		}

		
		String[] wantedTitles = new String[max];

		
		
		for (int i = 0; i < max; ++i) {
			
			if (i == 0) {
				wantedTitles[i] = "Date";
			}
			
			else if (i == 1) {
				wantedTitles[i] = "ScreenName";
			}
			
			else {
				wantedTitles[i] = "Word" + (i - 2);
			}
			
		}
		
		try {
			csvFile.write(data, wantedTitles);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Done! " + (data.size()-1) + " status get");
	}
	
	/**
	 * Main for terminal execution
	 * @param args terminal arguements (no needs here)
	 */
	public static void main(String[] args){
		
		BufferedReader input = new BufferedReader (new InputStreamReader(System.in));
		
		int nbTwitt = 10001;
		System.out.println("Word to look for : ");
		
		String recherche = null;
		try {
			recherche = input.readLine();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Searching " + nbTwitt + " twitter status containing : " + recherche);
		
		Etape0.etape0(recherche, nbTwitt);
	}

}
