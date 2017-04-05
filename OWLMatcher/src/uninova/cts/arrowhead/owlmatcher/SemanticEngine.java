package uninova.cts.arrowhead.owlmatcher;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.clarkparsia.pellet.sparqldl.jena.SparqlDLExecutionFactory;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

import uninova.cts.arrowhead.owlmatcher.semanticelements.SemanticConcept;
import uninova.cts.arrowhead.owlmatcher.semanticelements.SemanticElement;
import uninova.cts.arrowhead.owlmatcher.semanticelements.mappings.SemanticMapping;
import uninova.cts.arrowhead.owlmatcher.translations.XSLTFileFactory;
import uninova.cts.arrowhead.owlmatcher.translations.XSLTTransformation;
import uninova.cts.arrowhead.owlmatcher.translations.XSLTransformationsFactory;
import uninova.cts.arrowhead.owlmatcher.translations.xsltelements.XSLTDocument;
import uninova.cts.arrowhead.owlmatcher.translations.xsltelements.XSLTStyleSheet;
import uninova.cts.arrowhead.owlmatcher.translations.xsltelements.XSLTTemplate;
import uninova.cts.arrowhead.owlmatcher.utils.JDOM2_DOM_Manipulation;
import uninova.cts.arrowhead.owlmatcher.utils.TextAreaOutputStream;
import uninova.cts.arrowhead.owlmatcher.utils.TextPaneOutputStream;

@SuppressWarnings("deprecation")
public class SemanticEngine {
	
	private final SparqlQueryHolder sqh = new SparqlQueryHolder();
//	private final Ontology onto = new Ontology("C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Ontologies\\temperaturesensor_v3_1.owl");
//	private final Ontology onto = new Ontology("C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Ontologies\\IECON2016.owl");
	private static Ontology onto = null;
	private static PrintStream printStream;
	
	private final String schemaElementTagName = "xs:element";
	private final String annotationAttribTag = "sawsdl:modelReference";
	
	// para correr em windows
	private String ontologyLocation = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Ontologies\\tempsensor.owl";
	private String providerXMLLocation = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Xsd_Xml\\provider.xml";
	private String consumerXMLLocation = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Xsd_Xml\\consumer.xml";
	private String translationXSLTLocation = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Xsd_Xml\\";
	private String providerSchemaLocation = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Xsd_Xml\\provider.xsd";
	private String consumerSchemaLocation = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Xsd_Xml\\consumer.xsd";
	private String consumer2SchemaLocation = "C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Xsd_Xml\\consumertest.xsd";
											
	// para correr no servidor do GRES
//	private String ontologyLocation = "/var/www/html/dtg/SemanticTranslatorWeb/php/tempsensor.owl";
//	private String providerXMLLocation = "/var/www/html/dtg/SemanticTranslatorWeb/xsdfiles/provider.xml";
//	private String consumerXMLLocation = "/var/www/html/dtg/SemanticTranslatorWeb/xsdfiles/translatedconsumer.xml";
//	private String translationXSLTLocation = "/var/www/html/dtg/SemanticTranslatorWeb/xsdfiles/";
//	private String providerSchemaLocation = "/var/www/html/dtg/SemanticTranslatorWeb/php/provider.xsd";
//	private String consumerSchemaLocation = "/var/www/html/dtg/SemanticTranslatorWeb/php/consumer.xsd";
//	private String consumer2SchemaLocation = "/var/www/html/dtg/SemanticTranslatorWeb/php/consumertest.xsd";
	
	// para correr no servidor na maq virtual local
//	private String ontologyLocation = "/var/www/SemanticTranslatorWeb/php/tempsensor.owl";
//	private String providerXMLLocation = "/var/www/SemanticTranslatorWeb/xsdfiles/provider.xml";
//	private String consumerXMLLocation = "/var/www/SemanticTranslatorWeb/xsdfiles/translatedconsumer.xml";
//	private String translationXSLTLocation = "/var/www/SemanticTranslatorWeb/xsdfiles/";
//	private String providerSchemaLocation = "/var/www/SemanticTranslatorWeb/php/provider.xsd";
//	private String consumerSchemaLocation = "/var/www/SemanticTranslatorWeb/php/consumer.xsd";
//	private String consumer2SchemaLocation = "/var/www/SemanticTranslatorWeb/php/consumertest.xsd";

	private static OutputStream out;
	private static TextAreaOutputStream txtAreaOut;
	
	private SemanticAnnotatedSchema providerSchema;
	private SemanticAnnotatedSchema consumerSchema;
	
	public ArrayList<SemanticMapping> SemanticMappingTable; 
	
	
//	private ArrayList<SemanticAnnotatedElement> semAnnotElems = new ArrayList<SemanticAnnotatedElement>();
	
	public SemanticEngine() {
		SemanticMappingTable = new ArrayList<SemanticMapping>();
	}
	
	public SemanticEngine(String ontologyLocation) {
		this();
		this.ontologyLocation = ontologyLocation;
		onto = new Ontology(ontologyLocation);
	}

