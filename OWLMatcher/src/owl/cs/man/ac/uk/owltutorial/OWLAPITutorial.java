package owl.cs.man.ac.uk.owltutorial;

import java.io.File;
import java.net.URI;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class OWLAPITutorial {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//		IRI ontologyIRI = IRI.create("C:/Users/Luis/Documents/Uninova/Arrowhead/OWLMatcherPrototype/OWLMatcher/Ontologies/pizza.owl");
		//String folderAdd = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Ontologies\\pizza.owl";
//		String folderAdd2 = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Ontologies\\temperaturesensor_v1.owl";
		String folderAdd2 = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Ontologies\\temperaturesensor_v2.owl";
		URI fileUri = new File(folderAdd2).toURI();
		IRI ontologyIRI = IRI.create(fileUri);
		
		try {
			OWLOntology ontology = man.loadOntology(ontologyIRI);
			System.out.println(ontology.getLogicalAxiomCount());
			
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
	}
}
