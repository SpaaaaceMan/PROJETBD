import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class Etape0 {

	public static void main(String[] args){
		
		String FILE_NAME = "src/test.csv";
		File file = CSVFile.getResource(FILE_NAME);
		CSVFile csvFile = new CSVFile(file, ';');
		
		TwitterHandler result = new TwitterHandler();
		
		BufferedReader input = new BufferedReader (new InputStreamReader(System.in));

		int nbTwitt = 10001;
		
		System.out.println("Mot � rechercher : ");
		
		String recherche = null;
		try {
			recherche = input.readLine();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Recherche de " + nbTwitt + " tweet contenant : " + recherche);
		
		List<Map<String, String>> data = result.searchBy(recherche, nbTwitt);
		
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

}