	public SemanticEngine(TextAreaOutputStream out){
		this();
		this.txtAreaOut = out;
		
	}

	public SemanticEngine(PrintStream printStream){
		this();
		this.printStream = printStream;
		
	}
	
	public SemanticEngine(TextPaneOutputStream out){
		this();
		this.out = out;
		
	}
	
	public SemanticEngine(OutputStream out){
		this();
		this.out = out;
		
	}
	
	 public static void printQueryResults (ResultSet results, Query query) throws Exception {
	     ResultSetFormatter.out( System.out, results, query );
		 
	 }

	 
	 public void printSemanticAnnotatedElements(String providerLoc, String consumerLoc, String ontologyLoc) {
		System.out.println("Entered : printSemanticAnnotatedElements");

		if (!providerLoc.equals(null) && !providerLoc.equals("")) {
			providerSchemaLocation = providerLoc;
		}
		System.out.println("Configured path <providerLoc> for xsds to: "+providerLoc+";");

		if (!consumerLoc.equals(null) && !consumerLoc.equals("")) {
			consumerSchemaLocation = consumerLoc;
		}
		System.out.println("Configured path <consumerLoc> for xsds to: "+consumerLoc+";");
		
		System.out.println("Using ontology from folder <"+ontologyLoc+">");
		System.out.println("<Semantic Engine> Current working Directory = " + System.getProperty("user.dir"));
		
		if (!ontologyLoc.equals(null) && !ontologyLoc.equals("")) {
			onto = new Ontology(ontologyLoc);
		}

		System.out.println("Configured path for xsds;");

		providerSchema = new SemanticAnnotatedSchema(providerSchemaLocation);
		consumerSchema = new SemanticAnnotatedSchema(consumerSchemaLocation);

		System.out.println("providerSchema and consumerSchema classes initialized.");

		providerSchema.extractSemanticAnnotations(providerSchema.extractNodeList());
		consumerSchema.extractSemanticAnnotations(consumerSchema.extractNodeList());
		
		System.out.println("extracted semantic annotations for provider and consumer schemas.");

		// get provider's list of annotated elements
		ArrayList<SemanticAnnotatedElement> providerAnnotElems = providerSchema.annotatedElements; 
		// get consumer's list of annotated elements
		ArrayList<SemanticAnnotatedElement> consumerAnnotElems = consumerSchema.annotatedElements;
		

		for (int i = 0; i<consumerAnnotElems.size();i++)
			for(int j = 0; j<providerAnnotElems.size();j++){
//				boolean isEqual = this.annotationIsSemanticallyEqual(providerAnnotElems.get(j), consumerAnnotElems.get(i));
				boolean isEqualOOP = this.annotationIsSemanticallyEqualOOP(providerAnnotElems.get(j), consumerAnnotElems.get(i));
				if (isEqualOOP) {
					System.out.println("########################################################################################");
					System.out.print(" Annotation P - <" + providerAnnotElems.get(j).fullSemanticAnnotation + "> IS Semantically Equal to");
					System.out.println(" Annotation C - <" + consumerAnnotElems.get(i).fullSemanticAnnotation + ">");
					System.out.println("########################################################################################");

					SemanticMapping mapping = new SemanticMapping();
					
					mapping.setMapping(providerAnnotElems.get(j), consumerAnnotElems.get(i));
					
					SemanticMappingTable.add(mapping);
					
					
					
				} else {
					System.out.println("########################################################################################");
					System.out.print(" Annotation P - <" + providerAnnotElems.get(j).fullSemanticAnnotation + "> is NOT Semantically Equal to");
					System.out.println(" Annotation C - <" + consumerAnnotElems.get(i).fullSemanticAnnotation + ">");
					System.out.println("########################################################################################");
				}
				
				
			}
		
		System.out.println("\n########################################################################################");
		System.out.println("------------------------------ Mapping started -----------------------------------------");
		System.out.println("Mapping with <" + SemanticMappingTable.size()+ "> elements.");
		for (int nn=0; nn<SemanticMappingTable.size(); nn++) {
			System.out.println("Element <" + SemanticMappingTable.get(nn).getSourceSemAnnotElem().getName() + "> ---->  <"+SemanticMappingTable.get(nn).getTargetSemAnnotElemt().name+">.");
			
			JDOM2_DOM_Manipulation jdm = new JDOM2_DOM_Manipulation();
			SemanticMappingTable.get(nn).setSourceSemXmlPath(consumerSchema.getElementXmlPath(jdm.getJDOM2ElementFromDOM((Element)(providerSchema.getDoc().getFirstChild())), 
															 SemanticMappingTable.get(nn).getSourceSemAnnotElem().getName(), 
															 "/"));
		}
		
		System.out.println("------------------------------ Mapping finished ----------------------------------------");
		System.out.println("########################################################################################\n");
		
		System.out.println("########################################################################################");
		System.out.println("--------------------------- Generating translation ------------------------------------");
		
		System.out.println("<Semantic Engine> Current working Directory = " + System.getProperty("user.dir"));
		
		XSLTDocument XSLTDoc = generateTranslator(providerSchema, consumerSchema, SemanticMappingTable);
		Document XMLOutputScheleton;
		
		System.out.println("--------------------------- Printing Provider XML Scheleton ---------------------------------------");
		printTranslationXML(generateXMLScheleton(providerSchema), System.out);
		System.out.println("--------------------------- Saving Provider XML Scheleton -----------------------------------------");

		System.out.println("--------------------------- Printing Consumer XML Scheleton ---------------------------------------");
		printTranslationXML(generateXMLScheleton(consumerSchema), System.out);
		System.out.println("--------------------------- Saving Consumer XML Scheleton -----------------------------------------");

		System.out.println("--------------------------- Printing Consumer Test XML Scheleton ---------------------------------------");
		SemanticAnnotatedSchema consumerTestSchema = new SemanticAnnotatedSchema(consumer2SchemaLocation);
		printTranslationXML(generateXMLScheleton(consumerTestSchema), System.out);
		System.out.println("--------------------------- Saving Consumer Test XML Scheleton -----------------------------------------");

		System.out.println("--------------------------- Printing translator ---------------------------------------");
		printTranslationXML(XSLTDoc, System.out);
		
		SemanticAnnotatedSchema consumer2Schema = new SemanticAnnotatedSchema(consumer2SchemaLocation);
		XSLTDocument XSLTDoc2 = generateTranslator(providerSchema, consumer2Schema, SemanticMappingTable);
		printTranslationXML(XSLTDoc2, System.out);

		System.out.println("--------------------------- Saving Translator -----------------------------------------");
//		String loc = System.getProperty("user.dir");
//		String providerXMLFileName = providerXMLLocation;
//		String consumerXMLFileName = "translatedconsumer.xml";
//		String translationXSLTFileNameA = "XSLTTarget";
//		String translationXSLTFileNameB = "targetXSLT";
//		
		String loc = System.getProperty("user.dir");
		String providerXMLFileName = providerXMLLocation;
		String consumerXMLFileName = consumerXMLLocation;
		String translationXSLTFileNameA = "XSLTTarget";
		String translationXSLTFileNameB = "targetXSLT";
		
		// Saving XSLT generated - option A 
		XSLTFileFactory xsltFileDoc = new XSLTFileFactory(translationXSLTLocation, translationXSLTFileNameA);
		xsltFileDoc.saveToFile(XSLTDoc, false);
		
		// Saving XSLT generated - option B 
		XSLTransformationsFactory transformationFactory = new XSLTransformationsFactory();
		transformationFactory.toFile(translationXSLTLocation, XSLTDoc, translationXSLTFileNameB);

		System.out.println("--------------------------- Finished Saving Translator --------------------------------");
		
		XSLTTransformation transformation = new XSLTTransformation(providerXMLFileName, consumerXMLFileName, XSLTDoc);

		// executing transformation
		transformation.TransformXMLintoXML(providerXMLFileName, consumerXMLFileName, translationXSLTLocation+translationXSLTFileNameB);

	 }

