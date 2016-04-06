import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class Etape2 {
	
	public static File extractDF(String outPath, String dfFilePath, int minFreq, int minConf) {
		//Create the file
    	File dfFile = null;
    
    	//Initialize the Buffered Stuff
    	BufferedReader in  = null;
    	BufferedWriter out = null;
    	
    	//Each CSV line
    	String line = "";
    	
    	@SuppressWarnings("unused")
		Collection<AssociationRule> result = new ArrayList<AssociationRule>();
    	
    	try {

    		//Create the dfFile
    		dfFile = CSVFile.getResource(dfFilePath);
    		//Create the new dfFile if it doesn't exist
    		dfFile.createNewFile();
    		
    		//Read each line from the CSV
			in = new BufferedReader(new FileReader(CSVFile.getResource(outPath)));
			
			while ((line = in.readLine()) != null) {
				//Current line in the .out file
				String[] currentStrs = line.split(" ");
				//Current support
				@SuppressWarnings("unused")
				int support;
				//Current associative rule
				//AssociativeRule aR = new AssociationRule('A', 'A');
				
				for (String currentItem : currentStrs) {
					//If the first char is '(' then it's the support
					if (currentItem.charAt(0) == '(') {
						String supportValue = "" + currentItem.charAt(1);
						support = Integer.parseInt(supportValue);
					}
					else {
						//ToDo
					}
				}
			}
			
			//ToDo : write inside dfFile
			
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
		return dfFile;
	}

	public static void main(String[] args) {
		if (args.length < 4) {
			System.out.println("error : not enough arguments. Usage : Etape2 <apriori.out filepath (result from apriori algorithim)>" +
					"<Df file path (parent directory must exist)> <minFreq (int)> <minConf (int)>");
		}
		else {
			Etape2.extractDF(args[0], args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]));
		}
	}

}
