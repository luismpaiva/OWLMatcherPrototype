package uninova.cts.arrowhead.owlmatcher.translations.xsltelements;

import org.jdom2.Namespace;

public class XSLTForEach extends XSLTElement {
	
	private static String name = "for-each";
	private String _select = "";

	public XSLTForEach() {
		super(name);
	}

	public XSLTForEach(String name, String uri) {
		super(name, uri);
		// TODO Auto-generated constructor stub
	}

	public XSLTForEach(String name, String prefix, String uri) {
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