	 public void generateTranslation(String providerXMLFileName, String consumerXMLFileName, String translationXSLTFileName){
		 System.out.println("Generate Translation method");
		 XSLTTransformation transformation = new XSLTTransformation(providerXMLFileName, consumerXMLFileName, new XSLTDocument());
		 System.out.println("Generate Translation method - 2");
		 transformation.TransformXMLintoXML(providerXMLFileName, consumerXMLFileName, translationXSLTLocation+translationXSLTFileName);
		 System.out.println("Generate Translation method - 3");
	 }
	 
	 public XSLTDocument generateTranslator(SemanticAnnotatedSchema providerSchema, SemanticAnnotatedSchema consumerSchema, ArrayList<SemanticMapping> SemMapTbl){
//			SemanticEngine semEngine = new SemanticEngine();

			SemanticAnnotatedSchema consumerSchema1 = consumerSchema;
			System.out.println("<Semantic Engine:generateTranslation> consumerSchema working Directory = " + consumerSchema1.schemaLocation);

			if (consumerSchema == null){
		 		consumerSchema1 = new SemanticAnnotatedSchema(consumerSchemaLocation);
		 	}
			
		 	System.out.println("<Semantic Engine:generateTranslation> Current working Directory = " + System.getProperty("user.dir"));
			System.out.println("<Semantic Engine:generateTranslation> providerSchema working Directory = " + providerSchema.schemaLocation);
			System.out.println("<Semantic Engine:generateTranslation> consumerSchema working Directory = " + consumerSchema1.schemaLocation);

			Document docConsumerXSD = consumerSchema1.getDoc();
			
			Element rootElemConsumerXSD = docConsumerXSD.getDocumentElement();

			XSLTDocument XSLTdocument = new XSLTDocument();
			XSLTStyleSheet xslStylesheetRoot = new XSLTStyleSheet();
			// create XMLFROMSCHEMA and 
			// insert the template in a <xsl:template match="/">
			XSLTTemplate xslTemplateGenerated = new XSLTTemplate();
			
			xslTemplateGenerated = (XSLTTemplate) (consumerSchema.CreateXMLTranslatorFromSchema(rootElemConsumerXSD, xslTemplateGenerated, SemMapTbl, true));
			
			xslStylesheetRoot.addContent(xslTemplateGenerated);
			XSLTdocument.setRootElement(xslStylesheetRoot);
			
			return XSLTdocument;
	 }
	 
