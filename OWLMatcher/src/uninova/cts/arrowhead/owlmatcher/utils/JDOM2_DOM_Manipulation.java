package uninova.cts.arrowhead.owlmatcher.utils;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.DOMBuilder;
import org.jdom2.output.DOMOutputter;


public class JDOM2_DOM_Manipulation {
	org.jdom2.input.DOMBuilder domInputWorker = new DOMBuilder();
	org.jdom2.output.DOMOutputter domOutputWorker = new DOMOutputter();

	public JDOM2_DOM_Manipulation() {
		// TODO Auto-generated constructor stub
	}
	
	public org.jdom2.Document getJDOM2DocumentFromDOM(org.w3c.dom.Document doc){
//		org.jdom2.input.DOMBuilder domInputWorker = new DOMBuilder();
		
		return domInputWorker.build(doc);
	}

	public org.jdom2.Element getJDOM2ElementFromDOM(org.w3c.dom.Element elem){
//		org.jdom2.input.DOMBuilder domInputWorker = new DOMBuilder();
		org.jdom2.Element localElem = new Element("test");
	
		return domInputWorker.build(elem);
	}
	
	public org.jdom2.Attribute getJDOM2AttributeFromDOM(org.w3c.dom.Attr attr){
//		org.jdom2.input.DOMBuilder domInputWorker = new DOMBuilder();
		
		String attribLocalName = attr.getLocalName();
		String attribNodeName = attr.getNodeName();
		String attribName = attr.getName();
		String attribValue = attr.getValue(); 
		String attribNodeValue = attr.getNodeValue(); 
		
		
		return new org.jdom2.Attribute(attribName, attribValue);
	}
	
	public org.w3c.dom.Element getDOMElementFromJDOM2(org.jdom2.Element elem) throws JDOMException{
//		org.jdom2.input.DOMBuilder domInputWorker = new DOMBuilder();
//		org.w3c.dom.Element localElem = new Element("test");
	
		return domOutputWorker.output(elem);
	}
	

	

}
