package uninova.cts.arrowhead.owlmatcher.translations.xsltelements;

import org.jdom2.Namespace;

public class XSLTTemplate extends XSLTElement {
	
	private String _match = "/";
	private static String name = "template";

	public XSLTTemplate() {
		super(name);
		// TODO Auto-generated constructor stub
		this.setAttribute("match", _match);
	}

	public XSLTTemplate(String name, String uri) {
		super(name, uri);
		// TODO Auto-generated constructor stub
	}

	public XSLTTemplate(String name, String prefix, String uri) {
		super(name, prefix, uri);
		// TODO Auto-generated constructor stub
	}

	public String getMatch() {
		return _match;
	}

	public void setMatch(String matchValue) {
		this._match = matchValue;
		this.setAttribute("match", matchValue);
	}

}
