package Wrapper;

import java.util.HashMap;
import java.util.Map;

public class TextPreprocessor {
	public static Map<ETextField, String> ProcessText(String text){
		Map<ETextField, String> map = new HashMap<ETextField, String>();
		
		String[] lines = text.split("\n");
		String link = lines[0].substring(lines[0].indexOf(']')+1, lines[0].contains("(") ? lines[0].indexOf('(') : lines[0].lastIndexOf('['));
		String title = lines[1];
		String authors = lines[2];
		String comments = "";
		String journalRef = "";
		String subjects = "";
		String unprocessedRelations = "";
		
		for(int i=3; i<lines.length-1; i++){
			String line = lines[i];
			
			if(line.contains("Comments:")){
				comments = line.substring(line.indexOf(":")+1, line.length()-1);
			}
			
			if(line.contains("Journal-ref:")){
				journalRef = line.substring(line.indexOf(":")+1, line.length()-1);
			}
			
			if(line.contains("Subjects:")){
				subjects = line.substring(line.indexOf(":")+1, line.length()-1);
			}
		}
		
		//This is the text to be processed by the CoreNLP tool
		unprocessedRelations = lines[lines.length-1];
		
		map.put(ETextField.Link, link);
		map.put(ETextField.Title, title);
		map.put(ETextField.Authors, authors);
		map.put(ETextField.Comments, comments);
		map.put(ETextField.JournalRef, journalRef);
		map.put(ETextField.Subjects, subjects);
		map.put(ETextField.UnprocessedRelations, unprocessedRelations);
		map.put(ETextField.ProcessedRelations, "");
		
		return map;
	}
	
	public static String GetResult(Map<ETextField, String> map){
		String output = "Authors: " + map.get(ETextField.Authors) + "\n";
		output += "Title: " + map.get(ETextField.Title) + "\n";
		output += "Subjects: " + map.get(ETextField.Subjects) + "\n";
		output += "Comments: " + map.get(ETextField.Comments) + "\n";
		output += "Link: " + map.get(ETextField.Link) + "\n";
		output += "Relations: " + map.get(ETextField.ProcessedRelations);
		
		return output;
	}
}
