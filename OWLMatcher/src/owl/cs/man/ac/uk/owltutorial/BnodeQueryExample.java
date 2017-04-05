package owl.cs.man.ac.uk.owltutorial;

import java.io.File;
import java.net.URI;



import org.mindswap.pellet.PelletOptions;
import org.mindswap.pellet.jena.PelletReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.clarkparsia.pellet.sparqldl.jena.SparqlDLExecutionFactory;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;



public class BnodeQueryExample {
 public static void main(String[] args) throws Exception { 
 	PelletOptions.TREAT_ALL_VARS_DISTINGUISHED = false;
//     String ns = "http://www.w3.org/TR/2003/PR-owl-guide-20031209/food#";
      
     // create an empty ontology model using Pellet spec
     OntModel model3 = ModelFactory.createOntologyModel( PelletReasonerFactory.THE_SPEC );        

//		String folderAdd3 = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Ontologies\\temperaturesensor_v1.owl";
		String folderAdd3 = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Ontologies\\temperaturesensor_v2.owl";
		URI fileUri3 = new File(folderAdd3).toURI();
		
		OWLOntologyManager manager3 = OWLManager.createOWLOntologyManager();
		OWLOntology ontology3 = manager3.loadOntology(IRI.create(fileUri3));
     
     
     // read the file
     model3.read( IRI.create(fileUri3).toString() );	  

     // Construct sparql query
     String query3Begin = 
             "PREFIX rdf:  <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\r\n" + 
             "PREFIX rdfs:  <http://www.w3.org/2000/01/rdf-schema#>\r\n" + 
             "PREFIX about: <http://gres.uninova.pt/~fcm/ontology/temperaturesensor/>\r\n" + 
             "PREFIX owl: <http://www.w3.org/2002/07/owl#>\r\n" + 
             "\r\n" + 
             "SELECT * \r\n" + 
             "WHERE {\r\n";
     String query3End = "}";

     // Construct sparql where clause
     String query3Str =
             query3Begin + 
             "   ?subject ?a ?e ;\r\n"+
//             " 				   rdfs:equivalentClass ?object .\r\n"+
//             " 			   	   about:Measurement ?e.\r\n"+
//             + " 			  ?first"  
             query3End;
     
     
     Query query3 = QueryFactory.create( query3Str );

     printQueryResults( 
             "Running first query with PelletQueryEngine...", 
             SparqlDLExecutionFactory.create( query3, model3 ), query3 );
     
 }
 
 public static void printQueryResults( String header, QueryExecution qe, Query query ) throws Exception {
     System.out.println(header);
     
     ResultSet results = qe.execSelect();

		ResultSetFormatter.out( System.out, results, query );
		
		System.out.println();
 }
}
