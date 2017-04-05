package owl.cs.man.ac.uk.owltutorial;

import java.io.File;
import java.net.URI;
import java.util.Set;

//Portions Copyright (c) 2006 - 2008, Clark & Parsia, LLC. <http://www.clarkparsia.com>
//Clark & Parsia, LLC parts of this source code are available under the terms of the Affero General Public License v3.
//
//Please see LICENSE.txt for full license terms, including the availability of proprietary exceptions.
//Questions, comments, or requests for clarification: licensing@clarkparsia.com

//package org.mindswap.pellet.examples;

//import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.util.Version;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;

import pellet.Pellet;

/*
* Created on Oct 10, 2004
*/

/**
* @author Evren Sirin
*/
public class OWLAPIExample {
	
 private void showIndividualsDetails(PelletReasoner reasoner, OWLClass className){
		NodeSet<OWLNamedIndividual> individualsUnits = reasoner.getInstances( className, false);
		
		if (individualsUnits.isEmpty()){
			System.out.println("No Individuals for class: "+className.toString());

		}
		else {
			for(Node<OWLNamedIndividual> sameInd : individualsUnits) {
				// sameInd contains information about the individual (and all other individuals that were inferred to be the same)
				OWLNamedIndividual ind =  sameInd.getRepresentativeElement();
				System.out.println("Individual URL: "+ind);
				
			    // get the info about this specific individual
	//			Set<OWLLiteral> names = reasoner.getDataPropertyValues( ind, foafName );	    		    		    	
			    NodeSet<OWLClass> types = reasoner.getTypes( ind, true );		    
	
			    if( types.isEmpty() ) {
					System.out.println( "Type: Unknown " );
				}
				else{
				    OWLClass type = types.iterator().next().getRepresentativeElement();
					System.out.println( "Type:" + type + "; " );
				}
			    
			    // there may be zero or more homepages so check first if there are any found
	//			if( homepages.isEmpty() ) {
	//				System.out.println( "Homepage: Unknown " );
	//			}
	//			else {
	//				System.out.print( "Homepage:" );
	//				for( Node<OWLNamedIndividual> homepage : homepages ) {
	//					System.out.println( " " + homepage.getRepresentativeElement().getIRI() + "; " );
	//				}
	//			}
			    System.out.println();
		    }
		}	 
 } 
	
	
 public final static void main(String[] args) throws Exception  {
		String file = "http://www.mindswap.org/2004/owl/mindswappers#";
		String ontologyNamespace = "http://gres.uninova.pt/~fcm/ontology/temperaturesensor/";
		
//		String folderAdd2 = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Ontologies\\temperaturesensor_v1.owl";
		String folderAdd2 = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Ontologies\\temperaturesensor_v2.owl";
		URI fileUri = new File(folderAdd2).toURI();
		
		System.out.print("Reading file " + folderAdd2 + "...\n");
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ontology = manager.loadOntology(IRI.create(fileUri));
		
		PelletReasoner reasoner = PelletReasonerFactory.getInstance().createReasoner( ontology );
		System.out.println("done.");
		
//		
//		<owl:Class rdf:about="http://gres.uninova.pt/~fcm/ontology/temperaturesensor/Units"/>
//		
		reasoner.getKB().realize();
		reasoner.getKB().printClassTree();
		
		// create property and resources to query the reasoner
		OWLClass Person = manager.getOWLDataFactory().getOWLClass(IRI.create("http://xmlns.com/foaf/0.1/Person"));
		OWLClass Units = manager.getOWLDataFactory().getOWLClass(IRI.create(ontologyNamespace+"Units"));
		OWLClass Measurement = manager.getOWLDataFactory().getOWLClass(IRI.create(ontologyNamespace+"Measurement"));
		OWLClass Location = manager.getOWLDataFactory().getOWLClass(IRI.create(ontologyNamespace+"Location"));
//		OWLObjectProperty workHomepage = manager.getOWLDataFactory().getOWLObjectProperty(IRI.create("http://xmlns.com/foaf/0.1/workInfoHomepage"));
//		OWLDataProperty foafName = manager.getOWLDataFactory().getOWLDataProperty(IRI.create("http://xmlns.com/foaf/0.1/name"));
		
		OWLAPIExample example = new OWLAPIExample();
		example.showIndividualsDetails(reasoner, Measurement);
		example.showIndividualsDetails(reasoner, Units);
		example.showIndividualsDetails(reasoner, Location);
		
	}
}
