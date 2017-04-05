package uninova.cts.arrowhead.owlmatcher.translations.xsltelements;

import org.jdom2.Namespace;

public class XSLTStyleSheet extends XSLTElement {
	
	private static String name = "stylesheet";
	private static String version = "1.0";

	public XSLTStyleSheet() {
		super(name);
		this.setAttribute("version", version);
	}

	public XSLTStyleSheet(String name, String uri) {
		super(name, uri);
		// TODO Auto-generated constructor stub
	}

	public XSLTStyleSheet(String name, String prefix, String uri) {
		super(name, prefix, uri);
		// TODO Auto-generated constructor stub
	}

}
