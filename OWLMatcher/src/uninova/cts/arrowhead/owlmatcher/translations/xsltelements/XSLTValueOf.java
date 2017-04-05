package uninova.cts.arrowhead.owlmatcher.translations.xsltelements;

import org.jdom2.Namespace;

public class XSLTValueOf extends XSLTElement {
	
	private static String name = "value-of";
	private String _select = "";

	public XSLTValueOf() {
		super(name);
	}

	public XSLTValueOf(String name, String uri) {
		super(name, uri);
		// TODO Auto-generated constructor stub
	}

	public XSLTValueOf(String name, String prefix, String uri) {
		super(name, prefix, uri);
		// TODO Auto-generated constructor stub
	}

	public String getSelect() {
		return _select;
	}

	public void setSelect(String selectValue) {
		this._select = selectValue;
		this.setAttribute("select", selectValue);
	}

}
