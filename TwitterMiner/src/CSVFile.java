import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVFile {

	private File file;
    private char separator;
    
    public CSVFile(File file, char separator) {
        if (file == null) 
            throw new IllegalArgumentException("Le fichier ne peut pas etre nul");
        this.file = file;
        this.separator = separator;
    }
    
    public void write(List<Map<String, String>> mappedData, String[] titles) throws IOException {
        if (mappedData == null) 
            throw new IllegalArgumentException("la liste ne peut pas être nulle");
        if (titles == null)
            throw new IllegalArgumentException("les titres ne peuvent pas être nuls");
        FileWriter fw = null;
		try {
			fw = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        BufferedWriter bw = new BufferedWriter(fw);

        // Les titres
        boolean first = true;
        for (String title : titles) {
            if (first)
                first = false;
            else
                bw.write(separator);
            write(title, bw);
        }
        bw.write("\n");

        // Les données
        for (Map<String, String> oneData : mappedData) {
            first = true;
            for (String title : titles) {
                if (first)
                    first = false;
                else
                    bw.write(separator);
                final String value = oneData.get(title);
                write(value, bw);

            }
            
            bw.write("\n");
        }
        bw.close();
        fw.close();
    }
    
    public static String getResourcePath(String fileName) {
        final File f = new File("");
        final String dossierPath = f.getAbsolutePath() + File.separator + fileName;
        return dossierPath;
    }
    
    public static File getResource(String fileName) {
        final String completeFileName = getResourcePath(fileName);
        File file = new File(completeFileName);
        return file;
    }
    
    private void write(String value, BufferedWriter bw) throws IOException {
        if (value == null)
            value = "";
        
        boolean needQuote = false;

        if (value.indexOf("\n") != -1)
            needQuote = true;

        if (value.indexOf(separator) != -1)
            needQuote = true;

        if (value.indexOf("\"") != -1) {
            needQuote = true;
            value = value.replaceAll("\"", "\"\"");
        }

        if(needQuote)
            value = "\"" + value + "\"";

        bw.write(value);
    }
    
    private List<Map<String, String>> createMap() {

        List<Map<String, String>> data = new ArrayList<Map<String, String>>();

        Map<String, String> oneData1 = new HashMap<String, String>();
        oneData1.put("Id", "1");
        oneData1.put("Prénom", "Idéfix");
        oneData1.put("Couleur", "Blanc");
        oneData1.put("Age", "15");
        data.add(oneData1);

        Map<String, String> oneData2 = new HashMap<String, String>();
        oneData2.put("Id", "2");
        oneData2.put("Prénom", "Milou \"de Tintin\"");
        oneData2.put("Couleur", "Blanc");
        oneData2.put("Age", "7");
        data.add(oneData2);

        return data;
    }
    
	public static void main(String[] args) throws IOException {
		String FILE_NAME = "src/test.csv";
		File file;
		file = getResource(FILE_NAME);
		CSVFile CSV = new CSVFile(file, ';');
		List<Map<String, String>> data = CSV.createMap();
		String[] wantedTitles = { "Age", "Couleur", "Prénom", "Id" };
		CSV.write(data, wantedTitles);
	}

}