import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class Etape0 {

	public static void main(String[] args) {
		
		String FILE_NAME = "src/test.csv";
		File file = CSVFile.getResource(FILE_NAME);
		CSVFile csvFile = new CSVFile(file, ';');
		
		TwitterHandler result = new TwitterHandler();
		
		BufferedReader input = new BufferedReader (new InputStreamReader(System.in));

		System.out.println("Mot à rechercher : ");
		
		String recherche = null;
		try {
			recherche = input.readLine();
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("Recherche de tweet contenant : " + recherche);
		
		List<Map<String, String>> data = result.searchBy(recherche, 10000);
		
		String[] wantedTitles = new String[data.get(0).size()];
		
		for (int i = 0; i < data.get(0).size(); ++i) {
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
		
		System.out.println("Done! " + wantedTitles.length + " status get");
	}

}
