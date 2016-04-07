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

/**
 * 
 * @author Thomas MEDARD, Kurt SAVIO, Julien TEULLE
 *
 */
public class Etape1 {
	
	/**
     * Function to convert the CSV File to a Trans File for the apriori algorithim
     */
    public static File convertToTrans(String transFilePath, String csvFilePath, String separator) {
    	//Create the file
    	File transFile = null;
    
    	//Initialize the Buffered Stuff
    	BufferedReader in  = null;
    	BufferedWriter out = null;
    	
    	//Each CSV line
    	String line = "";

    	try {

    		//Create the transFile
    		transFile = CSVFile.getResource(transFilePath);
    		//Create the new transfile if it doesn't exist
    		transFile.createNewFile();
    		
    		//Read each line from the CSV
			in = new BufferedReader(new FileReader(CSVFile.getResource(csvFilePath)));
			//This is for ignoring the line with titles
			line = in.readLine();
			
			//This contains already found words
			List<String> referenceArray = new ArrayList<String>();
			
			//This contains the number corresponding to each word
			Map<String, Integer> referenceMap = new HashMap<String, Integer>();
			//The max int
			Integer maxInt = 0;
			
			//Results
			List<List<String>> result = new ArrayList<List<String>>();
			
			while ((line = in.readLine()) != null) {
				
			    // use the given separator to split the CSV
				String[] status = line.split(separator);
				
				//The current splited line
				List<String> currentLine = new ArrayList<String>();
				
				//Put the String[] into an ArrayList<String>
				Collections.addAll(currentLine, status);
				
				//Contains the current line converted
				List<String> resultLine = new ArrayList<String>();
				
				//Look in the current line
				for (int i = 2; i < currentLine.size(); ++i) {
					//Since we need an item Set, we ignore already processed words in a status
					if (status[i] == "" || resultLine.contains(referenceMap.get(currentLine.get(i)) + " ")) {
						continue;
					}
					//If the words hasn't been found yet, add it to the reference Array
					if (!referenceArray.contains(currentLine.get(i))) {
						referenceArray.add(currentLine.get(i));
								
						//Increment the maxInt
						++maxInt;
						
						referenceMap.put(currentLine.get(i), maxInt);
					}
					//Then we get the corresponding Integer
					resultLine.add(referenceMap.get(currentLine.get(i)).toString() + " ");
				}
				
				//And we add the result line to the final result
				result.add(resultLine);
				
				//Display the input
				for (int i = 2; i < status.length; ++i) {
					System.out.print(" " + status[i]);
				}
				
				System.out.println("line finished");
			}
			
			System.out.println("Finished reading CSV file");
			
			//Creating the BufferedWriter on the trans file
			out = new BufferedWriter(new FileWriter(transFile));
			
			//Begin writing
			for (List<String> displayedLine : result) {
				for (String displayStr : displayedLine) {
					System.out.print(displayStr);
					out.write(displayStr);
				}
				if (displayedLine.size() > 0) {
					System.out.println();
					out.newLine();
				}
			}
			
    	} 
    	catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} 
    	catch (IOException e) {
    		e.printStackTrace();
    	} 
    	//Close the buffered Stuff
    	finally {
    		try {
    			
	    		if (in != null) {
	    			in.close();		
	    		}
	    		if (out != null) {
	    			out.close();
	    		}
    		}
    		catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	System.out.println("Done");
		return transFile;
    }

    /**
     * This is the main for terminal use
     * @param args termianl agruments. Should be : Etape1 <Trans file path(parent repositeray must exists)> <Csv file path (must exist)> <Csv separator(, or ; in most cases)>
     * 
     */
	public static void main(String[] args) {
		//We need all required arguments
		if (args.length < 3) {
			System.out.println("error : not enough arguments. Usage : Etape1 <Trans file path(parent repositeray must exists)>" +
					"<Csv file path (must exist)> <Csv separator(, or ; in most cases)>");
		}
		//If we have enough we go!
		else {
			Etape1.convertToTrans(args[0], args[1], args[2]);
		}
	}

}