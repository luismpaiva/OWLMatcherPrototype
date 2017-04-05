package uninova.cts.arrowhead.owlmatcher.translations;

import java.util.ArrayList;

import org.jdom2.*;

import uninova.cts.arrowhead.owlmatcher.semanticelements.mappings.SemanticMapping;
import uninova.cts.arrowhead.owlmatcher.translations.xsltelements.*;

public class XSLTransformationsFactory {
	private XSLTDocument XSLTdocument;
	private Namespace xslNS;
	
	public XSLTransformationsFactory() {
		XSLTdocument = new XSLTDocument();
		xslNS = Namespace.getNamespace("xsl", "http://www.w3.org/1999/XSL/Transform");
	}
	
	public XSLTDocument GenerateXSLT(String content, ArrayList<SemanticMapping> mapTable) {
		
		XSLTStyleSheet xslStylesheet = new XSLTStyleSheet();
		XSLTdocument.setRootElement(xslStylesheet);
		
		xslStylesheet.addContent(createXSLTContent(mapTable));

		return XSLTdocument;
	}
	
	public XSLTElement createXSLTContent(ArrayList<SemanticMapping> mapTable){
		XSLTTemplate xslTemplate = new XSLTTemplate();
		xslTemplate.setMatch("/");
		
/*
 * 	<xsl:template match="/">
		<data> (rootConsumerElement)
			<sensortemp> (rootConsumerElement/Child1)       
				<xsl:value-of select="/values/indoorTemp" /> (rootConsumerElement/Child1/Child1ofChild1)       
			</sensortemp>

			<units> (rootConsumerElement/Child2)       
				<xsl:value-of select="$consumerValueUnits" /> (rootConsumerElement/Child2/Child2ofChild2)     
			</units>
		</data>
	</xsl:template>	


 * 
 * 
 * */		
		org.jdom2.output.DOMOutputter xs;
		
		String rootElementName = "data"; 
		Element rootElement = new Element(rootElementName);
		xslTemplate.addContent(rootElement);
		
//		Element sensor1temp = new Element("sensor1temp");
//		datavalues.addContent(sensor1temp);
		
//		XSLTValueOf xsltValueOf1 = new XSLTValueOf();
//		xsltValueOf1.setSelect("/datavalues/indoorTemp");
		
//		sensor1temp.addContent(xsltValueOf1);
		
		for(int i = 0; i<mapTable.size(); i++) {
			Element targetMapping = new Element(mapTable.get(i).getTargetSemAnnotElemt().getName());
			XSLTValueOf xsltValueOf = new XSLTValueOf();
			xsltValueOf.setSelect("/"+rootElementName+"/"+mapTable.get(i).getSourceSemAnnotElem().getName());
			targetMapping.addContent(xsltValueOf);

			rootElement.addContent(targetMapping);
		} 
		
		return xslTemplate;
	}
	
	public void toFile(String location, XSLTDocument xsltDoc){
		System.out.println("--------------------- Saving Translator to file ("+"finalXSLT"+")----------------------");
		XSLTFileFactory xsltFile = new XSLTFileFactory(location,"finalXSLT");
		xsltFile.saveToFile(xsltDoc, false);
		System.out.println("--------------------- Finished saving Translator to file ----------------------");
	}
	
	public void toFile(String location, XSLTDocument xsltDoc, String targetName){
		System.out.println("--------------------- Saving Translator to file ("+targetName+")----------------------");
		XSLTFileFactory xsltFile = new XSLTFileFactory(location,targetName);
		xsltFile.saveToFile(xsltDoc, targetName, false);
		System.out.println("--------------------- Finished saving Translator to file ----------------------");
	}
	

	
	public static void main(String [] args) {
		XSLTransformationsFactory x = new XSLTransformationsFactory();
//		x.toFile("",x.GenerateXSLT(""));
		
	}
}
