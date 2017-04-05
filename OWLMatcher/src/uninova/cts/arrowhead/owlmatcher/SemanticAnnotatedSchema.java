package uninova.cts.arrowhead.owlmatcher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Attribute;
import org.jdom2.JDOMException;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.DOMOutputter;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import antlr.collections.List;
import uninova.cts.arrowhead.owlmatcher.semanticelements.mappings.SemanticMapping;
import uninova.cts.arrowhead.owlmatcher.translations.xsltelements.XSLTValueOf;
import uninova.cts.arrowhead.owlmatcher.utils.JDOM2_DOM_Manipulation;

// class to process a Schema file which has annotations from an ontology
public class SemanticAnnotatedSchema {
	private final String schemaElementTagName = "xs:element";
	private final String schemaAnnotationTagName = "xs:annotation";
	private final String schemaStringTagName = "xs:string";
	private final String semanticAnnotationAttribTag = "sawsdl:modelReference";

	Document doc;

	public String name; 
	public String schemaLocation;
//	public ArrayList<Element> annotatedElements;
	public ArrayList<SemanticAnnotatedElement> annotatedElements; 
	
	public SemanticAnnotatedSchema(){
		annotatedElements = new ArrayList<SemanticAnnotatedElement>();
	}
	
	//read the Schema (XSD) file from a specific location
	public SemanticAnnotatedSchema(String fileLocation){
		this();
		this.schemaLocation = fileLocation;
	}
	
	
	public Element CreateXMLTranslatorFromSchema(Element elemXSD, Element elemXML, ArrayList<SemanticMapping> SemMapTbl){
		JDOM2_DOM_Manipulation Jdom2ToDom = new JDOM2_DOM_Manipulation();
		// process current node
		NodeList elemXSDChilds = elemXSD.getChildNodes();
		for (int i = 0; i<elemXSDChilds.getLength(); i++ )
		{
			String child = elemXSDChilds.item(i).getNodeName();
			// verify if childs exist

			if (child.startsWith("xs:")) {
				Element childXML = null; 
				if (child.equals(schemaElementTagName)) {
					childXML = elemXML.getOwnerDocument().createElement(elemXSDChilds.item(i)
																		.getAttributes()
																		.getNamedItem("name")
																		.getNodeValue());
					 
					if (((Element)elemXSDChilds.item(i)).hasAttribute(semanticAnnotationAttribTag)) {
						// get element from mapping and create an xsl value of element with this content
						for(int k = 0; k<SemMapTbl.size(); k++) {
							String SemMapTblElem = SemMapTbl.get(k).getTargetSemAnnotElemt().getName();
							if (childXML.getNodeName().equals(SemMapTblElem)) {
								XSLTValueOf xsltValueOf = new XSLTValueOf();
								xsltValueOf.setSelect(SemMapTbl.get(k).getTargetSemAnnotElemt().getName());
								try {
									childXML.appendChild(Jdom2ToDom.getDOMElementFromJDOM2(xsltValueOf));
								} catch (DOMException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JDOMException e){
									e.printStackTrace();
								}
							}
						}

						String elementValue = "providerValueToElement"; 
						childXML.setTextContent(elementValue);
					}
					
					elemXML.appendChild(childXML);
				}
				else if (child.equals("xs:attribute"))
				{
					String attValue = "providerValuetoAtt"; 
					// create attribute
					Attr attribTmp = elemXML.getOwnerDocument().createAttribute(elemXSDChilds.item(i)
																.getAttributes()
																.getNamedItem("name")
																.getNodeValue());
					attribTmp.setValue(attValue);
					// insert attribute in current element
					
					elemXML.setAttributeNode(attribTmp);
					
				}
					
	
				if(!child.equals(schemaAnnotationTagName)) {
					if (childXML != null)
						CreateXMLTranslatorFromSchema((Element)elemXSDChilds.item(i), childXML, SemMapTbl);
					else
						CreateXMLTranslatorFromSchema((Element)elemXSDChilds.item(i), elemXML, SemMapTbl);
				}
			}
		}
		return elemXML;
	}
	