	 public org.jdom2.Document generateXMLScheleton(SemanticAnnotatedSchema deviceXMLSchema){
//			SemanticEngine semEngine = new SemanticEngine();

			SemanticAnnotatedSchema deviceXMLSchema1 = deviceXMLSchema;
			System.out.println("<Semantic Engine:generateTranslation> consumerSchema working Directory = " + deviceXMLSchema1.schemaLocation);

			if (deviceXMLSchema == null){
		 		deviceXMLSchema1 = new SemanticAnnotatedSchema(consumerSchemaLocation);
		 	}
			
			org.jdom2.Document jdomDocDeviceXMLSchema = deviceXMLSchema1.getJDOM2Doc();
			org.jdom2.Element rootElemXMLSchema = jdomDocDeviceXMLSchema.getRootElement();
			
			org.jdom2.Document XMLScheletonDocument = new org.jdom2.Document();
			org.jdom2.Element firstChildElemXML;

			firstChildElemXML = deviceXMLSchema.CreateXMLScheletonFromSchema(rootElemXMLSchema, null, true);
			XMLScheletonDocument.setRootElement(firstChildElemXML);
			
			return XMLScheletonDocument;
	 }
	 
	 
	 public void printTranslationXML(org.jdom2.Document docToPrint, OutputStream os){
			org.jdom2.output.XMLOutputter xmlOutputter = new org.jdom2.output.XMLOutputter(org.jdom2.output.Format.getPrettyFormat());
	        try {
				xmlOutputter.output(docToPrint, os);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	 }
	 
	 public void printTranslationXML(Document docXml){
		    try
		    {
		      OutputFormat format = new OutputFormat (docXml);
		      StringWriter stringOut = new StringWriter ();
		      XMLSerializer serial = new XMLSerializer (stringOut, 
		                                                format);
		      serial.serialize(docXml);
		      System.out.println(stringOut.toString());
		    }
		    catch (FactoryConfigurationError e)
		    {
		      System.out.println
		          ("Unable to get a document builder factory: " + e);
		    }
		    catch (IOException e)
		    {
		      System.out.println("I/O error: " + e);
		    }
		 
	 }
	 
	 /* Annotation is considered equal, if 
	  * 	(a) all concepts in both are the same, and in the same order;
	  * 	(b) the annotations are equivalent, meaning concepts are equivalent  in ontology <owl:equivalentClass>
	  * 	(c) the annotations from provider is a subClassOf consumer (provider can be more specific/consumer needs to be at least the same abstraction))
	  * 	(d) if individuals are the same, from the same semantic path
	  */
	 public boolean annotationIsSemanticallyEqualOOP(SemanticAnnotatedElement annotationP, SemanticAnnotatedElement annotationC){
		 boolean equal = false;
		 int jIndexOld = -1;
		 int jIndexCur = -1;
		 for (int i = 0; i<annotationC.splitSemanticAnnotationsIndividuals.size(); i++)
		 {
			 System.out.println("Starting evaluation of: " + annotationC.splitSemanticAnnotationsIndividuals.get(i).getName() );
			 for (int j = 0; j<annotationP.splitSemanticAnnotationsIndividuals.size(); j++){
				 
				 if (annotationC.splitSemanticAnnotationsIndividuals.get(i).getType() == annotationP.splitSemanticAnnotationsIndividuals.get(j).getType()) {
					 if (j>jIndexCur) {
						 if (isSemanticallyEqual( annotationP.splitSemanticAnnotationsIndividuals.get(j), annotationC.splitSemanticAnnotationsIndividuals.get(i))){
							 jIndexCur = j;
							 break;
						 }
					 }
				 }
			 }
			 if (jIndexCur == jIndexOld)
				 break;
			 else {
				 if (i == annotationC.splitSemanticAnnotationsIndividuals.size() - 1)
					 equal = true;
			 }
			 jIndexOld = jIndexCur;
		 }
		 
		 return equal;
	 }
	 
	 public boolean isSemanticallyEqual(SemanticElement pAnnotationConcept, SemanticElement cAnnotationConcept){
		 boolean isSemanticallyEqual = false;
		 switch (pAnnotationConcept.getType()) {
				 case PROPERTY: 
					 if (isEqual(pAnnotationConcept.getName(),cAnnotationConcept.getName())) {
						 isSemanticallyEqual = true;
					 } else 
					 if (isEquivalentProperty(pAnnotationConcept.getName(), cAnnotationConcept.getName())){
						 	isSemanticallyEqual = true;
					 } else
					 if (isSubPropertyOf(pAnnotationConcept.getName(), cAnnotationConcept.getName())){
						 	isSemanticallyEqual = true;
				 	 }
					 break;
				 case CONCEPT:
					 if ( ((SemanticConcept)pAnnotationConcept).hasIndividual() && ((SemanticConcept)cAnnotationConcept).hasIndividual()) {
							 if (isSameIndividual( ((SemanticConcept)pAnnotationConcept).getIndividual().getName(), ((SemanticConcept)cAnnotationConcept).getIndividual().getName())){
							 		isSemanticallyEqual = true;
							 }
					 	} else {
							 if (isEqual(pAnnotationConcept.getName(),cAnnotationConcept.getName())) {
								 isSemanticallyEqual = true;
							 } else
							 if (isEquivalent(pAnnotationConcept.getName(), cAnnotationConcept.getName())) {
								 isSemanticallyEqual = true;
							 } else
							 if (isSubClassOf(pAnnotationConcept.getName(), cAnnotationConcept.getName())) {
								 isSemanticallyEqual = true;
							 }
					 	}
					 		
					 break;
//						 case INDIVIDUAL:
//							 if (isSameIndividual(pAnnotationConcept.getName(), cAnnotationConcept.getName())){
//								 return true;
//							 }
//							 break;
				 default:isSemanticallyEqual = false;
					
		 }
				 
//		 if (isEqual(pAnnotationConcept, cAnnotationConcept))
//			 return true;
//		 if (isEquivalent(pAnnotationConcept, cAnnotationConcept))
//			 return true;
//		 if (isSubClassOf(pAnnotationConcept, cAnnotationConcept))
//			 return true;
//
//		 if (isEqualProperty(pAnnotationConcept, cAnnotationConcept))
//		 	return true;
//	 	 if (isEquivalentProperty(pAnnotationConcept, cAnnotationConcept))
//		 	return true;
//		 if (isSubPropertyOf(pAnnotationConcept, cAnnotationConcept))
//			 return true;
//		 if (isSameIndividual(pAnnotationConcept.getName(), cAnnotationConcept.getName())){
//			 return true;
//		 }
//		 isSemanticallyEqual = true;
		 
		 return isSemanticallyEqual;
	 }
	 
	 public boolean isEquivalent(String pAnnotationConcept, String cAnnotationConcept) {
		 // run SPARQL query for <owl:equivalentClass>
		 
		 if (onto==null) {
			 onto = new Ontology(ontologyLocation);
		 }

		 onto.LoadPelletOntology();
		 
	     Query query = QueryFactory.create( sqh.providerClassIsEquivalentClassToConsumerClass(pAnnotationConcept, cAnnotationConcept));
		 
	     QueryExecution qe = SparqlDLExecutionFactory.create( query, onto.getModel() ); 
	     
	     ResultSet rs = qe.execSelect();
	     boolean isEquivalentResult = rs.hasNext();
	     
	     this.printReasoning(isEquivalentResult, pAnnotationConcept, cAnnotationConcept, "equivalentClass to");
	     	     
	     return isEquivalentResult;
	 }
	 
	 public boolean isSubClassOf(String pAnnotationConcept, String cAnnotationConcept){
		 // run SPARQL query for <rdfs:subClassOf>
		 boolean isSubClassOfResult;
		 if (onto==null) {
			 onto = new Ontology(ontologyLocation);
		 }
		 
		 onto.LoadPelletOntology();
		 
	     Query query = QueryFactory.create( sqh.providerClassIsSubClassOfConsumerClass(pAnnotationConcept, cAnnotationConcept));
		 
	     QueryExecution qe = SparqlDLExecutionFactory.create( query, onto.getModel() ); 
	     
	     ResultSet rs = qe.execSelect();
	     isSubClassOfResult = rs.hasNext();
	     
	     this.printReasoning(isSubClassOfResult, pAnnotationConcept, cAnnotationConcept, "subClassOf");
	     
		 return isSubClassOfResult;
	 } 
	 
	 public boolean isEqual(String pAnnotationConcept, String cAnnotationConcept){
		 boolean isEqualResult = cAnnotationConcept.equals(pAnnotationConcept);
		 
		 this.printReasoning(isEqualResult, pAnnotationConcept, cAnnotationConcept, "equal to");
		 		 
		 return isEqualResult;
	 }
	 
	 public boolean isSameIndividual(String pAnnotationIndividual, String cAnnotationIndividual){
		 // run SPARQL query for <owl:sameAs>
		 if (onto==null) {
			 onto = new Ontology(ontologyLocation);
		 }

		 onto.LoadPelletOntology();
		 
	     Query query = QueryFactory.create( sqh.providerIndividualSameAsConsumerIndividual(pAnnotationIndividual, cAnnotationIndividual));
		 
	     QueryExecution qe = SparqlDLExecutionFactory.create( query, onto.getModel() ); 
	     
	     ResultSet rs = qe.execSelect();
	     boolean isSameAs = rs.hasNext();
	     
	     this.printReasoningIndividual(isSameAs, pAnnotationIndividual, cAnnotationIndividual, "same Individual as ");
	     	     
	     return isSameAs;
	 }

	 public boolean isEquivalentProperty(String pAnnotationConcept, String cAnnotationConcept) {
		 // run SPARQL query for <owl:equivalentClass>
		 if (onto==null) {
			 onto = new Ontology(ontologyLocation);
		 }

		 onto.LoadPelletOntology();
		 
	     Query query = QueryFactory.create( sqh.providerPropertyEquivalentPropertyToConsumerProperty(pAnnotationConcept, cAnnotationConcept));
		 
	     QueryExecution qe = SparqlDLExecutionFactory.create( query, onto.getModel() ); 
	     
	     ResultSet rs = qe.execSelect();
	     boolean isEquivalentResult = rs.hasNext();
	     
	     this.printReasoningProperty(isEquivalentResult, pAnnotationConcept, cAnnotationConcept, "equivalentProperty to");
	     	     
	     return isEquivalentResult;
	 }
	 
	 public boolean isSubPropertyOf(String pAnnotationConcept, String cAnnotationConcept){
		 // run SPARQL query for <rdfs:subPropertyOf>
		 boolean isSubPropertyOfResult;
		 if (onto==null) {
			 onto = new Ontology(ontologyLocation);
		 }
			 

		 onto.LoadPelletOntology();
		 
	     Query query = QueryFactory.create( sqh.providerPropertyIsSubPropertyOfConsumerProperty(pAnnotationConcept, cAnnotationConcept));
		 
	     QueryExecution qe = SparqlDLExecutionFactory.create( query, onto.getModel() ); 
	     
	     ResultSet rs = qe.execSelect();
	     isSubPropertyOfResult = rs.hasNext();
	     
	     this.printReasoningProperty(isSubPropertyOfResult, pAnnotationConcept, cAnnotationConcept, "subPropertyOf");
	     
		 return isSubPropertyOfResult;
	 } 
	 
	 public boolean isEqualProperty(String pAnnotationConcept, String cAnnotationConcept){
		 boolean isEqualResult = cAnnotationConcept.equals(pAnnotationConcept);
		 
		 this.printReasoningProperty(isEqualResult, pAnnotationConcept, cAnnotationConcept, "equal Property to");
		 		 
		 return isEqualResult;
	 }
	 
	 public void printReasoning(boolean reasoning, String elementP, String elementC, String comparisonOperation){
		 if (reasoning) {
			System.out.println("----------------------------------------------------------------------------------------");
			System.out.print("Concept P - <" + elementP + "> IS "+comparisonOperation+" ");
			System.out.println("Concept C - <" + elementC +">");
			System.out.println("----------------------------------------------------------------------------------------");
	     }
	     else{
				System.out.println("----------------------------------------------------------------------------------------");
				System.out.print("Concept P - <" + elementP + "> is NOT "+comparisonOperation+" ");
				System.out.println("Concept C - <" + elementC +">");
				System.out.println("----------------------------------------------------------------------------------------");
	    	 
	     }
		 
	 }
	 
	 public void printReasoningProperty(boolean reasoning, String elementP, String elementC, String comparisonOperation){
		 if (reasoning) {
			System.out.println("----------------------------------------------------------------------------------------");
			System.out.print("Property P - <" + elementP + "> IS "+comparisonOperation+" ");
			System.out.println("Property C - <" + elementC +">");
			System.out.println("----------------------------------------------------------------------------------------");
	     }
	     else{
				System.out.println("----------------------------------------------------------------------------------------");
				System.out.print("Property P - <" + elementP + "> is NOT "+comparisonOperation+" ");
				System.out.println("Property C - <" + elementC +">");
				System.out.println("----------------------------------------------------------------------------------------");
	    	 
	     }
		 
	 }
	 
	 public void printReasoningIndividual(boolean reasoning, String elementP, String elementC, String comparisonOperation){
		 if (reasoning) {
			System.out.println("----------------------------------------------------------------------------------------");
			System.out.print("Individual P - <" + elementP + "> IS "+comparisonOperation+" ");
			System.out.println("Individual C - <" + elementC +">");
			System.out.println("----------------------------------------------------------------------------------------");
	     }
	     else{
				System.out.println("----------------------------------------------------------------------------------------");
				System.out.print("Individual P - <" + elementP + "> is NOT "+comparisonOperation+" ");
				System.out.println("Individual C - <" + elementC +">");
				System.out.println("----------------------------------------------------------------------------------------");
	    	 
	     }
		 
	 }
	 
	 public void extractSemanticAnnotations(NodeList nl){
			//loop to get elements
			for(int i = 0 ; i < nl.getLength(); i++)
			{
				Element first = (Element)nl.item(i) ;
				SemanticAnnotatedElement firstSemAnEl = new SemanticAnnotatedElement();
				if( first.hasAttribute(annotationAttribTag)) {
					String attrib = first.getAttribute(annotationAttribTag);
//					semAnnElements.add(first);
					firstSemAnEl.fullSemanticAnnotation = attrib;
					firstSemAnEl.name = first.getAttribute("name");
					firstSemAnEl.type = first.getAttribute("type");
					String[] a = firstSemAnEl.splitSemanticAnnotation(attrib);
//					String nm = first.getAttribute("name");
//					System.out.println(firstSemAnEl.name);
//					String nm1 = first.getAttribute("type");
//					System.out.println(firstSemAnEl.type);
//					String nm2 = first.getAttribute(annotationAttribTag);
//					System.out.println(firstSemAnEl.fullAnnotation);
				}
			}		 
	 }
	 
 	 public void extract(SemanticAnnotatedSchema providerSemanticAnnotationXSD, 
 			 			 SemanticAnnotatedSchema consumerSemanticAnnotationXSD) {
//			String attribName = "xmlns:xs";
			String xsdElement = "xs:element";
			String xsdAttribute = "xs:attribute";
			String xsdSAModelReference = "sawsdl:modelReference"; 
			
			Document providerDoc = providerSemanticAnnotationXSD.getDoc();
			Element providerRootEl = providerDoc.getDocumentElement();
			Document consumerDoc = consumerSemanticAnnotationXSD.getDoc();
			Element consumerRootEl = consumerDoc.getDocumentElement();
			
			NodeList consumerChildren = consumerRootEl.getChildNodes();
			NodeList providerChildren = providerRootEl.getChildNodes();
			
			for ( int i =0; i<consumerChildren.getLength(); i++){
				Element consumerChild = (Element) consumerChildren.item(i);
				if (consumerChild.hasAttribute(xsdSAModelReference)) {
					for (int j =0; j<providerChildren.getLength(); j++){
						Element providerChild = (Element) providerChildren.item(j);
						if (providerChild.hasAttribute(xsdSAModelReference)) {
							
						}
					}
				}
			}
			System.out.println(providerRootEl.getAttribute(xsdElement));
			System.out.println(consumerRootEl.getAttribute(xsdElement));
		} 

	 public Document createNewXMLDocument(){
		Document doc = null;

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return doc;
	 }
		
		
	 public String getProviderSchemaLocation() {
		return providerSchemaLocation;
	}

	public void setProviderSchemaLocation(String providerSchemaLocation) {
		this.providerSchemaLocation = providerSchemaLocation;
	}

	public String getConsumerSchemaLocation() {
		return consumerSchemaLocation;
	}

	public void setConsumerSchemaLocation(String consumerSchemaLocation) {
		this.consumerSchemaLocation = consumerSchemaLocation;
	}

	public static void main(String[] args) {
		SemanticEngine semEngine = new SemanticEngine();
		System.out.println("args are: " + args.length);
		String s = semEngine.ontologyLocation;
		
		if (args.length != 0) {
			semEngine.setProviderSchemaLocation(args[0]);
			semEngine.setConsumerSchemaLocation(args[1]);
		} 
		
//		semEngine.generateTranslation();

//		SemanticAnnotatedSchema consumerSchema1 = new SemanticAnnotatedSchema("C:\\Users\\Luis\\Documents\\Uninova\\Arrowhead\\OWLMatcherPrototype\\OWLMatcher\\Xsd_Xml\\consumer.xsd");
		semEngine.printSemanticAnnotatedElements(semEngine.getProviderSchemaLocation(), semEngine.getConsumerSchemaLocation(), s);
		
		SemanticAnnotatedSchema consumerSchema1 = new SemanticAnnotatedSchema(semEngine.getConsumerSchemaLocation());
		Document docXSD = consumerSchema1.getDoc();
		System.out.println("---------------- Semantic Engine Main ------------- ");
		
		
		Element rootXSD = docXSD.getDocumentElement();
		
		System.out.println("---------------- Semantic Engine Main 2 ------------- ");
		
		Document docXml = semEngine.createNewXMLDocument();
		Element elemXML = docXml.createElement("rootElement");
		System.out.println("---------------- Semantic Engine Main 3 ------------- ");

		elemXML = (Element) consumerSchema1.CreateXMLTranslatorFromSchema(rootXSD, elemXML, semEngine.SemanticMappingTable).getFirstChild();
		docXml.appendChild(elemXML);
		System.out.println("---------------- Semantic Engine Main 4 ------------- ");
	
	    try
	    {
	      OutputFormat format = new OutputFormat (docXml);
			System.out.println("---------------- Semantic Engine Main 5 ------------- ");
	      StringWriter stringOut = new StringWriter ();
			System.out.println("---------------- Semantic Engine Main 6 ------------- ");
	      XMLSerializer serial = new XMLSerializer (stringOut, 
	                                                format);
			System.out.println("---------------- Semantic Engine Main 7 ------------- ");
	      serial.serialize(docXml);
	      System.out.println("----------------- XML Translated Message --------------");
	      System.out.println(stringOut.toString());
	      System.out.println("---- Finished --- XML Translated Message --------------");
	    }
	    catch (FactoryConfigurationError e)
	    {
	      System.out.println
	          ("Unable to get a document builder factory: " + e);
	    }
	    catch (IOException e)
	    {
	      System.out.println("I/O error: " + e);
	    }
	 }
		
	 /*
	  * 
	  *  «««««««««««««« OLD »»»»»»»»»»»»»»
	  *  
	  *  
	  *  */
		 
	 @Deprecated
	 public static void printQueryResults( String header, QueryExecution qe, Query query ) throws Exception {
	     System.out.println(header);
	     
	     ResultSet results = qe.execSelect();
	     System.out.println("query hasnext:"+results.hasNext());

//		     System.setOut(new PrintStream(txtAreaOut));
	     
	     System.out.println("testing");
//		     ResultSetFormatter.out( txtAreaOut, results, query );
	     ResultSetFormatter.out( System.out, results, query );
//		     System.out.flush();			
//			 System.out.println();
	 }
		 
	 @Deprecated
	 public void run() throws Exception { 
		 onto.LoadPelletOntology();

//		     Query query3 = QueryFactory.create( sqh.subClassOfQuery("Temperature") );
	     Query query1 = QueryFactory.create( sqh.providerClassIsSubClassOfConsumerClass("InTemp", "Temp"));
	     System.out.println(query1);
	     printQueryResults( 
	             "Running first query with PelletQueryEngine...", 
	             SparqlDLExecutionFactory.create( query1, onto.getModel() ), query1 );
	     Query query2 = QueryFactory.create( sqh.providerClassIsSubClassOfConsumerClass("RoomTemp", "Temp1"));
	     System.out.println(query2);
	     printQueryResults( 
	             "Running first query with PelletQueryEngine...", 
	             SparqlDLExecutionFactory.create( query2, onto.getModel() ), query2 );
	     Query query3 = QueryFactory.create( sqh.providerClassIsSubClassOfConsumerClass("LeftSideRoomTemp", "Temp"));
	     System.out.println(query3);
	     printQueryResults( 
	             "Running first query with PelletQueryEngine...", 
	             SparqlDLExecutionFactory.create( query3, onto.getModel() ), query3 );
	     
	 }
		 
	 /* Annotation is considered equal, if 
	  * 	(a) all concepts in both are the same, and in the same order;
	  * 	(b) the annotations are equivalent, meaning concepts are equivalent  in ontology <owl:equivalentClass>
	  * 	(c) the annotations from provider is a subClassOf consumer (provider can be more specific/consumer needs to be at least the same abstraction))
	  * 	(d) if individuals are the same, from the same semantic path
	  */
	 @Deprecated
	 public boolean annotationIsSemanticallyEqual(SemanticAnnotatedElement annotationP, SemanticAnnotatedElement annotationC){
		 boolean equal = false;
		 int jIndexOld = -1;
		 int jIndexCur = -1;
		 for (int i = 0; i<annotationC.splitSemanticAnnotations.size(); i++)
		 {
			 for (int j = 0; j<annotationP.splitSemanticAnnotations.size(); j++){
				 if (j>jIndexCur) {
					 if (isSemanticallyEqual( annotationP.splitSemanticAnnotations.get(j), annotationC.splitSemanticAnnotations.get(i))){
						 jIndexCur = j;
						 break;
					 }
				 }
			 }
			 if (jIndexCur == jIndexOld)
				 break;
			 else {
				 if (i == annotationC.splitSemanticAnnotations.size() - 1)
					 equal = true;
			 }
			 jIndexOld = jIndexCur;
		 }
		 
		 return equal;
	 }
		 
	 @Deprecated
	 public boolean isSemanticallyEqual(String pAnnotationConcept, String cAnnotationConcept){
//			 if (isEqual(pAnnotationConcept, cAnnotationConcept))
//				 return true;
//			 if (isEquivalent(pAnnotationConcept, cAnnotationConcept))
//				 return true;
//			 if (isSubClassOf(pAnnotationConcept, cAnnotationConcept))
//				 return true;
	//
//			 if (isEqualProperty(pAnnotationConcept, cAnnotationConcept))
//			 	return true;
//		 	 if (isEquivalentProperty(pAnnotationConcept, cAnnotationConcept))
//			 	return true;
//			 if (isSubPropertyOf(pAnnotationConcept, cAnnotationConcept))
//				 return true;
		 if (isSameIndividual(pAnnotationConcept, cAnnotationConcept)){
			 return true;
		 }

		 return false;
	 }

		

}


