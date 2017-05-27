package Core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import Wrapper.ETextField;
import Wrapper.TextPreprocessor;

public class Main {

	public static void main(String[] args) {
		File folder = new File("Resources/");
		File[] listOfFiles = folder.listFiles();
		
	    for (int i = 0; i < listOfFiles.length; i++) {
	    	System.out.println("File " + listOfFiles[i].getName());
			String text = GetTextString(listOfFiles[i]);
				
			Map<ETextField, String> map = TextPreprocessor.ProcessText(text);	
			
			String unprocessedRelations = map.get(ETextField.UnprocessedRelation);
			
			//TODO CoreNLP processing to the unprocessedRelations string
			//TODO Put the CoreNLP processed string on the map by using map.put(ETextField.ProcessedRelations, processedRelation);

			System.out.println(TextPreprocessor.GetResult(map) + "\n\n");

	    }

	}
	
	public static String GetTextString(File file){
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String text = "";
			String tempHelper;
			while ((tempHelper = reader.readLine()) != null) {
				text += tempHelper;
		        text += "\n";
		    }
			
			return text;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "";
	}

}
