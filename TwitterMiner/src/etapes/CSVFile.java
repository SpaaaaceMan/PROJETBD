package etapes;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**This is the class for the CSVFile. An interesting upgrade would be to extend it from File
 * @author Thomas
 *
 */
public class CSVFile {

	/**
	 * The File to interact with
	 */
	private File file;
	
    /**
     * The char to separate words inside the CSV File.
     */
    private char separator;
    
    /**The constructor.
     * @param file The File to use. It has to be initialized. Idea : extends File to get ride of the File attribute
     * @param separator The char used to separate words inside the CSV File.
     */
    public CSVFile(File file, char separator) {
        if (file == null) 
            throw new IllegalArgumentException("Le fichier ne peut pas etre nul");
        this.file = file;
        this.separator = separator;
    }
    
    /**The function to write all lines inside the CSV.
     * @param mappedData The data mapped correctly, see result form {@link Etape0#etape0(String, int)}
     * @param titles The titles wich will be at the first line. There should be as many titles as cell (like the length)
     * @throws IOException some Exception from File class
     */
    public void write(List<Map<String, String>> mappedData, String[] titles) throws IOException {
    	
        if (mappedData == null) 
            throw new IllegalArgumentException("la liste ne peut pas être nulle");
        
        if (titles == null)
            throw new IllegalArgumentException("les titres ne peuvent pas être nuls");
        
        FileWriter fw = null;
        
		try {
			fw = new FileWriter(file);
		} 
		catch (IOException e) {
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
        for (int j = 0; j < mappedData.size() - 1; j++) {
            first = true;
            
            for (int i = 0; i < mappedData.get(j).size(); ++i){
            //for (String title : titles) {
            	
                if (first)
                    first = false;
                else
                    bw.write(separator);
                
                if (i == 0){
                	final String value = mappedData.get(j).get("Date");
                	write(value, bw);
                }
                else if (i == 1){
                	final String value = mappedData.get(j).get("ScreenName");
                	write(value, bw);
                }
                else {
                	final String value = mappedData.get(j).get("Word" + i);
                	write(value, bw);
                }

            }
            
            bw.write("\n");
        }
        bw.close();
        fw.close();
    }
    
    /**This write the .trans file, like the write function, but for the apriori algorithm.
     * @param mappedData The data mapped correctly, see result form {@link Etape0#etape0(String, int)}
     * @param titles The titles which will be at the first line. There should be as many titles as cell (like the length)
     * @throws IOException some Exception from File class
     */
    public void writeTrans(List<Map<String, String>> mappedData, ArrayList<String> titles) throws IOException {
    	
        if (mappedData == null) 
            throw new IllegalArgumentException("la liste ne peut pas être nulle");
        
        if (titles == null)
            throw new IllegalArgumentException("les titres ne peuvent pas être nuls");
        
        FileWriter fw = null;
        
		try {
			fw = new FileWriter(file);
		}
		catch (IOException e) {
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
        for (int j = 0; j < mappedData.size(); j++) {
            first = true;
            
            for (int i = 0; i < mappedData.get(j).size(); ++i){
            //for (String title : titles) {
                if (first)
                    first = false;
                else
                    bw.write(separator);
                
                if (i != mappedData.get(j).size() - 1){
                	final String value = mappedData.get(j).get("Motif " + i);
                	bw.write(value);
                }
                else {
                	final String value = mappedData.get(j).get("Support");
                	String separators = "";
                	for (int k = 0; k < titles.size() - mappedData.get(j).size(); ++k)
                		separators += separator;
                	bw.write(separators + value);
                }
            }
            bw.write("\n");
        }
        bw.close();
        fw.close();
    }
    
    /**This function is very useful (so much that it should be in an FileUtility class). It get the absolute file name.
     * @param fileName The relative file name
     * @return The absolute file name
     */
    public static String getResourcePath(String fileName) {
        final File f = new File("");
        final String dossierPath = f.getAbsolutePath() + File.separator + fileName;
        return dossierPath;
    }
    
    /**This function is very useful (so much that it should be in an FileUtility class). It get the File object from the relative file name.
     * @param fileName The relative file name
     * @return The File object
     */
    public static File getResource(String fileName) {
        final String completeFileName = getResourcePath(fileName);
        File file = new File(completeFileName);
        return file;
    }
    
    /**This is the function to add the '"' around words in the CSV file. 
     * @param value the line
     * @param bw The BufferedWriter linked to the CSV file to write into.
     * @throws IOException some Exception from File class
     */
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
    
    /** This is to create test mapped data. Unused and should be remove
     * @return test mapped data
     */
    @SuppressWarnings("unused")
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
    
    /**Convert this CSV file into a .trans file for apriori processing.
     * @return The File object to the .trans file
     */
    public File convertToTrans() {
    	File Transfile = new File (this.file.getAbsolutePath());
    
    	BufferedReader in  = null;
    	
    	String line = "";

    	try {

			in = new BufferedReader(new FileReader(this.file));
			while ((line = in.readLine()) != null) {
				
			    // use this separator as separator
				String[] status = line.split(Character.toString(this.separator));
				List<String> currentLine = new ArrayList<String>();
				
				Collections.addAll(currentLine, status);
		
				for (int i = 2; i < status.length; ++i) {
					System.out.println(" " + status[i]);
				}		
			}
    	} 
    	catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} 
    	catch (IOException e) {
    		e.printStackTrace();
    	} 
    	finally {
    		if (in != null) {
    			try {
    				in.close();
    			} 
    			catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	System.out.println("Done");
		return Transfile;
    }
    
	/*public static void main(String[] args) throws IOException {
		String FILE_NAME = "src/test.csv";
		File file = getResource(FILE_NAME);
		CSVFile CSV = new CSVFile(file, ';');
		TwitterHandler result = new TwitterHandler();
		List<Map<String, String>> data = result.searchBy();
		String[] wantedTitles = { "Date", "ScreenName", "Text"};
		CSV.write(data, wantedTitles);
	}*/

}
