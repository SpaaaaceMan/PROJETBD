package etapes;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Etape3 {
	
	private String file;
	
	

	public Etape3(String file) {
		this.file = file;
	}

	public ArrayList<String> Nettoyage () throws IOException{
		ArrayList<String> motsInutiles = new ArrayList<String>();
		String currentLine = new String();
		BufferedReader readFile = new BufferedReader (new FileReader(this.file));
		while ((currentLine = readFile.readLine()) != null){
			motsInutiles.add(currentLine);
		}
		return motsInutiles;
	}
	
}
