package uninova.cts.arrowhead.owlmatcher.translations;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.XMLFormatter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import uninova.cts.arrowhead.owlmatcher.translations.xsltelements.XSLTDocument;

public class XSLTTransformation {
	
	private Document _source;
	private Document _target;
	private String _sourceFileName = "genericSourceTransform.xml";
	private String _targetFileName = "genericTargetTransform.xml";
	private XSLTDocument _transformation;
	private String _translationFileName;
	private String _xsltFileExtension = ".xslt";
	
	public XSLTTransformation() {
		_source = new Document();
		_target = new Document();
		_transformation = new XSLTDocument();

	}
	
	public XSLTTransformation(String sourceLocation, String targetLocation, XSLTDocument transformation) {
		_sourceFileName = sourceLocation;
		_targetFileName = targetLocation;
		_transformation = transformation;
		_source = new Document();
		_target = new Document();
	}
	
	public void TransformXMLintoXML() {
		_target = this.XMLtoXML(_transformation);
	}
	
	public void TransformXMLintoXML(XSLTDocument transformation) {
		_target = this.XMLtoXML(transformation);
	}
	
	public void TransformXMLintoXML(String sourceXMLFileName, String targetXMLFileName, String translationXMLFileName) {
		this.XMLtoXML(sourceXMLFileName, targetXMLFileName, translationXMLFileName);
	}
	
	public void XMLtoXML(String sourceFilename, String targetFilename, String translationFileName) {
	    TransformerFactory factory = TransformerFactory.newInstance();
	    
	    System.out.println("XMLtoXML -> Source:" + sourceFilename + "; target: " + targetFilename + "; translation:" + translationFileName);

	    StreamSource xslt = new StreamSource(new File(translationFileName+
	    		(translationFileName.contains(_xsltFileExtension)?"":_xsltFileExtension)));
	    System.out.println("XMLtoXML -> After Stream source:"+translationFileName+
	    		(translationFileName.contains(_xsltFileExtension)?"":_xsltFileExtension));
	    Transformer transformer = null;

		try {
			transformer = factory.newTransformer(xslt);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}

	    System.out.println("XMLtoXML -> After Transformation");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    System.out.println("XMLtoXML -> After Transformation");
		
		StreamSource text = new StreamSource(new File(sourceFilename));

	    System.out.println("XMLtoXML -> After sourcefile streamsource");
        try {
			System.out.println("-------------------- Printing(&saving) translated message -------------------------- ");
			transformer.transform(text, new StreamResult(new File(targetFilename)));
			transformer.transform(text, new StreamResult(System.out));
			System.out.println("-------------------- Finished Printing(&saving) translated message -------------------------- ");
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Document XMLtoXML(XSLTDocument transformation)  {
        TransformerFactory factory = TransformerFactory.newInstance();
//        File targetXsltfile = new File("targetXSLT.xslt");
        StreamSource xslt = new StreamSource(new File("targetXSLT.xslt"));
        Transformer transformer = null;

		try {
			transformer = factory.newTransformer(xslt);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}

		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
        StreamSource text = new StreamSource(new File("Xsd_Xml/provider.xml"));

        try {
			System.out.println("-------------------- Printing(&saving) translated message -------------------------- ");
			transformer.transform(text, new StreamResult(new File("output.xml")));
			transformer.transform(text, new StreamResult(System.out));
			System.out.println("-------------------- Finished Printing(&saving) translated message -------------------------- ");
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return null;
	}
	
	public void printFormat(Document doc){
		XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
//		XmlFormatter x = new XmlFormatter().format(unformattedXml);
		xmlOutputter.outputString(doc);
	}
	
	public void printFormat(Document doc, String fileLocation){
//        StreamSource text = new StreamSource(new File("Xsd_Xml\\tempSensorProvider.xml"));
        StreamSource text = new StreamSource(new File("Xsd_Xml/tempSensorProvider.xml"));
        StreamResult str = new StreamResult(new File(fileLocation));
        Transformer transformer = null;
		try {
			transformer.transform(text, str);
		} catch (TransformerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
//		XmlFormatter x = new XmlFormatter().format(unformattedXml);
		try {
			xmlOutputter.output(doc, str.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException{
		XSLTTransformation transformation = new XSLTTransformation();
		
		transformation.XMLtoXML(new XSLTDocument());
	}
	
}
