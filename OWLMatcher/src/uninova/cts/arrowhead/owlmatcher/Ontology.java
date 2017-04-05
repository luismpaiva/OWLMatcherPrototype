package uninova.cts.arrowhead.owlmatcher;

import java.io.File;
import java.net.URI;

import org.mindswap.pellet.PelletOptions;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.ModelFactory;

public class Ontology {
	private String fileLocation;
	private OWLOntologyManager man;
	private OntModel model;
	
	public Ontology(){
		
	}
	
	public Ontology(String ontologyFileLocation){
		this.fileLocation = ontologyFileLocation;
		this.man = OWLManager.createOWLOntologyManager();
		LoadOntology();
	}

	private void LoadOntology(){
		URI fileUri = new File(fileLocation).toURI();
		IRI ontologyIRI = IRI.create(fileUri);
		
		try {
			OWLOntology ontology = man.loadOntology(ontologyIRI);
			System.out.println("Ontology created - axiom count:"+ontology.getLogicalAxiomCount());
			System.out.println("Using ontology "+ontologyIRI.toString());
			
		} catch (OWLOntologyCreationException e) {
			e.printStackTrace();
		}
		
	}
	
	public void CreatePelletModel(){
		PelletOptions.TREAT_ALL_VARS_DISTINGUISHED = false;
		model = ModelFactory.createOntologyModel( PelletReasonerFactory.THE_SPEC );    
	}
	
	public void ReadModel(URI fileUri){
		model.read( IRI.create(fileUri).toString() );
		
	}
	
	public void LoadPelletOntology(){
		String folderAdd2 = fileLocation;
		URI fileUri = new File(folderAdd2).toURI();
		IRI ontologyIRI = IRI.create(fileUri);
		this.CreatePelletModel();
		this.ReadModel(fileUri);
		
	}

	public OntModel getModel() {
		return model;
	}

	public void setModel(OntModel model) {
		this.model = model;
	}
	
}