	public org.jdom2.Element CreateXMLTranslatorFromSchema(Element elemXSD, org.jdom2.Element elemXML, ArrayList<SemanticMapping> SemMapTbl, boolean jdom){
		JDOM2_DOM_Manipulation Jdom2ToDom = new JDOM2_DOM_Manipulation();
		// process current node
		NodeList elemXSDChilds = elemXSD.getChildNodes();
		for (int i = 0; i<elemXSDChilds.getLength(); i++ )
		{
			String child = elemXSDChilds.item(i).getNodeName();
			// verify if childs exist

			if (child.startsWith("xs:")) {
				org.jdom2.Element childXML = null; 
				if (child.equals(schemaElementTagName)) {
					childXML = new org.jdom2.Element(elemXSDChilds.item(i)
																  .getAttributes()
																  .getNamedItem("name")
																  .getNodeValue());

					if (((Element)elemXSDChilds.item(i)).hasAttribute(semanticAnnotationAttribTag)) {
						// get element from mapping and create an xsl value of element with this content
						for(int k = 0; k<SemMapTbl.size(); k++) {
							String SemMapTblElem = SemMapTbl.get(k).getTargetSemAnnotElemt().getName();
							if (childXML.getName().equals(SemMapTblElem)) {
								XSLTValueOf xsltValueOf = new XSLTValueOf();
//								xsltValueOf.setSelect(SemMapTbl.get(k).getSourceSemAnnotElem().getName());
								xsltValueOf.setSelect(SemMapTbl.get(k).getSourceSemXmlPath());
								try {
									childXML.addContent(xsltValueOf);
								} catch (DOMException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								break;
//							} else {
//								String elementValue = "providerValueToElement"; 
//								childXML.setText(elementValue);
								
							}
							
							
						}
//						String elementValue = "providerValueToElement"; 
//						childXML.setText(elementValue);
					}
					
					elemXML.addContent(childXML);
				}
				else if (child.equals("xs:attribute"))
				{
					String attValue = "providerValuetoAtt"; 
					// create attribute
					org.jdom2.Attribute attribTmp = new Attribute(elemXSDChilds.item(i)
																.getAttributes()
																.getNamedItem("name")
																.getNodeValue(), attValue);
//					attribTmp.setValue(attValue);
					// insert attribute in current element
					
					elemXML.setAttribute(attribTmp);
					
				}
					
	
				if(!child.equals(schemaAnnotationTagName)) {
					if (childXML != null)
						CreateXMLTranslatorFromSchema((Element)elemXSDChilds.item(i), childXML, SemMapTbl, true);
					else
						CreateXMLTranslatorFromSchema((Element)elemXSDChilds.item(i), elemXML, SemMapTbl, true);
				}
			}
		}
		return elemXML;
		
	}
	
	public org.jdom2.Element CreateXMLScheletonFromSchema(org.jdom2.Element rootElemXSD, org.jdom2.Element elemSupportXML, boolean jdom) {
/*
  	private final String schemaElementTagName = "xs:element";
	private final String schemaAnnotationTagName = "xs:annotation";
	private final String schemaStringTagName = "xs:string";
	private final String semanticAnnotationAttribTag = "sawsdl:modelReference";
*/	
		
		
		JDOM2_DOM_Manipulation Jdom2ToDom = new JDOM2_DOM_Manipulation();
		Element rootElem2XSD = null;
		try {
			rootElem2XSD = Jdom2ToDom.getDOMElementFromJDOM2(rootElemXSD);
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (elemSupportXML == null) {
			int rootPosition = 1;
			elemSupportXML = new org.jdom2.Element(rootElem2XSD.getChildNodes().item(rootPosition).getAttributes().getNamedItem("name").getNodeValue()); 
		}
		
		// process current node
		NodeList elemXSDChilds = rootElem2XSD.getChildNodes();
		for (int i = 0; i<elemXSDChilds.getLength(); i++ )
		{
			String childNodeName = elemXSDChilds.item(i).getNodeName();

			// verify if childNodeName is from schema elements
			if (childNodeName.startsWith("xs:")) {
				org.jdom2.Element childXML = null; 
				if (childNodeName.equals(schemaElementTagName)) {
					childXML = new org.jdom2.Element(elemXSDChilds.item(i).getAttributes().getNamedItem("name").getNodeValue());
					elemSupportXML.addContent(childXML);
				}
				else if (childNodeName.equals("xs:attribute"))
				{
					String attValue = "providerValuetoAtt"; 
					// create attribute
					org.jdom2.Attribute attribTmp = new Attribute(elemXSDChilds.item(i).getAttributes().getNamedItem("name").getNodeValue(), attValue);
//					attribTmp.setValue(attValue);
					// insert attribute in current element
					
					elemSupportXML.setAttribute(attribTmp);
				}
	
				if(!childNodeName.equals(schemaAnnotationTagName)) {
					if (childXML != null)
						CreateXMLScheletonFromSchema(Jdom2ToDom.getJDOM2ElementFromDOM((Element)elemXSDChilds.item(i)), childXML, true);
					else
						CreateXMLScheletonFromSchema(Jdom2ToDom.getJDOM2ElementFromDOM((Element)elemXSDChilds.item(i)), elemSupportXML, true);
				}
			}
		}
		return elemSupportXML;
	}

	public NodeList extractNodeList() {
		File xsdFile = new File(schemaLocation);

		//parse the document
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		NodeList list = null, list2 = null, list3 = null;
		
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
//			Document doc;
			doc = docBuilder.parse(xsdFile);
			list = doc.getElementsByTagName(schemaElementTagName);
//			list2 = doc.getElementsByTagName(schemaAnnotationTagName);
//			
//			list3 = appendNodeLists(doc, list2,list3);
			
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e)  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
		
	} 
	
	public Document getDoc(){
		File xsdFile = new File(schemaLocation);

		//parse the document
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
//		NodeList list = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(xsdFile);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e){
			e.printStackTrace();
		} catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return doc;
}

