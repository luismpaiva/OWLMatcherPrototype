package uninova.cts.arrowhead.owlmatcher;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.jena.atlas.lib.NotImplemented;
import org.w3c.dom.Element;

import com.hp.hpl.jena.sparql.function.library.substr;

import uninova.cts.arrowhead.owlmatcher.semanticelements.SemanticConcept;
import uninova.cts.arrowhead.owlmatcher.semanticelements.SemanticElement;
import uninova.cts.arrowhead.owlmatcher.semanticelements.SemanticProperty;

public class SemanticAnnotatedElement {
	String name;
	String type;
	String fullSemanticAnnotation;
	
	String xmlPath;
	
	int minOccurences = 1;
	int maxOccurences = 1;
	
	ArrayList<String> splitSemanticAnnotations;
	ArrayList<SemanticElement> splitSemanticAnnotationsIndividuals;
	
	public SemanticAnnotatedElement(){
		splitSemanticAnnotations = new ArrayList<String>();
		splitSemanticAnnotationsIndividuals = new ArrayList<SemanticElement>();
	}
	
	public String[] splitSemanticAnnotation(String semanticAnnotation){
		String[] a;
		if (semanticAnnotation.startsWith("/"))
			a = semanticAnnotation.substring(1).split("/");
		else
			a = semanticAnnotation.split("/");
		
		for(int i=0;i<a.length;i++){
			// Even means it is a class, odd means it is a property. 
			if (i%2 == 0){ 
				SemanticConcept semElemC = new SemanticConcept();
				if (a[i].contains("[")){
					SemanticIndividual semInd = new SemanticIndividual();
					String[] conceptWithIndividual = extractIndividual(a[i]);
					
					System.out.println("Extracted individual "+conceptWithIndividual[1]+" from class - "+conceptWithIndividual[0]+". In \""+semanticAnnotation+"\"");
					
					semElemC.setName(conceptWithIndividual[0]);

					semInd.setName(conceptWithIndividual[1]);
					semElemC.setIndividual(semInd);
					
					splitSemanticAnnotationsIndividuals.add(semElemC);
				} else {
					semElemC.setName(a[i]);
					splitSemanticAnnotationsIndividuals.add(semElemC);
				}
			} else {
				 SemanticProperty semProp = new SemanticProperty();
				 semProp.setName(a[i]);
				 splitSemanticAnnotationsIndividuals.add(semProp);
			}
		}
			
		splitSemanticAnnotations = new ArrayList<String> (Arrays.asList(a));
		
		return a;
	}
	
	public ArrayList<SemanticElement> extractElement(String element){
		throw new UnsupportedOperationException();
	}
	
	public String[] extractIndividual(String element){
		return element.substring(0, element.indexOf("]")).split("\\[");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
