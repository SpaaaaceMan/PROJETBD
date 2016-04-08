package etapes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This the step to extract association rules from apriori result
 * @author Thomas MEDARD, Kurt SAVIO, Julien TEULLE
 *
 */
public class Etape2 {
	
	/**
	 * This will generate all under item sets from an item set
	 * @param itemSet
	 * @return
	 */
	public static ArrayList<String> getAllUnderItemSet (Collection<String> itemSet) {
		//This the Level 0 items
		ArrayList<String> level0Items = new ArrayList<String>();
		level0Items.addAll(itemSet);
		
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
			ArrayList<String> currentLevel0Items = new ArrayList<String>();
			currentLevel0Items.addAll(level0Items);
			
			//We build the new  level of under item
			for (int i = 0; (i + offSet) < underItems.size(); ++i) {
				//Current underItem list
				currentUnderItems = new ArrayList<String>();
				//We delete the current Item content from current Level0 Items
				for (int j = 0; j < underItems.get(i + offSet).length(); ++j) {
					String currentItem = "" + underItems.get(i + offSet);
					currentLevel0Items.remove(currentItem);
				}
				
				//If it's empty then we have finished for this under items level
				if (currentLevel0Items.size() <= 0) {
					break;
				}
				
				//Now we can add each new under item from this last under Item level
				for (int j = 0; j < currentLevel0Items.size(); ++j) {
					String itemToAdd = "(" + underItems.get(i + offSet) + "; " + currentLevel0Items.get(j) + ")";
					if (!currentUnderItems.contains(itemToAdd)) {
						currentUnderItems.add(itemToAdd);
					}
				}				
				underItems.addAll(currentUnderItems);
			}
			
			//We need to get the last iteration result size for offset
			offSet += currentUnderItems.size();
			//If we found nothing, then we have finished
			if (currentLevel0Items.size() <= 0) {
				break;
			}
		}	
		
