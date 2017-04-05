package uninova.cts.arrowhead.owlmatcher.translations.xsltelements;

import org.jdom2.Element;
import org.jdom2.Namespace;

public class XSLTElement extends Element {
	
	private static Namespace xsltNamespace = Namespace.getNamespace("xsl", "http://www.w3.org/1999/XSL/Transform");
	
	public XSLTElement() {
		// TODO Auto-generated constructor stub
	}

	public XSLTElement(String name) {
		super(name, xsltNamespace);
		// TODO Auto-generated constructor stub
	}

	public XSLTElement(String name, Namespace namespace) {
		super(name, namespace);
		// TODO Auto-generated constructor stub
	}

	public XSLTElement(String name, String uri) {
		super(name, uri);
		// TODO Auto-generated constructor stub
	}

	public XSLTElement(String name, String prefix, String uri) {
		super(name, prefix, uri);
		// TODO Auto-generated constructor stub
	}

}
