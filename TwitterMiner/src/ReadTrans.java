import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadTrans {
	
	public static void convertTransToCsv(String transFilePath, String csvFilePath) {
		
		try (BufferedReader br = new BufferedReader(new FileReader(transFilePath)))
		{
			String FILE_NAME = csvFilePath;
			File file = CSVFile.getResource(FILE_NAME);
			file.createNewFile();
			CSVFile csv = new CSVFile(file, ';');
			
			ArrayList<String> wantedTitles = new ArrayList<String>();
			String currentLine;
			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			int max = 0;
			
			while ((currentLine = br.readLine()) != null) {
				
				String[] currentSplitLine = currentLine.split(" ");
				
				Map<String, String> oneData = new HashMap<String, String>();
				
				for (int i = 0; i < currentSplitLine.length; ++i){
					System.out.println("taille " + currentSplitLine.length);
					if (i != currentSplitLine.length - 1){
						oneData.put("Motif " + i, currentSplitLine[i]);
					}
					else{
						oneData.put("Support", currentSplitLine[i]);
					}
					if (i > max)
						max = i;
				}
				System.out.println("endline");
		        data.add(oneData);
			}
			System.out.println("max = " + max);
			for (int i = 0; i < max + 1; ++i){
				if (i != max)
					wantedTitles.add("Motif " + i);
				else
				wantedTitles.add("Support");
			}
			csv.writeTrans(data, wantedTitles);

		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("error : not enough arguments. Usage : ReadTrans <Trans file file path (must exist)>" +
					"<Csv path(parent repositeray must exists)>");
		}
		else {
			ReadTrans.convertTransToCsv(args[0], args[1]);
		}
	}
}
