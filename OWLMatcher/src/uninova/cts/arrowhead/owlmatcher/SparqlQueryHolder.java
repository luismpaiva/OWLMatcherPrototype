package uninova.cts.arrowhead.owlmatcher;

public class SparqlQueryHolder {
	private String base ;
	private String prefixes = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
			"PREFIX owl: <http://www.w3.org/2002/07/owl#>\n"+
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
			"PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n";
	
	private String sufixes = "}";
	
	
	public SparqlQueryHolder(){
//		this.base = "PREFIX base: <http://gres.uninova.pt/~fcm/ontology/temperaturesensor/>\n";
//		this.base = "PREFIX base: <http://www.semanticweb.org/luis/ontologies/2016/3/untitled-ontology-40#>\n";
		this.base = "PREFIX base: <http://gres.uninova.pt/arrowhead/tempsensorexample/ontology/tempsensor#>";
		this.prefixes += this.base;
	}
	
	public SparqlQueryHolder(String base){
		this.base = base;
	}
	
	/*
	* SPARQL Query for childs of Measurement *subClassOf*
	*/
	public String subClassOfQuery(String subClass){
		String query =	"SELECT ?subject ?object\n"+
						"	WHERE { \n"+
						"		?subject  rdfs:subClassOf base:"+subClass+" ;\n"+
						"			rdfs:subClassOf ?object.\n";
				
		return prefixes+query+sufixes;
	}
	
// *************************************** CLASSES *****************************************

	/*
	* SPARQL Query for equivalent of Measurement *equivalentClass*
	*/
	public String equivalentClass(String equivalentClass){
		String query =	"SELECT ?subject ?object\n"+
				"	WHERE { \n"+
				"		?subject  owl:equivalentClass base:"+equivalentClass+" ;\n"+
				"			owl:equivalentClass ?object.\n";
		
		return prefixes+query+sufixes;
		
	}

	
	/*
	* SPARQL Query for childs of consumerClass *subClassOf*
	*/
	public String providerClassIsSubClassOfConsumerClassAll(String providerClass, String consumerClass){
		String query =	"SELECT ?subject\n"+
						"	WHERE { \n"+
						"		?subject  rdfs:subClassOf base:"+consumerClass+" ;\n";
				
		return prefixes+query+sufixes;
	}
	
	/*
	* SPARQL Query if providerClass is child of consumerClass *subClassOf*
	*/
	public String providerClassIsSubClassOfConsumerClass(String providerClass, String consumerClass){
		String query =	"SELECT ?object \n"
						+ "	WHERE { \n"
						+ "		base:" + providerClass + " rdfs:subClassOf ?object ."
						+ " 								FILTER (?object = base:" + consumerClass + ") .\n"
						;
				
		return prefixes+query+sufixes;
	}
	
	/*
	* SPARQL Query if consumerClass is child of providerClass *subClassOf*
	*/
	public String providerClassIsEquivalentClassToConsumerClass(String providerClass, String consumerClass){
		String query =	"SELECT ?object \n"
						+ "	WHERE { \n"
						+ "		base:" + providerClass + " owl:equivalentClass ?object ."
						+ " 								FILTER (?object = base:" + consumerClass + ") .\n"
						;
				
		return prefixes+query+sufixes;
	}	
	
// *************************************** PROPERTIES *****************************************
	
	/*
	* SPARQL Query if consumerProperty is equivalent to providerProperty 
	*/
	public String providerPropertyEquivalentPropertyToConsumerProperty(String providerProperty, String consumerProperty){
		String query =	"SELECT ?object \n"
						+ "	WHERE { \n"
						+ "		base:" + providerProperty + " owl:equivalentProperty ?object ."
						+ " 								FILTER (?object = base:" + consumerProperty + ") .\n"
						;
				
		return prefixes+query+sufixes;
	}	

	/*
	* SPARQL Query if providerClass is child of consumerClass *subClassOf*
	*/
	public String providerPropertyIsSubPropertyOfConsumerProperty(String providerProperty, String consumerProperty){
		String query =	"SELECT ?object \n"
						+ "	WHERE { \n"
						+ "		base:" + providerProperty + " rdfs:subPropertyOf ?object ."
						+ " 								FILTER (?object = base:" + consumerProperty + ") .\n"
						;
				
		return prefixes+query+sufixes;
	}
	
	// *************************************** INDIVIDUALS *****************************************
	
		/*
		* SPARQL Query if consumerIndividual is the same as (owl:sameAs) provider Individual 
		*/
		public String providerIndividualSameAsConsumerIndividual(String providerIndividual, String consumerIndividual){
			String query =	"SELECT ?object \n"
							+ "	WHERE { \n"
							+ "		base:" + providerIndividual + " owl:sameAs ?object ."
							+ " 								FILTER (?object = base:" + consumerIndividual + ") .\n"
							;
					
			return prefixes+query+sufixes;
		}	
	
}
