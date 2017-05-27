package CoreNLP;
import java.io.*;
import java.util.*;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class CoreNLPProcessor {
	
	//TODO
	public static String ProcessRelations(String unprocessedRelations){
		String processedRelations = "";
		
		Properties props = new Properties();
        props.setProperty("ssplit.eolonly","true");
        props.setProperty("annotators",
                "tokenize, ssplit, pos, depparse");
        // set up pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // get contents from file
        String content = unprocessedRelations;
        System.out.println(content);
        // read in a product review per line
        Annotation annotation = new Annotation(content);
        pipeline.annotate(annotation);

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            System.out.println("---");
            System.out.println("sentence: "+sentence);
            SemanticGraph tree = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            Collection<IndexedWord> roots = tree.getRoots();
            
            processedRelations = ProcessNodes(roots, tree);
        }
        
		return processedRelations;
	}
	
	public static String ProcessNodes(Collection<IndexedWord> roots, SemanticGraph tree){
		String processedRelations = "";
		
		for (Iterator<IndexedWord> iterator = roots.iterator(); iterator.hasNext();) {
        	processedRelations += ProcessNode(iterator.next(), tree);
        }
		
		return processedRelations;
	}
	
	public static String ProcessNode(IndexedWord root, SemanticGraph tree){
		String processedRelations = "\t";

    	List<IndexedWord> children = tree.getChildList(root);
    	if(children.size() == 0)
    		return "";
    	
    	processedRelations += (root.word() + " (");
    	
    	for(int j=0; j<children.size(); j++){
    		processedRelations += (children.get(j).word());
    		
    		if(j+1 < children.size())
    			processedRelations += (", ");
    	}
    	
    	processedRelations += (")\n");
        
    	for(int j=0; j<children.size(); j++){
    		processedRelations += ProcessNode(children.get(j), tree);
    	}
		
		return processedRelations;
	}
}
