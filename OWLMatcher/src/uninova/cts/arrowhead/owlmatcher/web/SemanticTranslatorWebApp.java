package uninova.cts.arrowhead.owlmatcher.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import uninova.cts.arrowhead.owlmatcher.SemanticEngine;

public class SemanticTranslatorWebApp {

	public SemanticTranslatorWebApp() {
		// TODO Auto-generated constructor stub
	}

	private ArrayList<String> readFile(String filename)
	{
	  ArrayList<String> records = new ArrayList<String>();
	  try
	  {
	    BufferedReader reader = new BufferedReader(new FileReader(filename));
	    String line;
	    while ((line = reader.readLine()) != null)
	    {
	      records.add(line);
	    }
	    reader.close();
	    return records;
	  }
	  catch (Exception e)
	  {
	    System.err.format("Exception occurred trying to read '%s'.", filename);
	    e.printStackTrace();
	    return null;
	  }
	}

	public static void main(String[] args) {
		
		String s = "tempsensor.owl";
		SemanticEngine semEngine = new SemanticEngine(s);
		System.out.println("args are: " + args.length);
		
		/* 
		 * args[0] - operation chooser
		 * args[1] - provider xsd location
		 * args[2] - consumer xsd location
		 * 
		 */
		
		
		if (args.length != 0) {
			int operation = Integer.parseInt(args[0]);
			switch(operation) {
			// full semantic translation
			case 1:break;
			// get map for xsd concepts
			case 2:break;
			// get XSLT
			case 3:break;
			// get translated XML
			// args 1 - must be provider XML message
			// args 2 - must be consumer XML target message
			// 
			case 4: System.out.println("(Java) Operation 4 - Get Translated XML");
				semEngine.generateTranslation(args[1], args[2], "targetXSLT.xslt");
				break;
			// get xml from xsd
			case 5:break;
			default:break;
			}
		}
		
		if (args.length != 0) {
			semEngine.setProviderSchemaLocation(args[1]);
			semEngine.setConsumerSchemaLocation(args[2]);
		} 
		
		// args will receive two filelocations:
		// 1 - provider xsd file
		// 2 - consumer xsd file
		System.out.println("Arguments received: ");
		for (int i=0; i<args.length; i++) {
			System.out.println("arg["+i+"]: "+args[i].toString());
		}
		System.out.println("Arguments received ok!");
		
		// Start Engine - receives 2 XSDs and the ontology
		if (args.length != 0)
			semEngine.printSemanticAnnotatedElements(args[1], args[2], s);
		else {
//			s = ;
			semEngine.printSemanticAnnotatedElements(semEngine.getProviderSchemaLocation(), semEngine.getConsumerSchemaLocation(), "Ontologies//tempsensor.owl");
		}
				
		
		
		
	}

}