		//The item set shouldn't be its own under item
		if (itemSet.size() <= 1) {
			for (String underItem : itemSet) {
				underItems.remove(underItem);
			}
		}
		//System.out.println(itemSet);
		//System.out.println(underItems);
		return underItems;
		
	}
	
	public static File extractDF(String outPath, String dfFilePath, int minFreq, int minConf) {
		//Create the file
    	File dfFile = null;
    
    	//Initialize the Buffered Stuff
    	BufferedReader in  = null;
    	BufferedReader inWords = null;
    	BufferedWriter out = null;
    	
    	try {

    		//Create the dfFile
    		dfFile = CSVFile.getResource(dfFilePath);
    		//Create the new dfFile if it doesn't exist
    		dfFile.createNewFile();
    		
    		//Read each line from the CSV
			in = new BufferedReader(new FileReader(CSVFile.getResource(outPath)));
			//Read from wordReference.txt
			inWords = new BufferedReader (new FileReader(CSVFile.getResource("files/wordReference.txt")));
			
			//corresponding word
			String correspondingWord = "";
			//All words reference
			ArrayList<String> wordsReference = new ArrayList<String>();
			//Reading from files/wordReference.txt
			System.out.println("Reading from wordReference");
			while ((correspondingWord = inWords.readLine()) != null) {
				wordsReference.add(correspondingWord);
			}
			
			//All .out lines
			String outLine = "";	
			//This is all item set associated with there frequency
			Map<ArrayList<String>, Integer> itemSets = new HashMap<ArrayList<String>, Integer>();
			//This all under item from a Set
			Map<ArrayList<String>, ArrayList<String>> underItemSets = new HashMap<ArrayList<String>, ArrayList<String>>();
			//First we need to get all lines because we need to know them all
			
			System.out.println("Reading from files");
			while ((outLine = in.readLine()) != null) {
				ArrayList<String> itemSet = new ArrayList<String>();
				//Current support
				int freq = 0;
				//Iterate into the line to get Y and its freq
				for (String line : outLine.split(" ")) {
					
					//If the first char is '(' then it's the support
					if (line.charAt(0) == '(') {
						String supportValue = "" + line.charAt(1);
						freq = Integer.parseInt(supportValue);
					}
					else if (Integer.parseInt(line) < wordsReference.size()){
						itemSet.add(wordsReference.get(Integer.parseInt(line)));
					}
					else {
						itemSet.add("unreferenced word");
					}
				}
				//We stock them and there under item set Point 1
				itemSets.put(itemSet, freq);
				underItemSets.put(itemSet, getAllUnderItemSet(itemSet));
			}
			
			//This is all kept association rules
			ArrayList<AssociationRule> aR = new ArrayList<AssociationRule>(); 
			
			//Then we can process them and make the point 2
			//We get the Iterator from underItemSets
			Iterator<Entry<ArrayList<String>, ArrayList<String>>> itemSetsIter = underItemSets.entrySet().iterator();
			while(itemSetsIter.hasNext()) {
				//Get current itemSet
				Entry<ArrayList<String>, ArrayList<String>> item = itemSetsIter.next();
				
				double itemFreq = itemSets.get(item.getKey());
				
				for (int i = 0; i < item.getValue().size(); ++i) {
					
					ArrayList<String>currentItem = new ArrayList<String>();
					
					String[] strs = item.getValue().get(i).split("; ");
					for (String str : strs) {
						currentItem.add(str);
					}
					
					if (itemSets.containsKey(currentItem)) {
						double itemTrust = itemFreq / itemSets.get(currentItem);
						if (itemTrust >= minFreq) {
							AssociationRule currentAR = new AssociationRule(item.getKey(), item.getValue(), itemFreq, itemTrust);
							aR.add(currentAR);
							System.out.println(currentAR);
						}
					}					
				}
			}
			//write inside dfFile
			
			//Creating the BufferedWriter on the trans file
			out = new BufferedWriter(new FileWriter(dfFile));
			
			//Begin writing
			for (AssociationRule ar : aR) {
				out.write(ar.toString());
				out.newLine();
			}
			
			System.out.println("done");
			
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
    	
		return dfFile;
	}
	
	public static ArrayList<AssociationRule> getExtractDF(String outPath, String dfFilePath, int minFreq, int minConf) {
		//Create the file
    	File dfFile = null;
    
    	//Initialize the Buffered Stuff
    	BufferedReader in  = null;
    	BufferedReader inWords = null;
    	BufferedWriter out = null;
    	
    	//This is all kept association rules
		ArrayList<AssociationRule> aR = new ArrayList<AssociationRule>(); 
    	
    	try {

    		//Create the dfFile
    		dfFile = CSVFile.getResource(dfFilePath);
    		//Create the new dfFile if it doesn't exist
    		dfFile.createNewFile();
    		
    		//Read from wordReference.txt
			inWords = new BufferedReader (new FileReader(CSVFile.getResource("files/wordReference.txt")));
			
			//corresponding word
			String correspondingWord = "";
			//All words reference
			ArrayList<String> wordsReference = new ArrayList<String>();
			//Reading from files/wordReference.txt
			while ((correspondingWord = inWords.readLine()) != null) {
				wordsReference.add(correspondingWord);
			}
			
    		//Read each line from the CSV
			in = new BufferedReader(new FileReader(CSVFile.getResource(outPath)));
			
			//All cvs lines
			String outLine = "";
			//This is all item set associated with there frequency
			Map<ArrayList<String>, Integer> itemSets = new HashMap<ArrayList<String>, Integer>();
			//This all under item from a Set
			Map<ArrayList<String>, ArrayList<String>> underItemSets = new HashMap<ArrayList<String>, ArrayList<String>>();
			//First we need to get all lines because we need to know them all
			while ((outLine = in.readLine()) != null) {
				ArrayList<String> itemSet = new ArrayList<String>();
				//Current support
				int freq = 0;
				//Iterate into the line to get Y and its freq
				for (String line : outLine.split(" ")) {
					
					//If the first char is '(' then it's the support
					if (line.charAt(0) == '(') {
						String supportValue = "" + line.charAt(1);
						freq = Integer.parseInt(supportValue);
					}
					else if (Integer.parseInt(line) < wordsReference.size()){
						itemSet.add(wordsReference.get(Integer.parseInt(line)));
					}
					else {
						itemSet.add("unreferenced word");
					}
				}
				//We stock them and ther under item set Point 1
				itemSets.put(itemSet, freq);
				underItemSets.put(itemSet, getAllUnderItemSet(itemSet));
			}
			
			//Then we can process them and make the point 2
			//We get the Iterator from underItemSets
			Iterator<Entry<ArrayList<String>, ArrayList<String>>> itemSetsIter = underItemSets.entrySet().iterator();
			while(itemSetsIter.hasNext()) {
				//Get current itemSet
				Entry<ArrayList<String>, ArrayList<String>> item = itemSetsIter.next();
				
				double itemFreq = itemSets.get(item.getKey());
				
				for (int i = 0; i < item.getValue().size(); ++i) {
					
					ArrayList<String>currentItem = new ArrayList<String>();
					
					String[] strs = item.getValue().get(i).split("; ");
					for (String str : strs) {
						currentItem.add(str);
					}
					
					if (itemSets.containsKey(currentItem)) {
						double itemTrust = itemFreq / itemSets.get(currentItem);
						if (itemTrust >= minFreq) {
							aR.add(new AssociationRule(item.getKey(), item.getValue(), itemFreq, itemTrust));
						}
					}					
				}
			}
			//write inside dfFile
			
			//Creating the BufferedWriter on the trans file
			out = new BufferedWriter(new FileWriter(dfFile));
			
			//Begin writing
			for (AssociationRule ar : aR) {
				out.write(ar.toString());
				out.newLine();
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
		return aR;
	}
	
	public static ArrayList<AssociationRule> extractAllDf (String outPath) {

		//Initialize the Buffered Stuff
    	BufferedReader in  = null;
    	BufferedReader inWords = null;
    	
    	//This is all kept association rules
		ArrayList<AssociationRule> aR = new ArrayList<AssociationRule>(); 
    	
    	try {

    		//Read from wordReference.txt
			inWords = new BufferedReader (new FileReader(CSVFile.getResource("files/wordReference.txt")));
			
			//corresponding word
			String correspondingWord = "";
			//All words reference
			ArrayList<String> wordsReference = new ArrayList<String>();
			//Reading from files/wordReference.txt
			System.out.println("Reading from wordReference");
			while ((correspondingWord = inWords.readLine()) != null) {
				wordsReference.add(correspondingWord);
			}
    		
    		//Read each line from the CSV
			in = new BufferedReader(new FileReader(CSVFile.getResource(outPath)));
			
			//All cvs lines
			String outLine = "";
			//This is all item set associated with there frequency
			Map<ArrayList<String>, Integer> itemSets = new HashMap<ArrayList<String>, Integer>();
			//This all under item from a Set
			Map<ArrayList<String>, ArrayList<String>> underItemSets = new HashMap<ArrayList<String>, ArrayList<String>>();
			//First we need to get all lines because we need to know them all
			while ((outLine = in.readLine()) != null) {
				ArrayList<String> itemSet = new ArrayList<String>();
				//Current support
				int freq = 0;
				//Iterate into the line to get Y and its freq
				for (String line : outLine.split(" ")) {
					
					//If the first char is '(' then it's the support
					if (line.charAt(0) == '(') {
						String supportValue = "" + line.charAt(1);
						freq = Integer.parseInt(supportValue);
					}
					else if (Integer.parseInt(line) < wordsReference.size()){
						itemSet.add(wordsReference.get(Integer.parseInt(line)));
					}
					else {
						itemSet.add("unreferenced word");
					}
				}
				//We stock them and ther under item set Point 1
				itemSets.put(itemSet, freq);
				underItemSets.put(itemSet, getAllUnderItemSet(itemSet));
			}
			
			//Then we can process them and make the point 2
			//We get the Iterator from underItemSets
			Iterator<Entry<ArrayList<String>, ArrayList<String>>> itemSetsIter = underItemSets.entrySet().iterator();
			while(itemSetsIter.hasNext()) {
				//Get current itemSet
				Entry<ArrayList<String>, ArrayList<String>> item = itemSetsIter.next();
				
				double itemFreq = itemSets.get(item.getKey());
				
				for (int i = 0; i < item.getValue().size(); ++i) {
					
					ArrayList<String>currentItem = new ArrayList<String>();
					
					String[] strs = item.getValue().get(i).split("; ");
					for (String str : strs) {
						currentItem.add(str);
					}
					
					if (itemSets.containsKey(currentItem)) {
						double itemTrust = itemFreq / itemSets.get(currentItem);
						aR.add(new AssociationRule(item.getKey(), item.getValue(), itemFreq, itemTrust));
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
    		}
    		catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	
    	System.out.println("Done");
		return aR;
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

