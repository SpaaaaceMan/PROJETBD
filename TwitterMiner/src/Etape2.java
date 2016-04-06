import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Etape2 {
	
	public static File extractDF(String outPath, String dfFilePath, int minFreq, int minConf) {
		//Create the file
    	File dfFile = null;
    
    	//Initialize the Buffered Stuff
    	BufferedReader in  = null;
    	BufferedWriter out = null;
    	
		Collection<AssociationRule> result = new ArrayList<AssociationRule>();
    	
    	try {

    		//Create the dfFile
    		dfFile = CSVFile.getResource(dfFilePath);
    		//Create the new dfFile if it doesn't exist
    		dfFile.createNewFile();
    		
    		//Read each line from the CSV
			in = new BufferedReader(new FileReader(CSVFile.getResource(outPath)));
			
			//All cvs lines
			String outLine = "";
			//This is all item set associated with there frequency
			Map<String, Integer> itemSets = new HashMap<String, Integer>();
			//First we need to get all lines because we need to know them all
			while ((outLine = in.readLine()) != null) {
				
				//Current item
				String item = "";
				//Current support
				int freq = 0;
				//Iterate into the line to get Y and its freq
				for (String line : outLine.split(" ")) {
					
					//If the first char is '(' then it's the support
					if (line.charAt(0) == '(') {
						String supportValue = "" + line.charAt(1);
						freq = Integer.parseInt(supportValue);
					}
					else {
						item += line;
					}
				}
				itemSets.put(item, freq);
				
			}
			
			//Then we can process them	
			//Now we find all under set (sous-ensemble)
			//For each item
			Iterator<Entry<String, Integer>> itemSetsIter = itemSets.entrySet().iterator();
			//This the Level 0 items
			ArrayList<String> level0Items = new ArrayList<String>();
			//We get all level 0 items
			while(itemSetsIter.hasNext()) {
				//Get current item
				Map.Entry<String, Integer> item = itemSetsIter.next();
				for (int i = 0; i < item.getKey().length(); ++i) {
					String itemCharStr = "" + item.getKey().charAt(i);
					//We want only Level 0 item
					if (level0Items.contains(itemCharStr)) {
						continue;
					}
					
					level0Items.add(itemCharStr);
					
				}
			}
		
			//This is all under non null item from item
			ArrayList<String> underItems = new ArrayList<String>();
			//We add the first level
			underItems.addAll(level0Items);
			//This is the underItems offset
			int offSet = 0;
			while (true) {
				//Current underItem list
				Collection<String> currentUnderItems = new ArrayList<String>();
				
				//Level 0 item that we need to add
				ArrayList<String> currentLevel0Items = level0Items;
				
				//We build the new  level of under item
				for (int i = 0; (i + offSet) < underItems.size(); ++i) {
					//We delete the current Item content from current Level0 Items
					for (int j = 0; j < underItems.get(i + offSet - 1).length(); ++j) {
						String currentItem = "" + underItems.get(i).charAt(j);
						currentLevel0Items.remove(currentItem);
					}
					
					//If it's empty then we have finished for this under items level
					if (currentLevel0Items.size() <= 0) {
						break;
					}
					
					//Now we can add each new under item from this last under Item level
					for (int j = 0; j < currentLevel0Items.size(); ++j) {
						currentUnderItems.add(underItems.get(i + offSet) + currentLevel0Items.get(j));
					}
					underItems.addAll(currentUnderItems);
				}
				
				System.out.println(currentUnderItems.toString());
				
				//We need to get the last iteration result size for offset
				offSet = currentUnderItems.size();
				if (offSet <= 0) {
					break;
				}
			}	
			
			for (String item : underItems) {
				System.out.println(item);
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