	public org.jdom2.Document getJDOM2Doc(){
		org.jdom2.input.DOMBuilder domInputWorker = new DOMBuilder();
		
		return domInputWorker.build(getDoc());
	}

	
	public void extractSemanticAnnotations(NodeList nl){
			//loop to get elements
			for(int i = 0 ; i < nl.getLength(); i++)
			{
				Element first = (Element)nl.item(i) ;
				SemanticAnnotatedElement firstSemAnEl = new SemanticAnnotatedElement();
				if( first.hasAttribute(semanticAnnotationAttribTag)) {
					String attrib = first.getAttribute(semanticAnnotationAttribTag);
//					semAnnElements.add(first);
					firstSemAnEl.fullSemanticAnnotation = attrib;
					firstSemAnEl.name = first.getAttribute("name");
					firstSemAnEl.type = first.getAttribute("type");
					
					// if element type is a string, must include a schema annotation with its mapping
//					if(firstSemAnEl.type.equals(schemaStringTagName)) {
//						extractSchemaAnnotationMapping(first.getChildNodes());
//					}

					String[] a = firstSemAnEl.splitSemanticAnnotation(attrib);
//					System.out.println(firstSemAnEl.name);
//					System.out.println(firstSemAnEl.type);
//					System.out.println(firstSemAnEl.fullAnnotation);
					annotatedElements.add(firstSemAnEl);
				}
			}		 
	}
	
	private void extractSchemaAnnotationMapping(NodeList nl){
		for(int i = 0 ; i < nl.getLength(); i++)
		{
			Element first = (Element)nl.item(i) ;
			// Element first will be an xs:annotation with childs elements named mapping, 
			// each with an individual and the value of this individual for this xsd 
			
			SemanticAnnotatedElement firstSemAnEl = new SemanticAnnotatedElement();
			
			
			if( first.hasAttribute(semanticAnnotationAttribTag)) {
				String attrib = first.getAttribute(semanticAnnotationAttribTag);
//				semAnnElements.add(first);
				firstSemAnEl.fullSemanticAnnotation = attrib;
				firstSemAnEl.name = first.getAttribute("name");
				firstSemAnEl.type = first.getAttribute("type");
				
				// if element type is a string, must include a schema annotation with its mapping
				if(firstSemAnEl.type.equals(schemaStringTagName)) {
					extractSchemaAnnotationMapping(first.getChildNodes());
				}

				String[] a = firstSemAnEl.splitSemanticAnnotation(attrib);
//				System.out.println(firstSemAnEl.name);
//				System.out.println(firstSemAnEl.type);
//				System.out.println(firstSemAnEl.fullAnnotation);
				annotatedElements.add(firstSemAnEl);
			}
		}		 
	}
	
	
	public static NodeList appendNodeLists(Document doc, NodeList a, NodeList b) {
        Element root = (Element)doc.getFirstChild(); 
		
		for (int i = 0; i < a.getLength(); i++)
			root.appendChild(a.item(i));
		
		for (int i = 0; i < b.getLength(); i++) 
			root.appendChild(b.item(i));
		
		return root.getChildNodes();
	}
	
	public String getElementXmlPath(org.jdom2.Element currentElement, String elementNameToFind, String currentXmlPath){
		String fullXmlPath = currentXmlPath;
		if (elementNameToFind.equals(currentElement.getAttributeValue("name"))) {
			fullXmlPath = currentXmlPath  + currentElement.getAttributeValue("name")+ "/";
		} else {
			if ( currentElement.getChildren().isEmpty() ) {
				if (currentElement.getName().equals(schemaElementTagName))
					fullXmlPath = currentXmlPath;
			} else {
				if (currentElement.getQualifiedName().equals(schemaElementTagName)) {
					fullXmlPath = currentXmlPath  + currentElement.getAttributeValue("name") + "/";
				}
				for (int k = 0; k<currentElement.getChildren().size(); k++) {
					String initialFullXmlPath = fullXmlPath;
					fullXmlPath = getElementXmlPath((org.jdom2.Element)currentElement.getChildren().get(k), elementNameToFind, fullXmlPath);
					if (!fullXmlPath.equals(initialFullXmlPath)) {
						break;
					}
				}	
			}
		}
		return fullXmlPath;
	}
}
